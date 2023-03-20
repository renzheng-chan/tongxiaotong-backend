package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbPurchase;
import scu.train.backend.Entity.TbPurchaseDetail;
import scu.train.backend.Service.PurchaseDetailService;
import scu.train.backend.Service.PurchaseService;

import java.util.List;
@RestController
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseDetailService purchaseDetailService;
    @ApiOperation("查询自己的购买订单情况")
    @GetMapping("/purchase/selectMineBuy/{pageNum}")
    public Result<PageInfo<TbPurchase>> selectPurchaseBuy(@PathVariable Integer pageNum){
        PageInfo<TbPurchase> purchase = purchaseService.selectByAccount(pageNum,"Buy");
        return new Result<>(true, StatusCode.OK,"查询成功",purchase);

    }
    @ApiOperation("查询自己的售出订单情况")
    @GetMapping("/purchase/selectMineSell/{pageNum}")
    public Result selectPurchaseSell(@PathVariable Integer pageNum){
        PageInfo<TbPurchase> purchase = purchaseService.selectByAccount(pageNum,"Sell");
        return new Result<>(true, StatusCode.OK,"查询成功",purchase);
    }
    @ApiOperation("查询订单详情")
    @GetMapping("/purchase/selectDetail/{purchaseId}")
    public Result selectBuysDetail(@PathVariable Integer purchaseId){
        TbPurchaseDetail tbPurchaseDetail = purchaseDetailService.selectByPurchaseId(purchaseId);
        return new Result<>(true, StatusCode.OK,"查询成功",tbPurchaseDetail);
    }
}
