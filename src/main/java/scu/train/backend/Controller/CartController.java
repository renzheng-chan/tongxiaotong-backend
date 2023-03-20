package scu.train.backend.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.*;
import scu.train.backend.Service.ProductService;
import scu.train.backend.Service.PurchaseDetailService;
import scu.train.backend.Service.PurchaseService;
import scu.train.backend.Service.ShoppingCartService;
import scu.train.backend.Common.Result;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
@Api("购物车模块")
@RestController
public class CartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    ProductService productService;


    @ApiOperation("添加商品到购物车")
    @PostMapping("/cart/addGoods")
    @PreAuthorize("hasAnyAuthority('user','admin','expert')")
    public Result<String> addGoods(@RequestBody TbProduct tbProduct){
        return shoppingCartService.addGoods(tbProduct);
    }
    @ApiOperation("展示购物车列表")
    @GetMapping("/cart/showCart/{pageNum}")
    @PreAuthorize("hasAnyAuthority('user','admin','expert')")
    public Result<PageInfo<TbShoppingcart>> showCart(@PathVariable Integer pageNum){
        PageInfo<TbShoppingcart> list = shoppingCartService.getShoppingCart(pageNum);
        return new Result<>(true,StatusCode.OK,"查询正常",list);
    }
    @ApiOperation("从购物车中删除商品 ")
    @PostMapping("/cart/deleteGoods")
    @PreAuthorize("hasAnyAuthority('user','admin','expert')")
    public Result<String> deleteGoods(Integer shoppingcartId){
       return shoppingCartService.deleteGoods(shoppingcartId);
    }
    @ApiOperation("(任务16)提交订单")
    @PostMapping("/cart/commitPurchase")
    @PreAuthorize("hasAnyAuthority('user','admin','expert')")
    public Result<String> commitPurchase(@RequestBody JSONObject jsonObject){
        //获取前端传递的JSON串
        List<TbShoppingcart> list = jsonObject.getList("list",TbShoppingcart.class);
        Integer address_id = jsonObject.getInteger("address_id");
        BigDecimal totalPrice = jsonObject.getBigDecimal("totalPrice");
        return shoppingCartService.commitPurchase(list,address_id,totalPrice);
    }
}
