package com.example.hotel.controller.order;

import com.example.hotel.blImpl.order.OrderService;
import com.example.hotel.dto.DtoOrderDetail;
import com.example.hotel.dto.DtoReserveGroup;
import com.example.hotel.dto.DtoReservePersonal;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.CreditTransVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * reserve hotel
     * _in an order, with starting and ending date,
     * chosen room in certain hotel, room number, calculated total price, child or not
     */
    @PostMapping("/")
    public ResponseVO reserveHotel(@RequestBody DtoReservePersonal dtoReservePersonal) throws OopsException {
        long id = orderService.addOrder(dtoReservePersonal);
        return ResponseVO.buildSuccess().setMessage(12);
    }

    @PostMapping("/group")
    public ResponseVO reserveHotelForGroup(@RequestBody DtoReserveGroup dtoReserveGroup) throws OopsException {
        orderService.addGroupOrder(dtoReserveGroup);
        return ResponseVO.buildSuccess().setMessage(12);
    }

    @GetMapping("/detail")
    public ResponseVO orderDetail(@RequestParam long orderId) {
        DtoOrderDetail dtoOrderDetail = orderService.orderDetail(orderId);
        return ResponseVO.buildSuccess(dtoOrderDetail);
    }

    /**
     * mark an order as expired (paid but nobody came)
     * _in order id
     */
    @PostMapping("/expire")
    public ResponseVO expire(@RequestParam int id) {
        orderService.expire(id);
        return ResponseVO.buildSuccess().setMessage(13);
    }

    /**
     * cancel a order (refund and probably affect user's credit)
     * _in order id
     */
    @PostMapping("/rm")
    public ResponseVO annulOrder(@RequestParam int id) {
        orderService.annulOrder(id);
        return ResponseVO.buildSuccess().setMessage(14);
    }

    /**
     * get all orders
     * DEPRECATED
     */
    @GetMapping("/")
    @Deprecated
    public ResponseVO retrieveAllOrders() {
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    /**
     * get all orders of certain user
     * _in user id
     * _out orders
     */
    @GetMapping("/by-user")
    public ResponseVO retrieveUserOrders(@RequestParam int id) {
        return ResponseVO.buildSuccess(orderService.getUserOrders(id));
    }

    /**
     * get all orders of certain hotel
     * _in hotel id
     * _out orders
     */
    @GetMapping("/by-hotel")
    public ResponseVO retrieveHotelOrders(@RequestParam int id) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(id));
    }

    /**
     * get all orders belonging to hotels bounded to
     */
    @GetMapping("/by-manager")
    public ResponseVO getOrderByManager(@RequestParam int id) {
        return ResponseVO.buildSuccess(orderService.getOrderByManager(id));
    }

    /**
     * get credit transactions of a user
     * _in user id
     * _out list of transaction, each of which contains delta of credit and info about reason
     */
    @GetMapping("/credit-trans")
    public ResponseVO getCreditTransaction(@RequestParam int id) {
        List<CreditTransVO> list = orderService.getCreditTransaction(id);
        return ResponseVO.buildSuccess(list);
    }

    /**
     * get all order in state 'Available' of someone
     * _ user id
     * _ out available orders
     */
    @GetMapping("/available")
    public ResponseVO available(@RequestParam int id) {
        return ResponseVO.buildSuccess(orderService.available(id));
    }

}
