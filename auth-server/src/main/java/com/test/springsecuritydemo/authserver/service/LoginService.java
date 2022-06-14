package com.test.springsecuritydemo.authserver.service;

import com.test.springsecuritydemo.authserver.entity.BaseResponse;
import com.test.springsecuritydemo.authserver.entity.SysUser;

public interface LoginService {

    BaseResponse login(SysUser user);

    BaseResponse logout();

}
