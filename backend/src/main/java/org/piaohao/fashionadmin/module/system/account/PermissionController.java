package org.piaohao.fashionadmin.module.system.account;

import cn.hutool.core.collection.CollUtil;
import org.piaohao.fashionadmin.db.system.entity.Permission;
import org.piaohao.fashionadmin.db.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("SysPermissionController")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/api/permissions")
    public Object permissions() {
        List<Permission> permissions = permissionService.list();
        permissions.forEach(p -> p.setKey(p.getId()));
        return buildPermissions("root", permissions);
    }

    private List<Permission> buildPermissions(String code, List<Permission> permissions) {
        List<Permission> childMenus = permissions.stream()
                .filter(m -> m.getParentCode().equals(code))
                .collect(Collectors.toList());
        childMenus.forEach(m -> {
            m.setChildren(buildPermissions(m.getCode(), permissions));
        });
        if (CollUtil.isEmpty(childMenus)) {
            return null;
        }
        return childMenus;
    }
}
