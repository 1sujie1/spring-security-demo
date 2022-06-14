package com.test.springsecuritydemo.authserver.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.test.springsecuritydemo.authserver.entity.SysUserRole;
import com.test.springsecuritydemo.authserver.entity.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {



}
