package tech.pinto.catel.util;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import tech.pinto.catel.enums.OrderState;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.order.RepoOrder;
import tech.pinto.catel.room.RepoRoomConfig;
import tech.pinto.catel.room.RepoRoomUnit;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.order.MapperOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.pinto.catel.user.RepoCreditEntry;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Slf4j
public class Task {

    private final MapperOrder mapperOrder;
    private final AccountMapper accountMapper;
    private final Initiator initiator;
    private final RepoOrder repoOrder;
    private final RepoCreditEntry repoCreditEntry;
    private final RepoRoomConfig repoRoomConfig;
    private final RepoRoomUnit repoRoomUnit;
    private final RepoHotel repoHotel;
    private final MapX mapX;

    @Autowired
    public Task(MapperOrder mapperOrder, AccountMapper accountMapper, Initiator initiator, RepoOrder repoOrder, RepoCreditEntry repoCreditEntry, RepoRoomConfig repoRoomConfig, RepoRoomUnit repoRoomUnit, RepoHotel repoHotel, MapX mapX) {
        this.mapperOrder = mapperOrder;
        this.accountMapper = accountMapper;
        this.initiator = initiator;
        this.repoOrder = repoOrder;
        this.repoCreditEntry = repoCreditEntry;
        this.repoRoomConfig = repoRoomConfig;
        this.repoRoomUnit = repoRoomUnit;
        this.repoHotel = repoHotel;
        this.mapX = mapX;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void changeOrderState() {
        var outdated = repoOrder.outdated();
        var entries = outdated.stream().map(mapX::toEntry).collect(Collectors.toList());
        outdated.forEach(System.out::println);
        entries.forEach(System.out::println);
        outdated.forEach(o -> o.setOrderState(OrderState.Finished));
        repoCreditEntry.saveAll(entries);
        repoOrder.saveAll(outdated);
        log.info("[task] Finish outdated orders");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void invalidVip() {
        accountMapper.vipExpire();
        log.info("[task] Expire outdated VIPs");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void freshRate() {
        mapperOrder.rate();
        repoHotel.freshRate();
        log.info("[task] Calculate rate for room");
    }

    @Scheduled(cron = "0 0 14 * * ?")
    public void refreshRoomUnit() {
        repoRoomUnit.dailyUpdateRemove();
        var units = repoRoomConfig.findAll().stream().map(mapX::toUnit).collect(Collectors.toList());
        var comingDay = LocalDate.now().plusDays(15);
        units.forEach(u -> u.getId().setDate(comingDay));
        units.forEach(System.out::println);
        repoRoomUnit.saveAll(units);
        log.info("[task] update room unit");
    }

    @PostConstruct
    public void init() {
        var loggerName = "org.hibernate.SQL";
        var logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(Level.OFF);
        initiator.init();
        freshRate();
        changeOrderState();
        invalidVip();
        refreshRoomUnit();
        logger.setLevel(Level.DEBUG);
    }

}
