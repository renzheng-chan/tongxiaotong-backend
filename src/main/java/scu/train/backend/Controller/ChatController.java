package scu.train.backend.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Entity.TbChat;
import scu.train.backend.Service.ChatService;

import java.util.Date;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    ChatService chatService;


    @ApiOperation("获取聊天记录")
    @GetMapping("/chat/getChat/{pageNum}")
    public Result<PageInfo<TbChat>> selectChat(@PathVariable Integer pageNum, Integer chatAccount2){
        PageInfo<TbChat> chatPageInfo = chatService.selectChat(chatAccount2,pageNum);
        return new Result<>(true, StatusCode.OK,"获取聊天记录成功",chatPageInfo);
    }

    @ApiOperation("添加某天的聊天记录")
    @PostMapping("/chat/AddChat")
    public Result<String> AddChat(@RequestBody TbChat tbChat){
        tbChat.setTime(new Date());
        int i = chatService.AddChat(tbChat);
        return new Result<>(i==1, i==1?StatusCode.OK:StatusCode.ERROR,i==1?"添加聊天记录成功":"添加聊天记录失败","");
    }

}
