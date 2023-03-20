package scu.train.backend.insertData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scu.train.backend.Dao.TbFinancingIntentionDao;
import scu.train.backend.Entity.TbFinancingIntention;

import java.util.Date;

@SpringBootTest
public class insertIntention {
    @Autowired
    TbFinancingIntentionDao tbFinancingIntentionDao;
    @Test
    public void insert(){
        TbFinancingIntention tbFinancingIntention = new TbFinancingIntention();
        for (int i = 1; i < 100; i++) {
            tbFinancingIntention.setAccount(i);
            tbFinancingIntention.setFinanceId(i);
            tbFinancingIntention.setArea(i);
            tbFinancingIntention.setItem("ttt");
            tbFinancingIntention.setPhone("2314");
            tbFinancingIntention.setPurpose("purpose");
            tbFinancingIntention.setCreateTime(new Date());
            tbFinancingIntention.setUpdateTime(new Date());
            tbFinancingIntention.setRealName("ccc");
            tbFinancingIntention.setRepaymentPeriod(new Date());
            tbFinancingIntention.setAddress(i%2==0?"aaa"+i:"bbb"+i);
            tbFinancingIntentionDao.insert(tbFinancingIntention);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
