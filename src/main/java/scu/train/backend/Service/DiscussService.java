package scu.train.backend.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbCommentDao;
import scu.train.backend.Dao.TbDiscussDao;
import scu.train.backend.Entity.TbComment;
import scu.train.backend.Entity.TbDiscuss;
import scu.train.backend.Service.DiscussService;

import java.util.List;

@Service
public class DiscussService {

    private static final Integer pageSize = 30;
    @Autowired
    TbDiscussDao tbDiscussDao;

    public PageInfo<TbDiscuss> selectAllDiscuss(Integer pageNum){
        List<TbDiscuss> tbDiscusses =  tbDiscussDao.selectAllDiscuss();
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(tbDiscusses);
    }

    public PageInfo<TbDiscuss> selectAllDiscussByCondition(Integer pageNum,TbDiscuss record){
        List<TbDiscuss> tbDiscusses = tbDiscussDao.selectAllDiscussByCondition(record);
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(tbDiscusses);
    }

    public PageInfo<TbDiscuss> selectAllDiscussByConditions(Integer pageNum,TbDiscuss record){
        List<TbDiscuss> tbDiscusses = tbDiscussDao.selectAllDiscussByConditions(record);
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(tbDiscusses);
    }

    public void insertBySelective(TbDiscuss tbDiscuss){
        tbDiscussDao.insertSelective(tbDiscuss);
    }

    public void deleteByPrimaryKey(Integer id){
        tbDiscussDao.deleteByPrimaryKey(id);
    }


}
