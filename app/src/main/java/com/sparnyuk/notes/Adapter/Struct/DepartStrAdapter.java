package com.sparnyuk.notes.Adapter.Struct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sparnyuk.notes.DBHelper.DBHelperStr;

import com.sparnyuk.notes.DEPART.Struct.ShowDepartStr;
import com.sparnyuk.notes.DEPART.Struct.UpdateStr;

import com.sparnyuk.notes.ModelRecord;
import com.sparnyuk.notes.R;
import com.sparnyuk.notes.TITLE.Struct.ListTitleStr;


import java.util.ArrayList;

public class DepartStrAdapter extends RecyclerView.Adapter<DepartStrAdapter.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordList;
    private DBHelperStr db;

    public DepartStrAdapter(Context context, ArrayList<ModelRecord> recordList) {
        this.context = context;
        this.recordList = recordList;
        db =new DBHelperStr(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_record,parent,false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ModelRecord model=recordList.get(position);
        String id=model.getId();
        String departa= model.getDepart();
        String image= model.getImage();

        holder.depart.setText(departa);


        if (image.equals("null")){
            holder.provileIv.setImageResource(R.drawable.ic_person_black);
        }
        else {
            holder.provileIv.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Здесь должен быть переход с участка на активити заметок
                Intent intent=new Intent(context, ListTitleStr.class);
                intent.putExtra("depart",departa);
                intent.putExtra("id_for_table",id);
                context.startActivity(intent);
            }
        });

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]option=new String[]{"Изменить", "Удалить"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==1){
                            db.delDepart(departa);
                            ((ShowDepartStr)context).onResume();
                        }
                        else if (which==0){
                            Intent intent=new Intent(context, UpdateStr.class);
                            intent.putExtra("updateID",id);
                            intent.putExtra("depart",departa);
                            intent.putExtra("image",image);

                            context.startActivity(intent);
                        }
                    }
                });
                builder.create().show();


            }

        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder {

        ImageView provileIv;
        TextView depart;
        ImageButton moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);
            depart=itemView.findViewById(R.id.nameTv);
            provileIv=itemView.findViewById(R.id.profileIv);
            moreBtn=itemView.findViewById(R.id.moreBtn);
        }
    }

}
