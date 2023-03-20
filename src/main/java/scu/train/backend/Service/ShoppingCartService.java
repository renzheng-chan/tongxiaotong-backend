package scu.train.backend.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.*;
import scu.train.backend.Entity.*;
import scu.train.backend.utils.RedisCache;
import scu.train.backend.utils.UniqueID;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ShoppingCartService {

    @Autowired
    TbShoppingcartDao tbShoppingcartDao;
    @Autowired
    TbProductDao tbProductDao;
    @Autowired
    TbPurchaseDao tbPurchaseDao;
    @Autowired
    TbPurchaseDetailDao tbPurchaseDetailDao;
    @Autowired
    TbAddressDao tbAddressDao;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UniqueID uniqueID;

    private Integer pageSize = 20;


    public PageInfo<TbShoppingcart> getShoppingCart(Integer pageNum){
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account = tbUser.getAccount();
        //从数据库中读取购物车
        PageHelper.startPage(pageNum,pageSize);
        List<TbShoppingcart> list = tbShoppingcartDao.selectByAccount(account);
        return new PageInfo<>(list);
    }

    public Result<String> addGoods(TbProduct tbProduct){
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TbShoppingcart> cartList =  tbShoppingcartDao.selectByAccount(tbUser.getAccount());
        Integer count = 0;
        Integer shoppingCartId = 0;
        //判断购物车中是否有该商品
        Boolean flag = false;
        for (TbShoppingcart tbShoppingcart:cartList) {
            if(tbShoppingcart.getProductId().equals(tbProduct.getProductId())){
                flag = true;
                count = tbShoppingcart.getCount();
                shoppingCartId =tbShoppingcart.getShoppingcartId();
                break;
            }
        }
        TbShoppingcart tbShoppingcart = new TbShoppingcart();
        tbShoppingcart.setUpdateTime(new Date());
        tbShoppingcart.setProductId(tbProduct.getProductId());
        tbShoppingcart.setAccount(tbUser.getAccount());
        tbShoppingcart.setCount(tbProduct.getCount()+count);
        tbShoppingcart.setPrice(tbProduct.getPrice());
        int i = 0;
        if(flag){
            //有该商品，那么就添加
            tbShoppingcart.setShoppingcartId(shoppingCartId);
            i=tbShoppingcartDao.updateByPrimaryKeySelective(tbShoppingcart);
        }else{
            tbShoppingcart.setCreateTime(new Date());
            i=tbShoppingcartDao.insertSelective(tbShoppingcart);
        }
        return new Result<>(i==1,i==1?StatusCode.OK:StatusCode.ERROR,i==1?"添加购物车成功":"添加购物车失败","");
    }

    //接口15

    public void update(TbShoppingcart tbShoppingcart){
        tbShoppingcartDao.updateByPrimaryKeySelective(tbShoppingcart);
    }

    public Result<String> commitPurchase(List<TbShoppingcart> list, Integer address_id,BigDecimal totalPrice) {
        //todo 这里相当于购买物品，需要加入支付验证。。。。
        //获取当前用户
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据list对shoppingCart表和product表,purchase表，purchase_detail表进行修改
        TbPurchase purchaseBuy = new TbPurchase();
        Integer purchase_id = uniqueID.generateAccount();
        purchaseBuy.setPurchaseId(purchase_id);
        purchaseBuy.setAccount(tbUser.getAccount());
        purchaseBuy.setPurchaseType("购买");
        purchaseBuy.setPurchaseStatus(1);
        //地址，如果不传，那么将使用默认地址，传了使用当前地址
        if(Objects.isNull(address_id)){
            //使用默认地址
            address_id = tbAddressDao.selectByAccountDefault(tbUser.getAccount());
        }
        purchaseBuy.setAddressId(address_id);
        purchaseBuy.setCreateTime(new Date());
        purchaseBuy.setUpdateTime(new Date());
        purchaseBuy.setTotalPrice(totalPrice);
        boolean flag = true;
        flag = tbPurchaseDao.insert(purchaseBuy) == 1;
        purchaseBuy.setPurchaseId(uniqueID.generateAccount());
        purchaseBuy.setPurchaseType("出售");
        flag = tbPurchaseDao.insert(purchaseBuy) == 1;
        for (TbShoppingcart tbShoppingcart:list) {
            //对shoppingCart表修改
            TbShoppingcart shoppingCart = tbShoppingcartDao.selectByPrimaryKey(tbShoppingcart.getShoppingcartId());
            if(Objects.isNull(shoppingCart)){
                shoppingCart = tbShoppingcart;
            }
            if(Objects.equals(tbShoppingcart.getCount(), shoppingCart.getCount())){
                //如果数量相等，那么就删除
                flag = tbShoppingcartDao.deleteByPrimaryKey(tbShoppingcart.getShoppingcartId())==1;
            }else{
                //数量小的话，就更新
                shoppingCart.setCount(shoppingCart.getCount()-tbShoppingcart.getCount());
                flag = tbShoppingcartDao.updateByPrimaryKey(shoppingCart) ==1;
            }
            //对product表修改
            TbProduct tbProduct = tbProductDao.selectByPrimaryKey(tbShoppingcart.getProductId());
            if(Objects.isNull(tbProduct)){
                //没上架还购买，出错
                return new Result<>(false,StatusCode.ERROR,"商品选择出错","");
            }
            tbProduct.setCount(tbProduct.getCount()-tbShoppingcart.getCount());
            flag = tbProductDao.updateByPrimaryKey(tbProduct) ==1;
            //
            TbPurchaseDetail tbPurchaseDetail = new TbPurchaseDetail();
            tbPurchaseDetail.setPurchaseId(purchase_id);
            tbPurchaseDetail.setProductId(tbShoppingcart.getProductId());
            tbPurchaseDetail.setCount(tbShoppingcart.getCount());
            tbPurchaseDetail.setUninPrice(tbShoppingcart.getPrice());
            tbPurchaseDetail.setSumPrice(totalPrice);
            //修改purchaseDetail表
            flag = tbPurchaseDetailDao.insert(tbPurchaseDetail) ==1;
        }
        //todo 此处四个表的修改应当是原子性的
        return new Result<>(flag,flag?StatusCode.OK:StatusCode.ERROR,flag?"购买成功":"购买失败","");
    }

    public Result<String> deleteGoods(Integer shoppingcartId) {
        int i =  tbShoppingcartDao.deleteByPrimaryKey(shoppingcartId);
        return new Result<>(i==1,i==1?StatusCode.OK:StatusCode.ERROR,i==1?"移出购物车成功":"移出购物车失败","");
    }
}
