package com.example.tripproject;

import android.app.AlertDialog;
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
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentEditorBinding;

import java.util.Calendar;

public class EditorFragment extends Fragment {

    private FragmentEditorBinding binding;
    private DatePickerDialog datePickerDialog;
    private Database db;
    int doResult = 0;

    public static EditorFragment newInstance() {
        return new EditorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentEditorBinding.inflate(inflater, container, false);

        db = RoomHelper.initDatabase(getContext());

        Spinner tripName = binding.etTripName;
        ArrayAdapter<String> adapterTripName = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{
                " Select Trip", "Conference", "Customer meeting", "Festival", "Tour", "Sightseeing",
        });

        adapterTripName.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        Spinner tripService = binding.etServices;

        ArrayAdapter<String> adapterService = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{" Select Services", "Go By Car", "Go By Plane", "Go By Train"});
        adapterService.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        tripService.setAdapter(adapterService);
        tripName.setAdapter(adapterTripName);

        binding.etDate.setOnClickListener(new View.OnClickListener() {
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
                        binding.etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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


        int tripId = getArguments().getInt("tripID");
        TripEntity trip = db.tripDao().getTripById(tripId);

        binding.etTripName.setSelection(adapterTripName.getPosition(trip.getNameTrip()));

        binding.etDestination.setText(trip.getDestination());
        binding.etDate.setText(trip.getDayOfTrip());
        binding.radioGroup.check(trip.isRiskEvaluate() == 1 ? binding.radioButtonYes.getId() : binding.radioButtonNo.getId());
        binding.EtNote.setText(trip.getNote());
        binding.etServices.setSelection(adapterService.getPosition(trip.getChooseServices()));

        binding.editAction.setOnClickListener(view -> {
            EditText tripDestination = binding.etDestination;
            EditText tripDay = binding.etDate;
            EditText tripNote = binding.EtNote;
            if (tripName.getSelectedItem() == " Select Trip") {
                Toast.makeText(getContext(), "Please Choose Locations", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "Please Choose Services", Toast.LENGTH_SHORT).show();
            } else {
                trip.setNameTrip(binding.etTripName.getSelectedItem().toString());
                trip.setDestination(binding.etDestination.getText().toString());
                trip.setDayOfTrip(binding.etDate.getText().toString());
                trip.setNote(binding.EtNote.getText().toString());
                trip.setChooseServices(binding.etServices.getSelectedItem().toString());
//            TripEntity tripUpdate = new TripEntity(name, destination,day,doResult,note, service);
                db.tripDao().update(trip);
                NavHostFragment.findNavController(this).navigate(R.id.action_editorFragment_to_mainFragment);
                Toast.makeText(getActivity(), "Edit Trip Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        binding.expenseAction.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("tripID", tripId);
            NavHostFragment.findNavController(this).navigate(R.id.action_editorFragment_to_expenseFragment, bundle);
        });
        // call action bar
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_del:
                int tripId = getArguments().getInt("tripID");
                TripEntity trip = db.tripDao().getTripById(tripId);
                return deleteAndReturn(trip);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean deleteAndReturn(TripEntity tripID) {

        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete trip?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes", (dialog, delete) -> {
                    db.tripDao().deleteById(tripID.getTripId());
                    Toast.makeText(getActivity(), "Delete Trip Successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigateUp();
                }).show();

        return true;
    }
}