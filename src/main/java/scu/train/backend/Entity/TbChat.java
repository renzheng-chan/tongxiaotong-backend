package scu.train.backend.Entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_chat
 * @author 
 */
@Data
public class TbChat implements Serializable {

    private Integer chatId;

    private Integer chatAccount1;

    private Integer chatAccount2;

    private String chatContent;

    /**
     * 问答日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;

    private static final long serialVersionUID = 1L;
}