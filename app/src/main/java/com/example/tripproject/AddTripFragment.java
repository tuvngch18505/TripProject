package com.example.tripproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.DatabaseHelper;
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentAddTripBinding;
import com.google.android.material.textfield.TextInputEditText;


import java.util.Calendar;

public class AddTripFragment extends Fragment {


    private FragmentAddTripBinding binding;
    private DatePickerDialog datePickerDialog;
    private int doResult = 1;
    private Database db;
    private int tripID;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTripBinding.inflate(inflater, container, false);
        db = RoomHelper.initDatabase(getContext());


        Spinner tripName = binding.itemTripName;
        ArrayAdapter<String> adapterTripName = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{
                " Select Trip", "Conference", "Customer meeting", "Festival", "Tour", "Sightseeing",
        });

        adapterTripName.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        Spinner tripService = binding.itemServices;

        ArrayAdapter<String> adapterService = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[] {"Select Services", "Go By Car", "Go By Plane", "Go By Train"});
        adapterService.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        tripService.setAdapter(adapterService);
        tripName.setAdapter(adapterTripName);
        tripName.setSelection(0);
        tripService.setSelection(0);

        binding.itemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        binding.itemDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton_yes) {
                    doResult = 1;
                } else {
                    doResult = 0;
                }
            }
        });


        binding.saveAction.setOnClickListener(view -> {

            TextInputEditText tripDestination = binding.itemDestination;
            EditText tripDay = binding.itemDate;
            TextInputEditText tripNote = binding.itemNote;

            if(tripName.getSelectedItem().toString() == " Select Trip"){
                tripName.requestFocus();
                Toast.makeText(getContext(), "Please Choose Trip", Toast.LENGTH_SHORT).show();
            } else if (tripDestination.length() == 0) {
                tripDestination.requestFocus();
                tripDestination.setError("Destination is required");
            } else if (tripDay.length() == 0) {
                tripDay.requestFocus();
                tripDay.setError("Date is required");
            } else if (tripNote.length() == 0) {
                tripNote.requestFocus();
                tripNote.setError("Note is required");
            } else if (tripService.getSelectedItem() == " Select Services") {
                tripService.requestFocus();
                Toast.makeText(getContext(), "Please Choose Services", Toast.LENGTH_SHORT).show();
            } else {
                String name = binding.itemTripName.getSelectedItem().toString();
                String destination = binding.itemDestination.getText().toString();
                String day = binding.itemDate.getText().toString();
                String note = binding.itemNote.getText().toString();
                String service = binding.itemServices.getSelectedItem().toString();
                TripEntity trip = new TripEntity(name, destination, day, doResult, note, service);
                db.tripDao().insert(trip);

                NavHostFragment.findNavController(this).navigate(R.id.mainFragment);
                Toast.makeText(getActivity(), "Add Trip Successfully!", Toast.LENGTH_SHORT).show();
            }


        });

        return binding.getRoot();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                requireActivity().onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }



}