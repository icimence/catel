package tech.pinto.catel.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OopsException extends Exception {

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "用户名或邮箱已存在！");
        map.put(2, "当前时段剩余房间数不足！");
        map.put(3, "用户名与密码不匹配！");
        map.put(4, "没有这个id！");
        map.put(5, "您的信用值过低！");
        map.put(6, "用户名与邮箱不匹配！");
        map.put(7, "该类房型已存在！");
        map.put(8, "不能白嫖！");
        map.put(9, "无效的日期参数");
        map.put(10, "无效的星级参数");
        map.put(11, "相关房型信息不存在");
        map.put(12, "相关酒店不存在");
        map.put(13, "优惠券要求不满足");
    }

    private String message;

    public OopsException(int code) {
        String message = map.get(code);
        this.message = message;
        System.err.println(message);
    }

}
