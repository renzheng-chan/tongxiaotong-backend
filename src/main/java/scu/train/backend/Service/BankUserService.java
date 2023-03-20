package scu.train.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbBankUserDao;
import scu.train.backend.Dao.TbFinancingIntentionDao;
import scu.train.backend.Dao.TbUserFinanceDao;
import scu.train.backend.Entity.TbBankUser;
import scu.train.backend.Entity.TbFinancingIntention;
import scu.train.backend.Entity.TbUserFinance;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.RedisCache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BankUserService {
    @Autowired
    private TbBankUserDao tbBankUserDao;

    @Autowired
    private TbUserFinanceDao tbUserFinanceDao;

    @Autowired
    private TbFinancingIntentionDao tbfinancingIntentionDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisCache redisCache;


    public TbBankUser selectByAccount(Integer account) {
        return tbBankUserDao.selectByAccount(account);
    }

    public void updatePassword(TbBankUser tbBankUser) {
        tbBankUserDao.updateByPrimaryKey(tbBankUser);
    }

    public Result<String> updateInfo(TbBankUser tbBankUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取老的bankUser
        TbBankUser bankUser = (TbBankUser) authentication.getPrincipal();
        Integer bankAccount = bankUser.getAccount();

        tbBankUser.setAccount(bankAccount);
        //修改个人信息,将tbBanker存入数据库中
        if (tbBankUserDao.updateByPrimaryKey(tbBankUser) != 1) {
            return new Result<>(false, StatusCode.ERROR, "修改失败");
        }
        TbBankUser newBankUser = tbBankUserDao.selectByAccount(bankAccount);
        //修改redis中的数据
        redisCache.setCacheObject("login:" + tbBankUser.getAccount(), newBankUser);
        //这次访问已经结束，下次访问会自动更新SecurityContext
        //因为修改了个人信息，返回一个新的jwtToken
        return new Result<>(true, StatusCode.OK, "修改成功", "");
    }


    public void updateTable(TbUserFinance tbUserFinance) {
        //插入到tb_user_finance
        tbUserFinanceDao.insert(tbUserFinance);
    }

    public void deleteRecord(Integer id) {
        //从intention表里删除
        tbfinancingIntentionDao.deleteByPrimaryKey(id);
    }

    public Result<String> process(TbFinancingIntention tbFinancingIntention) {
        Integer id = tbFinancingIntention.getIntentionId();
        Integer status = tbFinancingIntention.getStatus();
        if (status == 1) {
            TbUserFinance tbUserFinance = new TbUserFinance();
            TbFinancingIntention financingIntention = tbfinancingIntentionDao.selectByPrimaryKey(id);
            //获取所有financeIntention参数
            tbUserFinance.setBankId(tbfinancingIntentionDao.selectByBankId(tbFinancingIntention.getFinanceId()));
            tbUserFinance.setAccount(tbFinancingIntention.getAccount());
            tbUserFinance.setFinanceId(tbFinancingIntention.getFinanceId());
            tbUserFinance.setStatus(tbFinancingIntention.getStatus());
            tbUserFinance.setRemark(tbFinancingIntention.getResult());
            tbUserFinance.setRepayTime(tbFinancingIntention.getCreateTime());
            tbUserFinance.setTradeTime(tbFinancingIntention.getCreateTime());
            tbUserFinance.setUpdateTime(new Date());
            tbUserFinanceDao.insert(tbUserFinance);
            tbfinancingIntentionDao.deleteByPrimaryKey(id);
        } else if (status == 2) {
            tbfinancingIntentionDao.updateByPrimaryKeySelective(tbFinancingIntention);
            tbFinancingIntention.setResult("融资申请失败");
        }
        return new Result<>(true, StatusCode.OK, "处理成功", "");
    }
}

