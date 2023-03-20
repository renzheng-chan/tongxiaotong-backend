package scu.train.backend.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_shoppingcart
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbShoppingcart implements Serializable {
    /**
     * 购物车id
     */
    private Integer shoppingcartId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer count;

    private BigDecimal price;
    /**
     * 用户账号
     */
    private Integer account;
    /**
     * 创建时间
     * 注解@JsonFormat主要是后台到前台的时间格式的转换
     * 注解@DataFormat主要是前台到后台的时间格式的转换
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}