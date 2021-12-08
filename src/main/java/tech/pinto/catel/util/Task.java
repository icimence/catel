package tech.pinto.catel.util;

import tech.pinto.catel.room.MapperRoom;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.room.Room;
import tech.pinto.catel.order.MapperOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableScheduling
@Component
@Slf4j
public class Task {

    private final MapperOrder mapperOrder;
    private final AccountMapper accountMapper;
    private final MapperRoom mapperRoom;
    private final Initiator initiator;

    @Autowired
    public Task(MapperOrder mapperOrder, AccountMapper accountMapper, MapperRoom mapperRoom, Initiator initiator) {
        this.mapperOrder = mapperOrder;
        this.accountMapper = accountMapper;
        this.mapperRoom = mapperRoom;
        this.initiator = initiator;
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void updateOrder() {
        mapperOrder.finish();
        log.info("Update orders Successfully!");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateScore() {
        mapperOrder.rate();
        log.info("Update scores Successfully!");
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void updateVip() {
        accountMapper.vipExpire();
        log.info("Expire VIPs Successfully!");
    }

    @Scheduled(cron = "55 59 23 * * ?")
    public void updateRoom() {
        List<Room> rooms = mapperRoom.getAll();
        for (Room room : rooms) {
            mapperRoom.updateRoomNumber(room);
        }
        log.info("Update room numbers Successfully!");
    }

    @PostConstruct
    public void init() {
        initiator.init();
    }

}
