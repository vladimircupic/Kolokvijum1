package com.example.kolokvijum1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum1.databinding.ItemZadatakBinding;

import java.util.List;

public class ZadatakAdapter extends RecyclerView.Adapter<ZadatakAdapter.ViewHolder> {

    private List<Zadatak> lista;

    public ZadatakAdapter(List<Zadatak> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemZadatakBinding binding;

        public ViewHolder(ItemZadatakBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemZadatakBinding binding = ItemZadatakBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Zadatak z = lista.get(position);

        holder.binding.txtNaziv.setText(z.getNaziv());
        holder.binding.txtVreme.setText(z.getVreme());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}