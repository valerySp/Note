package com.sparnyuk.notes.DEPART.Water;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.Water.DepartWaterAdapter;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperWater;
import com.sparnyuk.notes.MainActivity;
import com.sparnyuk.notes.R;


public class ShowDepartWater extends AppCompatActivity {
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private DBHelperWater db;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Сантехнический");
        actionBar.setDisplayShowHomeEnabled(true);

        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        db =new DBHelperWater(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDepartWater.this, AddDepartWater.class));
            }
        });
    }

    private void loadRecords() {
        DepartWaterAdapter adapterRecord=new DepartWaterAdapter(ShowDepartWater.this,
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
        startActivity(new Intent(ShowDepartWater.this, MainActivity.class));
    }
}