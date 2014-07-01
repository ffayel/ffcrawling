package com.ff.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ff.tool.MyStatics;

public final class Encoding implements Filter  {

    public void init(FilterConfig filterConfig) throws ServletException { }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        req.setCharacterEncoding(MyStatics._ENCODING);
        resp.setContentType("text/html;charset="+MyStatics._ENCODING);
        resp.setCharacterEncoding(MyStatics._ENCODING);
        filterChain.doFilter(req, resp);
    }

    public void destroy() {}

}
