package scu.train.backend.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_purchase_detail
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbPurchaseDetail implements Serializable {
    /**
     * id
     */
    private Integer detailId;

    /**
     * 订单id
     */
    private Integer purchaseId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 单价
     */
    private BigDecimal uninPrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 总价格
     */
    private BigDecimal sumPrice;

    private static final long serialVersionUID = 1L;
}