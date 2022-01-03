package tech.pinto.catel.util;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import tech.pinto.catel.order.OrderState;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.order.RepoOrder;
import tech.pinto.catel.room.RepoRoomConfig;
import tech.pinto.catel.room.RepoRoomUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.pinto.catel.user.RepoCreditEntry;
import tech.pinto.catel.user.RepoUser;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Slf4j
public class Task {

    private final Initiator initiator;
    private final RepoOrder repoOrder;
    private final RepoCreditEntry repoCreditEntry;
    private final RepoRoomConfig repoRoomConfig;
    private final RepoRoomUnit repoRoomUnit;
    private final RepoHotel repoHotel;
    private final RepoUser repoUser;
    private final MapX mapX;

    @Value("${custom.reserve.ahead-limit}")
    private int aheadLimit;

    @Autowired
    public Task(Initiator initiator, RepoOrder repoOrder, RepoCreditEntry repoCreditEntry, RepoRoomConfig repoRoomConfig, RepoRoomUnit repoRoomUnit, RepoHotel repoHotel, RepoUser repoUser, MapX mapX) {
        this.initiator = initiator;
        this.repoOrder = repoOrder;
        this.repoCreditEntry = repoCreditEntry;
        this.repoRoomConfig = repoRoomConfig;
        this.repoRoomUnit = repoRoomUnit;
        this.repoHotel = repoHotel;
        this.repoUser = repoUser;
        this.mapX = mapX;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void changeOrderState() {
        var outdated = repoOrder.outdated();
        var entries = outdated.stream().map(mapX::toEntry).collect(Collectors.toList());
        outdated.forEach(o -> o.setOrderState(OrderState.Finished));
        repoCreditEntry.saveAll(entries);
        repoOrder.saveAll(outdated);
        log.info("[task] Finish outdated orders");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void invalidVip() {
        repoUser.vipExpire();
        log.info("[task] Expire outdated VIPs");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void freshCommentStat() {
        repoHotel.freshRate();
        repoHotel.freshScoreCount();
        log.info("[task] Calculate rate for room");
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void refreshRoomUnit() {
        repoRoomUnit.dailyUpdateRemove();
        var units = repoRoomConfig.findAll().stream().map(mapX::toUnit).collect(Collectors.toList());
        var comingDay = LocalDate.now().plusDays(aheadLimit);
        units.forEach(u -> u.getId().setDate(comingDay));
        repoRoomUnit.saveAll(units);
        log.info("[task] update room unit");
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("[task] init start!");
        System.out.println(aheadLimit);
        var loggerName = "org.hibernate.SQL";
        var logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(Level.OFF);
        initiator.init();
        freshCommentStat();
        changeOrderState();
        invalidVip();
//        logger.setLevel(Level.DEBUG);
    }

}
