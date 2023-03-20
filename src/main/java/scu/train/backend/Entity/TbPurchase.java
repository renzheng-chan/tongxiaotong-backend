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
 * tb_purchase
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbPurchase implements Serializable {
    /**
     * 订单id
     */
    private Integer purchaseId;

    /**
     * 账号
     */
    private Integer account;

    /**
     * 类型
     */
    private String purchaseType;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 地址
     */
    private Integer addressId;

    /**
     * 订单状态
     */
    private Integer purchaseStatus;

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