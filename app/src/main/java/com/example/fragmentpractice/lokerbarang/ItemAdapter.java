package com.example.fragmentpractice.lokerbarang;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentpractice.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;  // Pastikan variabel ini ada
    private RecyclerViewClickListener mListener;

    // Konstruktor untuk menerima itemList dari luar
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList; // Menginisialisasi itemList dengan parameter yang diberikan
    }
    public void setDeleteClickListener( RecyclerViewClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);  // Mengakses item dari itemList
        holder.namaBarang.setText("Nama Barang: " + item.getNamaBarang());
        holder.namaPenitip.setText("Nama Penitip: " + item.getNamaPenitip());

        // Mengecek apakah gambar ada atau tidak
        if (item.getGambar() != null) {
            Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load(Base64.decode(item.getGambar(),Base64.DEFAULT))
                    .placeholder(R.drawable.boneka)
                    .into(holder.imageView);
        } else {
            Glide.with(holder.itemView.getContext())
                            .load(R.drawable.default1)
                                    .into(holder.imageView);

        }

        holder.deleteaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickDelete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();  // Mengembalikan jumlah item
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView namaBarang, namaPenitip;
        ImageView imageView;
        ImageButton deleteaBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.namaBarangTextView);
            namaPenitip = itemView.findViewById(R.id.namaPenitipTextView);
            imageView = itemView.findViewById(R.id.itemImageView);
            deleteaBtn = itemView.findViewById(R.id.remove_item);
        }
    }
}
