package com.gy.gyservice.impl;

import com.gy.dao.mapper.GoodsMappingMapper;
import com.gy.dao.mapper.TykhMapper;
import com.gy.dao.model.GoodsMapping;
import com.gy.dao.model.GoodsToJson;
import com.gy.dao.model.RespMsg;
import com.gy.dao.model.Tykh;
import com.gy.gyservice.ITykhService;
import com.gy.gyutil.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("tykhServiceImpl")
public class TykhServiceImpl implements ITykhService {
    @Resource
    private TykhMapper tykhMapper;
    @Resource
    private GoodsMappingMapper goodsMappingMapper;

    @Value("${gy_gsdm}")
    private String gygsdm;

    private static final Logger LOG = LoggerFactory.getLogger(TykhServiceImpl.class);

    @Override
    public List<Tykh> getBjd(String gsdm, String fsrq,String brand,String anyField,Double priceStart,Double priceEnd,String timeStart,String timeEnd) {
        if(fsrq==null){
            return null;
        }
        List<Tykh> list = null;
        try{
            if(gygsdm.equals(gsdm)) {
                list = tykhMapper.selectBjd(fsrq, gsdm+"%", brand, anyField, priceStart, priceEnd, timeStart, timeEnd, "kh_tykh");
            } else {
                list = tykhMapper.selectBjd(fsrq, gsdm+"%", brand, anyField, priceStart, priceEnd, timeStart, timeEnd, "b2b_bjd");
            }
        }catch (Exception e){
            LOG.error("---获取报价单异常："+e.getMessage()+"---");
        }
        return list;
    }
    @Override
    public String getMaxFsrqByGsdm(String gsdm) {
        String fsrq=null;
        try{
            if(gygsdm.equals(gsdm)) {
                fsrq = tykhMapper.selectMaxFsrqByGsdm(gsdm+"%", "kh_tykh");
            } else {
                fsrq = tykhMapper.selectMaxFsrqByGsdm(gsdm+"%", "b2b_bjd");
            }
        }catch (Exception e){
            LOG.error("---获取报价单日期异常："+e.getMessage()+"---");
        }
        return fsrq;
    }
    @Override
    public List<Tykh> handleList(List<Tykh> list) {
        if(list!=null&&list.size()>0){
            for(Tykh tykh:list){
                if(tykh.getGoodsMapping()==null){
                    GoodsMapping goodsMapping = new GoodsMapping();
                    String goodsid = MD5Util.string2MD5(tykh.getSpxx().getTid().toString());
                    goodsMapping.setSpdm(tykh.getSpxx().getTid());
                    goodsMapping.setGoodsid(goodsid);
                    goodsMappingMapper.insert(goodsMapping);
                    tykh.setGoodsMapping(goodsMapping);
                }
            }
        }
        return list;
    }

    @Override
    public RespMsg responseMsg(List<Tykh> tykhList) {
        RespMsg respMsg = new RespMsg();
        List<GoodsToJson> list = new ArrayList<GoodsToJson>();
        if(tykhList!=null&&tykhList.size()>0) {
            for (Tykh tykh : tykhList) {
                GoodsToJson goodsToJson = new GoodsToJson();
                goodsToJson.setGoodsFullName(tykh.getSpxx().getFullname());
                goodsToJson.setGoodsId(tykh.getGoodsMapping().getGoodsid());
                goodsToJson.setPrice(tykh.getJg2());
                goodsToJson.setGoodsName(tykh.getSpxx().getName());
                goodsToJson.setColor(tykh.getSpxx().getColor());
                goodsToJson.setBrand(tykh.getSpxx().getPp());
                goodsToJson.setTime(tykh.getXgrq());
                list.add(goodsToJson);
            }
            respMsg.setList(list);
            LOG.info("---报价单列表转json数据结束---");
        }else{
            LOG.info("---报价单列表为空---");
            respMsg.setCode(RespMsg.EXCODE);
            respMsg.setMsg(RespMsg.EXMSG);
        }
        return respMsg;
    }
}
