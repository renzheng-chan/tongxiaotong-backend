package scu.train.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbBank;
import scu.train.backend.Entity.TbOrder;
import scu.train.backend.Entity.TbProduct;
import scu.train.backend.Service.BankService;

import java.util.Date;

@RestController
public class BankController {

    @Autowired
    BankService bankService;

    //新增银行情报
    @PostMapping("/bank/addbank")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result<String> add(@RequestBody TbBank tbBank){
        return bankService.addBank(tbBank);
    }

    //修改银行情报
    @PutMapping ("/bank/updatebank")
    @PreAuthorize("hasAnyAuthority('admin','bankUser')")
    public Result<String> update( @RequestBody TbBank tbBank) {
        bankService.update(tbBank);
        return new Result(true, StatusCode.OK,"修改成功",null);
    }
    //删除银行情报
    @DeleteMapping("/bank/deletebank/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public Result<String> deleteById(@PathVariable("id") Integer orderId) {
        return bankService.deleteById(orderId);
    }
}
