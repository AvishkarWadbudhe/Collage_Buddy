package com.example.collegebuddy.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.collegebuddy.Adapters.NoticeAdapter;
import com.example.collegebuddy.Models.NoticeDataModel;
import com.example.collegebuddy.databinding.ActivityNoticeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
private ActivityNoticeBinding binding;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;
    private Calendar calendar;
    private List<NoticeDataModel> noticeList;
    private NoticeAdapter noticeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("Notice");
        noticeList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(noticeList);
        fetchDataFromFirebase();

        binding.noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.noticeRecyclerView.setAdapter(noticeAdapter);

        binding.imageBackButton.setOnClickListener(view -> onBackPressed());
    }
    private void fetchDataFromFirebase() {
        // Create a query to retrieve data in descending order of timestamps

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeDataModel noticeDataModel = snapshot.getValue(NoticeDataModel.class);
                    noticeList.add(noticeDataModel);


                }
                Collections.reverse(noticeList);
                progressDialog.dismiss();
                noticeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });



    }
}