package com.example.nutribar;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Stats implements Parcelable{
    private double protein;
    private double feritin;
    private double insulin;
    private double magnesium;
    private double vitaminD;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int day;
    private int month;
    private int year;

    public double getProtein() {
        return protein;
    }

    public double getFeritin() {
        return feritin;
    }

//    public double getGlucose() {
//        return glucose;
//    }

    public double getInsulin() {
        return insulin;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public double getVitaminD() {
        return vitaminD;
    }

    public Stats(Parcel in) {
        this.protein = in.readDouble();
        this.feritin = in.readDouble();
        this.insulin = in.readDouble();
        this.magnesium = in.readDouble();
        this.vitaminD = in.readDouble();
        this.day = in.readInt();
        this.month = in.readInt();
        this.year = in.readInt();
    }

    public void writeToParcel(@NonNull Parcel parcel, int i){
        parcel.writeDouble(protein);
        parcel.writeDouble(feritin);
        parcel.writeDouble(insulin);
        parcel.writeDouble(magnesium);
        parcel.writeDouble(vitaminD);
        parcel.writeInt(day);
        parcel.writeInt(month);
        parcel.writeInt(year);
    }

    public Stats() {
    }

    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Stats> CREATOR = new Parcelable.Creator<Stats>() {
        public Stats createFromParcel(Parcel in) {
            return new Stats(in);
        }

        public Stats[] newArray(int size) {
            return new Stats[size];
        }
    };
}
