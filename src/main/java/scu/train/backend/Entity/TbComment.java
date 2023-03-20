package scu.train.backend.Entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * tb_comment
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbComment implements Serializable {
    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 知识id
     */
    private Integer knowledgeId;

    /**
     * 内容
     */
    private String content;

    private String nickName;

    /**
     * 发布者名字
     */
    private Integer account;

    /**
     * 创建时间
     */

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     * 注解@JsonFormat主要是后台到前台的时间格式的转换
     * 注解@DataFormat主要是前台到后台的时间格式的转换
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}