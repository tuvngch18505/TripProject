package com.example.tripproject;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tripproject.Adapter.ExpenseListAdapter;
import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.ExpenseEntity;
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentExpenseBinding;

import java.util.List;


public class ExpenseFragment extends Fragment {
    private Database database;
    private FragmentExpenseBinding binding;
    private MainViewModel mViewModel;
    private ExpenseListAdapter adapter;
    private int tripId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        database = RoomHelper.initDatabase(getContext());

        mViewModel.setDatabase(database);

        tripId = getArguments().getInt("tripID");

        Bundle bundle = new Bundle();
        //Recycler View
        RecyclerView rv = binding.recyclerview;
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(
                getContext(), (new LinearLayoutManager(getContext()).getOrientation())
        ));

        if (mViewModel.getDataExpenses(tripId).getValue().isEmpty()){
            binding.emptyExpense.setText("Empty!");
            binding.emptyExpense.setTextSize(20);
        }
            mViewModel.getDataExpenses(tripId).observe(getViewLifecycleOwner(), expense -> {
            adapter = new ExpenseListAdapter(expense);
            adapter.setListener((((view, position) -> {

                int expenseId = expense.get(position).getExpenseID();
                bundle.putInt("expenseId", expenseId);
                NavHostFragment.findNavController(this).navigate(R.id.action_expenseFragment_to_editor_ExpenseFragment, bundle);
            })));
            binding.recyclerview.setAdapter(adapter);
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        });


        setHasOptionsMenu(true);

        binding.fabAddExpense.setOnClickListener( view -> {
                    bundle.putInt("tripID" , tripId);
                    NavHostFragment.findNavController(this).navigate(R.id.action_expenseFragment_to_addExpenseFragment, bundle);
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete, menu);
        menu.add("Home").setIcon(R.drawable.ic_baseline_home_24).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM).setOnMenuItemClickListener(item->{
            backToHome();
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_del:
                return deleteAllExpense();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean backToHome() {
        Navigation.findNavController(getView()).navigate(R.id.mainFragment);
        return true;
    }

    private boolean deleteAllExpense() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete all trip?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes", (dialog, delete) -> {
                    database.expenseDAO().deleteAllExpense();
                    Toast.makeText(getActivity(), "Delete all expense Successfully", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("tripID", tripId);
                    Navigation.findNavController(getView()).navigate(R.id.expenseFragment, bundle);

                }).show();
        return true;
    }

}