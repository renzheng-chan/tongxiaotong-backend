package scu.train.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbBankUserDao;
import scu.train.backend.Dao.TbExpertDao;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbExpert;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.utils.DefaultImage;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.RedisCache;
import scu.train.backend.utils.UniqueID;

import java.util.*;

@Service
public class SignInService {
    @Autowired
    TbUserDao tbUserDao;
    @Autowired
    TbBankUserDao tbBankUserDao;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginService loginService;

    @Autowired
    UniqueID uniqueID;
    @Autowired
    DefaultImage defaultImage;
    @Autowired
    TbExpertDao tbExpertDao;


    @Autowired
    RedisCache redisCache;
    public HashMap<String, String> userRegister(TbUser tbUser) {
        //将用户密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(tbUser.getPassword());
        //为用户生成账号
        Integer account = uniqueID.generateAccount();
        //将用户信息存入后台
        String str = defaultImage.getDefaultIcon();
        tbUser.setAvatar(defaultImage.getDefaultIcon());
        tbUser.setPassword(password);
        tbUser.setAccount(account);
        tbUser.setCreateTime(new Date());
        tbUser.setUpdateTime(new Date());
        //默认信誉为60
        tbUser.setCredit(60);
        //默认积分为0
        tbUser.setIntegral(0);
        tbUserDao.insertSelective(tbUser);
        //更新expert表
        if(tbUser.getRole().equals("expert")){
            TbExpert tbExpert = new TbExpert();
            tbExpert.setAccount(account);
            tbExpert.setExpertName(tbUser.getRealName());
            tbExpert.setExpertPhoto(defaultImage.getDefaultIcon());
            tbExpertDao.insertSelective(tbExpert);
        }
        //将UserDetails存入上下文中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(tbUser,null,tbUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //将userDetail存入redis
        redisCache.setCacheObject("login:"+account,tbUser);
        //将jwt返回给Controller
        String jwt = jwtUtil.createToken(tbUser);
        HashMap<String,String> map = new HashMap<>();
        map.put("account",account+"");
        map.put("token",jwt);
        map.put("role",tbUser.getRole());
        return map;
    }
    public HashMap<String, String> bankRegister(TbBankUser tbBankUser) {
        //将用户密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(tbBankUser.getPassword());
        //为用户生成账号
        Integer account = uniqueID.generateAccount();
        //将用户信息存入后台
        String str = defaultImage.getDefaultIcon();
        tbBankUser.setAvatar(defaultImage.getDefaultIcon());
        tbBankUser.setPassword(password);
        tbBankUser.setAccount(account);
        tbBankUserDao.insert(tbBankUser);
        //将UserDetails存入上下文中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(tbBankUser,null,tbBankUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //将userDetail存入redis
        redisCache.setCacheObject("login:"+account,tbBankUser);
        //将jwt返回给Controller
        String jwt = jwtUtil.createToken(tbBankUser);
        HashMap<String,String> map = new HashMap<>();
        map.put("account",account+"");
        map.put("token",jwt);
        map.put("role",tbBankUser.getRole());
        return map;
    }


}
