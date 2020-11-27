package com.wang.photo.common.base;

import android.text.TextUtils;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.common.app.R;


/**
 * toolbar 右侧菜单管理
 * Created by 圣王 on 2015/6/18 0018.
 */
public class BaseMenuHolder {
    public MenuItem oneItem, twoItem;

    public BaseMenuHolder(Toolbar toolbar) {
        oneItem = toolbar.getMenu().findItem(R.id.menu_item_one);
        twoItem = toolbar.getMenu().findItem(R.id.menu_item_two);

    }

    /**
     * 初始化按钮，只显示1个文字按钮
     *
     */
    public void init(CharSequence one) {
        init(one, 0, null, 0);

    }

    /**
     * 初始化按钮，只显示1个图片按钮，长按可现实文字说明
     *
     */
    public void init(CharSequence one, int oneresId) {
        init(one, oneresId, null, 0);

    }

    /**
     * 初始化按钮，显示2个图片或文字按钮
     *
     */
    public void init(CharSequence one, int oneresId, CharSequence two, int tworesId) {
        if (TextUtils.isEmpty(one) && oneresId <= 0) {
            oneItem.setVisible(false);
        } else {
            oneItem.setVisible(true);
            oneItem.setIcon(oneresId);
            oneItem.setTitle(one);
        }

        if (TextUtils.isEmpty(two) && tworesId <= 0) {
            twoItem.setVisible(false);
        } else {
            twoItem.setVisible(true);
            twoItem.setIcon(tworesId);
            twoItem.setTitle(two);
        }
    }
}
