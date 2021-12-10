package tech.pinto.catel.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.user.dto.DtoUserInfo;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.CaptchaVO;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.order.CreditUpVO;
import tech.pinto.catel.vo.user.UserForm;
import tech.pinto.catel.vo.user.UserInfo;
import tech.pinto.catel.vo.user.UserVO;
import tech.pinto.catel.vo.user.VipForm;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController()
@RequestMapping("/api/user")
@Slf4j
public class AccountController {

    private final Producer kaptchaProducer;
    private final AccountService accountService;

    @Autowired
    public AccountController(Producer kaptchaProducer, AccountService accountService) {
        this.kaptchaProducer = kaptchaProducer;
        this.accountService = accountService;
    }

    /**
     * for login
     * _in one of emails and username and password
     * _out info of the account
     */
    @PostMapping("/login")
    public Response login(@RequestBody UserForm userForm) {
        try {
            var info = accountService.login(userForm);
            return Response.buildSuccess(info).setMessage(15);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }

    }

    /**
     * create an account
     * _in account info, including email, username, abd password
     */
    @PostMapping("/new-client")
    public Response registerAccount(@RequestBody UserVO userVO) {
        User user = new User();
        BeanUtil.copyProperties(userVO, user, CopyOptions.create().setIgnoreNullValue(true));
        try {
            accountService.registerAccount(user);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
        return Response.buildSuccess().setMessage(16);
    }

    /**
     * charge for vip
     * _in who, how long and what kind of vip it wanna be
     */
    @PostMapping("/vip")
    public Response vip(@RequestBody VipForm vipForm) {
        accountService.vip(vipForm);
        return Response.buildSuccess().setMessage(17);
    }

    /**
     * update account info
     * _in new account info, same to account creation. in addition, avatar can be set
     */
    @PutMapping("/info")
    public Response updateInfo(@RequestBody UserInfo userInfo) {
        try {
            accountService.updateInfo(userInfo);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
        return Response.buildSuccess().setMessage(18);
    }

    /**
     * get user info by id
     * _in user id
     * _out info
     */
    @GetMapping("/info")
    public Response getInfo(@RequestParam int id) {
        try {
            return Response.buildSuccess(accountService.getUserById(id));
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }

    /**
     * generate captcha
     * _out generated captcha
     */
    @GetMapping("/captcha")
    public Response getCaptcha() throws IOException {
        String kaptchaText = kaptchaProducer.createText();
        BufferedImage bi = kaptchaProducer.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpeg", outputStream);
        String base64Image = "data:image/jpeg;base64," + Base64.encodeBase64String(outputStream.toByteArray());
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setAnswer(kaptchaText);
        captchaVO.setPicBase(base64Image);
        return Response.buildSuccess(captchaVO);
    }

    /**
     * charge for user's credit
     * _in corresponding username and email to make sure correct behavior of operator and a number means credit added
     */
    @PostMapping("/credit-up")
    public Response creditUp(@RequestBody CreditUpVO creditUpVO) {
        try {
            accountService.creditUp(creditUpVO);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
        return Response.buildSuccess().setMessage(24);
    }

}
