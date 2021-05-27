package com.example.hotel.blImpl.hotel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.hotel.HotelServiceI;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Room;
import com.example.hotel.util.FzyService;
import com.example.hotel.util.OssService;
import com.example.hotel.vo.hotel.HotelVO;
import com.example.hotel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService implements HotelServiceI {

    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;
    private final OssService ossService;
    private final FzyService fzyService;
    private final OrderMapper orderMapper;

    @Autowired
    public HotelService(HotelMapper hotelMapper, RoomMapper roomMapper, OssService ossService, FzyService fzyService, OrderMapper orderMapper) {
        this.hotelMapper = hotelMapper;
        this.roomMapper = roomMapper;
        this.ossService = ossService;
        this.fzyService = fzyService;
        this.orderMapper = orderMapper;
    }

    @Override
    public void addHotel(HotelVO hotelVO) {
        Hotel hotel = new Hotel();
        BeanUtil.copyProperties(hotelVO, hotel, CopyOptions.create().setIgnoreNullValue(true));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public List<Hotel> getHotelByManager(Integer id) {
        return hotelMapper.selectByManager(id);
    }

    @Override
    public void updateHotelInfo(HotelVO hotelVO) {
        Hotel hotel = hotelMapper.selectById(hotelVO.getId());
        boolean newPic = null != hotelVO.getPic() && !hotelVO.getPic().equals(hotel.getPic());
        BeanUtil.copyProperties(hotelVO, hotel, CopyOptions.create().setIgnoreNullValue(true));
        if (newPic) {
            hotel.setPic(ossService.savePublic("hotel-res-img-public",
                    "hotel/" + hotelVO.getId().toString() + "/pic.png", hotelVO.getPic()));
        }
        hotelMapper.updateHotel(hotel);
    }

    @Override
    public List<Hotel> unboundHotel() {
        return hotelMapper.unboundHotel();
    }

    @Override
    public List<Hotel> fzySearch(String keyword) {
        List<Hotel> hotels = hotelMapper.selectAllHotel();
        hotels.sort((h1, h2) -> {
            double s1 = fzyService.similarity(keyword, h1);
            double s2 = fzyService.similarity(keyword, h2);
            double delta = s1 - s2;
            return delta > 0 ? 1 : delta < 0 ? -1 : 0;
        });
        return hotels.stream().filter(hotel -> fzyService.similarity(keyword, hotel) > 0.6).collect(Collectors.toList());
    }

    @Override
    public List<HotelVO> getHot() {
        List<Hotel> all = hotelMapper.selectAllHotel();
        HashMap<Integer, Integer> hots = new HashMap<>();
        all.forEach(o -> {
            hots.put(o.getId(), orderMapper.getHot(o.getId()));
        });
        all.sort(Comparator.comparingInt(x -> hots.get(x.getId())));
        return all.stream().map(o -> {
            HotelVO hotelVO = new HotelVO();
            BeanUtil.copyProperties(o, hotelVO, CopyOptions.create().ignoreNullValue());
            return hotelVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        return hotelMapper.selectAllHotel().stream().map(po -> {
            HotelVO vo = new HotelVO();
            BeanUtil.copyProperties(po, vo, CopyOptions.create().ignoreNullValue());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public HotelVO selectById(Integer hotelId) {
        Hotel hotel = hotelMapper.selectById(hotelId);
        HotelVO hotelVO = new HotelVO();
        BeanUtil.copyProperties(hotel, hotelVO, CopyOptions.create().ignoreNullValue());
        List<Room> rooms = roomMapper.selectRoomsByHotelId(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            BeanUtil.copyProperties(r, roomVO, CopyOptions.create().setIgnoreNullValue(true));
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        return hotelVO;
    }

}
