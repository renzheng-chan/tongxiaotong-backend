package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbFinancingIntention;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Entity.TbUserFinance;
import scu.train.backend.Service.FinanceIntentionService;

@RestController
public class FinanceIntentionController {
    @Autowired
    FinanceIntentionService financeIntentionService;

    @ApiOperation("查询自己的融资申请情况")
    @GetMapping("/intention/selectIntention/{pageNum}")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result<PageInfo<TbUserFinance>> selectIntentionByAccount(@PathVariable("pageNum") Integer pageNum){
        PageInfo<TbUserFinance> intentionList = financeIntentionService.selectByAccount(pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",intentionList);
    }

    @ApiOperation("提交融资意向")
    @PostMapping("/intention/insertIntention")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result insertIntention(@RequestBody TbFinancingIntention tbFinancingIntention){
        boolean flag = financeIntentionService.insertIntention(tbFinancingIntention);
        return new Result<>(flag,flag?StatusCode.OK:StatusCode.ERROR,flag?"提交融资意向成功":"提交融资意向失败");
    }
    @ApiOperation("修改融资意向")
    @PostMapping("/intention/updateIntention")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result updateIntention(@RequestBody TbFinancingIntention tbFinancingIntention){
        boolean flag = financeIntentionService.updateIntention(tbFinancingIntention);
        return new Result<>(flag,flag?StatusCode.OK:StatusCode.ERROR,flag?"修改融资意向成功":"修改融资意向失败");
    }
    @ApiOperation("删除融资意向")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    @PostMapping("/intention/deleteIntention")
    public Result deleteIntention(Integer finance_id){
        boolean flag = financeIntentionService.deleteIntention(finance_id);
        return new Result<>(flag,flag?StatusCode.OK:StatusCode.ERROR,flag?"删除融资意向成功":"删除融资意向失败");
    }
    @ApiOperation("查询推荐融资人")
    @GetMapping("/intention/recommend/{pageNum}")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result<PageInfo<TbUser>> intentionRecommend(@PathVariable("pageNum") Integer pageNum){
        PageInfo<TbUser> tbUserPageInfo = financeIntentionService.intentionRecommend(pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",tbUserPageInfo);
    }

}
