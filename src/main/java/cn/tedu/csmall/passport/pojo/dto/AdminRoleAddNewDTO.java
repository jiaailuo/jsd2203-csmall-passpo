package cn.tedu.csmall.passport.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminRoleAddNewDTO implements Serializable {
    /**管理员Id*/
    private Long adminId;


    /***/
    private Long roleId;

}
