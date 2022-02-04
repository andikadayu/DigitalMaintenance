package com.digitalmusdi.digitalmaintenance.ui.history;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.digitalmusdi.digitalmaintenance.R;
import com.digitalmusdi.digitalmaintenance.databinding.FragmentHistoryBinding;
import com.digitalmusdi.digitalmaintenance.databinding.FragmentHomeBinding;

public class HistoryFragment extends Fragment {

    HistoryViewModel historyViewModel;
    FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);


        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHistory;

        historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}