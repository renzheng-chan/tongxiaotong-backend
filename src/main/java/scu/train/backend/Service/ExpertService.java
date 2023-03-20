package scu.train.backend.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbExpertDao;
import scu.train.backend.Entity.TbExpert;
import scu.train.backend.Service.ExpertService;

import java.util.List;

@Service
public class ExpertService {

    @Autowired
    TbExpertDao tbExpertDao;

    private  Integer pageSize = 2;

    // 接口11
    public PageInfo<TbExpert> findPage(Integer pageNum){
        PageHelper.startPage(pageNum,2);
        List<TbExpert> experts = tbExpertDao.selectAllExpert();
        PageInfo<TbExpert> expertPageInfo = new PageInfo<>(experts);
        return expertPageInfo;
    }


    // 接口11
    public PageInfo<TbExpert> findPageByKeys(String keys,Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<TbExpert> experts = tbExpertDao.selectAllByKeys(keys);
        PageInfo<TbExpert> orderPageInfo = new PageInfo<>(experts);
        return orderPageInfo;
    }


}
