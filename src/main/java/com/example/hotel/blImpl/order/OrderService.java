package com.example.hotel.blImpl.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.hotel.HotelServiceI;
import com.example.hotel.bl.order.OrderServiceI;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.CreditTransVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.hotel.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceI {

    private final OrderMapper orderMapper;
    private final RoomMapper roomMapper;
    private final PersonMapper personMapper;
    private final HotelMapper hotelMapper;
    private final HotelServiceI hotelServiceI;
    private final AccountServiceI accountServiceI;
    private final AccountMapper accountMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, HotelServiceI hotelServiceI, AccountServiceI accountServiceI, RoomMapper roomMapper, PersonMapper personMapper, HotelMapper hotelMapper, AccountMapper accountMapper) {
        this.orderMapper = orderMapper;
        this.hotelServiceI = hotelServiceI;
        this.accountServiceI = accountServiceI;
        this.roomMapper = roomMapper;
        this.personMapper = personMapper;
        this.hotelMapper = hotelMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public void addOrder(OrderVO orderVO) throws OopsException {
        if (orderVO.getPrice() < 0) throw new OopsException(8);

        Order order = new Order();
        BeanUtil.copyProperties(orderVO, order, CopyOptions.create().setIgnoreNullValue(true));
        order.setUserId(personMapper.getUserId(order.getPersonId()));
        order.setHotelName(hotelMapper.selectById(order.getHotelId()).getName());

        int roomId = roomMapper.getRoomId(order);
        List<Integer> numbers = roomMapper.getRoomNumber(roomId);
        long s = order.getCheckInDate().toEpochDay() - LocalDate.now().toEpochDay();
        long t = order.getCheckOutDate().toEpochDay() - LocalDate.now().toEpochDay();

        for (int i = (int) s; i >= 0 && i < t; i++) {
            if (numbers.get(i) < order.getRoomNum()) {
                throw new OopsException(2);
            }
        }

        double credit = creditAnew(order.getUserId());
        int bound = hotelMapper.selectById(order.getHotelId()).getCreditBound();
        if (credit < bound) {
            throw new OopsException(5);
        }

        orderMapper.addOrder(order);
        roomMapper.updateRoomInfo(order, roomId);
    }

    private double creditAnew(int id) {
        return accountMapper.getCreditFromOrder(id) + 100 + accountMapper.getCreditFromDirect(id);
    }

    private OrderVO convert(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO, CopyOptions.create().ignoreNullValue());
        HotelVO hotel = hotelServiceI.selectById(order.getHotelId());
        orderVO.setHotelName(hotel.getName());
        orderVO.setHotelAddress(hotel.getAddress());
        return orderVO;
    }

    @Override
    public List<OrderVO> getAllOrders() {
        List<Order> orders = orderMapper.getAllOrders();
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<OrderVO> getUserOrders(int userId) {
        return orderMapper.getUserOrders(userId)
                          .stream().map(this::convert)
                          .collect(Collectors.toList());
    }

    @Override
    public void annulOrder(int orderId) {
        Order order = orderMapper.getOrderById(orderId);
        int roomId = roomMapper.getRoomId(order);
        accountServiceI.creditDown(order);
        roomMapper.updateRoomInfo(order, roomId);
        order.setOrderState(OrderState.Canceled);
        orderMapper.update(order);
    }

    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = orderMapper.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId() == hotelId).collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrderByManager(Integer managerId) {
        List<Hotel> hotels = hotelServiceI.getHotelByManager(managerId);
        List<Order> orders = new ArrayList<>();
        for (Hotel hotel : hotels) {
            orders.addAll(orderMapper.getOrderByHotel(hotel.getId()));
        }
        return orders;
    }

    @Override
    public List<CreditTransVO> getCreditTransaction(int userId) {
        List<CreditTransVO> normal = orderMapper.getUserOrders(userId).stream().filter(o -> o.getCreditDelta() != 0).map(o -> {
            CreditTransVO creditTransVO = new CreditTransVO();
            BeanUtil.copyProperties(o, creditTransVO, CopyOptions.create().ignoreNullValue());
            creditTransVO.setHotelName(hotelMapper.selectById(o.getHotelId()).getName());
            return creditTransVO;
        }).collect(Collectors.toList());
        accountMapper.getDirect(userId).forEach(o -> {
            CreditTransVO creditTransVO = new CreditTransVO();
            creditTransVO.setCreditDelta(o.getCreditDelta());
            creditTransVO.setHotelName("充值");
            creditTransVO.setId(-1);
            creditTransVO.setOrderState(OrderState.Direct);
            normal.add(creditTransVO);
        });
        return normal;
    }

    @Override
    public void expire(int orderId) {
        orderMapper.expire(orderId);
    }

    @Override
    public List<Order> available(int id) {
        return orderMapper.getAvailable(id);
    }

}
