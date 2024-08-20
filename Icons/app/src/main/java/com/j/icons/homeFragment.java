package com.j.icons;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import jOS.Core.ActionBar2;

public class homeFragment extends Fragment {

   @Override
    public void onCreate(Bundle savedInstanceState) {
       ActionBar2 toolbar = requireActivity().findViewById(R.id.actionbar);
       if (toolbar != null) {
           toolbar.setTitleCentered(true);
       }
       if (MainActivity.mSwitchHideFallback != null) {
           MainActivity.mSwitchHideFallback.setVisibility(View.GONE);
       }
       super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       Button public_static_void_main_string_args = view.findViewById(R.id.button);
       public_static_void_main_string_args.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_iconsListFragment));
       return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar2 toolbar = requireActivity().findViewById(R.id.actionbar);
        if (toolbar != null) {
            toolbar.setTitleCentered(true);
        }
        if (MainActivity.mSwitchHideFallback != null) {
            MainActivity.mSwitchHideFallback.setVisibility(View.GONE);
        }
    }
}