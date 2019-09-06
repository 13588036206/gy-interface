package com.gy.gyservice;

import com.gy.dao.model.CommunicationLog;

public interface ICommunicationService {
    public void saveLog(CommunicationLog communicationLog);
    public int countLogs(String company,String inputtime);
}
