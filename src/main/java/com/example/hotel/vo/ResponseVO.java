package com.example.hotel.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseVO {

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "成功添加酒店管理员！");
        map.put(2, "成功删除酒店！");
        map.put(3, "成功删除用户！");
        map.put(4, "成功绑定酒店！");
        map.put(5, "成功添加酒店优惠！");
        map.put(6, "成功删除优惠！");
        map.put(7, "解绑成功！");
        map.put(8, "成功添加酒店！");
        map.put(9, "成功更新酒店信息！");
        map.put(11, "成功添加房型！");
        map.put(12, "成功预定酒店！");
        map.put(13, "成功标记异常订单！");
        map.put(14, "成功取消订单！");
        map.put(15, "登录成功！");
        map.put(16, "注册成功！");
        map.put(17, "升级VIP成功！");
        map.put(18, "成功更新账户信息！");
        map.put(19, "成功添加个人信息！");
        map.put(20, "成功删除个人信息！");
        map.put(21, "成功更新个人信息！");
        map.put(22, "评论成功！");
        map.put(23, "成功添加营销人员!");
        map.put(24, "充值成功!");
        map.put(25, "删除房型成功!");
    }

    private Boolean success;
    private String message;
    private Object content;

    public static ResponseVO buildSuccess() {
        ResponseVO response = new ResponseVO();
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildSuccess(Object content) {
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildFailure(String message) {
        System.err.println(message);
        ResponseVO response = new ResponseVO();
        response.setSuccess(false);
        response.message = message;
        return response;
    }

    public ResponseVO setMessage(int code) {
        this.message = map.get(code);
        return this;
    }

}
