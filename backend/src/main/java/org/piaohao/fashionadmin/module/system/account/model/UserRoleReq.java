package org.piaohao.fashionadmin.module.system.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleReq {
    private Integer userId;
    private List<Integer> roleIds;
}
