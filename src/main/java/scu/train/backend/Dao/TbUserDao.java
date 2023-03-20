package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbUser;

import java.util.List;

@Mapper
public interface TbUserDao {
    int deleteByPrimaryKey(Integer account);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer account);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKeyWithBLOBs(TbUser record);

    int updateByPrimaryKey(TbUser record);

    TbUser loadUserAccount(Integer account);

    List<TbUser> selectAll();

    List<TbUser> selectCondition(TbUser tbUser);
}