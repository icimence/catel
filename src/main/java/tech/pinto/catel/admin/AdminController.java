package tech.pinto.catel.admin;

import tech.pinto.catel.user.AccountService;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.user.dto.DtoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AccountService accountService;

    @Autowired
    public AdminController(AdminService adminService, AccountService accountService) {
        this.adminService = adminService;
        this.accountService = accountService;
    }

    /**
     * create hotel manager
     * _in email, phone number and password
     */
    @PostMapping("/new-manager")
    public Response addManager(@RequestBody DtoLogin dtoLogin) {
        // TODO create manager
        return Response.buildSuccess().setMessage(1);
    }

    /**
     * remove hotel with given id
     * _in hotel id
     */
    @PostMapping("/rm-hotel")
    public Response deleteHotel(@RequestParam int id) {
        adminService.deleteHotel(id);
        return Response.buildSuccess().setMessage(2);
    }

    /**
     * remove user with given id
     * _in user id
     */
    @PostMapping("/rm-user")
    public Response deleteUser(@RequestParam int id) {
        adminService.deleteUser(id);
        return Response.buildSuccess().setMessage(3);
    }

    /**
     * bound given hotel to given manager
     * _in hotel id and user id
     */
    @PutMapping("/bind")
    public Response bindToManager(@RequestParam int hotelId, @RequestParam int managerId) {
        adminService.bindToManager(hotelId, managerId);
        return Response.buildSuccess().setMessage(managerId == -1 ? 7 : 4);
    }

    /**
     * get all marketers
     * _out all marketers
     */
    @GetMapping("/marketers")
    public Response getAllMarketers() {
        return Response.buildSuccess(adminService.getAllMarketers());
    }

    /**
     * get all managers
     * _out all managers
     */
    @GetMapping("/managers")
    public Response getAllManagers() {
        return Response.buildSuccess(adminService.getAllManagers());
    }

}
