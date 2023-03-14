package com.example.up;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MaskaHoriz implements Parcelable {
    protected MaskaHoriz(Parcel in) {
        Id = in.readInt();
        Title = in.readString();
        Position = in.readInt();
        Image = in.readString();
    }

    public static final Creator<MaskaHoriz> CREATOR = new Creator<MaskaHoriz>() {
        @Override
        public MaskaHoriz createFromParcel(Parcel in) {
            return new MaskaHoriz(in);
        }

        @Override
        public MaskaHoriz[] newArray(int size) {
            return new MaskaHoriz[size];
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

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    private int Id;
    private String Title;
    private int Position;
    private String Image;


    public MaskaHoriz(int id, String title, int position, String image) {
        Id = id;
        Title = title;
        Position = position;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Title);
        dest.writeInt(Position);
        dest.writeString(Image);
    }
}
