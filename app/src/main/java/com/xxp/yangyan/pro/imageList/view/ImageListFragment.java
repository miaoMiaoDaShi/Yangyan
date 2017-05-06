package com.xxp.yangyan.pro.imageList.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.ImageAdapter;
import com.xxp.yangyan.pro.base.BaseRecyclerViewFragment;
import com.xxp.yangyan.pro.entity.ImageInfo;
import com.xxp.yangyan.pro.gallery.view.GalleryActivity;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.imageList.presenter.Presenter;
import com.xxp.yangyan.pro.utils.UIUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.xxp.yangyan.R.id.swipe;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :图城视图
 */

public class ImageListFragment extends BaseRecyclerViewFragment<Presenter, ImageInfo>
        implements ImageListView<List<ImageInfo>> {
    private final String TAG = "ImageListFragment";
    @BindView(R.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    //谷歌的刷新空间
    @BindView(swipe)
    SwipeRefreshLayout swipeRefresh;
    //存放图片
    private List<ImageInfo> mImageInfos;
    private ImageAdapter mImageAdapter;
    private ProgressDialog mDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initFlowLayout();
        recylerViewClickListener();
    }

    private void initData() {
        mImageInfos = getList();
        mImageAdapter = (ImageAdapter) getAdapetr();
    }

    @Override
    protected RecyclerView getRecylerView() {
        return rvImages;
    }

    @Override
    protected List<ImageInfo> getList() {
        return mImageInfos = mImageInfos == null ? new ArrayList<ImageInfo>() : mImageInfos;
    }

    @Override
    protected RecyclerView.Adapter getAdapetr() {
        return mImageAdapter = mImageAdapter == null ? new ImageAdapter(getList(), this, null) : mImageAdapter;
    }


    private void recylerViewClickListener() {
        mImageAdapter.setOnItemClick(new ImageAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                mDialog = new ProgressDialog(getActivity());
                mDialog.setTitle("请稍后");
                mDialog.setMessage("编号: " + mImageInfos.get(position).getLink() + "套图获取中..");
                mDialog.setIndeterminate(false);
                mDialog.setCancelable(false);
                mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.unSubscribe();
                    }
                });
                mDialog.show();
                presenter.loadData(Model.TYPE_PARTICULARS, Integer.parseInt(mImageInfos.get(position).getLink()));
            }
        });

    }


    private void initFlowLayout() {
        final List<String> types = new ArrayList<>();
        types.add("童颜巨乳");
        types.add("泳池派对");
        types.add("美");
        types.add("动感丝袜");
        types.add("学生装");
        types.add("制服诱惑");
        types.add("动感丝袜");
        tagFlowLayout.setAdapter(new TagAdapter<String>(types) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = UIUtils.getInflaterView(R.layout.item_flowlayout);
                TextView type = (TextView) view.findViewById(R.id.tv_type);
                type.setText(s);
                return view;
            }
        });
        tagFlowLayout.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_imagecity;
    }

    @Override
    protected void loadData() {
        presenter.loadData(Model.TYPE_NEW, getBaseOnScrollListener().getCurrentPage());
    }

    @Override
    protected SwipeRefreshLayout getRefreshLayout() {
        return swipeRefresh;
    }

    @Override
    protected Presenter bindPresenter() {
        return new Presenter();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void showLoading(boolean type) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, "showError: ", throwable);
        //异常信息中含有404,说明数据到底了
        if (throwable.toString().contains("404")) {
            loadIsEmpty();
            //设置自己的那个RecyclerView滚动监听辅助类,数据到底了,不能再滚动加载更多了
            getBaseOnScrollListener().setEnd(true);
        } else {
            //其他的异常,当然也就是加载错误罗
            loadError();
        }
    }

    @Override
    public void showData(List<ImageInfo> imageInfos) {
        Log.d(TAG, "showData: imageInfos: size" + imageInfos.size());
        getBaseOnScrollListener().showImageListToView(imageInfos);

    }


    @Override
    public void loadGalleryError(Throwable throwable) {
        mDialog.setMessage("阿欧!加载失败");
    }

    @Override
    public void loadGallerySuccess(List<ImageInfo> Imageinfos) {
        mDialog.dismiss();
        Intent intent = new Intent(App.getmContext(), GalleryActivity.class);
        intent.putExtra(GalleryActivity.KEY_IMAGES, (Serializable) Imageinfos);
        startActivity(intent);
    }
}
