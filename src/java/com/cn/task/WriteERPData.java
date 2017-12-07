/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.controller.CommonController;
import com.cn.util.DatabaseOpt;
import com.cn.util.Units;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class WriteERPData implements Runnable {

    private static final Logger LOG = Logger.getLogger(WriteERPData.class);
    private final String ERP_DATA_STR;

    public WriteERPData(String ERP_DATA_STR) {
        this.ERP_DATA_STR = ERP_DATA_STR;
    }

    @Override
    public void run() {
        LOG.info(ERP_DATA_STR + "开始写GPS状态...");
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = null;
        CallableStatement statement = null;
        try {
            int delRes = deleteAllGpsState(ERP_DATA_STR);
            if (delRes == -1) {
                LOG.error("删除记录失败");
                return;
            } else {
                LOG.info("删除记录成功, 返回值:" + delRes);
            }
            conn = opt.getConnection(DatabaseOpt.GPS_STATUS);
            statement = conn.prepareCall("select * from V_VFI_STATE");
            ResultSet set = statement.executeQuery();
            JSONArray addList = new JSONArray();
            //JSONArray updateList = new JSONArray();
            while (set.next()) {
                String carNo = set.getString("VEHI_NO");
                int gpsState = set.getInt("VEHI_GPSSTATE");
                int taskState = set.getInt("VEHI_TASKSTATE");

                //新增记录
                JSONObject addObj = new JSONObject();
                addObj.put("ycs_Card", carNo);
                addObj.put("ycs_Gpsstate", gpsState);
                addObj.put("ycs_Taskstate", taskState);

                addList.add(addObj);
            }

            try {
                if (addList.size() > 0) {
                    LOG.info("添加数据" + addList.size() + "条");
                    CommonController controller = new CommonController();
                    ArrayList<Integer> result = controller.dataBaseOperateSpecial(JSONObject.toJSONString(addList, Units.features), "com.cn.bean.CarGPSState", "Yt_Card_Status", "add", ERP_DATA_STR);
                    if (result.get(0) != 0) {
                        LOG.error("写GPS状态失败!");
                    }
                }
            } catch (Exception e) {
                LOG.error("写GPS状态出错!", e);
            }
        } catch (Exception e) {
            LOG.error("数据库操作失败!", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                LOG.error("数据库关闭失败!", e);
            }
        }
        LOG.info(ERP_DATA_STR + "写GPS状态结束...");
    }
    
    private int deleteAllGpsState(String erpDataStr) {
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = null;
        CallableStatement statement = null;
        try {
            conn = opt.getConnection(erpDataStr);
            statement = conn.prepareCall("delete from Yt_Card_Status");
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("数据库操作失败!", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                LOG.error("数据库关闭失败!", e);
            }
        }
        return -1;
    }

}
