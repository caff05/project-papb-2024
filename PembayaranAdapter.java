package ap.mobile.pembayaran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class PembayaranAdapter extends RecyclerView.Adapter {

    private final Context ctx;
    private final List<Bayar> dataset;
    private DatabaseReference appDb;

    public PembayaranAdapter(Context ctx, List<Bayar> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    public void setAppDb(DatabaseReference appDb){
        this.appDb = appDb;
    }
    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvNama;
        private final TextView tvHarga;
        private final Button btDel;
        private Bayar bayar;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvNama = itemView.findViewById(R.id.tvNama);
            this.tvHarga = itemView.findViewById(R.id.tvNotel);
            this.btDel = itemView.findViewById(R.id.btDel);
            this.btDel.setOnClickListener(this);
        }
        public void bind(Bayar bayar){
            this.bayar = bayar;
            this.tvNama.setText(bayar.getNama());
            this.tvHarga.setText(bayar.getHarga());
        }

        @Override
        public void onClick(View v) {
            appDb.child(this.bayar.getId()).removeValue();

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_ponbuk, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.bind(this.dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}
