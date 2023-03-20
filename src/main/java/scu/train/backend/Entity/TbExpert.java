package scu.train.backend.Entity;

import java.io.Serializable;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_expert
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbExpert implements Serializable {
    /**
     * 专家id
     */
    private Integer expertAccount;

    /**
     * 用户账号
     */
    private Integer account;

    private String expertName;

    /**
     * 从事专业
     */
    private String profession;

    /**
     * 职位
     */
    private String position;

    /**
     * 所属单位
     */
    private String belong;

    /**
     * 专家介绍
     */
    private String introduction;

    private String expertPhoto;

    private static final long serialVersionUID = 1L;
}