package com.xxp.yangyan.pro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.entity.SettingInfo;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 个人中心,设置的adapter
 */

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

        //第0个条目是关于缓存信息的,所以要将大小显示在后面,默认该textVie为不可见
        if (position == 0) {
            holder.info.setVisibility(View.VISIBLE);
            holder.info.setText(settings.get(position).getCacheCount());
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
        @BindView(R.id.tv_info)
        TextView info;
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