package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbBank;
import scu.train.backend.Entity.TbBankFinance;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.BankService;
import scu.train.backend.Service.FinanceService;

import java.util.List;

@Api(tags = "银行融资模块接口")
@RestController
@RequestMapping("/bankFinance")
public class FinanceController {
    @Autowired
    private FinanceService financeService;
    @Autowired
    private BankService bankService;


    @ApiOperation(value = "添加银行融资政策")
    @PostMapping("/addFinance")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<String> add(@RequestBody TbBankFinance tbBankFinance) {
        return financeService.addFinance(tbBankFinance);
    }

    @ApiOperation(value = "查询银行情报")
    @GetMapping("/selectBank/{pageNum}")
    @PreAuthorize("hasAnyAuthority('bankUser','user','expert','admin')")
    public Result<PageInfo<TbBank>> selectBank(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<TbBank> banks = bankService.selectAllBank(pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",banks);
    }

    @ApiOperation("根据银行id查询融资政策信息")
    @GetMapping("/selectByBankId/{pageNum}/{bankId}")
    @PreAuthorize("hasAnyAuthority('bankUser','user','expert','admin')")
    public Result<PageInfo<TbBankFinance>> selectByBankId(@PathVariable("pageNum") Integer pageNum, @PathVariable("bankId") Integer bankId){
        PageInfo<TbBankFinance> financeList = financeService.selectByBankId(bankId,pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",financeList);

    }
    @ApiOperation("银行查询推荐融资人")
    @GetMapping("/selectRecommend/{pageNum}")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result<PageInfo<TbUser>> selectRecommend(@PathVariable("pageNum") Integer pageNum){
        PageInfo<TbUser> list = financeService.selectRecommend(pageNum);
        return new Result<>(true,StatusCode.OK,"查询推荐融资人成功",list);
    }
    @ApiOperation("修改融资政策信息")
    @PostMapping("/finance/update")
    @PreAuthorize("hasAuthority('bankUser')")
    public Result updateFinance(@RequestBody TbBankFinance tbBankFinance){
        financeService.updateFinance(tbBankFinance);
        return new Result<>(true, StatusCode.OK,"修改融资政策成功");
    }
    @ApiOperation("根据finance_id查询融资政策信息")
    @GetMapping("/finance/selectById/{id}")
    @PreAuthorize("hasAnyAuthority('bankUser','user','expert','admin')")
    public Result<TbBankFinance> selectById(@PathVariable("id") Integer id) {
        TbBankFinance tbBankFinance = financeService.selectById(id);
        return new Result<>(true,StatusCode.OK,"查询成功",tbBankFinance);
    }

}
