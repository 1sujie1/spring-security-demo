package com.test.springsecuritydemo.authserver.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.test.springsecuritydemo.authserver.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
}
