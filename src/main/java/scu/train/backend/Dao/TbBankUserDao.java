package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbBankUser;

import java.util.List;

@Mapper
public interface TbBankUserDao {
    int deleteByPrimaryKey(Integer bankuserAccount);

    int insert(TbBankUser record);

    int insertSelective(TbBankUser record);

    TbBankUser selectByPrimaryKey(Integer account);

    int updateByPrimaryKeySelective(TbBankUser record);

    int updateByPrimaryKey(TbBankUser record);

    List<TbBankUser> selectAllBankUser (Integer bankid);

    TbBankUser selectByAccount(Integer account);
}