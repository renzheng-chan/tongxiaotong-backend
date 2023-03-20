package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbBank;

import java.util.List;

@Mapper

public interface TbBankDao {
    int deleteByPrimaryKey(Integer bankId);

    int insert(TbBank record);

    int insertSelective(TbBank record);

    TbBank selectByPrimaryKey(Integer bankId);

    int updateByPrimaryKeySelective(TbBank record);

    int updateByPrimaryKey(TbBank record);

    List<TbBank> selectAllBank();
}