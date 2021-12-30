package tech.pinto.catel.hotel;

import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.hotel.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * create a hotel
     * _in name, description, address, business region, star and credit bound needed to reserve the hotel
     */
    @PostMapping("/new")
    public Response createHotel(@RequestBody HotelVO hotelVO) {

        hotelService.addHotel(hotelVO);
        return Response.buildSuccess().setMessage(8);
    }

    @GetMapping("/")
    public Response hotelQuery(DtoHotelQuery dtoHotelQuery) throws OopsException {
        List<DtoHotelBrief> hotels = hotelService.hotelQuery(dtoHotelQuery);
        return Response.buildSuccess(hotels);
    }

    /**
     * update hotel info
     * _in all changed fields
     */
    @PutMapping("/info")
    public Response updateHotelInfo(@RequestBody HotelVO hotelVO) {
        hotelService.updateHotelInfo(hotelVO);
        return Response.buildSuccess().setMessage(9);
    }

    /**
     * get all hotels
     * _out all hotels
     */
    @GetMapping("/all")
    public Response retrieveAllHotels() {
        return Response.buildSuccess(hotelService.retrieveHotels());
    }

    @GetMapping("/detail")
    public Response retrieveHotelDetail(@RequestParam int id) {
        var detail = hotelService.getDetail(id);
        return Response.buildSuccess(detail);
    }

    /**
     * get all hotels bounded to a manager
     * _in manager's id
     * _out hotels
     */
    @GetMapping("/by-manager")
    public Response getHotelByManager(@RequestParam int id) {
        return Response.buildSuccess(hotelService.getHotelByManager(id));
    }

    /**
     * get all hotels without manager
     * _out hotels
     */
    @GetMapping("/unbound")
    public Response unboundHotel() {
        return Response.buildSuccess(hotelService.unboundHotel());
    }

    /**
     * fuzzy search hotels with given keyword (including name, description, etc.)
     * _in keyword
     * _out matched hotels
     */
    @GetMapping("/search")
    public Response fzySearch(@RequestParam String keyword) {
        return Response.buildSuccess(hotelService.fzySearch(keyword));
    }

    /**
     * get hotels ordered by total sales (only count order without caring price)
     * _out ordered hotels
     */
    @GetMapping("/hot")
    public Response getHot(@RequestParam int limit) {
        var hot = hotelService.getHot(limit);
        return Response.buildSuccess(hot);
    }
    
    @GetMapping("/lucky")
    public Response luckyOne() {
        var hotel = hotelService.luckyOne();
        return Response.buildSuccess(hotel);
    }

}
