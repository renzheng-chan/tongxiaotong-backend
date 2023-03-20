package scu.train.backend.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_order
 * @author 
 */
@Data
public class TbOrder implements Serializable {
    /**
     * 需求id
     */
    private Integer orderId;

    /**
     * 标题
     */
    private String title;


    /**
     * 期望价格
     */
    private BigDecimal price;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态：需求是否已经满足
     */
    private Boolean orderStatus;

    /**
     * 类型：期望买，还是期望卖
     */
    private String type;

    private String publisherName;

    /**
     * 发布者
     */
    private Integer publisherAccount;

    /**
     * 交易方式
     */
    private String exchangeMeans;

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

