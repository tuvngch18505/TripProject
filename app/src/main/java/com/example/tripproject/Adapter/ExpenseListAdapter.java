package com.example.tripproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripproject.Models.ExpenseEntity;
import com.example.tripproject.Models.TripEntity;
import com.example.tripproject.R;
import com.example.tripproject.databinding.ListItemBinding;
import com.example.tripproject.databinding.ListItemExpenseBinding;

import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {

    private List<ExpenseEntity> expenseList;
    private List<TripEntity> tripList;
    private ListItemListener listener;

    public void setListener(ListItemListener listener){
        this.listener = listener;
    }

    public interface ListItemListener{
        void onItemClick(View view, int posion);
    }

    public ExpenseListAdapter(List<ExpenseEntity> expenseList){
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense, parent,false);

        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseEntity eData = expenseList.get(position);
        holder.bindData(eData);
    }


    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ListItemExpenseBinding itemViewBinding;


        public ExpenseViewHolder(View itemView){
            super(itemView);
            itemViewBinding = ListItemExpenseBinding.bind(itemView); //?
            itemView.setOnClickListener(this);
        }

        public void bindData(ExpenseEntity eData){
            itemViewBinding.typeExpense.setText(eData.getTypeOfExpenses());
            itemViewBinding.amountExpense.setText(eData.getAmountOfTheExpenses());
            itemViewBinding.timeExpense.setText(eData.getTimeOfTheExpenses());
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAbsoluteAdapterPosition()); // ??
        }
    }
}