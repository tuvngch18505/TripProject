package com.example.tripproject;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.ExpenseEntity;
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentEditorExpenseBinding;

import java.util.Objects;

public class Editor_ExpenseFragment extends Fragment {

    private FragmentEditorExpenseBinding binding;
    private Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditorExpenseBinding.inflate(inflater, container, false);
        database = RoomHelper.initDatabase(getContext());

        Bundle bundle = new Bundle();

        assert getArguments() != null;
        int expenseId = getArguments().getInt("expenseId");
        ExpenseEntity expense = database.expenseDAO().getExpenseById(expenseId);

        binding.itemTypeExpense.setText(expense.getTypeOfExpenses());
        binding.itemAmountExpense.setText(expense.getAmountOfTheExpenses());
        binding.itemTimeExpense.setText(expense.getTimeOfTheExpenses());
        binding.itemCommentExpense.setText(expense.getAddComments());

        binding.saveActionExpense.setOnClickListener(v -> {
            if (binding.itemTypeExpense.toString() == "") {
                binding.itemTypeExpense.requestFocus();
                binding.itemTypeExpense.setError("Type Expense is required!");
            } else if (binding.itemAmountExpense.toString() == "") {
                binding.itemAmountExpense.requestFocus();
                binding.itemAmountExpense.setError("Amount Expense is required");
            } else if (binding.itemTimeExpense.toString() == "") {
                binding.itemTimeExpense.requestFocus();
                binding.itemTimeExpense.setError("Time Expense is required");
            } else {
                expense.setTypeOfExpenses(binding.itemTypeExpense.getText().toString());
                 expense.setAmountOfTheExpenses(binding.itemAmountExpense.getText().toString());
                 expense.setTimeOfTheExpenses(binding.itemTimeExpense.getText().toString());
                expense.setAddComments(binding.itemCommentExpense.getText().toString());

                database.expenseDAO().update(expense);

                requireActivity().onBackPressed();
//                NavHostFragment.findNavController(this).navigate(R.id.expenseFragment, bundle);
                Toast.makeText(getActivity(), "Update Expense Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_del:
                int expenseId = getArguments().getInt("expenseId");
                ExpenseEntity expense = database.expenseDAO().getExpenseById(expenseId);
                return deleteAndReturn(expense);
            default: return super.onOptionsItemSelected(item);
        }

    }

    private boolean deleteAndReturn(ExpenseEntity expense) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete expense?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes" ,(dialog, delete) ->{
                    database.expenseDAO().deleteById(expense.getExpenseID());
                    Toast.makeText(getActivity(), "Delete Expense Successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigateUp();
                }).show();

        return true;
    }


}