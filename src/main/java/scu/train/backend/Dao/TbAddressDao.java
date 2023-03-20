package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbAddress;
@Mapper
public interface TbAddressDao {
    int deleteByPrimaryKey(Integer addressId);

    int insert(TbAddress record);

    int insertSelective(TbAddress record);

    TbAddress selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(TbAddress record);

    int updateByPrimaryKey(TbAddress record);

    Integer selectByAccountDefault(Integer account);
}