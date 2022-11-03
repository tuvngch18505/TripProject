package com.example.tripproject;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.ExpenseDAO;
import com.example.tripproject.Models.ExpenseEntity;
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentAddExpenseBinding;

import java.util.Calendar;


public class AddExpenseFragment extends Fragment {

    private FragmentAddExpenseBinding binding;
    private Database database;
    private int tripID;
    private DatePickerDialog datePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);

        database = RoomHelper.initDatabase(getContext());

        binding.itemTimeExpense.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    binding.itemTimeExpense.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();

        });

        CalendarExpense();
        tripID = getArguments().getInt("tripID");
        TripEntity trip = database.tripDao().getTripById(tripID);
        binding.txtTrip.setText(trip.getNameTrip());


        binding.saveActionExpense.setOnClickListener(v -> {


            if (binding.itemTypeExpense.length() == 0) {
                binding.itemTypeExpense.requestFocus();
                binding.itemTypeExpense.setError("Type Expense is required!");
            } else if (binding.itemAmountExpense.length() == 0) {
                binding.itemAmountExpense.requestFocus();
                binding.itemAmountExpense.setError("Amount Expense is required");
            } else if (binding.itemTimeExpense.length() == 0) {
                binding.itemTimeExpense.requestFocus();
                binding.itemTimeExpense.setError("Time Expense is required");
            } else {

                String typeExpense = binding.itemTypeExpense.getText().toString();
                String amount = binding.itemAmountExpense.getText().toString();
                String time = binding.itemTimeExpense.getText().toString();
                String comment = binding.itemCommentExpense.getText().toString();


                ExpenseEntity eData = new ExpenseEntity(trip, typeExpense, amount, time, comment);
                eData.setTripEntity(trip);


                database.expenseDAO().insert(eData);

                requireActivity().onBackPressed();
                //NavHostFragment.findNavController(this).navigate(R.id.expenseFragment, bundle);
                Toast.makeText(getActivity(), "Add Expense Successfully!", Toast.LENGTH_SHORT).show();
            }

        });


        return binding.getRoot();
    }

    private void CalendarExpense() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        binding.itemTimeExpense.setText(mDay + "/ " + (mMonth+1) + "/ " + mYear);
    }
}