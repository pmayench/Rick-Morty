package com.dam2.m08.rickmortyapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener
{
    //region Variables
    public static Context mContext;
    private List<Character> mData;
    private List<Character> listaOriginal;
    private View.OnClickListener listener;
    //endregion

    //region Constructor
    public RecyclerAdapter(Context mContext, List<Character> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(mData);
    }
    //endregion

    //region ViewHolder
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.character_item, parent, false);

        //Escoltar el event de selecció a la llista
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position)
    {
        Character character = mData.get(position);
        holder.txtTitle.setText(character.getName());

        Glide.with(mContext).load(mData.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount(){return mData.size();}

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.imageFilm);
            txtTitle = itemView.findViewById(R.id.txtName);
        }
    }
    //endregion

    //region Mètodes propis
    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    //Metode per filtrar els camps del buscador al MainActivity
    public void filtrado(String txtBuscar)
    {
        int size = txtBuscar.length();
        if(size == 0)
        {
            mData.clear();
            mData.addAll(listaOriginal);
        }
        else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                List<Character> collecion = mData.stream()
                        .filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(collecion);
            }
            else
            {
                for(Character m : listaOriginal)
                {
                    if(m.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                    {
                        mData.add(m);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    //endregion

    //region OnClick
    @Override
    public void onClick(View view)
    {
        if(listener != null)
            listener.onClick(view);
    }
    //endregion
}

