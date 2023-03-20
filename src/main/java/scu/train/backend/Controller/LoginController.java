package scu.train.backend.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.LoginService;
import scu.train.backend.Service.UserDetailServiceImpl;
import scu.train.backend.Common.Result;

import java.util.Map;
@Api("任务1 登录模块")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    //定义登录接口
    @PostMapping("/user/login")
    public Result<Map<String,String>>  userLogin(@RequestBody TbUser tbUser){
        return loginService.userLogin(tbUser);
    }
    @PostMapping("/bank/login")
    public Result<Map<String,String>> bankLogin(@RequestBody TbBankUser tbBankUser){
        return loginService.bankLogin(tbBankUser);
    }
    //此处路由不能使用logout，神奇的错误
    @RequestMapping("/userLogout")
    @PreAuthorize("hasAnyAuthority('expert','user','admin','bankUser')")
    public Result<String> logout(){
        return loginService.logout();
    }
}
