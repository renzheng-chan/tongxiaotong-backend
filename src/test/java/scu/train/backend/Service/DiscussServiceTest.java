package scu.train.backend.Service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scu.train.backend.Entity.TbDiscuss;

import java.util.Date;

@SpringBootTest
public class DiscussServiceTest {

    @Autowired
    DiscussService discussService;

    @Test
    public void selectAllDiscussTest(){
        System.out.println(discussService.selectAllDiscuss(1));
    }

    @Test
    public void selectAllDiscussByConditionTest(){
        TbDiscuss tbDiscuss = new TbDiscuss(null,0,1,"标题","内容",new Date());
        System.out.println(discussService.selectAllDiscussByCondition(1,tbDiscuss));
    }

    @Test
    public void insertBySelectiveTest(){
        TbDiscuss tbDiscuss = new TbDiscuss(null,0,1,"标题","内容",new Date());
        discussService.insertBySelective(tbDiscuss);
    }

    @Test
    public void deleteByPrimaryKey(){
        discussService.deleteByPrimaryKey(1);
        discussService.deleteByPrimaryKey(2);
        discussService.deleteByPrimaryKey(3);
    }

}
