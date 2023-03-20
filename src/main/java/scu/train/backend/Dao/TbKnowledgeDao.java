package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import scu.train.backend.Entity.TbKnowledge;

import java.util.List;

@Mapper
public interface TbKnowledgeDao {
    int deleteByPrimaryKey(Integer articleId);

    int insert(TbKnowledge record);

    int insertSelective(TbKnowledge record);

    TbKnowledge selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(TbKnowledge record);

    int updateByPrimaryKey(TbKnowledge record);

    List<TbKnowledge> selectAll();

    List<TbKnowledge> selectByAccount(@Param("account")Integer account);

    List<TbKnowledge> selectCondition(TbKnowledge tbKnowledge);
}