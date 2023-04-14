/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASM2;

/**
 *
 * @author admin
 */
public class Grade {
    String masv , hoten  ;
    int tienganh , tinhoc , GDTC;
//    double diemTB;
    

    public Grade() {
    }

    public Grade(String masv, String hoten, int tienganh, int tinhoc, int GDTC) {
        this.masv = masv;
        this.hoten = hoten;
        this.tienganh = tienganh;
        this.tinhoc = tinhoc;
        this.GDTC = GDTC;
       
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

    public int getTienganh() {
        return tienganh;
    }

    public void setTienganh(int tienganh) {
        this.tienganh = tienganh;
    }

    public int getTinhoc() {
        return tinhoc;
    }

    public void setTinhoc(int tinhoc) {
        this.tinhoc = tinhoc;
    }

    public int getGDTC() {
        return GDTC;
    }

    public void setGDTC(int GDTC) {
        this.GDTC = GDTC;
    }
    

    public double getDiemTB() {
        return (this.tinhoc + this.GDTC + this.tienganh)/3.0;
    }

//    public void setDiemTB(double diemTB) {
//        this.diemTB = diemTB;
//    }
    
}
