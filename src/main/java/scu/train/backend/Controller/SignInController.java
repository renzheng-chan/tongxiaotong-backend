package scu.train.backend.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.SignInService;
import scu.train.backend.utils.RedisCache;

import java.util.HashMap;
import java.util.Map;

@Api("注册模块接口")
@RestController
public class SignInController {
    @Autowired
    SignInService signInService;
    @PostMapping("/user/SignIn")
    public Result<Map<String,String>> userRegister(@RequestBody TbUser tbUser){
        HashMap<String,String> map = signInService.userRegister(tbUser);
        return new Result<>(true, StatusCode.OK,"注册成功",map);
    }
    @PostMapping("/bank/SignIn")
    public Result<Map<String,String>> bankRegister(@RequestBody TbBankUser bankUser){
        HashMap<String,String> map = signInService.bankRegister(bankUser);
        return new Result<>(true, StatusCode.OK,"注册成功",map);
    }


}