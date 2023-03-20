package scu.train.backend.insertData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scu.train.backend.Dao.TbProductDao;
import scu.train.backend.Entity.TbProduct;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class InsertProduct {
    @Autowired
    TbProductDao tbProductDao;

    public void insertData(){

        for (int i = 1; i < 1000; i++) {
            TbProduct tbProduct = new TbProduct();
            tbProduct.setCreateTime(new Date());
            tbProduct.setUpdateTime(new Date());
            tbProduct.setProductName("Test_name"+1);
            tbProduct.setInfo("");
            tbProduct.setCount(i);
            tbProduct.setPrice(BigDecimal.valueOf(i+0.1));
            tbProduct.setSource("Test_source"+i);
            tbProduct.setBelongAccount(i);
            tbProductDao.insert(tbProduct);
        }

    }
}
