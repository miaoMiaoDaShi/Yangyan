package com.xxp.yangyan.pro.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.utils.GlideUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 钟大爷 on 2017/2/5.
 */

public class ImageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageInfo> images;
    private List<Integer> mHeights;
    private Fragment fragment;
    private Activity activity;
    private final T t;

    public ImageAdapter(List<ImageInfo> images, Fragment fragment, Activity activity) {
        this.images = images;
        mHeights = new ArrayList<>();
        this.fragment = fragment;
        this.activity = activity;
        t = (T) ((fragment == null) ? activity : fragment);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(UIUtils.getInflaterView(R.layout.item_image_grid));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        // 随机高度, 模拟瀑布效果.
        if (mHeights.size() <= position) {
            mHeights.add((int) (500 + Math.random() * 200));
        }
        holder.title.setText(images.get(position).getTitle());
        if (t instanceof Fragment) {
            GlideUtils.loadImageView(fragment,
                    images.get(position).getImgUrl(),
                    holder.img);
        } else if (t instanceof Activity) {
            GlideUtils.loadImageView(activity,
                    images.get(position).getImgUrl(),
                    holder.img);
        }

        ViewGroup.LayoutParams lp = holder.img.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.img.setLayoutParams(lp);

        //图片集如果没有标题,则应该是用户收藏图片,应该将title的背景隐藏
        if (TextUtils.isEmpty(images.get(position).getTitle())){
            holder.titleLayout.setBackgroundColor(UIUtils.getColor(android.R.color.transparent));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onClickListener(position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_img)
        ViewGroup titleLayout;
        @BindView(R.id.iv_item)
        ImageView img;
        @BindView(R.id.tv_item)
        TextView title;

        public MyViewHolder(View itemView) {
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
