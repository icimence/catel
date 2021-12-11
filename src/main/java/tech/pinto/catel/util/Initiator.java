package tech.pinto.catel.util;

import org.springframework.stereotype.Service;
import tech.pinto.catel.comment.CommentService;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.domain.*;
import tech.pinto.catel.enums.BizRegion;
import tech.pinto.catel.enums.HotelStar;
import tech.pinto.catel.enums.RoomType;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.order.OrderService;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.room.*;
import tech.pinto.catel.user.RepoUser;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class Initiator {
    private final RepoHotel repoHotel;
    private final RepoUser repoUser;
    private final RepoRoomConfig repoRoomConfig;
    private final RepoRoom repoRoom;
    private final RepoRoomUnit repoRoomUnit;
    private final OrderService orderService;
    private final CommentService commentService;
    private final EntityManagerFactory entityManagerFactory;

    private final int numOfHotel = 30;
    private final int numOfConfig = 5;
    private final int numOfRoom = 15;
    private final int numOfDay = 15;
    private final int numOfUser = 10;
    private final int numberOfResident = 3;
    private final int numberOfOrder = 200;
    private final int numberOfComment = 100;

    private final LocalDate today = LocalDate.now();

    public Initiator(RepoHotel repoHotel, RepoUser repoUser, RepoRoomConfig repoRoomConfig, RepoRoom repoRoom, RepoRoomUnit repoRoomUnit, OrderService orderService, CommentService commentService, EntityManagerFactory entityManagerFactory) {
        this.repoHotel = repoHotel;
        this.repoUser = repoUser;
        this.repoRoomConfig = repoRoomConfig;
        this.repoRoom = repoRoom;
        this.repoRoomUnit = repoRoomUnit;
        this.orderService = orderService;
        this.commentService = commentService;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void init() {
        initHotelItem();
        initUserItem();
        initOrderItem();
        initComment();
        System.out.println("[task] database init");
    }

    private void initComment() {

        for (int i = 0; i < numberOfComment; i++) {
            var dto = new DtoPublishComment();
            dto.setScore(UtilRandom.ofInt(1, 6));
            dto.setContent("随机的评论内容容容容容容容容容容容容容容容容容容容容容容容容容容容容容容容容容！");
            dto.setTitle("随机的标题！");
            dto.setOrderId(Math.floorDiv(numberOfOrder, numberOfComment) * i + 1);
            commentService.comment(dto);
        }
    }

    private void initHotelItem() {

        var hotels = new ArrayList<Hotel>();
        var roomConfigs = new ArrayList<RoomConfig>();
        var rooms = new ArrayList<Room>();
        var units = new ArrayList<RoomUnit>();

        for (int i = 0; i < numOfHotel; i++) {
            var name = i + "号酒店";
            var address = name + "的地址";
            var bizRegion = UtilRandom.ofEnum(BizRegion.class);
            var star = UtilRandom.ofEnum(HotelStar.class);
            var desc = name + "的描述";
            var announcement = name + "的通知";
            var phone = UtilRandom.ofPhoneNumber();
            var hotel = new Hotel(name, address, bizRegion, star, desc, phone, null, 0, announcement);
            hotels.add(hotel);

            for (int j = 0; j < numOfConfig; j++) {
                var configName = j + "类房间";
                var type = UtilRandom.ofEnum(RoomType.class);
                var price = BigDecimal.valueOf(UtilRandom.ofInt(150, 300));
                var roomConfig = new RoomConfig(hotel, configName, 2, true, type, price);
                roomConfigs.add(roomConfig);

                for (int k = 0; k < numOfRoom; k++) {
                    var roomNo = "#0" + j + k;
                    var room = new Room(roomNo, hotel, roomConfig);
                    rooms.add(room);
                }

                for (int k = 0; k < numOfDay; k++) {
                    var unit = new RoomUnit(roomConfig, today.plusDays(k), price, numOfRoom);
                    units.add(unit);
                }

            }
        }

        repoHotel.saveAll(hotels);
        repoRoomConfig.saveAll(roomConfigs);
        repoRoom.saveAll(rooms);
        repoRoomUnit.saveAll(units);
        repoHotel.refreshMinPrice();
    }

    private void initUserItem() {

        var users = new ArrayList<User>();
        for (int i = 0; i < numOfUser; i++) {
            var name = "user" + i;
            var email = name + "@qq.com";
            var password = "x";
            var user = new User(email, password, name);
            users.add(user);
            for (int j = 0; j < numberOfResident; j++) {
                var realName = "真名" + j;
                var idNo = UtilRandom.ofIdNo();
                var phoneNumber = UtilRandom.ofPhoneNumber();
                var birthday = UtilRandom.ofDate();
                var resident = new Resident(realName, idNo, phoneNumber, birthday, user);
                user.getResidents().add(resident);
            }
        }

        repoUser.saveAll(users);
    }

    protected void initOrderItem() {
        for (int i = 0; i < numberOfOrder; i++) {

            var dto = new DtoReservePersonal();

            var hotelId = UtilRandom.ofLong(1, 1 + numOfHotel);
            var userId = UtilRandom.ofLong(1, 1 + numOfUser);
            dto.setUserId(userId);
            dto.setHotelId(hotelId);
            var user = repoUser.getById(userId);
            var hotel = repoHotel.getById(hotelId);

            var inOffset = UtilRandom.ofLong(1, 8);
            var duration = UtilRandom.ofLong(1, 5);
            var inDate = today.plusDays(inOffset);
            var outDate = inDate.plusDays(duration);
            dto.setCheckInDate(inDate.format(UtilDate.formatter));
            dto.setCheckOutDate(outDate.format(UtilDate.formatter));

            var configIdx = UtilRandom.ofInt(0, numOfConfig);
            var config = hotel.getConfigs().get(configIdx);
            dto.setConfigId(config.getId());

            var residentsId = new HashSet<Long>();
            do {
                var index = UtilRandom.ofInt(0, numberOfResident);
                var resident = user.getResidents().get(index);
                residentsId.add(resident.getId());
            } while (UtilRandom.ofBool(3));
            dto.setResidents(new ArrayList<>(residentsId));

            try {
                orderService.reserve(dto);
            } catch (OopsException e) {
                i--; // Retry
            }
        }
    }

}
