package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbExpert;

import java.util.List;

@Mapper
public interface TbExpertDao {
    int deleteByPrimaryKey(Integer expertAccount);

    int insert(TbExpert record);

    int insertSelective(TbExpert record);

    TbExpert selectByPrimaryKey(Integer expertAccount);

    int updateByPrimaryKeySelective(TbExpert record);

    int updateByPrimaryKey(TbExpert record);

    List<TbExpert> selectAllExpert();

    List<TbExpert> selectAllByKeys(String keys);

    TbExpert selectByAccount(Integer account);
}