package com.example.latihan123;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterHistory extends ArrayAdapter {

    private Context context;
    private List<DataHistory> historyList;

    public AdapterHistory(@NonNull Context context, List<DataHistory> Listitem) {
        super(context, R.layout.adapter_history, Listitem);
        this.context = context;
        historyList = Listitem;
    }

    public static class ViewHolder{
    private TextView tvSaklar,tvStatus, tvDiupdate, tvWaktu;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.adapter_history, null);
            holder.tvSaklar = convertView.findViewById(R.id.tvsaklar);
            holder.tvStatus = convertView.findViewById(R.id.tvstatus);
            holder.tvDiupdate = convertView.findViewById(R.id.tvdiupdate);
            holder.tvWaktu = convertView.findViewById(R.id.tvwaktu);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        DataHistory data = historyList.get(position);
        holder.tvSaklar.setText(data.getSaklar());
        holder.tvStatus.setText(data.getStatus());
        holder.tvDiupdate.setText(data.getUpdated_by());
        holder.tvWaktu.setText(data.getUpdated_at());
        return convertView;

    }
}
