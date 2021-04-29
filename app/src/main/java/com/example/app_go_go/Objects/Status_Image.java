package com.example.app_go_go.Objects;

public class Status_Image {
    public String id_img, img_bitmap, id_stt;

    public Status_Image() {
    }

    @Override
    public String toString() {
        return "Status_Image{" +
                "id_img='" + id_img + '\'' +
                ", img_bitmap='" + img_bitmap + '\'' +
                ", id_stt='" + id_stt + '\'' +
                '}';
    }

    public Status_Image(String id_img, String img_bitmap, String id_stt) {
        this.id_img = id_img;
        this.img_bitmap = img_bitmap;
        this.id_stt = id_stt;
    }

    public String getId_img() {
        return id_img;
    }

    public void setId_img(String id_img) {
        this.id_img = id_img;
    }

    public String getImg_bitmap() {
        return img_bitmap;
    }

    public void setImg_bitmap(String img_bitmap) {
        this.img_bitmap = img_bitmap;
    }

    public String getId_stt() {
        return id_stt;
    }

    public void setId_stt(String id_stt) {
        this.id_stt = id_stt;
    }
}
