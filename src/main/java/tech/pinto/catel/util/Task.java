package tech.pinto.catel.util;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import tech.pinto.catel.enums.OrderState;
import tech.pinto.catel.order.RepoOrder;
import tech.pinto.catel.room.MapperRoom;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.domain.Room;
import tech.pinto.catel.order.MapperOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.pinto.catel.user.RepoCreditEntry;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Slf4j
public class Task {

    private final MapperOrder mapperOrder;
    private final AccountMapper accountMapper;
    private final MapperRoom mapperRoom;
    private final Initiator initiator;
    private final RepoOrder repoOrder;
    private final RepoCreditEntry repoCreditEntry;
    private final MapX mapX;

    @Autowired
    public Task(MapperOrder mapperOrder, AccountMapper accountMapper, MapperRoom mapperRoom, Initiator initiator, RepoOrder repoOrder, RepoCreditEntry repoCreditEntry, MapX mapX) {
        this.mapperOrder = mapperOrder;
        this.accountMapper = accountMapper;
        this.mapperRoom = mapperRoom;
        this.initiator = initiator;
        this.repoOrder = repoOrder;
        this.repoCreditEntry = repoCreditEntry;
        this.mapX = mapX;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateOrder() {
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
    public void updateVip() {
        accountMapper.vipExpire();
        log.info("[task] Expire outdated VIPs");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateScore() {
        mapperOrder.rate();
        log.info("[task] Calculate rate for room");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoom() {
        List<Room> rooms = mapperRoom.getAll();
        for (Room room : rooms) {
            mapperRoom.updateRoomNumber(room);
        }
        log.info("Update room numbers Successfully!");
    }

    @PostConstruct
    public void init() {
        var loggerName = "org.hibernate.SQL";
        var logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(Level.OFF);
        initiator.init();
        logger.setLevel(Level.DEBUG);
    }

}
