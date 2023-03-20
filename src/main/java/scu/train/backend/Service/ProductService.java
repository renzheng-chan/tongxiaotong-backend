package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Controller.UploadController;
import scu.train.backend.Dao.TbProductDao;
import scu.train.backend.Entity.TbProduct;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.utils.DefaultImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {
    //每页显示多少条数据
    private final Integer pageSize = 8;
    @Autowired
    TbProductDao tbProductDao;
    @Autowired
    DefaultImage defaultImage;
    public PageInfo<TbProduct> selectAll(Integer pageNum){
        //在你需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可
        //紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。
        PageHelper.startPage(pageNum,pageSize);
        //分页时，实际返回的结果list类型是Page<E>
        //如果想取出分页信息，需要强制转换为Page<E>
        //用PageInfo对结果进行包装
        //PageInfo page = new PageInfo(productList);
        List<TbProduct> productList = tbProductDao.selectAll();
        return new PageInfo<>(productList);
    }

    public PageInfo<TbProduct> selectCondition(Integer pageNum,TbProduct tbProduct){
        PageHelper.startPage(pageNum,pageSize);
        List<TbProduct> productList = tbProductDao.selectCondition(tbProduct);
        return new PageInfo<>(productList);
    }

    public Integer getProductCountById(Integer id){
        return tbProductDao.selectByPrimaryKey(id).getCount();
    }

    public void modifyProductCount(TbProduct tbProduct){
        tbProductDao.updateByPrimaryKeySelective(tbProduct);
    }

    public BigDecimal getProductPriceById(Integer id){
        return tbProductDao.selectByPrimaryKey(id).getPrice();
    }

    public Result<String> addProduct(TbProduct tbProduct) {
        //添加nickName
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tbProduct.setBelongAccount(tbUser.getAccount());
        tbProduct.setNickName(tbUser.getNickName());
        tbProduct.setPicture(defaultImage.getDefaultProduct());
        tbProduct.setCreateTime(new Date());
        tbProduct.setUpdateTime(new Date());
        int i = tbProductDao.insertSelective(tbProduct);
        return new Result<>(i==1,i==1? StatusCode.OK:StatusCode.ERROR,i==1?"添加商品成功":"添加商品失败","");
    }

    public PageInfo<TbProduct> selectMine(Integer pageNum) {
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageHelper.startPage(pageNum,pageSize);
        List<TbProduct> productList = tbProductDao.selectByAccount(tbUser.getAccount());
        return new PageInfo<>(productList);
    }

    public int deleteProduct(TbProduct tbProduct) {
        //判断该商品是否为你的商品
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account = tbUser.getAccount();
        List<TbProduct> productList = tbProductDao.selectByAccount(account);
        boolean flag = false;
        for (TbProduct product:productList) {
            if(product.getProductId().equals(tbProduct.getProductId())){
                flag = true;
                break;
            }
        }
        if(flag){
            return tbProductDao.deleteByPrimaryKey(tbProduct.getProductId());
        }
        return 0;

    }
}
