package scu.train.backend.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbChatDao;
import scu.train.backend.Entity.TbChat;
import scu.train.backend.Entity.TbUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    TbChatDao tbChatDao;

    private Integer pageSize =30;
    public PageInfo<TbChat> selectChat(Integer chatAccount2,Integer pageNum) {
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account1 = tbUser.getAccount();
        PageHelper.startPage(pageNum,pageSize);
        List<TbChat> chatList = tbChatDao.selectChatInfo(account1, chatAccount2);
        return  new PageInfo<>(chatList);
    }

    public int AddChat(TbChat tbChat) {
        return tbChatDao.insert(tbChat);
    }
}
