package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbComment;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.Service.CommentService;

import java.util.Date;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("查询评论消息")
    @RequestMapping("/comment/selectComments/{id}/{pageNum}")
    public Result<PageInfo<TbComment>> selectByKnowledge(@PathVariable("id") Integer knowledgeId, @PathVariable("pageNum") Integer pageNum){
        PageInfo<TbComment> commentPageInfo = commentService.selectByKnowledgeId(knowledgeId,pageNum);
        return new Result<>(true, StatusCode.OK,"查询成功",commentPageInfo);
    }

    @ApiOperation("添加评论信息")
    @PostMapping("/comment/addComment")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<String> addByKnowledge(@RequestBody TbComment comment){
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account = tbUser.getAccount();
        comment.setAccount(account);
        comment.setNickName(tbUser.getNickName());
        comment.setCreateTime(new Date());
        int i = commentService.addByKnowledge(comment);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1? "添加评论成功":"添加评论失败","");
    }
    @ApiOperation("删除评论信息")
    @PostMapping("/comment/deleteComment/{comment_id}")
    @PreAuthorize("hasAnyAuthority('expert','user','admin')")
    public Result<String> addByKnowledge(@PathVariable Integer comment_id){
        int i = commentService.deleteComment(comment_id);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1? "添加评论成功":"添加评论失败","");
    }


}
