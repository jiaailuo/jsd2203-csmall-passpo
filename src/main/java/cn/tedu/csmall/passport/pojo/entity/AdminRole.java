package cn.tedu.csmall.passport.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminRole implements Serializable {

    /**数据Id*/
    private Long id;

    /**
     * 数据id
     */
    @ApiModelProperty(value = "数据id")
    private Long adminId;


    private Long roleId;

    private  LocalDateTime gmtCreate;
    private  LocalDateTime gmtModified;

}
