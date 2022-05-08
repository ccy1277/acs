package com.ccy1277.acs.sys.service.impl;

import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.mapper.UserMapper;
import com.ccy1277.acs.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
