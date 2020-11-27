package com.wang.photo.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.common.app.R;
import com.wang.photo.common.base.BaseActivity;
import com.wang.photo.common.base.ToolbarFinder;
import com.orhanobut.logger.Logger;

import org.apache.http.util.EncodingUtils;


/**
 * 网页加载
 * Created by xiangzi on 2016/8/4.
 */
public class WebViewActivity extends BaseActivity {
    private WebHolder holder;

    public static Intent newIntent(Context context, WebData webData) {
        return new Intent(context, WebViewActivity.class).putExtra(DATA_KEY, webData);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_activity_webview);
        holder = new WebHolder(this);
        WebData webData = getIntent().getParcelableExtra(DATA_KEY);

        if (webData == null) finish();

        assert webData != null;
        holder.init(webData.title);

        Logger.d("加载url-->" + webData.url);

        if (TextUtils.isEmpty(webData.postValue)) {
            holder.db_activity_wb.loadUrl(webData.url);
        } else {
            holder.db_activity_wb.postUrl(webData.url, EncodingUtils.getBytes(webData.postValue, "base64"));
        }


    }

    @Override
    public void onBackPressed() {
        if (holder.db_activity_wb.canGoBack()) {
            holder.db_activity_wb.goBack();
        } else {
            super.onBackPressed();
        }
    }

    static class WebHolder extends ToolbarFinder {
        private WebView db_activity_wb;
        private ProgressBar activity_wb_pb;
        private TextView tvTitle;

        WebHolder(Activity activity) {
            super(activity);
            db_activity_wb = findViewById(R.id.db_activity_wb);
            activity_wb_pb = findViewById(R.id.activity_wb_pb);
            init();
        }

        void init(String title) {
            initTab(tvTitle = getTextView(title));
        }

        @SuppressLint("SetJavaScriptEnabled")
        private void init() {
            db_activity_wb.getSettings().setJavaScriptEnabled(true);
            db_activity_wb.getSettings().setUseWideViewPort(true);
            db_activity_wb.getSettings().setLoadWithOverviewMode(true);
            db_activity_wb.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100)
                        activity_wb_pb.setVisibility(View.GONE);
                }
            });

            db_activity_wb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                    view.loadUrl(url);
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String title = view.getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        tvTitle.setText(title);
                    }
                }
            });

        }
    }

    /**
     * h5页面加载参数
     */
    public static class WebData implements Parcelable {
        String title;//页面标题
        String url;//地址
        String postValue;//请求的参数

        public WebData(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public WebData(String title, String url, @Nullable String postValue) {
            this.title = title;
            this.url = url;
            this.postValue = postValue;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.url);
            dest.writeString(this.postValue);
        }

        protected WebData(Parcel in) {
            this.title = in.readString();
            this.url = in.readString();
            this.postValue = in.readString();
        }

        public static final Creator<WebData> CREATOR = new Creator<WebData>() {
            @Override
            public WebData createFromParcel(Parcel source) {
                return new WebData(source);
            }

            @Override
            public WebData[] newArray(int size) {
                return new WebData[size];
            }
        };
    }

}
