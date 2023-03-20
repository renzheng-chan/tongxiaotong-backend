package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbDiscuss;

import java.util.List;

@Mapper
public interface TbDiscussDao {

    int insert(TbDiscuss record);

    int insertSelective(TbDiscuss record);

    List<TbDiscuss> selectAllDiscuss();

    List<TbDiscuss> selectAllDiscussByCondition(TbDiscuss record);

    List<TbDiscuss> selectAllDiscussByConditions(TbDiscuss record);
    void deleteByPrimaryKey(Integer discussId);
}
