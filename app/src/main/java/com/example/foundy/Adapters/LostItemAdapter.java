package com.example.foundy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;

import java.util.List;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    private Context mContext;
    private List<LostItem> mLostItemList;

    public LostItemAdapter(Context mContext, List<LostItem> mLostItemList) {
        this.mContext = mContext;
        this.mLostItemList = mLostItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lostitem_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LostItem lostItem = mLostItemList.get(position);
        holder.bind(lostItem);
    }

    @Override
    public int getItemCount() {
        return mLostItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView lostImage;
        private TextView whatText;
        private TextView whereText;
        private TextView whenText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lostImage = itemView.findViewById(R.id.lostImage);
            whatText = itemView.findViewById(R.id.whatText);
            whereText = itemView.findViewById(R.id.whereText);
            whenText = itemView.findViewById(R.id.whenText);
        }

        public void bind(LostItem lostItem) {
            whatText.setText(lostItem.getWhatLost());
            whereText.setText(lostItem.getWhereLost());
            whenText.setText(lostItem.getDate());
        }
    }
}
