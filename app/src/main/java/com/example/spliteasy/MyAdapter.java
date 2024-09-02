package com.example.spliteasy;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<HistoryItem> historyList;
    private DatabaseHelper dbHelper;
    private Context context;

    // Constructor
    public MyAdapter(Context context, List<HistoryItem> data, DatabaseHelper dbHelper) {
        this.historyList = data;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, text, date;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            text = itemView.findViewById(R.id.card_text);
            date = itemView.findViewById(R.id.card_date);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind the data to the views
        HistoryItem item = historyList.get(position);
        holder.text.setText(item.getText());
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());

        // Set up a long-click listener to delete the item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Show a confirmation dialog
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the item from the database
                                boolean isDeleted = dbHelper.deleteItem(item.getId());

                                if (isDeleted) {
                                    // Remove the item from the list
                                    historyList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, historyList.size());

                                    // Show confirmation to the user
                                    Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}

