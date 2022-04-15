package com.trs.app.aim_tip.impl.view;

import com.trs.app.aim_tip.R;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 15:23
 * Desc:封装了Dialog中样式的数据。便于定义自己需要的样式
 */
public class DialogStyleData {
    private  int dialogLayoutId;
    private final int itemLayoutId;
    public static final int USE_DEFAULT_STYLE=-1;

    public DialogStyleData() {
        this(USE_DEFAULT_STYLE, USE_DEFAULT_STYLE);
    }



    public DialogStyleData(int dialogLayoutId, int itemLayoutId) {

        if(dialogLayoutId==USE_DEFAULT_STYLE){
            dialogLayoutId =R.layout.permission_aim_dialog;
        }
        if(itemLayoutId==USE_DEFAULT_STYLE){
            itemLayoutId=R.layout.permission_aim_dialog_item;
        }
        this.dialogLayoutId = dialogLayoutId;
        this.itemLayoutId = itemLayoutId;
    }

    public int getDialogLayoutId() {
        return dialogLayoutId;
    }

    public int getItemLayoutId() {
        return itemLayoutId;
    }
}
