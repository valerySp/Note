package com.sparnyuk.notes.TITLE.GAS;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.GAS.TitleGasAdapter;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperGas;
import com.sparnyuk.notes.DEPART.GAS.ShowDepartGas;
import com.sparnyuk.notes.R;

public class ListTitleGas extends AppCompatActivity {

    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsTitle;
    private DBHelperGas dbHelperGas;
    String depart;
    ActionBar actionBar;
    String orderByNew= Constant.KEY_TIME_ADD+" DESC";
    String orderByOld= Constant.KEY_TIME_ADD+" ASC";
    String orderByTitleASC= Constant.KEY_TITLE+" ASC";
    String orderByTitleDESC= Constant.KEY_TITLE+" DESC";
    String currentOrderByStatus=orderByNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        addRecordBtn=findViewById(R.id.addRecordTitle);
        recordsTitle=findViewById(R.id.recordsTtl);
        dbHelperGas =new DBHelperGas(this);

        Intent intent=getIntent();
        depart=intent.getStringExtra("depart");

        actionBar=getSupportActionBar();
        actionBar.setTitle(depart);
        actionBar.setDisplayShowHomeEnabled(true);

        loadRecords(orderByNew);
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListTitleGas.this, AddTitleGas.class).putExtra("departAdd",depart));
            }
        });
    }

    private void loadRecords(String orderBy) {
        currentOrderByStatus=orderByNew;
        TitleGasAdapter adapterRecord= new TitleGasAdapter(ListTitleGas.this,
                dbHelperGas.getRecTitle(depart,orderBy));
        recordsTitle.setAdapter(adapterRecord);
        adapterRecord.notifyDataSetChanged();
    }

    private void searchRecords(String query, String depart) {
        TitleGasAdapter adapterRecord= new TitleGasAdapter(ListTitleGas.this,
                dbHelperGas.searchRecords(query,depart));
        recordsTitle.setAdapter(adapterRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecords(currentOrderByStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query,depart);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText, depart);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_sort){
            sortOptionDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortOptionDialog() {
        String[]options={"По возрастанию","По убыванию","Последние","Старые"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Сортировать")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            loadRecords(orderByTitleASC);
                        }
                        if (which==1){
                            loadRecords(orderByTitleDESC);
                        }
                        if (which==2){
                            loadRecords(orderByNew);
                        }
                        if (which==3){
                            loadRecords(orderByOld);
                        }

                    }
                })
                .create().show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListTitleGas.this, ShowDepartGas.class));
    }
}