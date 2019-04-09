package cn.easybuy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(urlPatterns = {"/*"},filterName="EncodeFilter" ,initParams = {@WebInitParam(name = "encode", value = "utf-8")}) 
public class EncodeFilter implements Filter {
	private String encode=null;
	public void destroy() {
		encode=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		if(null==request.getCharacterEncoding()){//先判断再进行设置，以后在执行的时候就没有进行设置了  效率高一点
			request.setCharacterEncoding(encode);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encode=filterConfig.getInitParameter("encode");		
		if(this.encode==null){
			this.encode=encode;
		}
	}
	
}
