package com.example.foundy.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;

import java.util.List;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    private final Context mContext;
    private final List<LostItem> mLostItemList;
    private final List<Uri> mLostItemImages;

    public LostItemAdapter(Context mContext, List<LostItem> mLostItemList,List<Uri> mLostItemImages ) {
        this.mContext = mContext;
        this.mLostItemList = mLostItemList;
        this.mLostItemImages = mLostItemImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lostitem_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < mLostItemList.size() && position<mLostItemImages.size()) {
            LostItem lostItem = mLostItemList.get(position);
            Uri uri = mLostItemImages.get(position);
            Log.i("LostItemAdapter", "Inside onBindViewHolder " + position + " list size: " + mLostItemImages.size());
            Log.i("LostItemAdapter", "Added image in position " + position);
            holder.bind(lostItem, uri);
        }
    }

    @Override
    public int getItemCount() {
        return mLostItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView lostImage;
        private final TextView whatText;
        private final TextView whereText;
        private final TextView whenText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lostImage = itemView.findViewById(R.id.lostImage);
            whatText = itemView.findViewById(R.id.whatText);
            whereText = itemView.findViewById(R.id.whereText);
            whenText = itemView.findViewById(R.id.whenText);
        }

        public void bind(LostItem lostItem, Uri uri) {
            if(uri == null)
                Log.i("LostItemAdapter" , "URI is null");
            whatText.setText(lostItem.getWhatLost());
            whereText.setText(lostItem.getWhereLost());
            whenText.setText(lostItem.getDate());
            Glide.with(mContext)
                    .load(uri)
                    .apply(new RequestOptions().override(600, 200))
                    .centerCrop()
                    .into(lostImage);
        }
    }
}
