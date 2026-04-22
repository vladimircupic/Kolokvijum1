package com.example.kolokvijum1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kolokvijum1.databinding.FragmentKorisniciBinding;

public class KorisniciFragment extends Fragment {

    private FragmentKorisniciBinding binding;

    private android.content.BroadcastReceiver bgReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bgReceiver = new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context context, android.content.Intent intent) {

                binding.getRoot().setBackgroundColor(
                        android.graphics.Color.YELLOW
                );
            }
        };

        requireContext().registerReceiver(
                bgReceiver,
                new android.content.IntentFilter("CHANGE_BG"),
                android.content.Context.RECEIVER_NOT_EXPORTED
        );
        binding = FragmentKorisniciBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (bgReceiver != null) {
            requireContext().unregisterReceiver(bgReceiver);
        }

        binding = null;
    }


}