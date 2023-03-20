package scu.train.backend.Controller;

import com.alibaba.fastjson2.JSONObject;
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
import scu.train.backend.Entity.TbComment;
import scu.train.backend.Entity.TbExpert;
import scu.train.backend.Entity.TbKnowledge;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.DiscussService;
import scu.train.backend.Service.KnowledgeService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "知识模块接口")
@RestController
public class KnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;

    //分页查询所有知识
    @ApiOperation(value = "分页查询所有知识")
    @GetMapping("/knowledge/selectAll/{pageNum}")
    public Result<JSONObject> selectAll(@PathVariable Integer pageNum) {
        PageInfo<TbKnowledge> knowledgePageInfo =  knowledgeService.selectAll(pageNum);
        JSONObject jsonObject = knowledgeService.getKnowledgeExpertJson(knowledgePageInfo);
        return new Result<>(true,StatusCode.OK,"查询成功",jsonObject);
    }
    @ApiOperation(value = "分页条件查询知识")
    @PostMapping("/knowledge/selectCondition/{pageNum}")
    public Result<JSONObject> selectCondition(@RequestBody TbKnowledge tbKnowledge,@PathVariable("pageNum") Integer pageNum) {
        PageInfo<TbKnowledge> tbKnowledgePageInfo = knowledgeService.selectCondition(tbKnowledge,pageNum);
        JSONObject jsonObject = knowledgeService.getKnowledgeExpertJson(tbKnowledgePageInfo);
        return new Result<>(true, StatusCode.OK, "查询成功", jsonObject);
    }

    @ApiOperation(value = "查询我的知识")
    @GetMapping("/knowledge/selectMine/{pageNum}")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<PageInfo<TbKnowledge>> selectMine(@PathVariable Integer pageNum) {
        PageInfo<TbKnowledge> pageInfo = knowledgeService.selectMine(pageNum);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    //添加知识
    @ApiOperation(value = "发布农业知识")
    @PostMapping("/knowledge/addKnowledge")
    @PreAuthorize("hasAnyAuthority('expert')")
    public Result<String> add(@RequestBody TbKnowledge tbKnowledge) {
        int i = knowledgeService.addKnowledge(tbKnowledge);
        return new Result<>(i ==1,i==1?StatusCode.OK:StatusCode.ERROR, i==1?"添加知识成功":"添加知识失败","");
    }

    //根据id修改知识
    @ApiOperation(value = "修改自己已经发布的知识")
    @PutMapping("/knowledge/modifyKnowledge")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<String> update(@RequestBody TbKnowledge tbKnowledge) {
        tbKnowledge.setUpdateTime(new Date());
        int i = knowledgeService.updateKnowledge(tbKnowledge);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1? "修改成功":"修改失败","");
    }

    //根据id修改知识
    @ApiOperation(value = "根据知识id删除知识")
    @DeleteMapping("/knowledge/delete/{id}")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<String> update(@PathVariable("id") Integer id) {
        int i = knowledgeService.deleteKnowledge(id);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1? "修改成功":"修改失败","");
    }


}
