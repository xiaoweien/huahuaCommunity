package com.huahua.article.filter;

import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 功能描述：请求接口之前的调用(AOP)
     * @Author LiHuaMing
     * @Date 19:08 2019/4/26
     * @Param [request, response, handler]
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---------------我进入拦截器了---------------");
        //前后端约定：前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token
        //Authorization:Bearer xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        //获取请求头中的数据
        String header = request.getHeader("Authorization");//Authorization参数名称
        if (StringUtils.isEmpty(header)){
            throw new RuntimeException("权限不足");
        }
        //Bearer也为null	header.startsWith查询字符串中以谋改革name开头的数据是否存在的判断
        if (!header.startsWith("Bearer ")){
            throw new RuntimeException("权限不足");
        }
        String token = header.substring(7);
        //token解析后的明文数据Claims
        Claims claims = null;
        try {
            claims = jwtUtil.parseJWT(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        //校验claims不能为空
        if (null == claims){
            throw new RuntimeException("权限不足");
        }
        //用户或者管理员的信息，放入session
        if (StringUtils.equals("admin", (String) claims.get("roles"))){
            request.setAttribute("admin_claims",claims);
        }
        if (StringUtils.equals("user", (String) claims.get("roles"))){
            request.setAttribute("user_claims",claims);
        }else {
            return true;
        }
        return true;
//        //判断，必须拥有管理员权限，否则不能删除
//        if (!StringUtils.equals("admin", (String) claims.get("roles"))){
//            throw new RuntimeException("权限不足");
//        }else {
//            return true;//放过
//        }
    }
}
