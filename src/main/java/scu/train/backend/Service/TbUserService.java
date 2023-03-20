package scu.train.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbUser;

@Service
public class TbUserService {

    @Autowired
    TbUserDao tbUserDao;
    public TbUser getUser(){
        return tbUserDao.selectByPrimaryKey(123);
    }

}
