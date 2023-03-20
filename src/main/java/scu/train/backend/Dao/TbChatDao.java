package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import scu.train.backend.Entity.TbChat;

import java.util.Date;
import java.util.List;

@Mapper
public interface TbChatDao {
    int deleteByPrimaryKey(Integer chatId);

    int insert(TbChat record);

    int insertSelective(TbChat record);

    TbChat selectByPrimaryKey(Integer chatId);

    int updateByPrimaryKeySelective(TbChat record);

    int updateByPrimaryKey(TbChat record);

    List<TbChat> selectChatInfo(@Param("account1") Integer account1,@Param("account2") Integer account2);
}