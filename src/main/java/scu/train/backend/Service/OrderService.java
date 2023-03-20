package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbOrderDao;
import scu.train.backend.Entity.TbOrder;
import scu.train.backend.Entity.TbUser;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderService {
    public int pageSize = 3;

    @Autowired
    TbOrderDao tbOrderDao;

    public PageInfo<TbOrder> selectOrdersCondition(TbOrder tbOrder,Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbOrder> orders = tbOrderDao.selectCondition(tbOrder);
        return new PageInfo<>(orders);

    }


    public void update(TbOrder tbOrder) {
        tbOrderDao.updateByPrimaryKeySelective(tbOrder);
    }

    public Result<String> deleteById(Integer id) {
        int i = tbOrderDao.deleteByPrimaryKey(id);
        return new Result<>(i ==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"删除需求成功":"删除需求失败","");
    }

    public PageInfo<TbOrder> selectAll(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbOrder> goods = tbOrderDao.selectAll();
        return new PageInfo<>(goods);
    }

    public Result<String> publishOrder(TbOrder tbOrder) {
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tbOrder.setPublisherAccount(tbUser.getAccount());
        tbOrder.setPublisherName(tbUser.getNickName());
        tbOrder.setCreateTime(new Date());
        tbOrder.setUpdateTime(new Date());
        int i = tbOrderDao.insert(tbOrder);
        return new Result<>(i ==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"发布需求成功":"发布需求失败","");
    }

    public Result<PageInfo<TbOrder>> selectMyOrder(Integer pageNum) {
        TbUser tbUser = (TbUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageHelper.startPage(pageNum,pageSize);
        List<TbOrder> list = tbOrderDao.selectByAccount(tbUser.getAccount());
        PageInfo<TbOrder> pageInfo = new PageInfo<>(list);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }
}
