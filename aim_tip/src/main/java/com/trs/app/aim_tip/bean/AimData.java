package com.trs.app.aim_tip.bean;

import java.util.Objects;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 13:52
 * Desc:
 */
public class AimData {
    String permission;
    String aim;

    public AimData(String permission, String aim) {
        this.permission = permission;
        this.aim = aim;
    }

    public String getAim() {
        return aim;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AimData itemData = (AimData) o;
        return Objects.equals(permission, itemData.permission) &&
                Objects.equals(aim, itemData.aim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, aim);
    }
}
