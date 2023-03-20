package scu.train.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import scu.train.backend.Entity.TbProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbProductDao {
    int deleteByPrimaryKey(Integer productId);
    //查询全部
    List<TbProduct> selectAll();
    //条件查询
    List<TbProduct> selectCondition(TbProduct tbProduct);

    int insert(TbProduct record);

    int insertSelective(TbProduct record);

    TbProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(TbProduct record);

    int updateByPrimaryKeyWithBLOBs(TbProduct record);

    int updateByPrimaryKey(TbProduct record);

    List<TbProduct> selectByAccount(Integer account);
}