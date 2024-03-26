package com.sparnyuk.notes.TITLE.Struct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperStr;

import com.sparnyuk.notes.R;

import org.jetbrains.annotations.Nullable;

public class AddTitleStr extends AppCompatActivity {

    EditText title,desc;
    ImageView picture;

    FloatingActionButton saveTitleBtn, savePict;
    String nameTitle,nameDesc, depart; //delete private
    DBHelperStr db;//delete private
    private static final int IMAGE_PICK_GALLERY_CODE_TITLE=103;
    Uri imageUriTitle;//delete private

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_add);

        title=findViewById(R.id.edTitle);
        desc=findViewById(R.id.edDesc);
        saveTitleBtn=findViewById(R.id.saveTitleBtn);
        View cardView = findViewById(R.id.imageCardView);
        savePict=findViewById(R.id.savePict);
        picture=findViewById(R.id.imNewImageAdd);

        Intent intent=getIntent();
        depart=intent.getStringExtra("departAdd");

        db=new DBHelperStr(this);

        saveTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    input();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        savePict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pickFromGallery();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cardView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void pickFromGallery() {
        Intent galleryIntent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE_TITLE);
    }

    @SuppressLint("Range")
    private void input() throws InterruptedException {
        nameTitle = title.getText().toString();
        nameDesc = desc.getText().toString();
        String selectQuery = "SELECT*FROM " + Constant.TABLE_NAME_STR + " WHERE " + Constant.KEY_TITLE
                + "=\'" + nameTitle + "\'"+" and "+Constant.KEY_DEPARTMENT
                + "=\'" + depart + "\'";
        SQLiteDatabase base = db.getWritableDatabase();
        Cursor cursor = base.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            nameTitle.equals("" + cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)));
            Toast.makeText(this, "Такая заметка есть!", Toast.LENGTH_SHORT).show();
            title.setText("");
        }
        base.close();
        nameTitle = title.getText().toString();
        nameDesc = desc.getText().toString();
            if (nameTitle.equals("") || nameDesc.equals("")) {
                Toast.makeText(this, "Введите заметку!", Toast.LENGTH_SHORT).show();
            } else {
                nameTitle = title.getText().toString();
                nameDesc = desc.getText().toString();
                String timeadd=""+System.currentTimeMillis();

                if (imageUriTitle==null){
                    long id = db.insertTitle("" + depart, "" + null, "" + nameTitle, "" + nameDesc, "" + timeadd, "" + null);
                    onBackPressed();
                }
                else{
                    long id = db.insertTitle("" + depart, "" + null, "" + nameTitle, "" + nameDesc, "" + timeadd, "" + null);
                    db.insertTitle("" + depart, "" + null, "" + nameTitle, "" + null, "" + timeadd, "" + imageUriTitle);
                    Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK&&requestCode==IMAGE_PICK_GALLERY_CODE_TITLE&&data!=null){
            imageUriTitle=data.getData();
            picture.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}