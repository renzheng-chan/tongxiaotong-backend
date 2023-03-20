package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbBankFinance;
import scu.train.backend.Entity.TbUser;

import java.util.List;

@Mapper
public interface TbBankFinanceDao {
    int deleteByPrimaryKey(Integer financeId);

    int insert(TbBankFinance record);

    int insertSelective(TbBankFinance record);

    TbBankFinance selectByPrimaryKey(Integer financeId);

    int updateByPrimaryKeySelective(TbBankFinance record);

    int updateByPrimaryKey(TbBankFinance record);

    void insertMulti(TbBankFinance tbBankFinance);

    List<TbBankFinance> selectByFinance(TbBankFinance tbBankFinance);

    List<TbUser> selectRecommend();

    List<TbBankFinance> selectByBankId(Integer bankId);
}