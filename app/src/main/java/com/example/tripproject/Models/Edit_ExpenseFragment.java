package com.example.tripproject.Models;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripproject.Adapter.TripListAdapter;
import com.example.tripproject.MainViewModel;
import com.example.tripproject.R;
import com.example.tripproject.databinding.FragmentEditorExpenseBinding;
import com.example.tripproject.databinding.FragmentMainBinding;

import java.util.List;

public class Edit_ExpenseFragment extends Fragment {

    private FragmentEditorExpenseBinding binding;
    private List<TripEntity> listTrip;
    private Database db;
    private TripListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditorExpenseBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}