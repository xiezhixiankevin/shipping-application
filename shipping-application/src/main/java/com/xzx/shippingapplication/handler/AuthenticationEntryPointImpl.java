package com.xzx.shippingapplication.handler;


import com.alibaba.fastjson.JSON;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        R result = R.ok().data("err", "认证失败请重新登录").code(HttpStatus.UNAUTHORIZED.value());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }

}
