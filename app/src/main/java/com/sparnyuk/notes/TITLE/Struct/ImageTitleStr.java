package com.sparnyuk.notes.TITLE.Struct;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparnyuk.notes.Adapter.Struct.ImageStrAdapter;

import com.sparnyuk.notes.DBHelper.DBHelperStr;

import com.sparnyuk.notes.ModelRecord;
import com.sparnyuk.notes.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTitleStr extends AppCompatActivity {

    private FloatingActionButton addRecordBtn;
    private GridView gridView;
    private DBHelperStr db;
    String depart,title;
    ActionBar actionBar;
    private static final int IMAGE_PICK_GALLERY_CODE_TITLE=103;
    private Uri imageUriTitle;
    private BitmapDrawable bitmapDrawable;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_grid);
        addRecordBtn=findViewById(R.id.addPicture);
        gridView =findViewById(R.id.recordsPictureGrid);
        db =new DBHelperStr(this);
        Intent intent=getIntent();
        depart=intent.getStringExtra("depart");
        title=intent.getStringExtra("title");

        actionBar=getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayShowHomeEnabled(true);

        loadRecords(depart,title);

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowDialogBox(position);
            }
        });
    }

    public void pickImage(){
        Intent galleryIntent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE_TITLE);
        loadRecords(depart,title);
    }

    public void ShowDialogBox(int pos){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image);
        ModelRecord modelImage= (ModelRecord) gridView.getItemAtPosition(pos);

        ImageView image=dialog.findViewById(R.id.full_image_view);

        Button btnSave= dialog.findViewById(R.id.btnSavePicture);
        Button btnDelete= dialog.findViewById(R.id.btnDeletePicture);
        Glide.with(this).load(modelImage.getGalery()).into(image);

        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(image);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage(modelImage.getGalery(),title);
                (ImageTitleStr.this).onResume();
                dialog.dismiss();
            }
        });
    }

    private void loadRecords(String depart,String title) {
        ImageStrAdapter adapterRecord=new ImageStrAdapter(ImageTitleStr.this,
                db.getPicture(depart,title));
        gridView.setAdapter(adapterRecord);
        adapterRecord.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecords(depart,title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==IMAGE_PICK_GALLERY_CODE_TITLE&&data!=null){
            imageUriTitle=data.getData();
            String timeadd=""+System.currentTimeMillis();
            long id= db.insertTitle(""+depart,""+null,""+title,""+null,""+timeadd,""+imageUriTitle);
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void saveImage(ImageView image){
        bitmapDrawable = (BitmapDrawable) image.getDrawable();
        bitmap = bitmapDrawable.getBitmap();

        FileOutputStream fos = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File Directory = new File(sdCard.getAbsolutePath() + "/Download");
        Directory.mkdir();

        String filename = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(Directory, filename);
        Toast.makeText(this, "Загружено", Toast.LENGTH_SHORT).show();

        try {
            fos = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent1.setData(Uri.fromFile(outFile));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteImage(String gallery,String titleName){
        String timeadd=""+System.currentTimeMillis();
        //titleName=title;
        db.deleteGallery(gallery,titleName);
    }
}
