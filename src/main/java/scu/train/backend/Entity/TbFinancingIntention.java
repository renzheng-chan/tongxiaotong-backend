package scu.train.backend.Entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_financing_intention
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbFinancingIntention implements Serializable {
    /**
     * 意向id
     */
    private Integer intentionId;

    /**
     * 用户账户
     */
    private Integer account;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 融资政策id
     */
    private Integer financeId;

    /**
     * 融资用途
     */
    private String purpose;

    /**
     * 融资农作物
     */
    private String item;

    /**
     * 还款日期
     */
    private Date repaymentPeriod;

    /**
     * 种植面积
     */
    private Integer area;

    /**
     * 创建时间
     */

    private static final long serialVersionUID = 1L;

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

    //融资状态
    private Integer status;

    //融资结果
    private String result;

}