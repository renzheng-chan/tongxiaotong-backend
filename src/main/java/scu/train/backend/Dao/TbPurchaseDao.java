package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import scu.train.backend.Entity.TbPurchase;

import java.util.List;

@Mapper
public interface TbPurchaseDao {
    int deleteByPrimaryKey(Integer purchaseId);

    int insert(TbPurchase record);

    int insertSelective(TbPurchase record);

    TbPurchase selectByPrimaryKey(Integer purchaseId);

    int updateByPrimaryKeySelective(TbPurchase record);

    int updateByPrimaryKey(TbPurchase record);

    List<TbPurchase> selectByAccount(@Param("account")Integer account,@Param("type") String type);

    // 接口16
    TbPurchase selectNewPurchaseId(Integer account);

    // 接口17
    List<TbPurchase> selectByPurchase(Integer name);
}