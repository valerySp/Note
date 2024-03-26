package com.sparnyuk.notes.DEPART.ETK;

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
import com.sparnyuk.notes.DBHelper.DBHelperETK;

import com.sparnyuk.notes.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateETK extends AppCompatActivity {
    CircleImageView profileIv;
    EditText nameDepart;
    FloatingActionButton saveBtn;
    private static final int IMAGE_PICK_GALLERY_CODE=103;
    private Uri imageUri;
    private String depart,updateID;
    private DBHelperETK db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_add);

        db=new DBHelperETK(this);

        profileIv=findViewById(R.id.profileIv);
        nameDepart=findViewById(R.id.nameDepart);
        saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setImageResource(R.drawable.update_btn);

        Intent intent=getIntent();
        updateID=intent.getStringExtra("updateID");
        nameDepart.setText(intent.getStringExtra("depart"));

        if (intent.getStringExtra("image").equals("null")){
            profileIv.setImageResource(R.drawable.ic_menu_camera);
        }
        else {
            profileIv.setImageURI(Uri.parse(intent.getStringExtra("image")));
        }

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                onBackPressed();
            }
        });
    }

    @SuppressLint("Range")
    private void update() {
        depart=nameDepart.getText().toString();
        //db.getUpDateDepart(""+updateID,""+depart,""+imageUri);

        String selectQuery="SELECT*FROM "+ Constant.TABLE_NAME_ETK+
                " WHERE "+Constant.KEY_ID+" =\'"+updateID+"\'";

        SQLiteDatabase base=db.getWritableDatabase();
        Cursor cursor=base.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                String departQuery=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT));
                if (!departQuery.equals(depart)){
                    if (depart.equals("")){
                        Toast.makeText(this, "Имя участка не может быть пустым", Toast.LENGTH_SHORT).show();
                        db.getUpDateDepartETK(""+departQuery,""+departQuery,""+imageUri);
                    } else
                    if (!checkTitle(nameDepart.getText().toString())){
                        db.getUpDateDepartETK(""+departQuery,""+departQuery,""+imageUri);
                    }
                    else
                        db.getUpDateDepartETK(""+departQuery,""+nameDepart.getText().toString(),""+imageUri);
                }
            } while (cursor.moveToNext());
        }
        base.close();

    }

    @SuppressLint("Range")
    public boolean checkTitle (String departQuery) {

        String selectQuery = "SELECT*FROM " + Constant.TABLE_NAME_ETK + " WHERE " + Constant.KEY_DEPARTMENT
                + "=\'" + departQuery + "\'";
        SQLiteDatabase base = db.getWritableDatabase();
        Cursor cursor = base.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String idCheck=""+ cursor.getString(cursor.getColumnIndex(Constant.KEY_ID));
            if (!idCheck.equals(updateID)){
                Toast.makeText(this, "Такой участок существует", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
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
}

