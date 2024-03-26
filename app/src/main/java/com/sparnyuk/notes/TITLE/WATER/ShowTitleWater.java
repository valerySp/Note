package com.sparnyuk.notes.TITLE.WATER;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperWater;
import com.sparnyuk.notes.R;

public class ShowTitleWater extends AppCompatActivity {

    EditText title,desc;
    private String recordID, depart;
    FloatingActionButton saveTitleUpdate;
    private DBHelperWater db;
    ImageButton openGallery;
    private Uri imageUriTitle;

    @SuppressLint({"SetTextI18n", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_show);

        title=findViewById(R.id.edTitleShow);
        desc=findViewById(R.id.edDescShow);
        saveTitleUpdate=findViewById(R.id.saveTitleUpdate);

        Intent intent=getIntent();
        depart=intent.getStringExtra("departTitle");
        recordID=intent.getStringExtra("id");

        openGallery =findViewById(R.id.showPicture);
        imageUriTitle=Uri.parse(intent.getStringExtra("galery"));

        db=new DBHelperWater(this);

        saveTitleUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeadd=""+System.currentTimeMillis();

                db.getUpDateTitle(""+recordID,""+title.getText().toString(),""+desc.getText().toString(),
                        ""+timeadd);
                onBackPressed();
            }
        });


        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowTitleWater.this, ImageTitleWater.class).putExtra("depart",depart)
                        .putExtra("title",title.getText().toString());
                startActivity(intent);
            }
        });

        String selectQuery="SELECT*FROM "+ Constant.TABLE_NAME_WATER+
                " WHERE "+Constant.KEY_ID+" =\'"+recordID+"\'";

        SQLiteDatabase base=db.getWritableDatabase();
        Cursor cursor=base.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                String nameTitle=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE));
                String nameDesc=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION));
                title.setText(nameTitle);
                desc.setText(nameDesc);
            } while (cursor.moveToNext());

        }
        base.close();
    }

    @Override
    @SuppressLint("Range")
    public void onBackPressed() {
        //Обеспечение обновления только при изменении описания
        String timeadd=""+System.currentTimeMillis();
        String selectQuery="SELECT*FROM "+ Constant.TABLE_NAME_WATER+
                " WHERE "+Constant.KEY_ID+" =\'"+recordID+"\'";

        SQLiteDatabase base=db.getWritableDatabase();
        Cursor cursor=base.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                String nameTitle=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE));
                String nameDesc=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION));
                if (!nameDesc.equals(desc.getText().toString())||!nameTitle.equals(title.getText().toString())){
                    if (title.getText().toString().equals("")){
                        Toast.makeText(this, "Имя заметки не может быть пустым", Toast.LENGTH_SHORT).show();
                        db.getUpDateTitle(""+nameTitle,""+nameTitle,""+desc.getText().toString(),
                                ""+timeadd);
                    } else
                    if (!checkTitle(title.getText().toString())){
                        db.getUpDateTitle(""+nameTitle,""+nameTitle,""+desc.getText().toString(),
                                ""+timeadd);
                    }
                    else
                        db.getUpDateTitle(""+nameTitle,""+title.getText().toString(),""+desc.getText().toString(),
                                ""+timeadd);
                }
            } while (cursor.moveToNext());
        }
        base.close();

        super.onBackPressed();
    }

    @SuppressLint("Range")
    public boolean checkTitle (String nameTitle) {

        String selectQuery = "SELECT*FROM " + Constant.TABLE_NAME_WATER + " WHERE " + Constant.KEY_TITLE
                + "=\'" + nameTitle + "\'";
        SQLiteDatabase base = db.getWritableDatabase();
        Cursor cursor = base.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String idCheck=""+ cursor.getString(cursor.getColumnIndex(Constant.KEY_ID));
            if (!idCheck.equals(recordID)){
                Toast.makeText(this, "Измените название заметки", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}