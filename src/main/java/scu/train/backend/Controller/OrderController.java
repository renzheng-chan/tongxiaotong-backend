package scu.train.backend.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Entity.TbOrder;
import scu.train.backend.Entity.TbPurchase;
import scu.train.backend.Entity.TbPurchaseDetail;
import scu.train.backend.Filter.JwtAuthenticationTokenFilter;
import scu.train.backend.Service.OrderService;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Service.PurchaseDetailService;
import scu.train.backend.Service.PurchaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(tags = "订单模块接口")
@RestController
@RequestMapping()
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    //todo 查询需求
    //任务3
    //查询所有商品
    @ApiOperation(value = "查询所有需求")
    @GetMapping("/order/selectAll/{pageNum}")
    public Result<PageInfo<TbOrder>> selectAll(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<TbOrder> tborders = orderService.selectAll(pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",tborders);
    }
     //分页条件查询所有需求
    //分页条件搜索需求
    @ApiOperation(value =  "分页条件查询所有需求")
    @RequestMapping(value = "/order/searchCondition/{pageNum}",method = RequestMethod.POST)
    public Result<PageInfo<TbOrder>> searchOrdersCondition(@RequestBody(required = false) TbOrder tbOrder, @PathVariable("pageNum") Integer pageNum){
        PageInfo<TbOrder> orders =  orderService.selectOrdersCondition(tbOrder,pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",orders);
    }
    //添加订单
    @ApiOperation(value = "发布需求")
    @PostMapping("/order/publishOrder")
    public Result<String> publishOrder(@RequestBody TbOrder tbOrder) {
        return orderService.publishOrder(tbOrder);
    }

    @ApiOperation(value = "根据id修改需求")
    @PostMapping("/order/update")
    public Result<String> update( @RequestBody TbOrder tbOrder) {
        tbOrder.setUpdateTime(new Date());
        orderService.update(tbOrder);
        return new Result(true, StatusCode.OK,"修改成功",null);
    }

    //删除订单
    @ApiOperation(value = "根据id删除需求")
    @PostMapping ("/order/delete/{id}")
    public Result<String> deleteById(@PathVariable("id") Integer orderId) {
        return orderService.deleteById(orderId);
    }
    @ApiOperation("查看自己的需求")
    @GetMapping("/order/selectMyOrder/{pageNum}")
    public Result<PageInfo<TbOrder>> selectMyOrder(@PathVariable("pageNum") Integer pageNum){
        return orderService.selectMyOrder(pageNum);

    }
}
