package tech.pinto.catel.util;

import org.springframework.stereotype.Service;
import tech.pinto.catel.enums.BizRegion;
import tech.pinto.catel.enums.HotelStar;
import tech.pinto.catel.enums.RoomType;
import tech.pinto.catel.hotel.Hotel;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.room.*;
import tech.pinto.catel.user.User;
import tech.pinto.catel.user.RepoUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class Initiator {
    private final RepoHotel repoHotel;
    private final RepoUser repoUser;
    private final RepoRoomConfig repoRoomConfig;
    private final RepoRoom repoRoom;
    private final RepoRoomUnit repoRoomUnit;

    public Initiator(RepoHotel repoHotel, RepoUser repoUser, RepoRoomConfig roomConfig, RepoRoomConfig repoRoomConfig, RepoRoom repoRoom, RepoRoomUnit repoRoomUnit) {
        this.repoHotel = repoHotel;
        this.repoUser = repoUser;
        this.repoRoomConfig = repoRoomConfig;
        this.repoRoom = repoRoom;
        this.repoRoomUnit = repoRoomUnit;
    }

    public void init() {
        final int numOfHotel = 30;
        final int numOfUser = 100;
        final int numOfConfig = 10;
        final int numOfRoom = 5;
        final int numOfDay = 90;
        
        var users = new ArrayList<User>();
        for (int i = 0; i < numOfUser; i++) {
            var name = "user" + i;
            var email = name + "@qq.com";
            var password = "x";
            var user = new User(email, password, name);
            users.add(user);
        }
        repoUser.saveAll(users);

        var today = LocalDate.now();

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

}
