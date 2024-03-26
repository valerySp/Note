package com.sparnyuk.notes.DEPART.Struct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.Struct.DepartStrAdapter;

import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperStr;

import com.sparnyuk.notes.MainActivity;
import com.sparnyuk.notes.R;


public class ShowDepartStr extends AppCompatActivity {
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private DBHelperStr db;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Производства");
        actionBar.setDisplayShowHomeEnabled(true);

        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        db =new DBHelperStr(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDepartStr.this, AddDepartStr.class));
            }
        });
    }

    private void loadRecords() {
        DepartStrAdapter adapterRecord=new DepartStrAdapter(ShowDepartStr.this,
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
        startActivity(new Intent(ShowDepartStr.this, MainActivity.class));
    }
}