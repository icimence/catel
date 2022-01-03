package tech.pinto.catel.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tech.pinto.catel.comment.CommentService;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.coupon.RepoCoupon;
import tech.pinto.catel.domain.*;
import tech.pinto.catel.hotel.BizRegion;
import tech.pinto.catel.hotel.HotelStar;
import tech.pinto.catel.room.RoomType;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.order.OrderService;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.room.*;
import tech.pinto.catel.user.RepoUser;
import tech.pinto.catel.util.error.OopsException;

import java.io.InputStreamReader;
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
    private final RepoCoupon repoCoupon;
    private final OrderService orderService;
    private final CommentService commentService;

    private final String hotelCsvPath = "/csv/人机酒店信息.csv";
    private final String roomConfigCsvPath = "/csv/人机酒店房间信息.csv";
    private int numOfHotel;
    private final int numOfRoomBase = 15;
    private final int numOfDay = 15;
    private final int numOfUser = 10;
    private final int numberOfResident = 3;
    private final int numberOfOrder = 200;
    private final int numberOfComment = 100;
    private final int numberOfCoupon = 30;

    private final HashMap<Long, Integer> numOfConfig = new HashMap<>();

    private final LocalDate today = LocalDate.now();

    public Initiator(RepoHotel repoHotel, RepoUser repoUser, RepoRoomConfig repoRoomConfig, RepoRoom repoRoom, RepoRoomUnit repoRoomUnit, RepoCoupon repoCoupon, OrderService orderService, CommentService commentService) {
        this.repoHotel = repoHotel;
        this.repoUser = repoUser;
        this.repoRoomConfig = repoRoomConfig;
        this.repoRoom = repoRoom;
        this.repoRoomUnit = repoRoomUnit;
        this.repoCoupon = repoCoupon;
        this.orderService = orderService;
        this.commentService = commentService;
    }

    public void init() throws Exception {
        initUserItem();
        initHotelItem();
        initOrderItem();
        initComment();
        initCoupon();
        System.out.println("[task] database init");
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

    private void initHotelItem() throws Exception {

        var hotels = new ArrayList<Hotel>();
        var roomConfigs = new ArrayList<RoomConfig>();
        var rooms = new ArrayList<Room>();
        var units = new ArrayList<RoomUnit>();

        List<CsvHotel> csvHotels;
        var is = new ClassPathResource(hotelCsvPath).getInputStream();
        try (var reader = new InputStreamReader(is)) {
            csvHotels = new CsvToBeanBuilder<CsvHotel>(reader).withType(CsvHotel.class).build().parse();
        }
        numOfHotel = csvHotels.size();
        csvHotels.forEach(System.out::println);

        List<CsvRoomConfig> csvRoomConfigs;
        is = new ClassPathResource(roomConfigCsvPath).getInputStream();
        try (var reader = new InputStreamReader(is)) {
            csvRoomConfigs = new CsvToBeanBuilder<CsvRoomConfig>(reader).withType(CsvRoomConfig.class).build().parse();
        }

        var name2hotel = new HashMap<String, Hotel>();

        for (var csvHotel : csvHotels) {
            var name = csvHotel.getName();
            var address = csvHotel.getAddress();
            var bizStr = csvHotel.getAddress().split("，")[3];
            bizStr = bizStr.substring(0, bizStr.length() - 1);
            var bizRegion = BizRegion.from(bizStr);
            var star = HotelStar.from(csvHotel.getStar());
            var desc = csvHotel.getDesc();
            var announcement = "有优惠券可用，欢迎入住！";
            var phone = UtilRandom.ofPhoneNumber();
            var hotel = new Hotel(name, address, bizRegion, star, desc, phone, 0, announcement);
            hotels.add(hotel);
            name2hotel.put(name, hotel);
        }

        repoHotel.saveAll(hotels);

        for (var csvRoomConfig : csvRoomConfigs) {
            var hotel = name2hotel.get(csvRoomConfig.getHotelName());
            if (hotel == null) {
                System.err.println(csvRoomConfig.getHotelName());
            }
            numOfConfig.put(hotel.getId(),
                            numOfConfig.getOrDefault(hotel.getId(), 0) + 1);

            var configName = csvRoomConfig.getConfigName();
            var peopleLimit = csvRoomConfig.getPeopleLimit();
            var type = RoomType.fromName(configName);
            var price = BigDecimal.valueOf(csvRoomConfig.getPrice());
            var breakfast = csvRoomConfig.getBreakfast().equals("有早餐");
            var roomNumOffset = UtilRandom.ofInt(-5, 6);
            var numOfRoom = numOfRoomBase + roomNumOffset;
            var roomConfig = new RoomConfig(hotel, configName, peopleLimit, breakfast, type, price, numOfRoom);
            roomConfigs.add(roomConfig);

            for (int k = 0; k < numOfRoom; k++) {
                var roomNo = configName + "#0" + k;
                var room = new Room(roomNo, hotel, roomConfig);
                rooms.add(room);
            }

            for (int k = 0; k < numOfDay; k++) {
                var unit = new RoomUnit(roomConfig, today.plusDays(k), price, numOfRoom);
                units.add(unit);
            }
        }

        repoRoomConfig.saveAll(roomConfigs);
        repoRoom.saveAll(rooms);
        repoRoomUnit.saveAll(units);
        repoHotel.refreshMinPrice();
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

            var configIdx = UtilRandom.ofInt(0, numOfConfig.get(hotelId));
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

    private void initCoupon() {
        var coupons = new ArrayList<CouponBase>();
        for (int j = 0; j < numberOfCoupon; j++) {
            coupons.add(CouponMultiple.random());
        }
        for (int j = 0; j < numberOfCoupon; j++) {
            coupons.add(CouponReduction.random());
        }
        coupons.forEach(couponBase -> {
            couponBase.setHotel(repoHotel.getById(1L));
            couponBase.setOwner(repoUser.getById(1L));
        });
        repoCoupon.saveAll(coupons);
    }

}
