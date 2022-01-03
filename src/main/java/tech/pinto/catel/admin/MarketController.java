package tech.pinto.catel.admin;

import tech.pinto.catel.user.UserService;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.user.dto.DtoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final UserService userService;

    @Autowired
    public MarketController(UserService userService) {
        this.userService = userService;
    }

    /**
     * create a marketer
     * _in email, phone number and password
     */
    @PostMapping("/new")
    public Response createMarketer(@RequestBody DtoLogin dtoLogin) {
        // TODO create market man
        return Response.buildSuccess().setMessage(23);
    }

}
