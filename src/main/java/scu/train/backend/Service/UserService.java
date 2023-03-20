package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbAddressDao;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.RedisCache;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private TbUserDao tbUserDao;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisCache redisCache;
    @Autowired
    private TbAddressDao tbAddressDao;
    //每页显示多条数据
    private Integer pageSize = 30;

    public PageInfo<TbUser> selectAll(Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        //查询所有用户
        List<TbUser> users = tbUserDao.selectAll();
        return new PageInfo<>(users);
    }

    public int updateSelective(TbUser tbUser) {
        return tbUserDao.updateByPrimaryKeySelective(tbUser);
    }

    public int deleteByAccount(Integer account) {
        return tbUserDao.deleteByPrimaryKey(account);
    }

    public TbUser selectByAccount(Integer account) {
        return tbUserDao.selectByPrimaryKey(account);
    }
    public Result<String> updateInfo(TbUser tbUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取老的bankUser
        TbUser user = (TbUser) authentication.getPrincipal();
        Integer account = user.getAccount();
        tbUser.setAccount(account);
        //修改个人信息,将tbUser存入数据库中
        if(tbUserDao.updateByPrimaryKeySelective(tbUser)!=1){
            return new Result<>(false, StatusCode.ERROR,"修改失败");
        }
        TbUser newUser = tbUserDao.selectByPrimaryKey(account);
        //修改redis中的数据
        // bug tbUser可能为空
        redisCache.setCacheObject("login:"+tbUser.getAccount(),newUser);
        //这次访问已经结束，下次访问会自动更新SecurityContext
        //因为修改了个人信息，返回一个新的jwtToken //《==不需要
        return new Result<>(true, StatusCode.OK, "修改成功","");
    }

    public PageInfo<TbUser> selectCondition(TbUser tbUser, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        //查询所有用户
        List<TbUser> users = tbUserDao.selectCondition(tbUser);
        return new PageInfo<>(users);

    }

    public boolean setAvatar(String avatar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser user = (TbUser) authentication.getPrincipal();
        user.setAvatar(avatar);
        int i = tbUserDao.updateByPrimaryKeyWithBLOBs(user);
        return i ==1;
    }
}
