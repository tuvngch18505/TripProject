package com.example.tripproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.R;
import com.example.tripproject.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> implements  Filterable{


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TripEntity> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(tripListAll);
            }else{
                for (TripEntity trip : tripListAll){
                    if(trip.getNameTrip().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(trip);
                    }else if(trip.getDayOfTrip().contains(charSequence.toString())){
                        filteredList.add(trip);
                    }else if(trip.getChooseServices().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(trip);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            tripList.clear();
            tripList.addAll((Collection<? extends TripEntity>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface ListItemListener{
        void onItemClick(View view, int position);
    }


    //add Attribute
    private List<TripEntity> tripList;
    private List<TripEntity> tripListAll;
    private ListItemListener listener;

    public void setListener(ListItemListener listener){
        this.listener = listener;
    }

    public TripListAdapter(List<TripEntity> tripList){
        this.tripList = tripList;
        this.tripListAll = new ArrayList<>(tripList);
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        TripEntity tData = tripList.get(position);
        holder.bindData(tData);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemBinding itemViewBinding;

        public TripViewHolder(View itemView) {
            super(itemView);
            itemViewBinding = ListItemBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        public void bindData(TripEntity tData){
            itemViewBinding.tripTitle.setText(tData.getNameTrip());
            itemViewBinding.dayTitle.setText(tData.getDayOfTrip());
            itemViewBinding.serviceTitle.setText(tData.getChooseServices());

        }

        @Override
        public void onClick(View view) {

            listener.onItemClick(view, getAbsoluteAdapterPosition());
        }


    }


}

