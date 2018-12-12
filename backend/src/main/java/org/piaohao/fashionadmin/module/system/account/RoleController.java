package org.piaohao.fashionadmin.module.system.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.piaohao.fashionadmin.constant.ResultCode;
import org.piaohao.fashionadmin.db.system.entity.Role;
import org.piaohao.fashionadmin.db.system.entity.RolePermission;
import org.piaohao.fashionadmin.db.system.mapper.RoleMapper;
import org.piaohao.fashionadmin.db.system.service.IRolePermissionService;
import org.piaohao.fashionadmin.db.system.service.IRoleService;
import org.piaohao.fashionadmin.exception.ServiceException;
import org.piaohao.fashionadmin.model.PageReq;
import org.piaohao.fashionadmin.model.TableData;
import org.piaohao.fashionadmin.module.system.account.model.DeleteRoleReq;
import org.piaohao.fashionadmin.module.system.account.model.RolePermissionReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("SysRoleController")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IRolePermissionService rolePermissionService;

    @PostMapping("/api/roles")
    public Object roles(@RequestBody PageReq pageReq) {
        pageReq.check();
        IPage<Role> page = new Page<>(pageReq.getCurrentPage(), pageReq.getPageSize());
        roleMapper.selectPage(page, new LambdaQueryWrapper<Role>().eq(Role::getStatus, 1));
        return TableData.fromPage(page);
    }

    @PostMapping("/api/allRoles")
    public Object allRoles() {
        return roleService.list();
    }

    @PostMapping("/api/saveRole")
    public Object saveRole(@RequestBody Role role) {
        Integer id = role.getId();
        String roleName = role.getRoleName();
        if (id == null) {
            Role record = roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
            if (record != null) {
                throw new ServiceException(ResultCode.ROLE_NAME_CONFLICT);
            }
            role.setCreatedTime(System.currentTimeMillis());
            role.setUpdateTime(System.currentTimeMillis());
            roleService.save(role);
        } else {
            Role record = roleService.getById(id);
            if (record == null) {
                throw new ServiceException(ResultCode.RESOURCE_NOT_EXISTS);
            }
            record.setRoleName(role.getRoleName());
            record.setUpdateTime(System.currentTimeMillis());
            roleService.updateById(record);
        }
        return role;
    }

    @PostMapping("/api/removeRole")
    public Object removeRole(@RequestBody DeleteRoleReq req) {
        for (Role role : req.getData()) {
            Role record = roleService.getById(role.getId());
            if (record == null) {
                throw new ServiceException(ResultCode.RESOURCE_NOT_EXISTS);
            }
            record.setStatus(0);
            roleService.updateById(record);
        }
        return "";
    }

    @PostMapping("/api/saveRolePermission")
    public Object saveRolePermission(@RequestBody RolePermissionReq req) {
        Integer roleId = req.getRoleId();
        Role record = roleService.getById(roleId);
        if (record == null) {
            throw new ServiceException(ResultCode.RESOURCE_NOT_EXISTS);
        }
        List<Integer> permissionIds = req.getPermissionIds();
        List<RolePermission> rolePermissions = permissionIds.stream()
                .map(permissionId -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(permissionId);
                    return rolePermission;
                })
                .collect(Collectors.toList());
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
        rolePermissionService.saveBatch(rolePermissions);
        return "";
    }

    @PostMapping("/api/getRolePermission")
    public Object getRolePermission(@RequestBody Role role) {
        Integer roleId = role.getId();
        Role record = roleService.getById(roleId);
        if (record == null) {
            throw new ServiceException(ResultCode.RESOURCE_NOT_EXISTS);
        }
        return rolePermissionService.list(
                new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId))
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

}
