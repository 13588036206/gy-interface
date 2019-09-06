package com.gy.gyweb.controller;

import com.gy.dao.model.CommunicationLog;
import com.gy.dao.model.RespMsg;
import com.gy.dao.model.Tykh;
import com.gy.gyservice.ICommunicationService;
import com.gy.gyservice.ITykhService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TykhController {
    @Autowired
    private ITykhService tykhService;

    @Autowired
    private ICommunicationService communicationService;

    @RequestMapping(value="/request",method= RequestMethod.POST)
    public String handleRequest(HttpServletRequest request) {

        String dataSource = request.getParameter("dataSource");
        String brand = request.getParameter("brand")==null?null:"%"+request.getParameter("brand")+"%";
        String keyword = request.getParameter("keyword")==null?null:"%"+request.getParameter("keyword")+"%";
        Double priceStart = request.getParameter("priceStart")==null?null:Double.parseDouble(request.getParameter("priceStart"));
        Double priceEnd = request.getParameter("priceEnd")==null?null:Double.parseDouble(request.getParameter("priceEnd"));
        String timeStart = request.getParameter("timeStart");
        String timeEnd = request.getParameter("timeEnd");

        String fsrq= tykhService.getMaxFsrqByGsdm(dataSource);
        List<Tykh> tykhList = tykhService.getBjd(dataSource,fsrq,brand,keyword,priceStart,priceEnd,timeStart,timeEnd);
        tykhList = tykhService.handleList(tykhList);
        RespMsg respMsg = tykhService.responseMsg(tykhList);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentDate = df.format(new Date());
        if(RespMsg.SUCCESSCODE.equals(respMsg.getCode())){
            String companyCode = request.getParameter("companyCode");
            String timestamp = request.getParameter("timestamp");
            CommunicationLog communicationLog = new CommunicationLog();
            communicationLog.setCompany(companyCode);
            communicationLog.setRequesttime(timestamp);
            communicationLog.setState(1);
            communicationLog.setInputtime(currentDate);
            communicationService.saveLog(communicationLog);
        }
        return respMsg.toString();
    }

}
