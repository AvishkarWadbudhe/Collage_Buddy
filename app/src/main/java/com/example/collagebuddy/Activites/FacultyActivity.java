package com.example.collagebuddy.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.collagebuddy.Adapters.FacultyAdapter;
import com.example.collagebuddy.Models.FacultyDataModel;
import com.example.collagebuddy.R;
import com.example.collagebuddy.databinding.ActivityFacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FacultyActivity extends AppCompatActivity {
private ActivityFacultyBinding binding;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    List<FacultyDataModel> facultyList;
    private ProgressDialog progressDialog;
   private  FacultyAdapter facultyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        databaseReference =  FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        facultyList =new ArrayList<>();
        facultyAdapter = new FacultyAdapter(facultyList);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        fetchDataFromFirebase();
        setRecycleView();
        binding.imageBackButton.setOnClickListener(view -> onBackPressed());
    }
    private void fetchDataFromFirebase() {
        // Attach a ValueEventListener to the database reference
        DatabaseReference facultyReference = databaseReference.child("FacultyDetails");

        // Attach a ValueEventListener to the reference
        facultyReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                facultyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FacultyDataModel faculty = snapshot.getValue(FacultyDataModel.class);
                    facultyList.add(faculty);
                }
                progressDialog.dismiss();
                facultyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void setRecycleView()
    {
        binding.recyclerViewFaculty.setAdapter(facultyAdapter);
        binding.recyclerViewFaculty.setLayoutManager(new LinearLayoutManager(this));
    }
}