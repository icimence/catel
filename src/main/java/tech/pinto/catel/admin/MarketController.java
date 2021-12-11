package tech.pinto.catel.admin;

import tech.pinto.catel.user.AccountService;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final AccountService accountService;

    @Autowired
    public MarketController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * create a marketer
     * _in email, phone number and password
     */
    @PostMapping("/new")
    public Response createMarketer(@RequestBody UserForm userForm) {
        // TODO create market man
        return Response.buildSuccess().setMessage(23);
    }

}
