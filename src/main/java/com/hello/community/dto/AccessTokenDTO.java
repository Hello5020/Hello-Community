package com.hello.community.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code;
    private String state;
}
