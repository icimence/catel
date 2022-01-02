package tech.pinto.catel.order;

import tech.pinto.catel.order.dto.DtoReserveGroup;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.CreditTransVO;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.order.dto.DtoOrderDetail;
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
    public Response reserveHotel(@RequestBody DtoReservePersonal dtoReservePersonal) throws OopsException {
        var orderId = orderService.reserve(dtoReservePersonal);
        return Response.buildSuccess(orderId).setMessage(12);
    }

    @PostMapping("/preview")
    public Response preview(@RequestBody DtoReservePersonal dtoReservePersonal) throws OopsException {
        var preview = orderService.refPreview(dtoReservePersonal);
        return Response.buildSuccess(preview);
    }

    @PostMapping("/group")
    public Response reserveHotelForGroup(@RequestBody DtoReserveGroup dtoReserveGroup) throws OopsException {
        orderService.reserveForGroup(dtoReserveGroup);
        return Response.buildSuccess().setMessage(12);
    }

    @GetMapping("/detail")
    public Response orderDetail(@RequestParam long orderId) {
        DtoOrderDetail dtoOrderDetail = orderService.orderDetail(orderId);
        return Response.buildSuccess(dtoOrderDetail);
    }

    /**
     * mark an order as expired (paid but nobody came)
     * _in order id
     */
    @PostMapping("/expire")
    public Response expire(@RequestParam int id) {
        orderService.expire(id);
        return Response.buildSuccess().setMessage(13);
    }

    /**
     * cancel a order (refund and probably affect user's credit)
     * _in order id
     */
    @PostMapping("/rm")
    public Response annulOrder(@RequestParam long id) {
        orderService.annulOrder(id);
        return Response.buildSuccess().setMessage(14);
    }

    /**
     * get all orders
     * DEPRECATED
     */
    @GetMapping("/")
    @Deprecated
    public Response retrieveAllOrders() {
        return Response.buildSuccess(orderService.getAllOrders());
    }

    /**
     * get all orders of certain user
     * _in user id
     * _out orders
     */
    @GetMapping("/by-user")
    public Response retrieveUserOrders(@RequestParam int id) {
        return Response.buildSuccess(orderService.getUserOrders(id));
    }

    /**
     * get all orders of certain hotel
     * _in hotel id
     * _out orders
     */
    @GetMapping("/by-hotel")
    public Response retrieveHotelOrders(@RequestParam long id) {
        return Response.buildSuccess(orderService.getHotelOrders(id));
    }

    /**
     * get all orders belonging to hotels bounded to
     */
    @GetMapping("/by-manager")
    public Response getOrderByManager(@RequestParam int id) {
        return Response.buildSuccess(orderService.getOrderByManager(id));
    }

    /**
     * get credit transactions of a user
     * _in user id
     * _out list of transaction, each of which contains delta of credit and info about reason
     */
    @GetMapping("/credit-trans")
    public Response getCreditTransaction(@RequestParam int id) {
        List<CreditTransVO> list = orderService.getCreditTransaction(id);
        return Response.buildSuccess(list);
    }

    /**
     * get all order in state 'Available' of someone
     * _ user id
     * _ out available orders
     */
    @GetMapping("/available")
    public Response available(@RequestParam int id) {
        return Response.buildSuccess(orderService.available(id));
    }

}
