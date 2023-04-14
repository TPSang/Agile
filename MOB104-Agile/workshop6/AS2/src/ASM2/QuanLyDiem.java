/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ASM2;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class QuanLyDiem extends javax.swing.JFrame {
    //1. Khai báo biến toàn cục

    ArrayList<Grade> list = new ArrayList<>(); // danh sách sinh viên list
    int current = 0; //vị trí hiện hành
    String url = "jdbc:sqlserver://localhost:1433;databasename=DSV";
    String Username = "sa";
    String Password = "123";

    /**
     * Creates new form 
     */
    public QuanLyDiem() {
        initComponents();
        setLocationRelativeTo(null);
        load_data();
    }
    
        public void showDetails()
    {
        //1. Lấy ra sinh viên hiện hành dựa vào current
        Grade stu = list.get(current);
        //2. Gán thông tin của sinh viên lên các controls
        txtMASV.setText(stu.getMasv());
        txtTiengAnh.setText(String.valueOf(stu.getTienganh()));
        txtTin.setText(String.valueOf(stu.getTinhoc()));
        txtGD.setText(String.valueOf(stu.getGDTC()));
                
    }

    public boolean vali_date() {
        if (txtMASV.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Mã số sinh viên không được để trống");
            txtMASV.setBackground(Color.red);
            txtMASV.requestFocus();
            return false;
        }
        if (txtTiengAnh.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Điểm tiếng anh không được để trống");
            txtTiengAnh.setBackground(Color.red);
            txtTiengAnh.requestFocus();
            return false;
        }
        try {
            double diemTiengAnh = Double.parseDouble(txtTiengAnh.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm tiếng anh phải là số");
            txtTiengAnh.setBackground(Color.red);
            txtTiengAnh.setText("");
            txtTiengAnh.requestFocus();
            return false;   
        }
        if (txtTin.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Điểm tin không được để trống");
            txtTin.setBackground(Color.red);
            txtTin.requestFocus();
            return false;
        }
        try {
            double diemTin = Double.parseDouble(txtTin.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm tin phải là số");
            txtTin.setBackground(Color.red);
            txtTin.setText("");
            txtTin.requestFocus();
            return false;
        }

        if (txtGD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Điểm giáo dục thể chất không được để trống");
            txtGD.setBackground(Color.red);
            txtGD.requestFocus();
            return false;
        }
        try {
            double diemGDTC = Double.parseDouble(txtGD.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm GD thể chất phải là số");
            txtGD.setBackground(Color.red);
            txtGD.setText("");
            txtGD.requestFocus();
            return false;
        }

        return true;
    }

    public void load_data() { 
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cn = DriverManager.getConnection(url, Username, Password);
            PreparedStatement ps = cn.prepareStatement("select students.MaSV , students.HoTen , TiengAnh , TinHoc , GDTC , (TiengAnh + TinHoc + GDTC )/ 3.0 as 'Diem TB'"
                    + " from students , Grade "
                    + " where students.MaSV = Grade.MaSV");
            ResultSet rs = ps.executeQuery();
            list.clear(); // xóa list đi để lấy dữ liệu từ bảng bỏ vào list
            while (rs.next()) {
                // tạo đối tượng sinh viên stu
                //Student stu = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6),rs.getString(7));
                Grade gr = new Grade();
                // gán dữ liệu vào cho các thuộc tính
                gr.setMasv(rs.getString(1));
                gr.setHoten(rs.getString(2));
                gr.setTienganh(rs.getInt(3));
                gr.setTinhoc(rs.getInt(4));
                gr.setGDTC(rs.getInt(5));
                list.add(gr);

            }
            ps.close();
            cn.close();

            DefaultTableModel model = (DefaultTableModel) tblBangDiem.getModel();
            model.setRowCount(0);
            //2. duyệt qua danh sách sinh viên list, lấy từng sinh viên thêm vào table
            for (Grade gr : list) {
                Object[] row = new Object[]{gr.getMasv(), gr.getHoten(), gr.getTienganh(), gr.getTinhoc(), gr.getGDTC(), gr.getDiemTB()};
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
       public void search() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cn = DriverManager.getConnection(url, Username, Password);
            PreparedStatement ps = cn.prepareStatement("select students.MaSV , students.HoTen , TiengAnh , TinHoc , GDTC , (TiengAnh + TinHoc + GDTC )/ 3.0 as 'Diem TB'"
                    + " from students , Grade "
                    + " where students.MaSV = Grade.MaSV AND students.MaSV  =  ? ");
            
            ps.setString(1, txtSearch.getText());
            
            ResultSet rs = ps.executeQuery();

            
            list.clear(); // xóa list đi để lấy dữ liệu từ bảng bỏ vào list
            while (rs.next()) {
                // tạo đối tượng sinh viên stu
                //Student stu = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6),rs.getString(7));
                Grade gr = new Grade();
                // gán dữ liệu vào cho các thuộc tính
                gr.setMasv(rs.getString(1));
                gr.setHoten(rs.getString(2));
                gr.setTienganh(rs.getInt(3));
                gr.setTinhoc(rs.getInt(4));
                gr.setGDTC(rs.getInt(5));

                list.add(gr);

            }
            ps.close();
            cn.close();

            DefaultTableModel model = (DefaultTableModel) tblBangDiem.getModel();
            model.setRowCount(0);
            //2. duyệt qua danh sách sinh viên list, lấy từng sinh viên thêm vào table
            for (Grade gr : list) {
                Object[] row = new Object[]{gr.getMasv(), gr.getHoten(), gr.getTienganh(), gr.getTinhoc(), gr.getGDTC(), gr.getDiemTB()};
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public void insert() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cn = DriverManager.getConnection(url, Username, Password);
            PreparedStatement ps = cn.prepareStatement("insert into Grade values(?,?,?,?) ");
            //1. Truyền giá trị từ các control vào đối số 
            ps.setString(1, txtMASV.getText());
            ps.setInt(2,Integer.parseInt(txtTiengAnh.getText()));
            ps.setInt(3,Integer.parseInt(txtTin.getText()));
            ps.setInt(4,Integer.parseInt(txtGD.getText()));
           
            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Thêm thành công.");
            }   
            ps.close();
            cn.close();
            
//            this.load_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm Không Thành Công.");
            e.printStackTrace();
        }
        
        load_data();
    }
      
      public void delete(){
          try {
              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              Connection cn = DriverManager.getConnection(url, Username, Password);
              PreparedStatement ps = cn.prepareStatement("insert into Grade values(?,?,?,?) ");
              
              ps.setString(1, txtMASV.getText());
              int kq = ps.executeUpdate();
              if(kq==1){
                  JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                  ps.close();
                  cn.close();
              }
          } catch (Exception e) {
              e.printStackTrace();
          }      
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMASV = new javax.swing.JTextField();
        txtTiengAnh = new javax.swing.JTextField();
        txtTin = new javax.swing.JTextField();
        txtGD = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangDiem = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản Lý Điểm Sinh Viên");

        jLabel2.setText("Tìm kiếm");

        jLabel3.setText("Mã SV:");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/search-icon.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel4.setText("Họ tên SV:");

        jLabel5.setText("      Mã SV:");

        jLabel6.setText("Tiếng anh:");

        jLabel7.setText("    Tin học:");

        jLabel8.setText("Giáo dục TC:");

        txtMASV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMASVActionPerformed(evt);
            }
        });

        txtGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGDActionPerformed(evt);
            }
        });

        jLabel9.setText("Điểm TB");

        jLabel11.setText("10");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(8, 8, 8))
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMASV)
                    .addComponent(txtTiengAnh)
                    .addComponent(txtTin)
                    .addComponent(txtGD, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMASV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTiengAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtGD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/new.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/save-icon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/update-icon.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/Button-First-icon.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/Button-Rewind-icon.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/Button-Forward-icon.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASM2/Button-Last-icon.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("3 sinh viên có điểm cao nhất:");

        tblBangDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MãSV", "Họ tên", "Tiếng anh", "Tin học", "GDTC", "Điểm TB"
            }
        ));
        tblBangDiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangDiemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBangDiem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnFirst)
                                .addGap(18, 18, 18)
                                .addComponent(btnPrev)
                                .addGap(18, 18, 18)
                                .addComponent(btnNext)
                                .addGap(24, 24, 24)
                                .addComponent(btnLast))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(80, 80, 80))
            .addGroup(layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        txtMASV.setText("");
        txtTiengAnh.setText("");
        txtTin.setText("");
        txtGD.setText("");
        txtSearch.setText("");

    }//GEN-LAST:event_btnNewActionPerformed

    private void txtGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGDActionPerformed

    private void txtMASVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMASVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMASVActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (vali_date()) {
            JOptionPane.showMessageDialog(this, "Du lieu hop le");
            insert();
            load_data();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
         delete();
         load_data();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cn = DriverManager.getConnection(url,Username,Password);
            PreparedStatement ps = cn.prepareStatement("update Grade set TiengAnh=?, TinHoc = ?, GDTC = ?  where masv = ? ");
            //1. Truyền giá trị từ các control vào đối số 
            ps.setString(4, txtMASV.getText());
            ps.setInt(1,Integer.parseInt(txtTiengAnh.getText()));
            ps.setInt(2,Integer.parseInt(txtTin.getText()));
            ps.setInt(3,Integer.parseInt(txtGD.getText()));
            
            // thực thi : kq = 1 thành công kq = 0 thất bại.
            int kq = ps.executeUpdate();
            if (kq == 1)
            {
                JOptionPane.showMessageDialog(this, "Update thành công.");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Update thất bại.");
            }
            //3. Update xong rồi nhớ đóng kết nối.
            ps.close();
            cn.close();
            //4. nhớ gọi lại load_data() để load dữ liệu mới lên jtable
            this.load_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Không Thành Công.");
            e.printStackTrace();
        }   
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        current = 0;
        showDetails();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void tblBangDiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangDiemMouseClicked
        current = tblBangDiem.getSelectedRow();
        showDetails();
    }//GEN-LAST:event_tblBangDiemMouseClicked

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        if(current>0){
            current--;
            showDetails();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if(current<list.size()-1){
            current ++;
            showDetails();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        current = list.size() - 1;
        showDetails();
    }//GEN-LAST:event_btnLastActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyDiem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBangDiem;
    private javax.swing.JTextField txtGD;
    private javax.swing.JTextField txtMASV;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTiengAnh;
    private javax.swing.JTextField txtTin;
    // End of variables declaration//GEN-END:variables
}
