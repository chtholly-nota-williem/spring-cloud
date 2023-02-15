package com.zbom.filter;


import com.netflix.discovery.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.RequestContent;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AccessFilter extends ZuulFilter {
    /**
     * 过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型：
     *     public static final String ERROR_TYPE = "error";
     *     public static final String POST_TYPE = "post";
     *     public static final String PRE_TYPE = "pre";
     *     public static final String ROUTE_TYPE = "route";
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 判断过滤器是否要执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 定义网关权限规则
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String accessToken = request.getParameter("accessToken");
        System.out.println(String.format("%s AccessFilter request to %s", request.getMethod(),request.getRequestURL().toString()));
        if ("abcd".equals(accessToken)) {
            currentContext.setSendZuulResponse(true);
            currentContext.setResponseStatusCode(200);
            currentContext.set("success",true);
        } else {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.setResponseBody("{\"result\":\"accessToken is not correct!\"}");
            currentContext.set("success",false);
        }
        return null;
    }
}
