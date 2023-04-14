/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author trand
 */
public class sinhvien implements Serializable{
private String maSV;
    private String hoTen;
    private String email;
    private String soDT;
    private int gioiTinh;
    private String diaChi;
    private String srcHinh;

    public sinhvien() {
    }

    public sinhvien(String maSV, String hoTen, String email, String soDT, int gioiTinh, String diaChi, String srcHinh) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDT = soDT;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.srcHinh = srcHinh;
    }

   
  

}
