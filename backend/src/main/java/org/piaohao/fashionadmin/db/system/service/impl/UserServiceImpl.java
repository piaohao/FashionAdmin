package org.piaohao.fashionadmin.db.system.service.impl;

import org.piaohao.fashionadmin.db.system.entity.User;
import org.piaohao.fashionadmin.db.system.mapper.UserMapper;
import org.piaohao.fashionadmin.db.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author piaohao
 * @since 2018-12-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
