package scu.train.backend.Configure;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import scu.train.backend.Filter.JwtAuthenticationTokenFilter;
import scu.train.backend.handler.AccessDeniedHandlerImpl;
import scu.train.backend.handler.AuthenticationEntryImpl;

@Configuration
//开启配置，使用注解来设置权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
//新版不springboot已经不继承websecurityconfigureradapter了，直接注入SecurityFilterChain的bean即可
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryImpl authenticationEntry;
    @Autowired
    private  JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    //创建BCryptPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/product/selectAll/{pageNum}",
                "/order/selectAll/{pageNum}",
                "/order/searchCondition/{pageNum}",
                "/knowledge/selectAll/{pageNum}",
                "/knowledge/selectCondition/{pageNum}",
                "/comment/selectComments/{id}/{pageNum}",
                "/product/selectCondition/{pageNum}",
                "/**/*.jpeg",
                "/**/*.png",
                "/*.png"
        );
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 匿名访问
                .antMatchers("/user/login").anonymous()
                .antMatchers("/bank/login").anonymous()
//                .antMatchers("/testCors").hasAuthority("system:dept:list222")
                .antMatchers("/user/SignIn").anonymous()
                .antMatchers("/bank/SignIn").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //添加过滤器
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //添加异常处理器
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntry)
                .accessDeniedHandler(accessDeniedHandler);
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
