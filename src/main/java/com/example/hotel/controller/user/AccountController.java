package com.example.hotel.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.model.User;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.CaptchaVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.order.CreditUpVO;
import com.example.hotel.vo.user.UserForm;
import com.example.hotel.vo.user.UserInfo;
import com.example.hotel.vo.user.UserVO;
import com.example.hotel.vo.user.VipForm;
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
    private final AccountServiceI accountService;

    @Autowired
    public AccountController(Producer kaptchaProducer, AccountServiceI accountService) {
        this.kaptchaProducer = kaptchaProducer;
        this.accountService = accountService;
    }

    /**
     * for login
     * _in one of emails and username and password
     * _out info of the account
     */
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user;
        try {
            user = accountService.login(userForm);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess(user).setMessage(15);

    }

    /**
     * create an account
     * _in account info, including email, username, abd password
     */
    @PostMapping("/new-client")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        User user = new User();
        BeanUtil.copyProperties(userVO, user, CopyOptions.create().setIgnoreNullValue(true));
        try {
            accountService.registerAccount(user);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(16);
    }

    /**
     * charge for vip
     * _in who, how long and what kind of vip it wanna be
     */
    @PostMapping("/vip")
    public ResponseVO vip(@RequestBody VipForm vipForm) {
        accountService.vip(vipForm);
        return ResponseVO.buildSuccess().setMessage(17);
    }

    /**
     * update account info
     * _in new account info, same to account creation. in addition, avatar can be set
     */
    @PutMapping("/info")
    public ResponseVO updateInfo(@RequestBody UserInfo userInfo) {
        try {
            accountService.updateInfo(userInfo);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(18);
    }

    /**
     * get user info by id
     * _in user id
     * _out info
     */
    @GetMapping("/info")
    public ResponseVO getInfo(@RequestParam int id) {
        try {
            return ResponseVO.buildSuccess(accountService.getUserById(id));
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    /**
     * generate captcha
     * _out generated captcha
     */
    @GetMapping("/captcha")
    public ResponseVO getCaptcha() throws IOException {
        String kaptchaText = kaptchaProducer.createText();
        BufferedImage bi = kaptchaProducer.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpeg", outputStream);
        String base64Image = "data:image/jpeg;base64," + Base64.encodeBase64String(outputStream.toByteArray());
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setAnswer(kaptchaText);
        captchaVO.setPicBase(base64Image);
        return ResponseVO.buildSuccess(captchaVO);
    }

    /**
     * charge for user's credit
     * _in corresponding username and email to make sure correct behavior of operator and a number means credit added
     */
    @PostMapping("/credit-up")
    public ResponseVO creditUp(@RequestBody CreditUpVO creditUpVO) {
        try {
            accountService.creditUp(creditUpVO);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(24);
    }

}
