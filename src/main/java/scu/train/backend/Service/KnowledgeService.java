package scu.train.backend.Service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import scu.train.backend.Common.Result;
import scu.train.backend.Controller.UploadController;
import scu.train.backend.Dao.TbExpertDao;
import scu.train.backend.Dao.TbKnowledgeDao;
import scu.train.backend.Entity.TbExpert;
import scu.train.backend.Entity.TbKnowledge;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.utils.DefaultImage;

import java.util.Date;
import java.util.List;

@Service
public class KnowledgeService {
    //每页显示多条数据
    private static final Integer pageSize = 30;
    @Autowired
    private TbKnowledgeDao tbKnowledgeDao;
    @Autowired
    DefaultImage defaultImage;

    @Autowired
    TbExpertDao tbExpertDao;

    public PageInfo<TbKnowledge> selectCondition(TbKnowledge tbKnowledge,Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbKnowledge> tbKnowledgeList = tbKnowledgeDao.selectCondition(tbKnowledge);
        return new PageInfo<>(tbKnowledgeList);
    }


    public int addKnowledge(TbKnowledge tbKnowledge) {
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tbKnowledge.setPublisherName(tbUser.getRealName());
        tbKnowledge.setCover(defaultImage.getDefaultCover());
        tbKnowledge.setPublisherAccount(tbUser.getAccount());
        tbKnowledge.setCreateTime(new Date());
        tbKnowledge.setUpdateTime(new Date());
        return tbKnowledgeDao.insert(tbKnowledge);
    }

    public Integer updateKnowledge(TbKnowledge tbKnowledge) {
        return tbKnowledgeDao.updateByPrimaryKeySelective(tbKnowledge);
    }

    public Integer deleteKnowledge(Integer id) {
        return tbKnowledgeDao.deleteByPrimaryKey(id);
    }


    public PageInfo<TbKnowledge> selectAll(Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbKnowledge> list = tbKnowledgeDao.selectAll();
        return new PageInfo<>(list);
    }

    public PageInfo<TbKnowledge> selectMine(Integer pageNum) {
        //获取登陆的用户id
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer account = tbUser.getAccount();
        PageHelper.startPage(pageNum,pageSize);
        List<TbKnowledge> list = tbKnowledgeDao.selectByAccount(account);
        return new PageInfo<>(list);



    }

    public JSONObject getKnowledgeExpertJson(PageInfo<TbKnowledge> knowledgePageInfo) {
        List<TbKnowledge> knowledgeList = knowledgePageInfo.getList();
        JSONObject jsonObject = new JSONObject();
        for (TbKnowledge knowledge:knowledgeList) {
            Integer article_id = knowledge.getArticleId();
            Integer account = knowledge.getPublisherAccount();
            TbExpert expert = tbExpertDao.selectByAccount(account);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(knowledge);
            jsonArray.add(expert);
            jsonObject.put(article_id+"",jsonArray);
        }
        return jsonObject;
    }
}
