package com.common.app.common.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.common.app.R;
import com.common.app.common.base.BasePopupWindow;
import com.common.app.common.base.BaseViewItemFinder;
import com.common.app.common.chooseimgs.ImageFolder;
import com.common.app.common.utils.GlideUtil;
import com.common.app.common.utils.MetricsUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

/**
 * 选择图片文件目录弹窗
 */
public class ChooseImageFolderPopWindow extends BasePopupWindow {
    private ImageFolderHolder holder;
    private OnClickChooseImageListener onClickChooseImageListener;
    private ImageFolderAdapter adapter;

    public ChooseImageFolderPopWindow(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.x_pop_choose_image_fokder, null);
    }

    @Override
    protected void onViewCreate(View view) {
        holder = new ImageFolderHolder(view);
        adapter = new ImageFolderAdapter(context);
        holder.recyclerView.setAdapter(adapter);
    }

    /**
     * 设置数据
     */
    public void init(List<ImageFolder> imageFolders) {
        adapter.clear();
        adapter.addAll(imageFolders);
    }

    @Override
    public void show(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - MetricsUtils.getHeight(context));
    }

    public void setOnClickChooseImageListener(OnClickChooseImageListener onClickChooseImageListener) {
        this.onClickChooseImageListener = onClickChooseImageListener;
    }

    public interface OnClickChooseImageListener {
        void onChooseImage(ImageFolder imageFolder);
    }

    private class ImageFolderHolder extends BaseViewItemFinder {
        private RecyclerView recyclerView;

        ImageFolderHolder(View view) {
            super(view);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(getContext(), R.color.color_F0F0F0), 2));
        }
    }

    private class ImageFolderAdapter extends RecyclerArrayAdapter<ImageFolder> {

        ImageFolderAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageFolderItemHolder(parent);
        }
    }

    private class ImageFolderItemHolder extends BaseViewHolder<ImageFolder> {
        private ImageView ivImage;
        private TextView tvName, tvNum;

        ImageFolderItemHolder(ViewGroup parent) {
            super(parent, R.layout.x_item_image_folder);
            ivImage = $(R.id.iv_image);
            tvName = $(R.id.tv_name);
            tvNum = $(R.id.tv_num);
        }

        @Override
        public void setData(final ImageFolder data) {
            super.setData(data);
            Glide.with(getContext()).load(data.getFirstImagePath()).apply(GlideUtil.getRequestOptions()).into(ivImage);
            tvName.setText(data.getName());
            tvNum.setText(String.format("%s张", String.valueOf(data.images.size())));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onClickChooseImageListener != null)
                        onClickChooseImageListener.onChooseImage(data);
                }
            });
        }
    }
}
