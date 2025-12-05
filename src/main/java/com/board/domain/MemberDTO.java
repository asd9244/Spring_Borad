package com.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberDTO {

    private String memberId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String status;
    private Date joinDate;
    private Date lastLogin;
    private Date updateTime;
}
