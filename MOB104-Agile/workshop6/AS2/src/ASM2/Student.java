/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ASM2;




public class Student {
	String masv ;
	String hoten ;
	String email ;
	String soDT ;
	boolean gioitinh ;
	String diachi ;
	String hinh ;

    public Student() {
    }

    public Student(String masv, String hoten, String email, String soDT, boolean gioitinh, String diachi, String hinh) {
        this.masv = masv;
        this.hoten = hoten;
        this.email = email;
        this.soDT = soDT;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.hinh = hinh;
    }

   

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
    
    
        
}
