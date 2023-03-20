package scu.train.backend.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Arrays;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_product
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbProduct implements Serializable {
    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 货源
     */
    private String source;

    private String nickName;

    /**
     * 商品信息
     */
    private String info;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 所属账号
     */
    private Integer belongAccount;

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

    /**
     * 商品图片
     */
    private String picture;

    private static final long serialVersionUID = 1L;
}