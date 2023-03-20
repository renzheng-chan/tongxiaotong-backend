package scu.train.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.RedisCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtUtil jwtUtil;

    public Result<String> logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authenticationToken.getPrincipal();
        String account = userDetails.getUsername();
        //删除redis中的值
        redisCache.deleteObject("login:"+account);
        return new Result<>(true,StatusCode.OK,"注销成功","");
    }

    public Result<Map<String,String>> userLogin(TbUser tbUser) {
        // AuthenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tbUser.getAccount()+" "+"user",tbUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出对应的提示
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }
        TbUser user = (TbUser) authentication.getPrincipal();
        // 如果认证通过了，使用account生成一个jwt
        String jwt = jwtUtil.createToken(tbUser);
        System.out.println(jwt);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("role",user.getRole());
        // 将jwt和UserDetails信息存入redis中
        redisCache.setCacheObject("login:"+tbUser.getAccount(),user);
        return new Result<>(true, StatusCode.OK,"登录成功",map);
    }

    public Result<Map<String,String>> bankLogin(TbBankUser tbBankUser) {
        // AuthenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tbBankUser.getAccount()+" "+"bank",tbBankUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出对应的提示
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }
        TbBankUser bankUser = (TbBankUser) authentication.getPrincipal();
        // 如果认证通过了，使用tbBankUser生成一个jwt
        String jwt = jwtUtil.createToken(tbBankUser);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("role",bankUser.getRole());
        // 将jwt和UserDetails信息存入redis中
        redisCache.setCacheObject("login:"+tbBankUser.getAccount(),bankUser);
        return new Result<>(true, StatusCode.OK,"登录成功",map);
    }
}
