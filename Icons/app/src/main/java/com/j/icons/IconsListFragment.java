package com.j.icons;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jOS.Core.ActionBar2;
import jOS.Core.utils.ErrorUtils;
import jOS.Core.utils.IconUtils;

public class IconsListFragment extends Fragment implements IconSelectListener {
    private RecyclerView iconRecyclerView;
    public static IconListRecyclerAdapter iconListRecyclerAdapter;
    private ArrayList<IconUtils.Icon> icons;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar2 toolbar = requireActivity().findViewById(R.id.actionbar);
        if (toolbar != null) {
            toolbar.setTitleCentered(false);
        }
        if (MainActivity.mSwitchHideFallback != null) {
            MainActivity.mSwitchHideFallback.setVisibility(View.VISIBLE);
        }
        super.onCreate(savedInstanceState);

        PackageManager pm = requireContext().getPackageManager();
        Resources res = getResources();

        try {
            icons = IconUtils.ParseIconPack(requireContext(), "com.j.icons", res);
        } catch (Exception e) {
            ErrorUtils.handle(e, requireActivity());
            icons = new ArrayList<IconUtils.Icon>();
        }

        icons.add(new IconUtils.Icon("jOS System Component", AppCompatResources.getDrawable(requireContext(), jOS.Core.R.drawable.ic_launcher_j), requireActivity().getComponentName(), requireContext()));

        iconListRecyclerAdapter = new IconListRecyclerAdapter(this);
        iconListRecyclerAdapter.setIcons(icons);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listicons, container, false);

        iconRecyclerView = view.findViewById(R.id.fragment_listicons_recyclerView);
        iconRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iconRecyclerView.setAdapter(iconListRecyclerAdapter);

        return view;
    }

    @Override
    public void onIconSelect(IconUtils.Icon icon) {
        icon.getImage();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar2 toolbar = requireActivity().findViewById(R.id.actionbar);
        if (toolbar != null) {
            toolbar.setTitleCentered(false);
        }
        if (MainActivity.mSwitchHideFallback != null) {
            MainActivity.mSwitchHideFallback.setVisibility(View.VISIBLE);
        }
    }
}
