package org.piaohao.fashionadmin.module.system.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.piaohao.fashionadmin.db.system.entity.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteRoleReq {
    private List<Role> data;
}
