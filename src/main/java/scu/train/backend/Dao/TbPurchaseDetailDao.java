package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import scu.train.backend.Entity.TbPurchaseDetail;
@Mapper
public interface TbPurchaseDetailDao {
    int deleteByPrimaryKey(Integer detailId);

    // 接口16
    int insert(TbPurchaseDetail record);

    int insertSelective(TbPurchaseDetail record);

    TbPurchaseDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(TbPurchaseDetail record);

    int updateByPrimaryKey(TbPurchaseDetail record);

    // 接口16
    TbPurchaseDetail selectByPurchaseId(Integer purchaseId);
}