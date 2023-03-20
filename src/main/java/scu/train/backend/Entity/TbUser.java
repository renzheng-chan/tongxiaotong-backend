package scu.train.backend.Entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * tb_user
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbUser implements Serializable,UserDetails {
    /**
     * 用户账户
     */
    protected Integer account;

    /**
     * 用户真实姓名
     */
    protected String realName;

    /**
     * 密码
     */
    protected String password;

    /**
     * 昵称
     */
    protected String nickName;

    /**
     * 性别
     */
    protected String sex;

    /**
     * 邮箱
     */
    protected String email;

    /**
     * 手机号
     */
    protected String phone;

    /**
     * 身份证号
     */
    protected String identityNum;

    /**
     * 地址
     */
    protected String address;

    /**
     * 角色
     */
    protected String role;

    /**
     * 创建时间
     * 注解@JsonFormat主要是后台到前台的时间格式的转换
     * 注解@DataFormat主要是前台到后台的时间格式的转换
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateTime;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 信誉
     */
    private Integer credit;

    /**
     * 头像
     */
    protected String avatar;

    protected List<String> permissions;
    @JSONField(serialize = false)
    protected List<SimpleGrantedAuthority> authorities;

    protected static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象
        permissions =  new ArrayList<>(Collections.singletonList(this.role));
       authorities = new ArrayList<>();
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }
        return authorities;
    }
    @Override
    public String getUsername() {
        return String.valueOf(this.getAccount());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}