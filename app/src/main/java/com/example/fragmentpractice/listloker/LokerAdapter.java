package com.example.fragmentpractice.listloker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.R;

import java.util.List;

public class LokerAdapter extends RecyclerView.Adapter<LokerAdapter.LokerViewHolder> {

    private List<Loker> lokers;
    private ListLokerListener mListener;

    // Set data loker
    public void setLokers(List<Loker> lokers) {
        this.lokers = lokers;
        notifyDataSetChanged();
    }
    public void setListener(ListLokerListener listener){
        mListener = listener;
    }
    public interface ListLokerListener{
        void onItemClick(Loker loker);
    }

    @Override
    public LokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loker, parent, false);
        return new LokerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LokerViewHolder holder, int position) {
        Loker loker = lokers.get(position);
        holder.namaLoker.setText(loker.getNama());
        holder.status.setText(loker.getStatus());
        holder.capacity.setText("Kapasitas: " + loker.getCapacity());
        holder.price.setText("Harga: Rp " + loker.getPrice());
        holder.location.setText("Lokasi: " + loker.getLocation()); // Menampilkan lokasi
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(loker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lokers == null ? 0 : lokers.size();
    }

    public static class LokerViewHolder extends RecyclerView.ViewHolder {
        TextView namaLoker, status, capacity, price, location;

        public LokerViewHolder(View itemView) {
            super(itemView);
            namaLoker = itemView.findViewById(R.id.namaLoker);
            status = itemView.findViewById(R.id.status);
            capacity = itemView.findViewById(R.id.capacity);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location); // Menambahkan TextView untuk lokasi
        }
    }
}
