package com.sparnyuk.notes.DEPART.Otdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.Otdel.DepartOtdelAdapter;

import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperOtd;

import com.sparnyuk.notes.MainActivity;
import com.sparnyuk.notes.R;


public class ShowDepartOtdel extends AppCompatActivity {
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private DBHelperOtd db;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Отдел");
        actionBar.setDisplayShowHomeEnabled(true);

        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        db =new DBHelperOtd(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDepartOtdel.this, AddDepartOtdel.class));
            }
        });
    }

    private void loadRecords() {
        DepartOtdelAdapter adapterRecord=new DepartOtdelAdapter(ShowDepartOtdel.this,
                db.getDepartName(Constant.KEY_DEPARTMENT));
        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecords();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowDepartOtdel.this, MainActivity.class));
    }
}