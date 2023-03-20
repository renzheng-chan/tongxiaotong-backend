package scu.train.backend.Dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import scu.train.backend.Entity.TbShoppingcart;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TbShoppingcartDao {
    int deleteByPrimaryKey(Integer shoppingcartId);

    int insert(TbShoppingcart record);

    int insertSelective(TbShoppingcart record);

    TbShoppingcart selectByPrimaryKey(Integer shoppingcartId);

    int updateByPrimaryKeySelective(TbShoppingcart record);

    int updateByPrimaryKey(TbShoppingcart record);


    // 接口15
    List<TbShoppingcart> selectByShopping(Integer name);

    //接口15
    BigDecimal getProductPriceById(Integer id);

    List<TbShoppingcart> selectByAccount(Integer account);

    int updateCount(@Param("shoppingId")Integer shoppingCartId,@Param("count") Integer count);
}