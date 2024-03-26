package com.sparnyuk.notes.Adapter.Builder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparnyuk.notes.DBHelper.DBHelperBuilder;
import com.sparnyuk.notes.DBHelper.DBHelperWater;
import com.sparnyuk.notes.ModelRecord;
import com.sparnyuk.notes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImageBuilderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ModelRecord> recordList;
    DBHelperBuilder db1; //delete private

    public ImageBuilderAdapter(Context c, ArrayList<ModelRecord> recordList) {
        this.context = c;
        this.recordList = recordList;
        db1 = new DBHelperBuilder(context);
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //String id=recordList.get(position).getId();
        //Long.parseLong(id);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;

        if (convertView == null) {
            grid = new View(context);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.picture_record, parent, false);

        } else {
            grid = (View) convertView;
        }

        ImageView imageView = grid.findViewById(R.id.namePicture);

        ModelRecord model=recordList.get(position);
        String id=model.getId();
        String departa= model.getDepart();
        String image= model.getGalery();

        TextView textView=grid.findViewById(R.id.time_pict);
        Date date=new Date(Long.parseLong(model.getTime()));
        SimpleDateFormat format=new SimpleDateFormat("dd-MMM-YY:HH-mm");
        String str=format.format(date);
        textView.setText(str);

        if(imageView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        imageView.setImageURI(Uri.parse(image));

        //распределение фото равномерно для всех дисплеев
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imageView.setLayoutParams(new GridView.LayoutParams((width/3-20),320));
        //imageView.getLayoutParams().height = width / 3;
        //imageView.getLayoutParams().width =height  / 3;
        return grid;
    }
}
