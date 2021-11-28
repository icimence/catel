package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.RoomServiceI;
import com.example.hotel.model.Room;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/room")
public class RoomController {

    private final RoomServiceI roomService;

    @Autowired
    public RoomController(RoomServiceI roomService) {
        this.roomService = roomService;
    }

    /**
     * create a room type for hotel
     * _in hotel id, room type, room number, price, people number upper bound, with breakfast or not
     */
    @PostMapping("/new")
    public ResponseVO addRoomInfo(@RequestBody Room room) {
        try {
            roomService.insertRoomInfo(room);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(11);
    }

    /**
     * get available room number in future 30 day for given hotel
     * _in hotel id
     * _out room number list
     */
    @GetMapping("/available")
    public ResponseVO available(@RequestParam int id) {
        return ResponseVO.buildSuccess(roomService.getRoomNumber(id));
    }

    /**
     * get hotel's all rooms info
     * _in hotel id
     * _out room info list
     */
    @GetMapping("/by-hotel")
    public ResponseVO getByHotel(@RequestParam int id) {
        List<RoomVO> rooms = roomService.getByHotel(id);
        return ResponseVO.buildSuccess(rooms);
    }

    /**
     * remove a room type
     * _in room id
     */
    @PostMapping("/rm-room")
    public ResponseVO removeRoom(@RequestParam int id) {
        roomService.removeRoom(id);
        return ResponseVO.buildSuccess().setMessage(25);
    }

}
