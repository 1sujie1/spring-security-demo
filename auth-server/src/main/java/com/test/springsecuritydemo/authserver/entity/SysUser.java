package com.test.springsecuritydemo.authserver.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ds_sys_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;

    private String userName;

    private String password;

    private Date createTime;

    private Date updateTime;

    private String remark;

}
