package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbFinancingIntentionDao;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Dao.TbUserFinanceDao;
import scu.train.backend.Entity.TbFinancingIntention;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Entity.TbUserFinance;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceIntentionService {
    @Autowired
    TbFinancingIntentionDao tbFinancingIntentionDao;

    @Autowired
    TbUserFinanceDao tbUserFinanceDao;

    @Autowired
    TbUserDao tbUserDao;

    private final Integer pageSize = 30;
    public PageInfo<TbUserFinance> selectByAccount(Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TbUserFinance> userFinanceList = tbUserFinanceDao.selectByAccount(tbUser.getAccount());
        return new PageInfo<>(userFinanceList);
    }

    public boolean insertIntention(TbFinancingIntention tbFinancingIntention) {
        return tbFinancingIntentionDao.insert(tbFinancingIntention)==1;
    }

    public boolean updateIntention(TbFinancingIntention tbFinancingIntention) {
        return tbFinancingIntentionDao.updateByPrimaryKey(tbFinancingIntention) == 1;
    }

    public boolean deleteIntention(Integer financeId) {
        return tbFinancingIntentionDao.deleteByPrimaryKey(financeId) ==1;
    }
    public PageInfo<TbUser> intentionRecommend(Integer pageNum) {
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //查询登录用户的地区
        List<TbFinancingIntention> intentionList = tbFinancingIntentionDao.selectByAccount(Integer.valueOf(tbUser.getUsername()));
        //根据地区模糊匹配该地区的其他融资人
        List<TbUser> userList = new ArrayList<>();
        for (TbFinancingIntention intention:intentionList) {
            String address = intention.getAddress();
            List<TbFinancingIntention> tb = tbFinancingIntentionDao.selectByAddress(address);
            for (TbFinancingIntention tbFinancingIntention:tb) {
                Integer account = Integer.valueOf(tbFinancingIntention.getAccount());
                TbUser tbuser = tbUserDao.selectByPrimaryKey(account);
                userList.add(tbuser);
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(userList);


    }
}
