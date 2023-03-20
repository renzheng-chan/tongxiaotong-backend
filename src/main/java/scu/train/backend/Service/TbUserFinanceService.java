package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbUserFinanceDao;
import scu.train.backend.Entity.TbUserFinance;

import java.util.List;

@Service
public class TbUserFinanceService {
    @Autowired
    TbUserFinanceDao tbUserFinanceDao;

    private final Integer pageSize = 30;
    public PageInfo<TbUserFinance> selectByAccount(Integer account, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbUserFinance> tbUserFinanceList = tbUserFinanceDao.selectByAccount(account);
        return new PageInfo<>(tbUserFinanceList);
    }
}
