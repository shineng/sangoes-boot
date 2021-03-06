package com.sangoes.boot.uc.modules.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sangoes.boot.common.msg.Result;
import com.sangoes.boot.uc.modules.admin.service.ISysUserService;
import com.sangoes.boot.uc.modules.admin.utils.UserDetailsImpl;
import com.sangoes.boot.uc.modules.admin.vo.UserDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2018
 *
 * @author jerrychir
 * @date 2018/10/28 12:15 PM
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:" + username);
        // 获取UserDetailsVo
        UserDetailsVo userDetailsVo = userService.selectUserDetailsByUsername(username);
        if (ObjectUtil.isNull(userDetailsVo)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return  new UserDetailsImpl(userDetailsVo);
    }
}
