package com.test.springsecuritydemo.authserver.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.test.springsecuritydemo.authserver.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
