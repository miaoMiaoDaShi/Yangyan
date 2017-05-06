package com.xxp.yangyan.pro.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.entity.ImageInfo;
import com.xxp.yangyan.pro.utils.GlideUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 所有以格子样式显示图片的RecyclerView的adapter
 */

public class ImageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageInfo> images;
    private List<Integer> mHeights;
    private Fragment fragment;
    private Activity activity;
    private final T t;
    private LayoutInflater mLayoutInflater;
    private final int itemColor[] = {
            R.color.blue_and_green,
            R.color.blue,
            R.color.green,
            R.color.blueA,
            R.color.greenA,
            R.color.pinkA,
            R.color.pinkB,
            R.color.violet};

    //颜色值,避免滑动时重复变色
    private SparseArray<Integer> mSparseArray;

    public ImageAdapter(List<ImageInfo> images, Fragment fragment, Activity activity) {
        this.images = images;
        mHeights = new ArrayList<>();
        this.fragment = fragment;
        this.activity = activity;
        mSparseArray = new SparseArray<>();
        t = (T) ((fragment == null) ? activity : fragment);
        mLayoutInflater = LayoutInflater.from(t instanceof Activity ? (Context) t : ((Fragment) t).getContext());


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_image_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        //将4种基本的颜色,随机添加到集合中
        if (mSparseArray.size() <= position) {
            mSparseArray.put(position, UIUtils.getColor(itemColor[(int) (Math.random() * itemColor.length)]));
        }
        holder.rvItemImage.setCardBackgroundColor(mSparseArray.get(position));
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

//        ViewGroup.LayoutParams lp = holder.titleLayout.getLayoutParams();
//        // 随机高度, 模拟瀑布效果.
//        if (mHeights.size() <= position) {
//            mHeights.add((int) (lp.height + Math.random() * 200));
//            lp.height = mHeights.get(position);
//        }
//
//        holder.img.setLayoutParams(lp);

        //图片集如果没有标题,则应该是用户收藏图片,应该将title的背景隐藏
        if (TextUtils.isEmpty(images.get(position).getTitle())) {
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

        @BindView(R.id.rv_item_image)
        CardView rvItemImage;
        @BindView(R.id.ll_item_title)
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
