package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbFinancingIntention;
import scu.train.backend.Entity.TbUserFinance;

import java.util.List;

@Mapper
public interface TbFinancingIntentionDao {
    int deleteByPrimaryKey(Integer intentionId);

    int insert(TbFinancingIntention record);

    int insertSelective(TbFinancingIntention record);

    TbFinancingIntention selectByPrimaryKey(Integer intentionId);

    int updateByPrimaryKeySelective(TbFinancingIntention record);

    int updateByPrimaryKey(TbFinancingIntention record);

    List<TbFinancingIntention> selectByAccount(Integer account);

    List<TbFinancingIntention> selectByAddress(String address);

    Integer selectByBankId(Integer financeId);
}