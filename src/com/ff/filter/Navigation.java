package com.ff.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ff.dao.data.MarvelApiKey;
import com.ff.tool.MyProperties;
import com.ff.tool.MyTime;

public final class Navigation implements Filter
{
    public void destroy() {}

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        //recupération des key pour l'api marvel et creation du hash

        request.setAttribute("marvel", new MarvelApiKey(MyProperties.getString("marvel.publickey"), MyProperties.getString("marvel.privatekey")));
        
        request.setAttribute("now", MyTime.now());
        chain.doFilter(req, res);
    }

    public void init(final FilterConfig config) { }

}
