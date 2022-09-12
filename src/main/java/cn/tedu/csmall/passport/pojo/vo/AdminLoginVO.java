package cn.tedu.csmall.passport.pojo.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
//只进行查询数据的结果
import java.io.Serializable;
import java.util.List;

@Data
public class AdminLoginVO implements Serializable {


  /**用户名*/
    private String username;

   /**密码*/
    private String password;


    /**管理员id*/
    private Long id;

    /**账户是否启用   0代表禁用,1代表启用*/
    private Integer enable;

    /**次账号的权限列表*/
    private List<String> permissions;
}
