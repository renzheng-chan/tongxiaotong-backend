package scu.train.backend.insertData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scu.train.backend.Dao.TbBankFinanceDao;
import scu.train.backend.Entity.TbBankFinance;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class insertFinance {
    @Autowired
    TbBankFinanceDao tbBankFinanceDao;
    @Test
    public void insert(){
        TbBankFinance tbBankFinance  = new TbBankFinance();
        for (int i = 0; i < 1000 ; i++) {
            tbBankFinance.setBankId(i);
            tbBankFinance.setFinanceAmount(i+1000);
            tbBankFinance.setRate(new BigDecimal("0.8"));
            tbBankFinance.setIsexist((byte) 1);
            tbBankFinance.setRepayTime(i+1);
            tbBankFinance.setCreateTime(new Date());
            tbBankFinance.setUpdateTime(new Date());
            tbBankFinanceDao.insert(tbBankFinance);
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
