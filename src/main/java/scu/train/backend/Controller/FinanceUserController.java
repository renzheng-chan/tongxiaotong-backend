package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbUserFinanceDao;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Entity.TbUserFinance;
import scu.train.backend.Service.TbUserFinanceService;

@RestController
public class FinanceUserController {

    @Autowired
    TbUserFinanceService tbUserFinanceService;

    @ApiOperation("查询自己的融资情况")
    @GetMapping("/userFinance/selectMine/{pageNum}")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result<PageInfo<TbUserFinance>> selectIntentionByAccount(@PathVariable("pageNum") Integer pageNum){
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<TbUserFinance> intentionList = tbUserFinanceService.selectByAccount(tbUser.getAccount(),pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",intentionList);
    }
}
