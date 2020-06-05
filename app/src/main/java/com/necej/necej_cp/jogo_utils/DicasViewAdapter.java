package com.necej.necej_cp.jogo_utils;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.necej.necej_cp.JogoActivity;
import com.necej.necej_cp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class DicasViewAdapter extends RecyclerView.Adapter<DicasViewAdapter.DicasHolder> {
    ArrayList<String> listaPalavras;
    Context mContext;
    Bundle mDesc;
    public DicasViewAdapter (ArrayList<String> list){
        listaPalavras = list;
        mDesc = JogoActivity.getDescricoes();
    }

    @NonNull
    @Override
    public DicasViewAdapter.DicasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_textv_jogo,parent,false);
        return new DicasViewAdapter.DicasHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull DicasHolder holder, int position) {
        holder.textView.setText(mDesc.getString(listaPalavras.get(position)));
    }

    @Override
    public int getItemCount() {
        return listaPalavras.size();
    }

    public static class DicasHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public DicasHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}