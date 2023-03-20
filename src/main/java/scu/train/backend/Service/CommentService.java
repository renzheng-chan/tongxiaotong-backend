package scu.train.backend.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.train.backend.Dao.TbCommentDao;
import scu.train.backend.Entity.TbComment;

import java.util.List;

@Service
public class CommentService {


    @Autowired
    TbCommentDao tbCommentDao;
    private final Integer pageSize = 20;


    public PageInfo<TbComment> selectByKnowledgeId(Integer knowledgeId, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbComment> commentList = tbCommentDao.selectByKnowledgeId(knowledgeId);
        return new PageInfo<>(commentList);
    }

    public int addByKnowledge(TbComment comment) {
        return tbCommentDao.insert(comment);
    }

    public int deleteComment(Integer commentId) {
        return tbCommentDao.deleteByPrimaryKey(commentId);
    }
}
