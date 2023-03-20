package scu.train.backend.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import scu.train.backend.Entity.TbUser;
import scu.train.backend.utils.JwtUtil;
import scu.train.backend.utils.RedisCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
//每个请求经过过滤器一次
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //todo 注册和登录都需要输入验证码
        //获取Token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //当不存在token,可以放行，后续还有认证的过滤器阻止
            filterChain.doFilter(request,response);
            //过滤器返回时也会经过这里，return阻止对下方代码的访问
            return;
        }
        //todo
        //解析Token
        String account = jwtUtil.getUsernameFromToken(token);
        String redisKey = "login:"+account;
        System.out.println(redisKey);
        // 从redis中取出用户信息
        //todo
        UserDetails userDetails = redisCache.getCacheObject(redisKey);
        UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //此处不能使用父类来接收，溢出的参数会发生异常
        //放行
        filterChain.doFilter(request,response);
    }
}
