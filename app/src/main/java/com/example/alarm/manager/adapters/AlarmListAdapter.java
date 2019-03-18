package com.example.alarm.manager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alarm.manager.R;
import com.example.alarm.manager.model.AlarmEntity;
import com.example.alarm.manager.util.DateOperations;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private Context context;

    private List<AlarmEntity> dataList;

    private OnItemClick onItemClick;

    public AlarmListAdapter(Context context, List<AlarmEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindData(dataList.get(holder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmName;
        TextView alarmDateTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmName = itemView.findViewById(R.id.alarm_name);
            alarmDateTime = itemView.findViewById(R.id.alarm_date_time);

        }

        private void bindData(final AlarmEntity alarmEntity) {
            if ("".equalsIgnoreCase(alarmEntity.getAlarmLabel())) {
                alarmName.setText(context.getString(R.string.label));
            } else {
                alarmName.setText(alarmEntity.getAlarmLabel());
            }

            alarmDateTime.setText(DateOperations.convertDate(alarmEntity.getAlarmTime()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onItemClicked(alarmEntity);
                    }
                }
            });

        }


    }


    public interface OnItemClick {
        void onItemClicked(AlarmEntity alarmEntity);
    }


    public void setItemClick(OnItemClick onEditItemClick) {
        this.onItemClick = onEditItemClick;
    }
}
