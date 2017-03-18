package com.xxp.yangyan.pro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.bean.SettingInfo;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SettingInfo> settings;

    public SettingAdapter(List<SettingInfo> settings) {
        this.settings = settings;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.getInflaterView(R.layout.item_setting);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyHolder holder = (MyHolder) viewHolder;
        holder.image.setImageResource(settings.get(position).getImage());
        holder.title.setText(settings.get(position).getTitle());

        if(position==0){
            holder.more.setVisibility(View.VISIBLE);
            holder.more.setText(settings.get(position).getCacheCount());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_setting)
        TextView title;
        @BindView(R.id.tv_more)
        TextView more;
        @BindView(R.id.civ_setting)
        ImageView image;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onClickListener(int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}