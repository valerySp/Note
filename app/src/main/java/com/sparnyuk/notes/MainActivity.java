package com.sparnyuk.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.sparnyuk.notes.DEPART.Builder.ShowDepartBuilder;
import com.sparnyuk.notes.DEPART.Climate.ShowDepartClimate;
import com.sparnyuk.notes.DEPART.ETK.ShowDepartETK;
import com.sparnyuk.notes.DEPART.GAS.ShowDepartGas;
import com.sparnyuk.notes.DEPART.Otdel.ShowDepartOtdel;
import com.sparnyuk.notes.DEPART.Other.ShowDepartOther;
import com.sparnyuk.notes.DEPART.Struct.ShowDepartStr;
import com.sparnyuk.notes.DEPART.Water.ShowDepartWater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startService(new Intent(this,MyServiceNoti.class)); - временно останавливаем!! (дублирование в Notification);

        Toolbar toolbar=findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_gas){
            Intent intent = new Intent(MainActivity.this, ShowDepartGas.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_water){
            Intent intent = new Intent(MainActivity.this, ShowDepartWater.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_etk){
            Intent intent = new Intent(MainActivity.this, ShowDepartETK.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_climate){
            Intent intent = new Intent(MainActivity.this, ShowDepartClimate.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_dev){
            Intent intent = new Intent(MainActivity.this, ShowDepartBuilder.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_str){
            Intent intent = new Intent(MainActivity.this, ShowDepartStr.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_otd){
            Intent intent = new Intent(MainActivity.this, ShowDepartOtdel.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.nav_other){
            Intent intent = new Intent(MainActivity.this, ShowDepartOther.class);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
    /*
    private EditText edTitle, edDisc;
    private View listView;
    private ActivityMainBinding binding;
    private static final int STORAGE_REQUEST_CODE_EXPORT=1;
    private static final int STORAGE_REQUEST_CODE_IMPORT=2;
    //private String[] storagePermission;
    DBHelperNoti notiBD;
    private PendingIntent pendingIntent;
    //DBHelperGAS dbHelperGAS;
    */





    /*
    @Override
    protected void onDestroy() {
        startActivity(getIntent());
        super.onDestroy();
    }*/
    /*
    public void setAlarm(){
        notiBD.deleteNoti();
        if(notiBD.getRecNoti()!=0)
        {
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP,notiBD.getRecNoti(), pendingIntent);
            //Toast.makeText(this, "Будильник установлен на " + (notiBD.getRecNoti()), Toast.LENGTH_SHORT).show();
        }
    }*/




/* НУЖНО СОЗДАВАТЬ ДЛЯ КАЖДОЙ ТАБЛИЦЫ!!!!!!!!!!
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.backup){
            if (checkStorage()){
                exportCSV();
            }
            else {
                requestStorageExport();
            }
        }
        if (id==R.id.restore){
            if (checkStorage()){
                importCSV();
            }
            else {
                requestStorageExport();
            }
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
                            //loadRecords(orderByTitleASC);
                        }
                        if (which==1){
                           // loadRecords(orderByTitleDESC);
                        }
                        if (which==2){
                         //   loadRecords(orderByNew);
                        }
                        if (which==3){
                          //  loadRecords(orderByOld);
                        }

                    }
                })
                .create().show();

    }

    private boolean checkStorage(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStorageImport(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE_IMPORT);
    }
    private void requestStorageExport(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE_EXPORT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case STORAGE_REQUEST_CODE_EXPORT:{
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    exportCSV();
                } else {
                    Toast.makeText(this, "Huita", Toast.LENGTH_SHORT).show();
                }
            }break;
            case STORAGE_REQUEST_CODE_IMPORT:{
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    importCSV();
                } else {
                    Toast.makeText(this, "Huita", Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }

    private void exportCSV() {
        File folder=new File(Environment.getExternalStorageDirectory()+"/"+"SQLiteBackup");
        boolean isFolderCreated=false;
        if (!folder.exists()){
            isFolderCreated=folder.mkdir();
        }
        String csvFileName="SQLite_Backup.csv";
        String filePathAndName=folder.toString()+"/"+csvFileName;

        ArrayList<ModelRecord> records=new ArrayList<>();
        records.clear();

        records=dbHelper.getBackUpRecords();
        try{
            FileWriter fw=new FileWriter(filePathAndName);
            for (int i=0; i<records.size();i++){
                fw.append(""+records.get(i).getId());
                fw.append(",");
                fw.append(""+records.get(i).getDepart());
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
    private void importCSV() {
    }*/