package com.xxp.yangyan.pro.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.api.MMApi;
import com.xxp.yangyan.pro.bean.CategoryInfo;
import com.xxp.yangyan.pro.utils.GlideUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Fragment fragment;
    //数据源
    private List<CategoryInfo> categories;
    //颜色值,避免滑动时重复变色
    private SparseArray<Integer> sparseArray;
    private final int itemColor[] = {
            R.color.blue_and_green,
            R.color.blue,
            R.color.green,
            R.color.pink};
//    private final int background[] = {R.mipmap.bg_xgmn, R.mipmap.bg_snll, R.mipmap.bg_mrxb,
//            R.mipmap.bg_swmt, R.mipmap.bg_xgtx, R.mipmap.bg_rhdy, R.mipmap.bg_nshj, R.mipmap.bg_omns};

    private final String TAG = "CategoryAdapter";

    public CategoryAdapter(List<CategoryInfo> categories, Fragment fragment) {
        this.categories = categories;
        this.fragment = fragment;
        this.sparseArray = new SparseArray();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.getInflaterView(R.layout.item_category);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyHolder holder = (MyHolder) viewHolder;
        Log.e(TAG, "onBindViewHolder: " + MMApi.MY_BASE_URL + categories.get(position).getImgUrl());

        if(sparseArray.size()<=position){
            sparseArray.put(position,UIUtils.getColor(itemColor[(int) (Math.random() * 4)]));
        }

        holder.cardView.setCardBackgroundColor(sparseArray.get(position));
        holder.title.setText(categories.get(position).getTitle());

        GlideUtils.loadImageView(fragment,
                MMApi.MY_BASE_URL + categories.get(position)
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
        return categories.size();
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