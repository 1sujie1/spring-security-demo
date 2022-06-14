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
@TableName("ds_sys_role")
public class SysRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;

    private String roleName;

    private String roleCode;

    private int state;

}
