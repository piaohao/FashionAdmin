package org.piaohao.fashionadmin.db.system.service.impl;

import org.piaohao.fashionadmin.db.system.entity.Permission;
import org.piaohao.fashionadmin.db.system.mapper.PermissionMapper;
import org.piaohao.fashionadmin.db.system.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author piaohao
 * @since 2018-12-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
