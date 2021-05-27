package com.example.hotel.vo;

import lombok.Data;

@Data
public class OssTokenVO {

    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String region;

}
