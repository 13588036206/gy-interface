package com.gy.gyservice.impl;
import com.gy.dao.mapper.CommunicationLogMapper;
import com.gy.dao.model.CommunicationLog;
import com.gy.gyservice.ICommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service("communicationService")
public class CommunicationServiceImpl implements ICommunicationService {

    private static final Logger LOG = LoggerFactory.getLogger(TykhServiceImpl.class);

    @Resource
    private CommunicationLogMapper communicationLogMapper;

    @Override
    public void saveLog(CommunicationLog communicationLog) {
        try{
            communicationLogMapper.insert(communicationLog);
        }catch (Exception e){
            LOG.error("记录访问日志失败，失败原因："+e.getMessage());
        }

    }

    @Override
    public int countLogs(String company, String inputtime) {
        int count = 0;
        try{
            count =  communicationLogMapper.countLogs(company,inputtime);
        }catch (Exception e){
            LOG.error("获取访问次数失败，失败原因："+e.getMessage());
        }
        return count;
    }


}
