package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbDiscuss;
import scu.train.backend.Service.DiscussService;

@RestController
public class DiscussController {

    @Autowired
    DiscussService discussService;

    @ApiOperation("获取全部讨论分页信息")
    @RequestMapping("/discuss/select/{pageNum}")
    public Result selectAllDiscuss(@PathVariable Integer pageNum){
        System.out.println("enter here");
        return new Result(true, StatusCode.OK,"查询成功",discussService.selectAllDiscuss(pageNum));
    }

    @ApiOperation("条件查询分页信息")
    @PostMapping("/discuss/selectCondition/{pageNum}")
    public Result<PageInfo<TbDiscuss>> selectAllDiscussByCondition(@RequestBody TbDiscuss record, @PathVariable Integer pageNum){
        return new Result<>(true,StatusCode.OK,"查询成功",discussService.selectAllDiscussByCondition(pageNum,record));
    }

    @ApiOperation("查询自己参与提问的讨论信息")
    @GetMapping("/discuss/selectMineRai/{pageNum}")
    public  Result selectMyDiscuss(@PathVariable Integer pageNum){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account =  Integer.valueOf(principal.getUsername());
        return new Result(true,StatusCode.OK,"查询成功",discussService.selectAllDiscussByCondition(pageNum,
                new TbDiscuss(null,null,account,null,null,null)));
    }

    @ApiOperation("查询自己参与回答的讨论信息")
    @GetMapping("/discuss/selectMineAns/{pageNum}")
    public Result selectMyAnswer(@PathVariable Integer pageNum){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account =  Integer.valueOf(principal.getUsername());
        return new Result(true,StatusCode.OK,"查询成功",discussService.selectAllDiscussByCondition(pageNum,
                new TbDiscuss(null,account,null,null,null,null)));
    }

    @ApiOperation("查询自己参与的全部讨论信息")
    @GetMapping("/discuss/selectMineAll/{pageNum}")
    public Result selectMyAll(@PathVariable Integer pageNum){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account =  Integer.valueOf(principal.getUsername());
        return new Result(true,StatusCode.OK,"查询成功",discussService.selectAllDiscussByConditions(pageNum,
                new TbDiscuss(null,account,account,null,null,null)));
    }


    @ApiOperation("插入讨论")
    @PostMapping("/discuss/add")
    public Result add(@RequestBody TbDiscuss tbDiscuss){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account =  Integer.valueOf(principal.getUsername());
        if (tbDiscuss.getQuestionerAccount() == null){
            tbDiscuss.setQuestionerAccount(account);
        }
        discussService.insertBySelective(tbDiscuss);
        return new Result<>(true,StatusCode.OK,"插入成功");
    }

    @ApiOperation("删除讨论")
    @DeleteMapping("/discuss/delete/{id}")
    public Result delete(@PathVariable Integer id){
        discussService.deleteByPrimaryKey(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
