package scu.train.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbBankUserDao;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbUser;

import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    TbUserDao tbUserDao;

    @Autowired
    TbBankUserDao tbBankUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username过来的时候携带身份信息，再根据身份信息选择数据库 "456789 bankUser"
        String[] id = username.split(" ");
        System.out.println(id[0]);
        System.out.println(id[1]);
        System.out.println(username);
        //查询username对应的用户信息
        Integer account = Integer.valueOf(id[0]);
        String role = id[1];
        //根据role选择表
        if(role.equals("user")){
            TbUser user =tbUserDao.selectByPrimaryKey(account);
            if(Objects.isNull(user)){
                throw new RuntimeException("用户名或者密码错误");
            }
            //把数据封装成UserDetail类
            return user;
        }else if(role.equals("bank")){
            TbBankUser bankUser = tbBankUserDao.selectByPrimaryKey(account);
            if(Objects.isNull(bankUser)){
                throw new RuntimeException("用户名或者密码错误");
            }
            //把数据封装成UserDetail类
            return bankUser;
        }
        return null;
    }
}
