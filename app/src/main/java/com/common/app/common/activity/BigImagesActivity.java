package com.common.app.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.app.R;
import com.common.app.common.adapter.SimplePageViewAdapter;
import com.common.app.common.base.BaseActivity;
import com.common.app.common.base.BaseFragment;
import com.common.app.common.base.BaseViewFinder;
import com.common.app.common.utils.BitmapUtils;
import com.common.app.common.utils.GlideUtil;
import com.common.app.common.utils.ImageUrlUtils;
import com.common.app.common.utils.UiCompat;
import com.common.app.common.widget.CustomDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 多图大图查看
 */
public class BigImagesActivity extends BaseActivity {
    private static final String DATA = "DATA";
    private static final String POSITION_KEY = "POSITION";

    private BigImgsHolder bigImgsHolder;
    private ArrayList<String> listImgs;//图片集
    private int allnum;//位置,总数


    /**
     * @param imgs     arraylist 图片集
     * @param position 初始位置
     */
    public static Intent newIntent(Context context, ArrayList<String> imgs, int position) {
        return new Intent(context, BigImagesActivity.class).putStringArrayListExtra(DATA, imgs).putExtra(POSITION_KEY, position);
    }

    /**
     * @param imgs     arraylist 图片集
     * @param position 初始位置
     */
    public static Intent newIntent(Context context, String[] imgs, int position) {
        List<String> data = Arrays.asList(imgs);
        ArrayList<String> _data = new ArrayList<>(data);
        Logger.d("images size-》" + _data.size());
        return new Intent(context, BigImagesActivity.class).putStringArrayListExtra(DATA, _data).putExtra(POSITION_KEY, position);
    }


    /**
     * 查看单张图片
     */
    public static Intent newIntent(Context context, String image) {
        return newIntent(context, new String[]{image}, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiCompat.setImageTransparent(this);
        setContentView(R.layout.x_activity_bigimgs);
        bigImgsHolder = new BigImgsHolder(this);

        listImgs = getIntent().getStringArrayListExtra(DATA);
        if (listImgs.isEmpty()) finish();
        int position = getIntent().getIntExtra(POSITION_KEY, 0);
        allnum = listImgs.size();
        Fragment[] fragments = new Fragment[allnum];

        bigImgsHolder.initView(allnum, position);

        SimplePageViewAdapter adapter = new SimplePageViewAdapter(getSupportFragmentManager(), fragments) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ImgFragment.newInstance(listImgs.get(position));
            }
        };
        bigImgsHolder.viewPager.setAdapter(adapter);
        bigImgsHolder.viewPager.setCurrentItem(position);

        bigImgsHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bigImgsHolder.initView(allnum, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0://什么也没做
                        bigImgsHolder.setTvNumVisibility(false);
                        break;
                    case 1://开始滑动
                        bigImgsHolder.setTvNumVisibility(true);
                        break;
                    case 2://滑动结束
                        break;
                }
            }
        });
    }

    /**
     * 图片碎片
     */
    public static class ImgFragment extends BaseFragment {
        private static final String DATA_imgurl = "data_imgurl";
        private PhotoView photoView;
        private ProgressBar progressBar;

        public static Fragment newInstance(String imgurl) {
            ImgFragment fragment = new ImgFragment();
            Bundle bundle = new Bundle();
            bundle.putString(DATA_imgurl, imgurl);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getLayoutId() {
            return R.layout.x_fragment_big_image;
        }

        @Override
        public void initHolder(View view) {
            photoView = view.findViewById(R.id.photoView);
            progressBar = view.findViewById(R.id.progress_bar);
        }

        @Override
        public void initView() {
            init();
        }

        protected void init() {
            Bundle bundle = getArguments();
            if (bundle == null) return;
            final String imgurl = getArguments().getString(DATA_imgurl);
            Logger.d("-load image url->" + imgurl);
            Glide.with(this).load(ImageUrlUtils.getImageLargeUrl(imgurl)).thumbnail(0.1f).apply(GlideUtil.getRequestOptionsBigImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(photoView);

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            });

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getActivity() != null) {
                        new CustomDialog.Builder(getActivity())
                                .setMessage(getActivity().getString(R.string.whether_save_photo))
                                .setConfirmListener(new CustomDialog.OnClickListener() {
                                    @Override
                                    public void onClick(CustomDialog dialogCustom, View v) {
                                        BitmapUtils.saveImage2Local(getActivity(), imgurl);
                                    }
                                })
                                .create().show();
                    }
                    return false;
                }
            });
        }
    }

    private class BigImgsHolder extends BaseViewFinder {

        private ViewPager viewPager;
        private TextView tv_num;

        BigImgsHolder(Activity activity) {
            super(activity);
            viewPager = activity.findViewById(R.id.x_bigimgs_viewpagere);
            tv_num = activity.findViewById(R.id.x_bigimgs_tv_num);
        }

        /**
         * 设置数量显示
         */
        public void initView(int allnum, int position) {
            if (allnum > 1)
                tv_num.setText(String.format("%s/%s", position + 1, allnum));
        }

        /**
         * 设置提示数量显示状态
         */
        void setTvNumVisibility(boolean isShow) {
            tv_num.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

}
