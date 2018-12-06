package org.piaohao.fashionadmin.module.system.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.piaohao.fashionadmin.db.system.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteUserReq {
    private List<User> data;
}
