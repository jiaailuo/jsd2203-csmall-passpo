package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginDTO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginVO;
import cn.tedu.csmall.passport.security.LoginPrincipal;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Api(tags = "管理员管理模块")
@RestController
@RequestMapping("/admin")
public class AdminController {

    
    @Autowired
    private IAdminService adminService;

    public AdminController() {
        log.debug("创建控制器对象：AdminController");
    }

    // http://localhost:9081/admin/add-new
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    //添加权限  只有次权限才能进入
//    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @Transactional
    public JsonResult addNew(@RequestBody AdminAddNewDTO adminAddNewDTO) {
        log.debug("接收到的请求参数：{}", adminAddNewDTO);
        adminService.addNew(adminAddNewDTO);
        return JsonResult.ok();
    }

    // http://localhost:9081/admin
    @ApiOperation("查询列表")
    @PreAuthorize("hasAnyAuthority('/ams/admin/read','/pms/product/read')")//权限标尺
    @ApiOperationSupport(order = 401)
    @GetMapping("")
    /**
     @AuthenticationPrincipal LoginPrincipal loginPrincipal
     如果不添加注解  只会将参数默认为请求的值,而不是作为当事人的信息
     */
    public JsonResult list(@AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        log.debug("接收到查询管理员列表的请求");
        log.debug("当前认证信息:{}",loginPrincipal);
        Long id=loginPrincipal.getId();
        log.debug("从认证信息获取当前登录的管理员id:{}",id);;
        String username=loginPrincipal.getUsername();
        log.debug("从认证信息获取当前登录的管理员username:{}",username);
        List<AdminListItemVO> admins = adminService.list();
        return JsonResult.ok(admins);
    }

    //localhost:9081/admin/login
    @ApiOperationSupport(order = 400)
    @ApiOperation("登陆页面")
    @PostMapping("/login")
    public JsonResult login(@RequestBody AdminLoginDTO adminLoginDTO){
        log.debug("接受到的请求参数{}",adminLoginDTO);

        //TODO  需要调用Service组件处理登陆
        String jwt=adminService.login(adminLoginDTO);

        //通过日志进行简单的输出
        return JsonResult.ok(jwt);
    }
}
