package com.sparnyuk.notes.DEPART.GAS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.GAS.DepartGasAdapter;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperGas;
import com.sparnyuk.notes.MainActivity;
import com.sparnyuk.notes.R;


public class ShowDepartGas extends AppCompatActivity {
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private DBHelperGas dbHelperGas;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Газовый");
        actionBar.setDisplayShowHomeEnabled(true);

        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        dbHelperGas = new DBHelperGas(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDepartGas.this, AddDepartGas.class));
            }
        });
    }

    private void loadRecords() {
        DepartGasAdapter adapterRecord= new DepartGasAdapter(ShowDepartGas.this,
                dbHelperGas.getDepartName(Constant.KEY_DEPARTMENT));
        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecords();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowDepartGas.this, MainActivity.class));
    }
}

/*
    private void searchRecords(String query) {
        AdapterRecord adapterRecord=new AdapterRecord(Water.this,
                dbHelper.searchRecords(query));
        recordsRv.setAdapter(adapterRecord);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_water,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

 */
