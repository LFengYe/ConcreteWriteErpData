/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

/**
 *
 * @author LFeng
 */
public class CarGPSState {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    
    @FieldDescription(description = "车牌号")
    private String ycs_Card;
    @FieldDescription(description = "GPS状态")
    private int ycs_Gpsstate;
    @FieldDescription(description = "任务状态")
    private int ycs_Taskstate;

    public String getYcs_Card() {
        return ycs_Card;
    }

    public void setYcs_Card(String ycs_Card) {
        this.ycs_Card = ycs_Card;
    }

    public int getYcs_Gpsstate() {
        return ycs_Gpsstate;
    }

    public void setYcs_Gpsstate(int ycs_Gpsstate) {
        this.ycs_Gpsstate = ycs_Gpsstate;
    }

    public int getYcs_Taskstate() {
        return ycs_Taskstate;
    }

    public void setYcs_Taskstate(int ycs_Taskstate) {
        this.ycs_Taskstate = ycs_Taskstate;
    }
    
    
}
