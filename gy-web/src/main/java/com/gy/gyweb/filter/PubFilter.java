package com.gy.gyweb.filter;

import com.gy.dao.model.CommunicationLog;
import com.gy.dao.model.RespMsg;
import com.gy.gyservice.ICommunicationService;
import com.gy.gyservice.ITykhService;
import com.gy.gyservice.impl.CommunicationServiceImpl;
import com.gy.gyservice.impl.TykhServiceImpl;
import com.gy.gyutil.MD5Util;
import com.gy.gyweb.config.CommonConfig;
import com.gy.gyweb.dynamicDS.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sun.net.util.URLUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter(filterName = "pubFilter",urlPatterns = "/request")
@Order(0)
public class PubFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(TykhServiceImpl.class);

    private int maxTimes;

    private ICommunicationService communicationService;

    /**
     * fileter的加载在bean之前，所以这时候在communicationService上加自动装配会取不到
     * communicationService的实体bean,所以需要ApplicationContext主动去调用一次
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        communicationService = ctx.getBean("communicationService", CommunicationServiceImpl.class);
        maxTimes = ctx.getBean("commonConfig", CommonConfig.class).getMaxTimes();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String companyCode = servletRequest.getParameter("companyCode");
        String dataSource =servletRequest.getParameter("dataSource");
        String timestamp = servletRequest.getParameter("timestamp");
        String dataType =servletRequest.getParameter("dataType");

        if(DynamicDataSourceContextHolder.containDataSourceKey(dataSource)){
            DynamicDataSourceContextHolder.setDataSourceKey(dataSource);
        }else{
            LOG.info("---参数异常！---");
            RespMsg respMsg = new RespMsg(RespMsg.ERRORCODE,RespMsg.ERRORMSG);
            responseMsg(respMsg,servletResponse);
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentDate = df.format(new Date());
        CommunicationLog communicationLog = new CommunicationLog();
        communicationLog.setCompany(companyCode);
        communicationLog.setRequesttime(timestamp);
        communicationLog.setState(0);
        communicationLog.setInputtime(currentDate);
        String key = servletRequest.getParameter("key");
        String myKey = "tietong0760ZhouShouBy@!@";//对方铁通要求key写死，非本人意愿
        LOG.info("companyCode:"+companyCode+"||dataSource:"+dataSource+"||timestamp:"+timestamp+"||dataType:"+dataType+"||key:"+key);
        if(companyCode==null||dataSource==null||timestamp==null||dataType==null||!"pricelist".equals(dataType)||!myKey.equals(key)){
            LOG.info("---参数异常！---");
            communicationService.saveLog(communicationLog);
            RespMsg respMsg = new RespMsg(RespMsg.ERRORCODE,RespMsg.ERRORMSG);
            responseMsg(respMsg,servletResponse);
            return;
        }
        currentDate = currentDate.substring(0,10);
       int count = communicationService.countLogs(companyCode,currentDate+"%");
        if(count>=maxTimes){
            LOG.info("---当天已经访问"+maxTimes+"次，不允许再次访问！---");
            communicationService.saveLog(communicationLog);
            RespMsg respMsg = new RespMsg(RespMsg.OVERMAXCODE,RespMsg.OVERMAXMSG);
            responseMsg(respMsg,servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    public void responseMsg(RespMsg respMsg,ServletResponse servletResponse) throws IOException, ServletException{
        servletResponse.setContentType("text/plain; charset=utf-8");
        servletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = servletResponse.getWriter();
        out.append(respMsg.toString());
        out.flush();
    }
}
