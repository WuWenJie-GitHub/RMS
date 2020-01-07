package org.tangdao.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tangdao.common.network.IpUtils;
import org.tangdao.common.web.http.ServletUtils;
import org.tangdao.common.web.http.UserAgentUtils;
import org.tangdao.modules.sys.model.domain.Log;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.impl.PasswordEncoderService;
import org.tangdao.modules.sys.utils.LogUtils;
import org.tangdao.modules.sys.utils.UserUtils;
import org.tangdao.modules.sys.utils.ValidCodeUtils;

import eu.bitwalker.useragentutils.UserAgent;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
		
		protected void configure(HttpSecurity http) throws Exception {
			http
	            .csrf()
	            .disable()
				.antMatcher("/api")
	            .authorizeRequests()
	            .anyRequest().hasRole("APP");
			http
				// session失效跳转的链接
				.sessionManagement()
					// Spring Security的默认启用防止固化session攻击
					.sessionFixation().migrateSession().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
  				// 解决不允许显示在iFrame的问题
				.headers().frameOptions().disable()
				.cacheControl().disable();
		}
		
	}
    
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    	
    	@Value("${security.loginUrl}")
    	private String loginUrl;
    	
    	@Value("${security.logoutUrl}")
    	private String logoutUrl;
    	
    	@Value("${security.successUrl}")
    	private String successUrl;
    	
    	@Value("${security.authorizeUrl}")
    	private String authorizeUrl;
    	
    	private UserDetailsService userDetailsService;
    	
    	private PasswordEncoderService passwordEncoderService;
    	
        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, PasswordEncoderService passwordEncoderService) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoderService = passwordEncoderService;
        }

    	@Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoderService);
        }
    	
    	@Override
        public void configure(WebSecurity web) {
            web
            	.ignoring()
            		.antMatchers("/static/**", "/webjars/**", "/v2/**", "/swagger**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	
        	http.headers().frameOptions().disable();
            http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(authorizeUrl).authenticated()
            .and()
            		.addFilterBefore(new OncePerRequestFilter() {
						@Override
						protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException {
							if(request.getRequestURI().equals(loginUrl)&&request.getMethod().equals("POST")) {
								String validCode = request.getParameter(ValidCodeUtils.VALID_CODE);
								if(!ValidCodeUtils.validate(request, ValidCodeUtils.VALID_CODE, validCode, false)) {
									ServletUtils.redirectUrl(request, response, loginUrl+"?error=true");
									return;
								}
							}
							filterChain.doFilter(request, response);
						}
					}, UsernamePasswordAuthenticationFilter.class)
            		.formLogin()
                    .loginPage(loginUrl).permitAll()
                    .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {
							// TODO Auto-generated method stub
							User user = UserUtils.getUser();
							//权限和菜单信息
							user.setMenus(UserUtils.getMenuByParentCode(null));
							
						    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( user, authentication.getCredentials(), user.getAuthorities());  
						    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  					    
							SecurityContextHolder.getContext().setAuthentication(authenticationToken);
							
							//更新登录信息
							user.setLastLoginIp(IpUtils.getRemoteAddr(request));
							UserUtils.updateUserLogininfo(user);
							
							// 记录用户登录日志
							LogUtils.saveLog(user, request, "系统登录", Log.TYPE_LOGIN_LOGOUT);
							
							request.getSession().setAttribute("host", IpUtils.getRemoteAddr(request));
							UserAgent userAgent = UserAgentUtils.getUserAgent(request);
							request.getSession().setAttribute("deviceName", userAgent.getOperatingSystem().getName());
							super.setAlwaysUseDefaultTargetUrl(true);
							super.setDefaultTargetUrl(successUrl);
//							super.setUseReferer(true);
							super.onAuthenticationSuccess(request, response, authentication);
						}
					})
                    .failureUrl(loginUrl+"?error=true")
            .and().logout()
            		.logoutUrl(logoutUrl).permitAll()
            		.addLogoutHandler(new LogoutHandler() {
						
						@Override
						public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
							// TODO Auto-generated method stub
							if (authentication != null && authentication.getPrincipal() != null) {
         						LogUtils.saveLog((User)authentication.getPrincipal(), request, "系统退出", Log.TYPE_LOGIN_LOGOUT);
         					}
							// 在这里可以手动清理redis
							sessionRepository.deleteById(request.getSession().getId());
						}
					})
             		.logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler() {
             			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
             					Authentication authentication) throws IOException, ServletException {
         					super.setAlwaysUseDefaultTargetUrl(true);
             				super.setDefaultTargetUrl(loginUrl+"?logout");
//             				super.setUseReferer(true);
             				super.onLogoutSuccess(request, response, authentication);
             			}
					});
            http
            	.sessionManagement()
                 // 无效session跳转
            	.invalidSessionUrl(loginUrl)
            	.maximumSessions(1)
                 // session过期跳转
            	.expiredUrl(loginUrl+"?expired=true")
            	.sessionRegistry(sessionRegistry());
        }
        
        /**
         * 解决session失效后 sessionRegistry中session没有同步失效的问题，启用并发session控制，首先需要在配置中增加下面监听器
         * @return
         */
        @Bean
        public HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
        }
        
        @Lazy
        @Autowired
        public RedisIndexedSessionRepository sessionRepository;
        
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Bean
        public SpringSessionBackedSessionRegistry<Session> sessionRegistry() {
        	return new SpringSessionBackedSessionRegistry(sessionRepository);
        }
	}

}
