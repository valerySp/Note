package com.sparnyuk.notes.Adapter.Struct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sparnyuk.notes.DBHelper.DBHelperStr;

import com.sparnyuk.notes.ModelRecord;
import com.sparnyuk.notes.Notification;
import com.sparnyuk.notes.R;
import com.sparnyuk.notes.TITLE.Struct.ListTitleStr;
import com.sparnyuk.notes.TITLE.Struct.ShowTitleStr;


import java.util.ArrayList;

public class TitleStrAdapter extends RecyclerView.Adapter<TitleStrAdapter.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordTitleList;
    private DBHelperStr db;

    public TitleStrAdapter(Context context, ArrayList<ModelRecord> recordList) {
        this.context = context;
        this.recordTitleList = recordList;
        db =new DBHelperStr(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tilte_record,parent,false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ModelRecord model=recordTitleList.get(position);
        String id=model.getId();
        String departa=model.getDepart();
        String title= model.getTitle();
        String galery= model.getGalery();

        //holder.desc.setText(desc);
        holder.title.setText(title);
        /*if (image==null){
            holder.provileIv.setImageURI(Uri.parse("@null"));
        }
        else {

        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Здесь должен быть переход с участка на активити заметок
                Intent intent=new Intent(context, ShowTitleStr.class);
                intent.putExtra("departTitle",departa);
                intent.putExtra("id",id);
                intent.putExtra("galery",galery);
                context.startActivity(intent);
            }
        });

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]option=new String[]{"Удалить", "Напоминание"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);

                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            db.delTitle(departa,title);
                            ((ListTitleStr)context).onResume();
                        }
                        if (which==1){
                            /*Intent intent=new Intent(context, Notification.class);
                            intent.putExtra("depart",departa);
                            intent.putExtra("title",title);
                            intent.putExtra("id",id);//добавлено для заметок
                            context.startActivity(intent);*/
                            Toast.makeText(context, "На стадии разработки\n Буду признателен в помощи", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordTitleList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder {

        TextView title;
        ImageButton moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.nameTitle);
            //desc=itemView.findViewById(R.id.edDesc);
            moreBtn=itemView.findViewById(R.id.moreBtn);
        }
    }

}
