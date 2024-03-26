package com.sparnyuk.notes.DEPART.ETK;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.ETK.DepartETKAdapter;

import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperETK;

import com.sparnyuk.notes.MainActivity;
import com.sparnyuk.notes.R;


public class ShowDepartETK extends AppCompatActivity {
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private DBHelperETK db;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
        actionBar=getSupportActionBar();
        actionBar.setTitle("ЭТК");
        actionBar.setDisplayShowHomeEnabled(true);

        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        db =new DBHelperETK(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDepartETK.this, AddDepartETK.class));
            }
        });
    }

    private void loadRecords() {
        DepartETKAdapter adapterRecord=new DepartETKAdapter(ShowDepartETK.this,
                db.getDepartNameETK(Constant.KEY_DEPARTMENT));
        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecords();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowDepartETK.this, MainActivity.class));
    }
}