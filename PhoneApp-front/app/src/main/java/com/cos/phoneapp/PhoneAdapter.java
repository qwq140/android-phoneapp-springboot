package com.cos.phoneapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 어댑터와 RecyclerView와 연결 (Databinding 사용금지) (MVVM 사용금지)
public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private static final String TAG = "PhoneAdapter";
    private List<Phone> phones;
    private MainActivity mainActivity;

    public PhoneAdapter(List<Phone> phones, MainActivity mainActivity) {
        this.phones = phones;
        this.mainActivity = mainActivity;
    }

    public void addItem(Phone phone) {
        phones.add(phone);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        phones.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        holder.setItem(phones.get(position));
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvTel;
        private Phone phone;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvTel = itemView.findViewById(R.id.tel);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                phone = phones.get(position);
                mainActivity.editPhone(position, phone);
            });
        }

        public void setItem(Phone phone) {
            tvName.setText(phone.getName());
            tvTel.setText(phone.getTel());
        }
    }
}

