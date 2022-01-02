package tech.pinto.catel.user;

import tech.pinto.catel.user.dto.DtoGetCreditHistory;
import tech.pinto.catel.user.dto.*;
import tech.pinto.catel.util.error.CustomException;
import tech.pinto.catel.util.error.OopsException;
import tech.pinto.catel.vo.CaptchaVO;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.order.CreditUpVO;
import tech.pinto.catel.user.dto.DtoUserUpdate;
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
public class UserController {

    private final Producer kaptchaProducer;
    private final AccountService accountService;

    @Autowired
    public UserController(Producer kaptchaProducer, AccountService accountService) {
        this.kaptchaProducer = kaptchaProducer;
        this.accountService = accountService;
    }

    /**
     * for login
     * _in one of emails and username and password
     * _out info of the account
     */
    @PostMapping("/login")
    public Response login(@RequestBody DtoLogin dtoLogin) {
        try {
            var info = accountService.login(dtoLogin);
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
    @PostMapping("/")
    public Response registerAccount(@RequestBody DtoRegister dtoRegister) throws OopsException {
        accountService.registerAccount(dtoRegister);
        return Response.buildSuccess().setMessage(16);
    }

    @PostMapping("/pwd")
    public Response changePassword(@RequestBody DtoChangePwd dtoChangePwd) throws OopsException {
        accountService.changePassword(dtoChangePwd);
        return Response.buildSuccess();
    }

    /**
     * charge for vip
     * _in who, how long and what kind of vip it wanna be
     */
    @PostMapping("/vip")
    public Response vip(@RequestBody DtoUserVip dtoUserVip) {
        accountService.vip(dtoUserVip);
        return Response.buildSuccess().setMessage(17);
    }

    /**
     * update account info
     * _in new account info, same to account creation. in addition, avatar can be set
     */
    @PutMapping("/")
    public Response updateInfo(@RequestBody DtoUserUpdate dtoUserUpdate) throws OopsException {
        accountService.updateInfo(dtoUserUpdate);
        return Response.buildSuccess().setMessage(18);

    }

    /**
     * get user info by id
     * _in user id
     * _out info
     */
    @GetMapping("/info")
    public Response getInfo(@RequestParam long id) throws CustomException {
        var r = accountService.info(id);
        return Response.buildSuccess(r);
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

    @GetMapping("/credit-history")
    public Response creditHistory(DtoGetCreditHistory dtoGetCreditHistory) {
        var r = accountService.creditHistory(dtoGetCreditHistory);
        return Response.buildSuccess(r);
    }
}
