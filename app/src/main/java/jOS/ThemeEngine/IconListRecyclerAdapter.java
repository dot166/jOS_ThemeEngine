package jOS.ThemeEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jOS.Core.utils.IconUtils;

public class IconListRecyclerAdapter extends RecyclerView.Adapter<IconViewHolder> {
    private IconSelectListener listener;
    private List<IconUtils.Icon> icons;

    public IconListRecyclerAdapter(IconSelectListener listener) {
        this.listener = listener;
        this.icons = new ArrayList<IconUtils.Icon>();
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        IconUtils.Icon icon = icons.get(position);
        holder.bind(icon, listener);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public void setIcons(ArrayList<IconUtils.Icon> icons) {
        this.icons = icons;
        notifyDataSetChanged();
    }
}
