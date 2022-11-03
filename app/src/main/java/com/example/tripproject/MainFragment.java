package com.example.tripproject;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.example.tripproject.Adapter.TripListAdapter;
import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.RoomHelper;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private FragmentMainBinding binding;
    private List<TripEntity> listTrip;
    private Database db;
    private TripListAdapter adapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = RoomHelper.initDatabase(getContext());

        listTrip = new ArrayList<>();
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.setDatabase(db);
        binding = FragmentMainBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);

        //RecyclerView
        RecyclerView rv = binding.recyclerview;
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(
                getContext(), (new LinearLayoutManager(getContext()).getOrientation())
        ));

        // Get Data from DB
        if (db.tripDao().getAllTrips().isEmpty()) {
            binding.emptyTrip.setText("Empty!");
            binding.emptyTrip.setTextSize(20);

        }else{
            mViewModel.getData().observe(getViewLifecycleOwner(), tripEntities -> {
                adapter = new TripListAdapter(tripEntities);
                adapter.setListener(((view, position) -> {
                    Bundle bundle = new Bundle();
                    int tripId = tripEntities.get(position).getTripId();
                    bundle.putInt("tripID", tripId);
                    NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_editorFragment, bundle);
                }));
                binding.recyclerview.setAdapter(adapter);
                binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
            });
        }


        binding.fabAddTrip.setOnClickListener(v -> this.onItemClick());

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.menu_search, menu);
        menu.add("Delete").setIcon(R.drawable.ic_baseline_delete_sweep_24).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM).setOnMenuItemClickListener(item->{
            deleteAllTrip();
            return true;
        });

        if(db.tripDao().getAllTrips().isEmpty()){
            return;
        }else{
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private boolean deleteAllTrip() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete all trip?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes" ,(dialog, delete) ->{
                    db.tripDao().deleteAllTrip();
                    Toast.makeText(getActivity(), "Delete all trip successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.mainFragment);
                }).show();
        return true;
    }

    public void onItemClick() {
        Navigation.findNavController(getView()).navigate(R.id.addTripFragment);

    }


}