package scu.train.backend.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbPurchaseDetailDao;
import scu.train.backend.Entity.TbPurchaseDetail;
import scu.train.backend.Service.PurchaseDetailService;

import java.util.List;

@Service
public class PurchaseDetailService {

    @Autowired
    TbPurchaseDetailDao tbPurchaseDetailDao;

    // 接口16

    public void add(TbPurchaseDetail tbPurchaseDetail){
        tbPurchaseDetailDao.insertSelective(tbPurchaseDetail);
    }

    // 接口17

    public TbPurchaseDetail selectByPurchaseId(Integer purchaseId){
        return tbPurchaseDetailDao.selectByPurchaseId(purchaseId);
    }


}
