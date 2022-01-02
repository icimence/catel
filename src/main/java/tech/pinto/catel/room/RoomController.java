package tech.pinto.catel.room;

import tech.pinto.catel.domain.Room;
import tech.pinto.catel.room.dto.DtoScreenRoomConfig;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * create a room type for hotel
     * _in hotel id, room type, room number, price, people number upper bound, with breakfast or not
     */
    @PostMapping("/new")
    public Response addRoomInfo(@RequestBody Room room) {
        try {
            roomService.insertRoomInfo(room);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
        return Response.buildSuccess().setMessage(11);
    }

    /**
     * get available room number in future 30 day for given hotel
     * _in hotel id
     * _out room number list
     */
    @GetMapping("/available")
    public Response available(@RequestParam long id) {
        return Response.buildSuccess(roomService.getRoomNumber(id));
    }

    /**
     * get hotel's all rooms info
     * _in hotel id
     * _out room info list
     */
    @GetMapping("/by-hotel")
    public Response getByHotel(@RequestParam long id) {
        var rooms = roomService.getByHotel(id);
        return Response.buildSuccess(rooms);
    }

    /**
     * remove a room type
     * _in room id
     */
    @PostMapping("/rm-room")
    public Response removeRoom(@RequestParam long id) {
        roomService.removeRoom(id);
        return Response.buildSuccess().setMessage(25);
    }

    @GetMapping("/")
    public Response information(@RequestParam long id) throws OopsException {
        var r = roomService.information(id);
        return Response.buildSuccess(r);
    }

    @GetMapping("/screen")
    public Response filter(DtoScreenRoomConfig dtoScreenRoomConfig) throws OopsException {
        var r = roomService.screen(dtoScreenRoomConfig);
        return Response.buildSuccess(r);
    }
    
}
