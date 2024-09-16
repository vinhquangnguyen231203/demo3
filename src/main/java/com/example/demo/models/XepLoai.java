package com.example.demo.models;

public enum XepLoai {
    GIOI("Giỏi"),KHA("Khá"), TRUNG_BINH("Trung bình"), YEU("Yếu");
    private String xl;
    XepLoai(String xl){
        this.xl =xl;
    }

    public String getXl() {
        return xl;
    }

    public static XepLoai fromXl(String xl){
        for ( XepLoai x : XepLoai.values()){
            if (x.getXl().equals(xl)){
                return x;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + xl);
    }
}
