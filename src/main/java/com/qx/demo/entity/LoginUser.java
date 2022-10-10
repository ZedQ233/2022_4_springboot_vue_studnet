package com.qx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZedQ
 * @date 2022年09月30日 21:43 
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
