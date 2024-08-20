package com.j.icons;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jOS.Core.utils.IconUtils;

public class IconViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private ImageView image;

    public IconViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.item_icon_title);
        image = itemView.findViewById(R.id.item_icon_image);
    }

    public void bind(final IconUtils.Icon icon, final IconSelectListener listener) {
        title.setText(icon.getTitle());
        image.setImageDrawable(icon.getImage());
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIconSelect(icon);
            }
        });
    }
}
