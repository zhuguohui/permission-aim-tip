package com.trs.app.aim_tip.bean;

import java.util.List;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 14:15
 * Desc:
 */
public class AimDescription {


    /**
     * androidPermissionNames
     */
    private List<String> androidPermissionNames;
    /**
     * showPermissionName
     */
    private String showPermissionName;
    /**
     * permissionAimDescription
     */
    private String permissionAimDescription;








   public AimData getAimData(String permission){
        if(androidPermissionNames.contains(permission)){
            return  new AimData(showPermissionName,permissionAimDescription);
        }
        return null;
    }
}
