package com.example.collegebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegebuddy.Activites.EbookActivity;
import com.example.collegebuddy.Activites.ExamInfoAndResultActivity;
import com.example.collegebuddy.Activites.FacultyActivity;
import com.example.collegebuddy.Activites.NoticeActivity;
import com.example.collegebuddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.examResultInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExamInfoAndResultActivity.class);
                startActivity(intent);
            }
        });
        binding.Notice.setOnClickListener(view -> {
            Intent intent =new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(intent);
        });

        binding.cardViewEbook.setOnClickListener(view -> {
            Intent intent =new Intent(MainActivity.this, EbookActivity.class);
            startActivity(intent);
        });
        binding.cardViewFacultyDetail.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FacultyActivity.class);
            startActivity(intent);
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}