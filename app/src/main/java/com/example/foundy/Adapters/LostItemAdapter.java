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
import com.example.foundy.RecyclerViewInterface;
import com.example.foundy.Structures.Item;

import java.util.List;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Item> mItemList;
    private final List<Uri> mLostItemImages;
    private final RecyclerViewInterface recyclerViewInterface;

    public LostItemAdapter(Context mContext, List<Item> mItemList, List<Uri> mLostItemImages, RecyclerViewInterface recyclerViewInterface) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        this.mLostItemImages = mLostItemImages;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lostitem_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < mItemList.size() && position<mLostItemImages.size()) {
            Item item = mItemList.get(position);
            Uri uri = mLostItemImages.get(position);
            Log.i("LostItemAdapter", "Inside onBindViewHolder " + position + " list size: " + mLostItemImages.size());
            Log.i("LostItemAdapter", "Added image in position " + position);
            holder.bind(item, uri);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null)
                    {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Item item, Uri uri) {
            if(uri == null)
                Log.i("LostItemAdapter" , "URI is null");
            whatText.setText(item.getWhatLost());
            whereText.setText(item.getWhereLost());
            whenText.setText(item.getDate());
            Glide.with(mContext)
                    .load(uri)
                    .apply(new RequestOptions().override(600, 200))
                    .centerCrop()
                    .into(lostImage);
        }
    }
}
