package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbPurchaseDao;
import scu.train.backend.Dao.TbUserDao;

import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.UserDetailServiceImpl;
import scu.train.backend.Service.UserService;
import scu.train.backend.utils.DefaultImage;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.PasswordParm;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "用户模块接口")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private TbUserDao tbUserDao;
    @Autowired
    private PasswordParm passwordParm;
    @Autowired
    private DefaultImage defaultImage;

    //修改用户密码
    @ApiOperation(value = "修改用户密码")
    @PostMapping("/user/updatePassword")
    @PreAuthorize("hasAnyAuthority('admin','expert','user')")
    public Result<String> updatePassword(@RequestBody PasswordParm passwordParm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser tbUser = (TbUser) authentication.getPrincipal();
        Integer account = tbUser.getAccount();
        TbUser user = userService.selectByAccount(account);
        //数据库密码
        String oldDataBasePassword = user.getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //传过来的老密码
        String oldPassword = bCryptPasswordEncoder.encode(passwordParm.getOldPassword());
        if (!bCryptPasswordEncoder.matches(oldPassword, oldDataBasePassword)) {
            return new Result<>(false, StatusCode.ERROR, "原密码输入错误","");
        }
        //密码相同
        user.setPassword(passwordParm.getNewPassword());
        int i = userService.updateSelective(user);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1? "修改密码成功":"修改密码失败","");
    }

    @ApiOperation(value = "查询个人信息")
    @GetMapping("/user/selectInfo")
    @PreAuthorize("hasAnyAuthority('admin','expert','user')")
    public Result<TbUser> selectInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser tbUser = (TbUser) authentication.getPrincipal();
        Integer account = tbUser.getAccount();
        TbUser user = userService.selectByAccount(account);
        return new Result<>(true, StatusCode.OK,"查询成功",user);
    }

    @ApiOperation(value = "修改个人基本信息")
    @PostMapping("/user/updateInfo")
    @PreAuthorize("hasAnyAuthority('admin','expert','user')")
    public Result<String> updateInfo(@RequestBody TbUser tbUser) {
        return userService.updateInfo(tbUser);
    }

    //查询所有用户
    @ApiOperation(value = "查询所有用户")
    @GetMapping("/user/queryAllUser/{pageNum}")
    @PreAuthorize("hasAuthority('admin')")
    public Result<PageInfo<TbUser>> selectAll(@PathVariable Integer pageNum){
        PageInfo<TbUser> tbUsers = userService.selectAll(pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",tbUsers);
    }

    //删除用户
    @ApiOperation(value = "根据用户id删除用户")
    @DeleteMapping("/user/deleteUser/{account}")
    public Result<String> deletes(@PathVariable("account") Integer account) {
        int i = userService.deleteByAccount(account);
        return new Result<>(i==1,i==1?StatusCode.OK:StatusCode.ERROR,"删除成功","");
    }

    //分页条件查询
    @ApiOperation(value = "分页条件查询用户")
    @PostMapping("/user/selectCondition/{pageNum}")
    public Result<PageInfo<TbUser>> selectCondition(@RequestBody TbUser tbUser,
                                     @PathVariable("pageNum") Integer pageNum){
        PageInfo<TbUser> pageInfo = userService.selectCondition(tbUser, pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    //todo 待测试
    @ApiOperation("用户上传头像")
    @PostMapping("/user/uploadIcon")
    public Result<String> uploadAvatar(@RequestPart("Icon") MultipartFile file) throws Exception {
        //先保存文件同时，返回byte[] 数组
        String avatar = null;
        //将数组设置为当前头像
        boolean flag = userService.setAvatar(avatar);
        return new Result<>(flag, flag?StatusCode.OK:StatusCode.ERROR,flag?"上传头像成功":"上传头像失败","");

    }
}


