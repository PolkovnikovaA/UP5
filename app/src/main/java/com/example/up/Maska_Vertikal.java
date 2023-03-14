package com.example.up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Maska_Vertikal implements Parcelable {
    private int Id;
    private String Title;
    private String Image;
    private String Description;

    public Maska_Vertikal(Parcel in) {
        Id = in.readInt();
        Title = in.readString();
        Image = in.readString();
        Description = in.readString();
    }

    public Maska_Vertikal(int id, String title, String image, String description) {
        Id = id;
        Title = title;
        Image = image;
        Description = description;
    }

    public static final Creator<Maska_Vertikal> CREATOR = new Creator<Maska_Vertikal>() {
        @Override
        public Maska_Vertikal createFromParcel(Parcel in) {
            return new Maska_Vertikal(in);
        }

        @Override
        public Maska_Vertikal[] newArray(int size) {
            return new Maska_Vertikal[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Title);
        parcel.writeString(Image);
        parcel.writeString(Description);
    }
}