package scu.train.backend.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbPurchaseDao;
import scu.train.backend.Entity.TbPurchase;
import scu.train.backend.Entity.TbUser;

import java.util.List;


@Service
public class PurchaseService {

    @Autowired
    TbPurchaseDao tbPurchaseDao;

    private Integer pageSize = 30;

    public PageInfo<TbPurchase> selectByAccount(Integer pageNum,String type) {
        //获取用户id
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account = Integer.valueOf(principal.getUsername());
        PageHelper.startPage(pageNum,pageSize);
        List<TbPurchase> tbPurchases = tbPurchaseDao.selectByAccount(account,type);
        return new PageInfo<>(tbPurchases);
    }

    public void add(TbPurchase purchase){
        tbPurchaseDao.insertSelective(purchase);
    }

    //接口16
    public TbPurchase selectNewPurchaseId(Integer account){
        TbPurchase purchase = tbPurchaseDao.selectNewPurchaseId(account);
        return purchase;
    }

    //接口17
    public List<TbPurchase> selectByPurchaseType(){
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer name = Integer.valueOf(tbUser.getUsername());
        List<TbPurchase> purchases = tbPurchaseDao.selectByPurchase(name);
        System.out.println(purchases);
        return purchases;
    }


}
