package com.xxp.yangyan.pro.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.xxp.yangyan.pro.bean.ImageInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AnalysisHTML {
    //获取主页的指定内容
    public static List<ImageInfo> HomePageToList(String content){
        List<ImageInfo> images = new ArrayList<>();
        try {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("div.post-thumb");
            for (Element element : elements) {
                String catalogue = element.select("header").select("div").select("span").text();
                String title = element.select("header").select("h2").select("a").text();
                String link = element.select("a").attr("href");
                String imgUrl = element.select("img").attr("src");

                ImageInfo image = new ImageInfo();
                image.setTitle(title);
                image.setCatalogue(catalogue);
                image.setImgUrl(imgUrl);
                image.setLink(link.replace(HTMLOne.MEINV_BASE_URL, ""));

                images.add(image);
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return images;
    }
    public static List<ImageInfo> ParticularsToList(String content, String id){
        List<ImageInfo> images = new ArrayList<>();
        try {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("div#page").select("div#content").select("div.container")
                    .select("div.row").select("div#primary").select("main#main").select("article#post-"+id)
                    .select("div.entry-content").select("div.rgg-imagegrid").select("a");
            for (Element element : elements) {
                String imgUrl = element.attr("href");
                String imgDisplay = element.select("img").attr("src");

                ImageInfo image = new ImageInfo();
                image.setImgUrl(imgUrl);
                Log.i("捕捉", "ParticularsToList: "+image.getImgUrl());

                images.add(image);

            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return images;
    }
    public static void loadGallery(final int position, final List<ImageInfo> imageList, final Context context) {
        final ProgressDialog mDialog;
        mDialog = new ProgressDialog(context);
        mDialog.setTitle("请稍后");
        mDialog.setMessage("编号: "+imageList.get(position).getLink()+"套图获取中..");
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();

//        ApiManager
//                .getCompositeSubscription()
//                .add(ApiManager
//                        .getContentService()
//                        .getParticulars(imageList.get(position).getLink())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<ResponseBody>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody s) {
//
//                                List<ImageInfo> images = new ArrayList<ImageInfo>();
//                                try {
//                                    images = (ParticularsToList(s.string(),imageList.get(position).getLink()));
//                                    mDialog.dismiss();
//                                    Intent intent = new Intent(UIUtils.getContext(), GalleryActivity.class);
//                                    intent.putExtra("gallery", (Serializable) images);
//                                    context.startActivity(intent);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }));
    }
}
