package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbBankDao;
import scu.train.backend.Entity.TbBank;
import scu.train.backend.Entity.TbOrder;
import scu.train.backend.Entity.TbProduct;

import java.util.List;
@Service
public class BankService {

    @Autowired
    private TbBankDao tbBankDao;
    private Integer pageSize;
    public PageInfo<TbBank> selectAllBank(Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        //查询银行情报
        List<TbBank> list =  tbBankDao.selectAllBank();
        return new PageInfo<>(list);
    }

    public Result<String> addBank(TbBank tbBank) {
        //添加银行情报
        int i = tbBankDao.insert(tbBank);
        return new Result<>(i==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"添加银行成功":"添加银行失败","");
    }

    public void update(TbBank tbBank) {
        //更新银行情报
        tbBankDao.updateByPrimaryKeySelective(tbBank);
    }

    public Result<String> deleteById(Integer orderId) {
        //删除银行情报
        int i = tbBankDao.deleteByPrimaryKey(orderId);
        return new Result<>(i ==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"删除银行情报成功":"删除银行情报失败","");
    }
}
