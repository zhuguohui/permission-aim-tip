package com.trs.app.aim_tip.impl.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trs.app.aim_tip.R;
import com.trs.app.aim_tip.bean.AimData;

import java.util.List;


public class PermissionsAimDescribeDialog extends DialogFragment {
    private Context context;
    private List<AimData> data;
    private onDismissListener onDismissListener;
    private DialogStyleData dialogStyleData;

    public PermissionsAimDescribeDialog(Context context, List<AimData> list, DialogStyleData dialogStyleData) {
        this.context = context;
        this.data = list;
        this.dialogStyleData = dialogStyleData;
    }

    @Override
    public void onStart() {
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

        super.onStart();
    }

    public void setOnDismissLister(onDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        View view = LayoutInflater.from(context).inflate(dialogStyleData.getDialogLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.dialog_rv);
        recyclerView.setAdapter(new AimDescribeAdapter(data));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        view.findViewById(R.id.aim_tip_id_recycle_view).setOnClickListener(v -> {
            this.dismiss();
            if (onDismissListener != null) onDismissListener.onAction();
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (getParentFragmentManager() != null) super.dismiss();
    }

    class AimDescribeAdapter extends RecyclerView.Adapter<aimDescribeViewHolder> {
        private List<AimData> itemDataList;

        public AimDescribeAdapter(List<AimData> itemDataList) {
            this.itemDataList = itemDataList;
        }

        @NonNull
        @Override
        public aimDescribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(dialogStyleData.getItemLayoutId(), parent, false);
            return new aimDescribeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull aimDescribeViewHolder holder, int position) {
            holder.permissionsAim.setText(itemDataList.get(position).getAim());
            holder.permissionsTitle.setText(itemDataList.get(position).getPermission());

        }

        @Override
        public int getItemCount() {
            return itemDataList.size();
        }
    }

    class aimDescribeViewHolder extends RecyclerView.ViewHolder {
        private TextView permissionsTitle;
        private TextView permissionsAim;

        public aimDescribeViewHolder(View itemView) {
            super(itemView);
            permissionsTitle = itemView.findViewById(R.id.aim_tip_id_item_title);
            permissionsAim = itemView.findViewById(R.id.aim_tip_id_item_content);
        }
    }


    public interface onDismissListener {
        void onAction();
    }
}
