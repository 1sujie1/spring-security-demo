package com.test.springsecuritydemo.authserver.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ds_sys_user_role")
public class SysUserRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;

    private int userId;

    private int roleId;

}
