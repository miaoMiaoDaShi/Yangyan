package com.xxp.yangyan.pro.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.api.MyApi;
import com.xxp.yangyan.pro.entity.ClassifyInfo;
import com.xxp.yangyan.pro.utils.GlideUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 分类列表的适配器
 */
public class ClassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Fragment fragment;
    //数据源
    private List<ClassifyInfo> classifyInfos;
    //颜色值,避免滑动时重复变色
    private SparseArray<Integer> sparseArray;
    private LayoutInflater mLayoutInflater;
    private final int itemColor[] = {
            R.color.blue_and_green,
            R.color.blue,
            R.color.green,
            R.color.pink};

    private final String TAG = "ClassifyAdapter";

    public ClassifyAdapter(Context context, List<ClassifyInfo> classifyInfos, Fragment fragment) {
        this.classifyInfos = classifyInfos;
        this.fragment = fragment;
        this.sparseArray = new SparseArray();
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_category,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyHolder holder = (MyHolder) viewHolder;

        //将4种基本的颜色,随机添加到集合中
        if(sparseArray.size()<=position){
            sparseArray.put(position,UIUtils.getColor(itemColor[(int) (Math.random() * 4)]));
        }

        //为标签设置颜色
        holder.cardView.setCardBackgroundColor(sparseArray.get(position));
        holder.title.setText(classifyInfos.get(position).getTitle());

        //图片的加载
        GlideUtils.loadImageView(fragment,
                MyApi.MY_BASE_URL + classifyInfos.get(position)
                        .getImgUrl(),
                holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classifyInfos.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category)
        ImageView imageView;
        @BindView(R.id.cv_category)
        CardView cardView;
        @BindView(R.id.tv_category)
        TextView title;

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