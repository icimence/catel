package tech.pinto.catel.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.enums.UserType;
import tech.pinto.catel.user.AccountService;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.util.OopsException;
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
        User user = new User();
        user.setUserType(UserType.Marketer);
        BeanUtil.copyProperties(userForm, user, CopyOptions.create().ignoreNullValue());
        try {
            accountService.registerAccount(user);
        } catch (OopsException e) {
            return Response.buildFailure(e.getMessage());
        }
        return Response.buildSuccess().setMessage(23);
    }

}
