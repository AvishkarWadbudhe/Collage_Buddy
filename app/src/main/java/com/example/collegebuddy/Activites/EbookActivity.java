package com.example.collegebuddy.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import com.example.collegebuddy.Adapters.EbookAdapter;
import com.example.collegebuddy.Listeners.OnItemClickListener;
import com.example.collegebuddy.Models.EbookDataModel;
import com.example.collegebuddy.databinding.ActivityEbookBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EbookActivity extends AppCompatActivity implements OnItemClickListener {
private ActivityEbookBinding binding;

    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;
    private  List<EbookDataModel> eBookList;

    private EbookAdapter ebookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Content");
        progressDialog.show();
        eBookList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Ebooks");
        fetchDataFromFirebase();
        ebookAdapter = new EbookAdapter(eBookList,this,this);


        binding.recyclerViewEbook.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerViewEbook.setAdapter(ebookAdapter);
        binding.backImage.setOnClickListener(view -> onBackPressed());
    }
    private void fetchDataFromFirebase() {
        // Attach a ValueEventListener to the database reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eBookList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EbookDataModel ebookDataModel = dataSnapshot.getValue(EbookDataModel.class);
                    eBookList.add(ebookDataModel);
                }
                Collections.reverse(eBookList);

                // Notify the adapter of data change
                progressDialog.dismiss();
                ebookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if any
               // showToast("Failed to fetch data");
            }
        });
    }
    @Override
    public void onItemClick(EbookDataModel ebook) {
        // Assuming ebook.getPdfUrl() returns the URL to the PDF file
        String pdfUrl = ebook.getPdfUrl();

        try {
            // Create an Intent to open the PDF using the system's default PDF viewer
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(pdfUrl);
            intent.setDataAndType(uri, "application/pdf");

            // Start the activity with this intent
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            // Handle the exception
            // This should only occur if no app on the device can handle PDF files.
        }
    }


}