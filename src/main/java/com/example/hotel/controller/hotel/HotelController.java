package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelServiceI;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.hotel.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelServiceI hotelService;

    @Autowired
    public HotelController(HotelServiceI hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * create a hotel
     * _in name, description, address, business region, star and credit bound needed to reserve the hotel
     */
    @PostMapping("/new")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess().setMessage(8);
    }

    /**
     * update hotel info
     * _in all changed fields
     */
    @PutMapping("/info")
    public ResponseVO updateHotelInfo(@RequestBody HotelVO hotelVO) {
        hotelService.updateHotelInfo(hotelVO);
        return ResponseVO.buildSuccess().setMessage(9);
    }

    /**
     * get all hotels
     * _out all hotels
     */
    @GetMapping("/all")
    public ResponseVO retrieveAllHotels() {
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    /**
     * get hotel by id
     * _in hotel id
     * _out hotel detail information
     */
    @GetMapping("/detail")
    public ResponseVO retrieveHotelDetail(@RequestParam int id) {
        return ResponseVO.buildSuccess(hotelService.selectById(id));
    }

    /**
     * get all hotels bounded to a manager
     * _in manager's id
     * _out hotels
     */
    @GetMapping("/by-manager")
    public ResponseVO getHotelByManager(@RequestParam int id) {
        return ResponseVO.buildSuccess(hotelService.getHotelByManager(id));
    }

    /**
     * get all hotels without manager
     * _out hotels
     */
    @GetMapping("/unbound")
    public ResponseVO unboundHotel() {
        return ResponseVO.buildSuccess(hotelService.unboundHotel());
    }

    /**
     * fuzzy search hotels with given keyword (including name, description, etc.)
     * _in keyword
     * _out matched hotels
     */
    @GetMapping("/search")
    public ResponseVO fzySearch(@RequestParam String keyword) {
        return ResponseVO.buildSuccess(hotelService.fzySearch(keyword));
    }

    /**
     * get hotels ordered by total sales (only count order without caring price)
     * _out ordered hotels
     */
    @GetMapping("/hot")
    public ResponseVO getHot() {
        return ResponseVO.buildSuccess(hotelService.getHot());
    }

}
