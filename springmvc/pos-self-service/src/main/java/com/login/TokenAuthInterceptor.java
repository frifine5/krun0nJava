package com.login;

import com.common.ParamsUtil;
import com.common.globel.LocalCacheRepo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;




/**
 * api访问的token验证拦截器
 *
 *
 * @author WangChengyu
 * 2020/1/10 11:27
 *
 */
@Configuration
public class TokenAuthInterceptor extends WebMvcConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(TokenAuthInterceptor.class);


    @Value("${init.token.ifInterceptor:true}")
    private boolean ifInterceptor;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除的路径， 不拦截的路径
        addInterceptor.excludePathPatterns("/a/user/login");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/logout");

        addInterceptor.excludePathPatterns("/test/download1"); // for get test


        //拦截所有路径
        addInterceptor.addPathPatterns("/**");
    }


    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            // 失败时返回值的格式
            Map<String, Object> errRtn = new HashMap<>();
            try {
                if(!ifInterceptor){
                    return true;
                }
                Enumeration<String> headerNames = request.getHeaderNames();
                while(headerNames.hasMoreElements()){
                    System.out.printf(headerNames.nextElement() + "\t");
                }
                System.out.println();

                String token = request.getHeader("Authorization");
                logger.info("token = " + token);
//                String token = request.getParameter("token");
                if(ParamsUtil.checkNull(token)){
                    errRtn.put("code", 1001);
                    errRtn.put("msg", "登录失效");
                }else{
                    Object ret = LocalCacheRepo.getCache(token);
                    logger.info("token=" + ret);
                    if(ret == null || ParamsUtil.checkNull(ret.toString()) || "null".equalsIgnoreCase(ret.toString())){
                        errRtn.put("code", 1001);
                        errRtn.put("msg", "登录失效");
                    }else{
                        logger.info("验证token通过");
                        return true;                    // 验证通过，请求继续
                    }
                }
                logger.info("验证token不通过");
                // 验证失败，返回验证结果
            } catch (Exception e) {
                logger.error("interceptor 异常", e);
                errRtn.put("code", 1001);
                errRtn.put("msg", "登录失效或验证服务响应超时");
            }

            String requestType = request.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(requestType)){
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
                response.setHeader("CONTEXTPATH", basePath);
            }
            response.setHeader("SESSIONSTATUS", "TIMEOUT");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            String jsonRtn = JSONObject.fromObject(errRtn).toString();
            try{
                out = response.getWriter();
                out.append(jsonRtn);
            }catch (Exception e){
                response.sendError(500);
            }finally {
                try {
                    out.close();
                }catch (Exception e){}
            }
            return false;
        }
    }





}
