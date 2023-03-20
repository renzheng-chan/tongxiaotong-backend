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
 * tb_bank_finance
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbBankFinance implements Serializable {
    /**
     * 政策ID
     */
    private Integer financeId;

    /**
     * 所属银行ID
     */
    private Integer bankId;

    /**
     * 融资金额
     */
    private Integer financeAmount;

    /**
     * 年利率
     */
    private BigDecimal rate;

    /**
     * 还款年限
     */
    private Integer repayTime;

    /**
     * 政策是否仍在使用
     */
    private Byte isexist;

    /**
     * 创建时间
     */

    /**
     * 最近一次更新时间
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

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Integer financeAmount) {
        this.financeAmount = financeAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Integer repayTime) {
        this.repayTime = repayTime;
    }

    public Byte getIsexist() {
        return isexist;
    }

    public void setIsexist(Byte isexist) {
        this.isexist = isexist;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
