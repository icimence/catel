package com.example.hotel.util;

import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.model.Room;
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

    private final OrderMapper orderMapper;
    private final AccountMapper accountMapper;
    private final RoomMapper roomMapper;
    private final TestInit testInit;

    @Autowired
    public Task(OrderMapper orderMapper, AccountMapper accountMapper, RoomMapper roomMapper, TestInit testInit) {
        this.orderMapper = orderMapper;
        this.accountMapper = accountMapper;
        this.roomMapper = roomMapper;
        this.testInit = testInit;
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void updateOrder() {
        orderMapper.finish();
        log.info("Update orders Successfully!");
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void updateScore() {
        orderMapper.rate();
        log.info("Update scores Successfully!");
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void updateVip() {
        accountMapper.vipExpire();
        log.info("Expire VIPs Successfully!");
    }

    @Scheduled(cron = "55 59 23 * * ?")
    public void updateRoom() {
        List<Room> rooms = roomMapper.getAll();
        for (Room room : rooms) {
            roomMapper.updateRoomNumber(room);
        }
        log.info("Update room numbers Successfully!");
    }

    @PostConstruct
    public void init() {
        testInit.testInit();
    }

}
