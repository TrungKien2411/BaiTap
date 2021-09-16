/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frmGUI;

import QLTV.DbConnect;
import QLTV.Operation;
import QLTV.Feature;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class frmQLS extends javax.swing.JFrame {

    //Khai báo một đối tượng của class DbConnect để gọi các hàm thực hiện
    Feature con;
    DbConnect helper;
    private int flag = 0;
    String strFind ;
    String defaultSQLStatement;
    frmTK statComponent;
    /**
     * Creates new form frmQLS
     */
    public frmQLS() {
        initComponents();
        // khởi tạo đối tượng con
        con = new Feature();
        helper = new DbConnect();
        statComponent = new frmTK();
        statComponent.setVisible(false);
        defaultSQLStatement = "SELECT * FROM SACH";
        showData(helper.GET(defaultSQLStatement)); 
    } 
    
    // viet ham xoa Text
    public void ClearText(){
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtTheLoai.setText("");
        txtTacGia.setText("");
        cbTinhTrang.setSelectedIndex(0);
        txtSoLuong.setText("");
    }

    //lấy dữ liệu vào table
    public void showData(ResultSet rs){
        
        String[] columnNames = {"Mã Sách","Tên Sách","Thể Loại","Tác Giả","Tình Trạng", "Số Lượng"};
        // Đối tượng này để chứa dữ liệu đổ từu sql cho vào JTable
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(columnNames);
        
        tblSach.setModel(model);
        
        String MaSach = "";
        String TenSach = "";
        String TheLoai = "";
        String TacGia = "";
        String TinhTrang = "";
        String SoLuong = "";

        try {
            while (rs.next()){
                MaSach = rs.getString("MaSach");
                TenSach = rs.getString("TenSach");
                TheLoai = rs.getString("TheLoai");
                TacGia = rs.getString("TacGia");
                TinhTrang = rs.getString("TinhTrang");
                SoLuong = rs.getString("SoLuong");
                
                model.addRow(new Object[]{MaSach, TenSach, TheLoai, TacGia, TinhTrang, SoLuong});
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmQLS.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    //thực hiện Insert
    public void InsertData() throws SQLException {
        String TinhTrang ="";
        
        TinhTrang = cbTinhTrang.getSelectedItem().toString();
        
        String[]stringsSQL = {txtMaSach.getText().toString(), txtTenSach.getText(),txtTheLoai.getText(),
            txtTacGia.getText(), TinhTrang, txtSoLuong.getText()};
        
        int isInsert = helper.INSERT(Operation.INSERTSQLSach,stringsSQL,5);
        if (isInsert > 0 ){
            JOptionPane.showMessageDialog(this, "Ban da them du lieu thanh cong!");
        }
        else {
            JOptionPane.showMessageDialog(this, "Ban chua them duoc du lieu");
        }
         
        showData(helper.GET(defaultSQLStatement));
        ClearText();
    }

    // thực hiện Update
    public void UpdateData() throws SQLException {
        String TinhTrang ="";
        
        TinhTrang = cbTinhTrang.getSelectedItem().toString();
        
        String[]stringsSQL = {txtTenSach.getText(),txtTheLoai.getText(),
            txtTacGia.getText(), TinhTrang, txtSoLuong.getText(),txtMaSach.getText().toString(),};
        
        int isUpdate = helper.UPDATE(Operation.UPDATESQLSach, stringsSQL, 5);
        if (isUpdate > 0 ){
            JOptionPane.showMessageDialog(this, "Ban da sua du lieu thanh cong!");
        }
        else {
            JOptionPane.showMessageDialog(this, "Ban chua sua duoc du lieu");
        }
        
        showData(helper.GET(defaultSQLStatement));
        ClearText();
    }

    // thực hiện xóa DL
    public void DeleteData() throws SQLException {
        int isDelete = helper.DELETE(Operation.DELETESQLSach,txtMaSach.getText().toString());
        if (isDelete > 0 ){
            JOptionPane.showMessageDialog(this, "Ban da xoa du lieu thanh cong!");
        }
        else {
            JOptionPane.showMessageDialog(this, "Ban chua xoa duoc du lieu");
        }
        
        showData(helper.GET(defaultSQLStatement));
        ClearText();
    }

    public void FilterData(){
        String TinhTrang ="";
        TinhTrang = cbTinhTrang.getSelectedItem().toString();
        
        String[]stringsSQL = {txtMaSach.getText(),txtTenSach.getText(),txtTheLoai.getText(),
            txtTacGia.getText(), TinhTrang, txtSoLuong.getText(),txtTenSach.getText()};
        ResultSet result = con.FilterSach(stringsSQL,strFind);
        System.out.println(result);
        if (result != null){
            showData(result);
            JOptionPane.showMessageDialog(this, "Du lieu da duoc tim thay!");
        }
        else {
            JOptionPane.showMessageDialog(this, "Khong tim thay du lieu can tim!");
        }
        ClearText();
    }

    // lấy thông tin dữ liêu được chọn
    public void getSelectedData(){
        int selectedRow = tblSach.getSelectedRow();
        
        txtMaSach.setText(tblSach.getValueAt(selectedRow, 0).toString());
        txtTenSach.setText(tblSach.getValueAt(selectedRow, 1).toString());
        txtTheLoai.setText(tblSach.getValueAt(selectedRow, 2).toString());
        txtTacGia.setText(tblSach.getValueAt(selectedRow, 3).toString());
        cbTinhTrang.setSelectedItem(tblSach.getValueAt(selectedRow, 4));
        txtSoLuong.setText(tblSach.getValueAt(selectedRow, 5).toString());
    }


    // thực hiện nút First
    public void getSelectFirst(){
        int selectedRow = 0;
        txtMaSach.setText(tblSach.getValueAt(selectedRow, 0).toString());
        txtTenSach.setText(tblSach.getValueAt(selectedRow, 1).toString());
        txtTheLoai.setText(tblSach.getValueAt(selectedRow, 2).toString());
        txtTacGia.setText(tblSach.getValueAt(selectedRow, 3).toString());
        cbTinhTrang.setSelectedItem(tblSach.getValueAt(selectedRow, 4));
        txtSoLuong.setText(tblSach.getValueAt(selectedRow, 5).toString());
        tblSach.setRowSelectionInterval(0, 0);
    }

    // Thực hiện nút Previous
    public void getSelectPrevious(){
        if (tblSach.getSelectedRow()>=1){
            int selectedRow = tblSach.getSelectedRow()-1;
            txtMaSach.setText(tblSach.getValueAt(selectedRow, 0).toString());
            txtTenSach.setText(tblSach.getValueAt(selectedRow, 1).toString());
            txtTheLoai.setText(tblSach.getValueAt(selectedRow, 2).toString());
            txtTacGia.setText(tblSach.getValueAt(selectedRow, 3).toString());
            cbTinhTrang.setSelectedItem(tblSach.getValueAt(selectedRow, 4));
            txtSoLuong.setText(tblSach.getValueAt(selectedRow, 5).toString());
            tblSach.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    //thực hiện nút next
    public void getSelectNext(){
        if (tblSach.getSelectedRow()< tblSach.getRowCount()-1){
            int selectedRow = tblSach.getSelectedRow()+1;
            txtMaSach.setText(tblSach.getValueAt(selectedRow, 0).toString());
            txtTenSach.setText(tblSach.getValueAt(selectedRow, 1).toString());
            txtTheLoai.setText(tblSach.getValueAt(selectedRow, 2).toString());
            txtTacGia.setText(tblSach.getValueAt(selectedRow, 3).toString());
            cbTinhTrang.setSelectedItem(tblSach.getValueAt(selectedRow, 4));
            txtSoLuong.setText(tblSach.getValueAt(selectedRow, 5).toString());
            tblSach.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    //thực hiện nút Last
    public void getSelectLast(){
        int selectedRow = tblSach.getRowCount()-1;
        txtMaSach.setText(tblSach.getValueAt(selectedRow, 0).toString());
        txtTenSach.setText(tblSach.getValueAt(selectedRow, 1).toString());
        txtTheLoai.setText(tblSach.getValueAt(selectedRow, 2).toString());
        txtTacGia.setText(tblSach.getValueAt(selectedRow, 3).toString());
        cbTinhTrang.setSelectedItem(tblSach.getValueAt(selectedRow, 4));
        txtSoLuong.setText(tblSach.getValueAt(selectedRow, 5).toString());
        tblSach.setRowSelectionInterval(selectedRow, selectedRow);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtTheLoai = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();
        txtTim = new javax.swing.JTextField();
        cbTinhTrang = new javax.swing.JComboBox<>();
        btnCancel = new javax.swing.JButton();
        btnTK = new javax.swing.JButton();
        btnRestore = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setText("Mã Sách:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Tên Sách:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Thể Loại:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Tác Giả:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Tình Trạng:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Số Lượng:");

        txtMaSach.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N

        txtTenSach.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        txtTheLoai.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        txtTacGia.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        txtSoLuong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("Tìm Kiếm");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Nhập tên sách");

        btnFirst.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnFirst.setText("First");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrevious.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnPrevious.setText("Previous");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnLast.setText("Last");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnInsert.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        tblSach.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1143, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel9.setText("Quản lý sách");

        btnTim.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnTim.setText("Search");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        txtTim.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        txtTim.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimCaretUpdate(evt);
            }
        });

        cbTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn", "Hết" }));

        btnCancel.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnTK.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnTK.setText("Statistical");
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        btnRestore.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        btnRestore.setText("Restore");
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(9, 9, 9)))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSach)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                            .addComponent(txtTacGia, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                            .addComponent(txtTheLoai)
                            .addComponent(txtTenSach))
                        .addGap(18, 18, 18)
                        .addComponent(btnRestore))
                    .addComponent(cbTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancel)
                            .addComponent(btnTim))
                        .addGap(30, 30, 30))))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(469, 469, 469)
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(btnTim)
                            .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirst)
                            .addComponent(btnPrevious)
                            .addComponent(btnNext)
                            .addComponent(btnLast))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnSave))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack)
                            .addComponent(btnClose)
                            .addComponent(btnTK)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRestore))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        ClearText();
        btnSave.setEnabled(true);
        flag=3;
        btnInsert.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        getSelectLast();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        getSelectFirst();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        getSelectPrevious();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        getSelectNext();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        ClearText();
        btnSave.setEnabled(true);
        flag=1;
        btnInsert.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        ClearText();
        btnSave.setEnabled(true);
        flag=2;
        btnInsert.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        try {
        if(flag==1){
            InsertData();
            btnInsert.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
        else if(flag==2){
            UpdateData();
            btnInsert.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
        else{
            DeleteData();
            btnInsert.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
        ClearText();
        btnSave.setEnabled(false);
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new frmTV().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        // TODO add your handling code here:
        getSelectedData();
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        strFind = txtTim.getText();
        if(strFind.length()>0){
            FilterData();
        ClearText();
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void txtTimCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimCaretUpdate

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        showData(helper.GET(defaultSQLStatement));
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        statComponent.setVisible(true);
        statComponent.fillStatForm();
    }//GEN-LAST:event_btnTKActionPerformed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        // TODO add your handling code here:
        ClearText();
    }//GEN-LAST:event_btnRestoreActionPerformed

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
            java.util.logging.Logger.getLogger(frmQLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmQLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmQLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmQLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold> 

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmQLS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnRestore;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTK;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbTinhTrang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTheLoai;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables

}
