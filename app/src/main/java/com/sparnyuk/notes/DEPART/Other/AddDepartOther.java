package com.sparnyuk.notes.DEPART.Other;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperOther;
import com.sparnyuk.notes.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddDepartOther extends AppCompatActivity {
    CircleImageView profileIv;
    EditText nameDepart;
    FloatingActionButton saveBtn;
    private static final int IMAGE_PICK_GALLERY_CODE=103;
    private Uri imageUri;
    private String depart;
    private DBHelperOther db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_add);

        db=new DBHelperOther(this);

        profileIv=findViewById(R.id.profileIv);
        nameDepart=findViewById(R.id.nameDepart);
        saveBtn=findViewById(R.id.saveBtn);

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input();
                onBackPressed();
            }
        });
    }

    @SuppressLint("Range")
    private void input() {
        /*depart=nameDepart.getText().toString();
        long id= db.insertDepart(""+depart,""+imageUri);*/
        depart = nameDepart.getText().toString();
        String selectQuery = "SELECT*FROM " + Constant.TABLE_NAME_OTHER + " WHERE " + Constant.KEY_DEPARTMENT
                + " =\'" + depart+"\'";
        SQLiteDatabase base = db.getWritableDatabase();
        Cursor cursor = base.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            nameDepart.equals("" + cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)));
            Toast.makeText(this, "Такое подразделение существует", Toast.LENGTH_SHORT).show();
            nameDepart.setText("");
        }
        base.close();
        depart = nameDepart.getText().toString();
        if (depart.equals("")) {
            Toast.makeText(this, "Введите название подразделения!", Toast.LENGTH_SHORT).show();
        } else {
            depart = nameDepart.getText().toString();
            db.insertDepart(""+depart,""+imageUri);
            onBackPressed();
        }
    }

    private void pickFromGallery() {
        Intent galleryIntent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK&&requestCode==IMAGE_PICK_GALLERY_CODE&&data!=null){
            imageUri=data.getData();
            profileIv.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddDepartOther.this, ShowDepartOther.class));
    }
}
