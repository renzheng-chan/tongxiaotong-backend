package scu.train.backend.Controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Entity.TbProduct;
import scu.train.backend.Service.ProductService;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.insertData.InsertProduct;


import java.util.HashMap;


@RestController
public class ProductController {
    @Autowired
    public ProductService productService;

    @Autowired
    public InsertProduct insertProduct;
    @ApiOperation("查询所有商品")
    @GetMapping("/product/selectAll/{pageNum}")
    public Result<PageInfo<TbProduct>> selectAll(@PathVariable("pageNum") Integer pageNum){
        PageInfo<TbProduct> products = productService.selectAll(pageNum);
        System.out.println("Select All");
        return new Result<>(true, StatusCode.OK, "查询成功", products);
    }
    @ApiOperation("条件查询商品")
    @PostMapping("/product/selectCondition/{pageNum}")
    public Result<PageInfo<TbProduct>> selectCondition(@PathVariable("pageNum") Integer pageNum, @RequestBody(required = false) TbProduct tbProduct){
        //进行条件查询，支持对货物ID，名称，货源，所属账号进行查询
        PageInfo<TbProduct> products = productService.selectCondition(pageNum,tbProduct);
        System.out.println("Select Condition");
        return new Result<>(true,StatusCode.OK,"查询成功",products);
    }
    //添加商品
    @PostMapping("/product/Add")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<String> addProduct(@RequestBody TbProduct tbProduct){
        return productService.addProduct(tbProduct);
    }
    @ApiOperation("查询自己的商品")
    @GetMapping("/product/selectMine/{pageNum}")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<PageInfo<TbProduct>> selectMyProduct(@PathVariable Integer pageNum){
        PageInfo<TbProduct> productPageInfo = productService.selectMine(pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",productPageInfo);

    }
    //todo
    @ApiOperation("删除商品")
    @PostMapping("/product/deleteProduct")
    public Result<String> deleteProduct(@RequestBody TbProduct tbProduct){
        int i = productService.deleteProduct(tbProduct);
        return new Result<>(i==1,i==1?StatusCode.OK:StatusCode.ERROR,i==1?"删除成功":"删除失败","");

    }


}
