package com.example.collagebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collagebuddy.Models.FacultyDataModel;
import com.example.collagebuddy.R;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder> {

    private final List<FacultyDataModel> facultyList;


    public FacultyAdapter(List<FacultyDataModel> facultyList) {
        this.facultyList = facultyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textDesignation;
        private ImageView imageView;
        private TextView contactTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.teacherName);
            textDesignation = itemView.findViewById(R.id.designation);
            imageView = itemView.findViewById(R.id.profileImage);
            contactTextView = itemView.findViewById(R.id.contactNumber);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FacultyDataModel faculty = facultyList.get(position);

        holder.textName.setText(faculty.getName());
        holder.textDesignation.setText(faculty.getDesignation());
        holder.contactTextView.setText(faculty.getContact());


        if (!faculty.getImageUrl().equalsIgnoreCase("none")) {
            Glide.with(holder.itemView.getContext())
                    .load(faculty.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.imageView);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder_image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }
}
