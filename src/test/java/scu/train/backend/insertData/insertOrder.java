package scu.train.backend.insertData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scu.train.backend.Dao.TbOrderDao;
import scu.train.backend.Entity.TbOrder;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class insertOrder {
    @Autowired
    TbOrderDao tbOrderDao;
    @Test
    public void insert(){
        TbOrder tbOrder = new TbOrder();
        for (int i = 0; i < 100; i++) {
            tbOrder.setPrice(new BigDecimal("0.8"));
            tbOrder.setOrderStatus(i%2==0);
            tbOrder.setType("sell");
            tbOrder.setTitle(i%2==0?"aaaa"+i:"ccc"+i);
            tbOrder.setContent("content"+i);
            tbOrder.setCreateTime(new Date());
            tbOrder.setExchangeMeans("online");
            tbOrder.setPublisherAccount(i);
            tbOrder.setUpdateTime(new Date());
            tbOrderDao.insert(tbOrder);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
