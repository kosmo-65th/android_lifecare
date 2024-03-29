package com.example.lifecare.appointment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.DoctorVO;

import java.util.ArrayList;

public class doctorAdapter extends RecyclerView.Adapter<doctorAdapter.doctorViewHolder> implements OnItemClickListener {

    private ArrayList<DoctorVO> arrayList;

    OnItemClickListener listener;

    public void setOnItemClicklistener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(doctorAdapter.doctorViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public class doctorViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_major;
        TextView tv_name;
        TextView tv_level;

        public doctorViewHolder(View itemView) {
            super(itemView);
            this.iv_img = itemView.findViewById(R.id.iv_img);
            this.tv_major = itemView.findViewById(R.id.tv_major);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_level = itemView.findViewById(R.id.tv_level);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(doctorViewHolder.this, v, position);
                    }
                }
            });
        }
    }

    public doctorAdapter(ArrayList<DoctorVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public doctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_doctor_item, parent, false);
        doctorViewHolder holder = new doctorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull doctorViewHolder holder, int position) {

        holder.tv_major.setText(arrayList.get(position).getDoctor_major());
        holder.tv_name.setText(arrayList.get(position).getDoctor_name());
        holder.tv_level.setText(arrayList.get(position).getDoctor_position());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

}
