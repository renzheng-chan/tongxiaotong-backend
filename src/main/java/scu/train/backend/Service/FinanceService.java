package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbBankFinanceDao;
import scu.train.backend.Entity.TbBankFinance;
import scu.train.backend.Entity.TbUser;

import java.util.Date;
import java.util.List;

@Service
public class FinanceService {
    @Autowired
    private TbBankFinanceDao tbBankFinanceDao;
    public Result<String> addFinance(TbBankFinance tbBankFinance) {
        int i = tbBankFinanceDao.insert(tbBankFinance);
        return new Result<>(i==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"添加政策成功":"添加政策失败","");
    }
    public List<TbBankFinance> selectByFinance(TbBankFinance tbBankFinance) {
        //查询个人融资
        List<TbBankFinance> tbBankFinances = tbBankFinanceDao.selectByFinance(tbBankFinance);
        return tbBankFinances;
    }

    public TbBankFinance selectByFinanceId(Integer id) {
        //根据id查询融资
        TbBankFinance tbBankFinance = tbBankFinanceDao.selectByPrimaryKey(id);
        return tbBankFinance;
    }

    public void deleteByFinanceId(Integer id) {
        //删除融资
        tbBankFinanceDao.deleteByPrimaryKey(id);
    }

    public void updateByFinance(TbBankFinance tbBankFinance) {
        //修改融资
        tbBankFinanceDao.updateByPrimaryKeySelective(tbBankFinance);
    }
    private final Integer pageSize = 30;

    public PageInfo<TbUser> selectRecommend(Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbUser>  userList = tbBankFinanceDao.selectRecommend();
        System.out.println(userList);
        //todo 优化sql
        return new PageInfo<>(userList);
    }

    public void updateFinance(TbBankFinance tbBankFinance) {
        tbBankFinanceDao.updateByPrimaryKey(tbBankFinance);
    }

    public TbBankFinance selectById(Integer id) {
        return tbBankFinanceDao.selectByPrimaryKey(id);
    }

    public PageInfo<TbBankFinance> selectByBankId(Integer bankId, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbBankFinance> list = tbBankFinanceDao.selectByBankId(bankId);
        return new PageInfo<>(list);
    }

}
