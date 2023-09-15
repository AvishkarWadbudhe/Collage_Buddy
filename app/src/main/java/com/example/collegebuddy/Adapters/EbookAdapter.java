package com.example.collegebuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegebuddy.Listeners.OnItemClickListener;
import com.example.collegebuddy.Models.EbookDataModel;
import com.example.collegebuddy.R;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder>{

    private Context context;
    private List<EbookDataModel> ebookList;
    private OnItemClickListener onItemClickListener;


    public EbookAdapter(List<EbookDataModel> ebookList,Context context,OnItemClickListener onItemClickListener) {
        this.ebookList = ebookList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_container, parent, false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        EbookDataModel ebook = ebookList.get(position);

        holder.ebookTitleTextView.setText(ebook.getEbookTitle());
        holder.dateTextView.setText(ebook.getDate());
        holder.TimeTextView.setText(ebook.getTime());

        if (ebook.getEbookThumbnail() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(ebook.getEbookThumbnail())
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.thumbnailImageView);
        }
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder_image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.thumbnailImageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onItemClickListener.onItemClick(ebook);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ebookList.size();
    }

    static class EbookViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView ebookTitleTextView;
        private  TextView dateTextView;
        private TextView TimeTextView;
        EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.eBookThumbnail);
            ebookTitleTextView = itemView.findViewById(R.id.eBookTitle);
            dateTextView = itemView.findViewById(R.id.date);
            TimeTextView = itemView.findViewById(R.id.time);

        }
    }
}
