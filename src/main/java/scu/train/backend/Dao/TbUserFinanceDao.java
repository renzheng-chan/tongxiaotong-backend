package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbUserFinance;

import java.util.List;

@Mapper
public interface TbUserFinanceDao {
    int deleteByPrimaryKey(Integer userFinanceId);

    int insert(TbUserFinance record);

    int insertSelective(TbUserFinance record);

    TbUserFinance selectByPrimaryKey(Integer userFinanceId);

    int updateByPrimaryKeySelective(TbUserFinance record);

    int updateByPrimaryKey(TbUserFinance record);

    List<TbUserFinance> selectByAccount(Integer account);
}