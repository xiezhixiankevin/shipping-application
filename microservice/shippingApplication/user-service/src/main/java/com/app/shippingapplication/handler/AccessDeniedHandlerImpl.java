package com.app.shippingapplication.handler;

import cn.itcast.feign.common.R;
import com.alibaba.fastjson.JSON;
import com.app.shippingapplication.util.WebUtils;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        R result = R.ok().data("err", "权限不足").code(HttpStatus.FORBIDDEN.value());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);

    }
}

