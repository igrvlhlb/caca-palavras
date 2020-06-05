package com.necej.necej_cp.jogo_utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DicasViewAdapter extends RecyclerView.Adapter<DicasViewAdapter.DicasHolder> {
    ArrayList<Palavra> listaPalavras;
    @NonNull
    @Override
    public DicasViewAdapter.DicasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DicasHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listaPalavras.size();
    }

    public static class DicasHolder extends RecyclerView.ViewHolder{

        public DicasHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}