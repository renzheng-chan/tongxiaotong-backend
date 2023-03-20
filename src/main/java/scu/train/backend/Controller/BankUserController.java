package scu.train.backend.Controller;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;


import scu.train.backend.Dao.TbUserFinanceDao;
import scu.train.backend.Entity.TbBankUser;

import scu.train.backend.Entity.TbFinancingIntention;
import scu.train.backend.Service.BankUserService;
import scu.train.backend.utils.PasswordParm;


import java.util.Date;
import java.util.Map;


@RestController
@Api(tags = "银行工作人员模块接口")
public class BankUserController {
    @Autowired
    private BankUserService bankUserService;
    //用户登陆之后，修改用户密码
    @ApiOperation(value = "银行用户登陆之后，修改用户密码")
    @PostMapping("/bankUser/updatePassword")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<String> UpdatePassword(@RequestBody PasswordParm passwordParm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbBankUser tbBankUser = (TbBankUser) authentication.getPrincipal();
        Integer account = tbBankUser.getAccount();
        TbBankUser bankUser = bankUserService.selectByAccount(account);
        //数据库密码
        String oldDataBasePassword = bankUser.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //传过来的老密码
        String oldPassword = bCryptPasswordEncoder.encode(passwordParm.getOldPassword());
        if (!bCryptPasswordEncoder.matches(oldPassword, oldDataBasePassword)) {
            return new Result<>(false, StatusCode.ERROR, "原密码输入错误","");
        }
        //密码相同
        bankUser.setPassword(passwordParm.getNewPassword());
        bankUserService.updatePassword(bankUser);
        return new Result<>(true, StatusCode.OK, "修改密码成功");
    }

    //用户登陆之后，根据用户名展示个人信息
    @ApiOperation(value = "用户登录之后，查询个人信息")
    @GetMapping("/bankUser/selectInfo")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<TbBankUser> showInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbBankUser tbBankUser = (TbBankUser) authentication.getPrincipal();
        return new Result<>(true, StatusCode.OK, "查询成功", tbBankUser);
    }

    //用户登陆之后，根据用户名修改个人基本信息
    @ApiOperation(value = "用户登录之后，修改个人基本信息")
    @PostMapping("/bankUser/modifyInfo")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<String> updateInfo(@RequestBody TbBankUser tbBankUser) {
        tbBankUser.setUpdateTime(new Date());
        return bankUserService.updateInfo(tbBankUser);
    }

    //处理融资申请
    @ApiOperation(value = "处理融资申请")
    @PostMapping("/bankUser/handleapply")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<String> process(@RequestBody TbFinancingIntention tbFinancingIntention){
       return bankUserService.process(tbFinancingIntention);
    }
}

