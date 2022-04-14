package com.trs.app.aim_tip.impl.view;

import com.trs.app.aim_tip.R;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 15:23
 * Desc:封装了Dialog中样式的数据。便于定义自己需要的样式
 */
public class DialogStyleData {
    private final int layoutId;
    private final int itemLayoutId;

    public DialogStyleData() {
        this(R.layout.dialog_permissions_aim, R.layout.item_dialog_permissions_aim);
    }

    public DialogStyleData(int layoutId) {
        this(layoutId, R.layout.item_dialog_permissions_aim);
    }

    public DialogStyleData(int layoutId, int itemLayoutId) {
        this.layoutId = layoutId;
        this.itemLayoutId = itemLayoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getItemLayoutId() {
        return itemLayoutId;
    }
}
