package com.example.week9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<String> tl;

    public recyclerAdapter(ArrayList<String> tl){
        this.tl = tl;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView leffa;

        public MyViewHolder(final View view){
            super(view);
            leffa = view.findViewById(R.id.txtName);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineaarinen, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {

        String name = tl.get(position); // tähän leffan nimi haettuna listasta
        holder.leffa.setText(name);
    }

    @Override
    public int getItemCount() {
        return tl.size();
    }
}
