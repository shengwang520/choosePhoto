package com.wang.choosephoto.scollviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.wang.choosephoto.R;
import com.wang.choosephoto.adapter.BasePagerAdapter;
import com.wang.choosephoto.utils.GlideLoadUtils;
import com.wang.choosephoto.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 * Created by wang on 2016/5/9.
 */
public class ScrollViewPagerImpl {
    public Context context;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons;
    private Fragment[] fragments;

    private List<ImgData> listData;//图片数据源
    private OnClickImgListener onClickImgListener;

    private ScrollImgTaskImpl scrollImgTask;
    private boolean isScroll;//是否在滚动
    private int buttonResId = R.drawable.x_selector_scoll_point;

    public ScrollViewPagerImpl(Context context, ViewPager viewPager, RadioGroup radioGroup) {
        this.context = context;
        this.viewPager = viewPager;
        this.radioGroup = radioGroup;
    }

    /**
     * 设置数据源
     */
    public void setListData(List<ImgData> listData) {
        this.listData = listData;
        fragments = new Fragment[listData.size()];
        radioButtons = new ArrayList<>(listData.size());

        if (listData.size() == 1) radioGroup.setVisibility(View.INVISIBLE);
        else radioGroup.setVisibility(View.VISIBLE);

    }

    /**
     * 设置小点图片
     */
    public void setButtonResId(int buttonResId) {
        this.buttonResId = buttonResId;
    }

    /**
     * 设置回掉接口
     */
    public void setOnClickImgListener(OnClickImgListener onClickImgListener) {
        this.onClickImgListener = onClickImgListener;
    }

    /**
     * 初始化界面
     */
    public void initView() {
        List<View> views = new ArrayList<>();
        for (final ImgData imgData : listData) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (imgData.resid > 0) {
                imageView.setImageResource(imgData.resid);
            } else {
                GlideLoadUtils.getInstance().glideLoad(context, imgData.imgurl, imageView, GlideUtil.getRequestOptions());
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickImgListener != null)
                        onClickImgListener.onClickImg(listData.indexOf(imgData), imgData);
                }
            });
            views.add(imageView);
        }

        BasePagerAdapter<View> pagerAdapter = new BasePagerAdapter<>(views);
        viewPager.setAdapter(pagerAdapter);

        createButtons(fragments.length);

        if (radioButtons.size() > 0) radioButtons.get(viewPager.getCurrentItem()).setChecked(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (radioButtons.size() > 0)
                    radioButtons.get(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (scrollImgTask == null) {
            scrollImgTask = new ScrollImgTaskImpl(viewPager);
            onResume();
        }


    }

    /**
     * 获取所有图片地址
     */
    public ArrayList<String> getAllImages() {
        ArrayList<String> result = new ArrayList<>();
        for (ImgData album : listData) {
            result.add(album.imgurl);
        }
        return result;
    }

    /**
     * 数据是否为空
     */
    public boolean isDataEmpty() {
        try {
            return listData == null || listData.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 创建按钮
     */
    @SuppressLint("ClickableViewAccessibility")
    private void createButtons(int num) {
        radioGroup.removeAllViews();
        for (int i = 0; i < num; i++) {
            RadioButton radioButton = new RadioButton(context);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(20, 20);
            params.setMargins(10, 0, 10, 0);
            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            radioButton.setBackgroundResource(buttonResId);
            radioButtons.add(radioButton);
            radioButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            radioGroup.addView(radioButton, params);
        }

    }

    /**
     * 开始滚动
     */
    public void onResume() {
        if (scrollImgTask != null && !isScroll) {
            scrollImgTask.onResume();
            isScroll = true;
        }
    }

    /**
     * 暂停滚动
     */
    public void onPause() {
        if (scrollImgTask != null) {
            scrollImgTask.onPause();
            isScroll = false;
        }
    }


    public interface OnClickImgListener {
        /**
         * 点击图片回掉
         */
        void onClickImg(int position, ImgData imgData);
    }

    /**
     * 图片实体
     */
    public static class ImgData implements Parcelable {
        public String id;//图片id
        public String url;//图片webview
        public String imgurl;//图片路径
        public String imgname;//图片内容
        public int resid;
        public static final Creator<ImgData> CREATOR = new Creator<ImgData>() {
            @Override
            public ImgData createFromParcel(Parcel source) {
                return new ImgData(source);
            }

            @Override
            public ImgData[] newArray(int size) {
                return new ImgData[size];
            }
        };

        public ImgData(String id, String url, String imgurl) {
            this.id = id;
            this.url = url;
            this.imgurl = imgurl;
        }

        public ImgData(String id, String url, int resid) {
            this.id = id;
            this.url = url;
            this.resid = resid;
        }

        public ImgData(String id, String url, String imgurl, String imgname) {
            this.id = id;
            this.url = url;
            this.imgurl = imgurl;
            this.imgname = imgname;
        }

        public int type;//类型

        public ImgData(int resid) {
            this.resid = resid;
        }

        public ImgData(String imgurl) {
            this.imgurl = imgurl;
        }

        public ImgData() {
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setImgname(String imgname) {
            this.imgname = imgname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ImgData(String id, String url, String imgurl, String imgname, int type) {
            this.id = id;
            this.url = url;
            this.imgurl = imgurl;
            this.imgname = imgname;
            this.type = type;
        }

        protected ImgData(Parcel in) {
            this.id = in.readString();
            this.url = in.readString();
            this.imgurl = in.readString();
            this.imgname = in.readString();
            this.resid = in.readInt();
            this.type = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.url);
            dest.writeString(this.imgurl);
            dest.writeString(this.imgname);
            dest.writeInt(this.resid);
            dest.writeInt(this.type);
        }
    }
}
