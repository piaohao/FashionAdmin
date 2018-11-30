package org.piaohao.fashionadmin.module.system.account.model;

import lombok.Data;

@Data
public class LoginReq {
    private String userName;
    private String password;
    private String type;
}
