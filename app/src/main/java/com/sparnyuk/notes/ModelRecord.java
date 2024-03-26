package com.sparnyuk.notes;

import java.io.Serializable;

public class ModelRecord {

    String  id,depart,image,title,desc,galery,time,timeNoti;


    public ModelRecord(String id, String depart, String image, String title, String desc,String galery,String time) {
        this.id = id;
        this.depart = depart;
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.galery=galery;
        this.time=time;
    }

    //Данная модель для уведомлений
    public ModelRecord(String id, String depart, String title, String timeNoti) {
        this.id = id;
        this.depart = depart;
        this.title = title;
        this.timeNoti=timeNoti;
    }

    //Model for getPicture - i can set time in gridview
    public ModelRecord(String galery,String time) {
        this.galery=galery;
        this.time=time;
    }

    public String getTimeNoti() {
        return timeNoti;
    }

    public void setTimeNoti(String timeNoti) {
        this.timeNoti = timeNoti;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGalery() {
        return galery;
    }

    public void setGalery(String galery) {
        this.galery = galery;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
