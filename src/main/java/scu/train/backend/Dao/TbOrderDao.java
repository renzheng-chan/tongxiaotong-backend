package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbOrder;
import java.util.List;
@Mapper
public interface TbOrderDao {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    TbOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);

    List<TbOrder> selectCondition(TbOrder tbOrder);


    List<TbOrder> selectAll();

    List<TbOrder> selectByAccount(Integer account);
}