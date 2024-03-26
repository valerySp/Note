package com.sparnyuk.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class FullScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);
        Intent intent = getIntent();

        // Selected image id
        String position = intent.getExtras().getString("image_id");
        //ImageAdapterWater imageAdapter = new ImageAdapterWater(this,);

        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Glide.with(this).load(position).into(imageView);
        //imageView.setImageURI(Uri.parse(position));
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }
}