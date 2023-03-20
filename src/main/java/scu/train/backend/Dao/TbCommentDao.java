package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbComment;

import java.util.List;

@Mapper
public interface TbCommentDao {
    int deleteByPrimaryKey(Integer commentId);

    int insert(TbComment record);

    int insertSelective(TbComment record);

    TbComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(TbComment record);

    int updateByPrimaryKey(TbComment record);

    List<TbComment> selectByKnowledgeId(Integer id);
}