package org.numisoft.drawertest.recycler.holders;

import android.view.View;

import org.numisoft.drawertest.model.NavMenuItem;

import butterknife.ButterKnife;

/**
 * Created by mac-200 on 11/28/17.
 */

public class HeaderItemHolder extends BaseItemHolder {

//    @BindView(R.id.header_image)
//    ImageView ivHeader;

    public HeaderItemHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(NavMenuItem item, int position) {
//        ivHeader.setImageResource(R.drawable.material_background);
    }

}
