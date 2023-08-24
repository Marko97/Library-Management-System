/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.Toolkit;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Marko
 */
public class IssueBook extends javax.swing.JFrame {

    /**
     * Creates new form IssueBook
     */
    
    DefaultTableModel modelBooks, modelUsers;
    
    public IssueBook() {
        initComponents();
        setIcon();
        setBookDetailsToTable();
        setUserDetailsToTable();
    }
    
    public void setBookDetailsToTable(){
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from book_details");
            
            while(resultSet.next()){
                String bookId = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("quantity");
                String isbn = resultSet.getString("isbn");
                
                Object[] object = { bookId, bookName, author, quantity};
                modelBooks =(DefaultTableModel) tbl_bookDetails.getModel();
                modelBooks.addRow(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setUserDetailsToTable(){
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_details");
            
            while(resultSet.next()){
                String userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                
                Object[] object = {userId, userName};
                modelUsers =(DefaultTableModel) tbl_userDetails.getModel();
                modelUsers.addRow(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void getBookDetails(){
        int bookId = Integer.parseInt(txt_bookId.getText());
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement("select * from book_details where book_id = ?");
            prepareStatement.setInt(1, bookId);
            ResultSet resultSet = prepareStatement.executeQuery();
            
            if(resultSet.next()){
                lbl_bookId.setText(resultSet.getString("book_id"));
                lbl_bookName.setText(resultSet.getString("book_name"));
                lbl_author.setText(resultSet.getString("author"));
                lbl_quantity.setText(resultSet.getString("quantity"));
            }else{
                lbl_bookError.setText("ID knjige ne postoji.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getUserDetails(){
        int userId = Integer.parseInt(txt_userId.getText());
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement("select * from user_details where user_id = ?");
            prepareStatement.setInt(1, userId);
            ResultSet resultSet = prepareStatement.executeQuery();
            
            if(resultSet.next()){
                lbl_userId.setText(resultSet.getString("user_id"));
                lbl_userName.setText(resultSet.getString("user_name"));
            }else{
                lbl_userError.setText("ID korisnika ne postoji.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean issueBook(){
        
        boolean isIssued = false;
        int bookId = Integer.parseInt(txt_bookId.getText());
        int userId = Integer.parseInt(txt_userId.getText());
        String bookName = lbl_bookName.getText();
        String userName = lbl_userName.getText();
        
        
        java.util.Date uIssueDate = (java.util.Date) date_issueDate.getDate();
        java.util.Date uDueDate = (java.util.Date) date_dueDate.getDate();
        
        java.sql.Date sqlIssueDate = new java.sql.Date(uIssueDate.getTime());
        java.sql.Date sqlDueDate = new java.sql.Date(uDueDate.getTime());
        
        
        
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "insert into issue_book_details(book_id, book_name, user_id, user_name, issue_date, due_date, status) values (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, bookId);
            prepareStatement.setString(2, bookName);
            prepareStatement.setInt(3, userId);
            prepareStatement.setString(4, userName);
            prepareStatement.setDate(5, sqlIssueDate);
            prepareStatement.setDate(6, sqlDueDate);
            prepareStatement.setString(7, "Posuđena");
            
            int rowCount = prepareStatement.executeUpdate();
            if(rowCount > 0){
                isIssued = true;
            }else{
                isIssued = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return isIssued;
        
        
    }
    
    public void updateBookCount(){
        int bookId = Integer.parseInt(txt_bookId.getText());
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "update book_details set quantity = quantity - 1 where book_id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, bookId);
            
            int rowCount = prepareStatement.executeUpdate();
            if(rowCount > 0){
                JOptionPane.showMessageDialog(this, "Broj knjiga ažuriran.");
                int initialCount = Integer.parseInt(lbl_quantity.getText());
                lbl_quantity.setText(Integer.toString(initialCount - 1));
            }else{
                JOptionPane.showMessageDialog(this, "Broj knjiga nije ažuriran.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean isAlreadyIssued(){
        boolean isAlreadyIssued = false;
        int bookId = Integer.parseInt(txt_bookId.getText());
        int userId = Integer.parseInt(txt_userId.getText());
        
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "select * from issue_book_details where book_id = ? and user_id = ? and status = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, bookId);
            prepareStatement.setInt(2, userId);
            prepareStatement.setString(3, "Posuđena");
            
            ResultSet resultSet = prepareStatement.executeQuery();
            if(resultSet.next()){
                isAlreadyIssued = true;
            }else{
                isAlreadyIssued = false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAlreadyIssued;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_userName = new javax.swing.JLabel();
        lbl_userId = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_userDetails = new javax.swing.JTable();
        lbl_userError = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        date_issueDate = new com.toedter.calendar.JDateChooser();
        date_dueDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        issueBookButton = new rojerusan.RSMaterialButtonCircle();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_quantity = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_bookId = new javax.swing.JLabel();
        lbl_bookName = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bookDetails = new javax.swing.JTable();
        lbl_bookError = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_userId = new javax.swing.JTextField();
        txt_bookId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(75, 75, 75));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon("D:\\Marko\\Faks\\5. Godina\\Diplomski rad\\Icons\\users_icon.png")); // NOI18N
        jLabel12.setText("Podatci o korisniku");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("Ime korisnika");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setText("ID korisnika");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        lbl_userName.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_userName.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(lbl_userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 230, 30));

        lbl_userId.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_userId.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(lbl_userId, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 230, 30));

        tbl_userDetails.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tbl_userDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID korisnika", "Ime korisnika"
            }
        ));
        tbl_userDetails.setRowHeight(40);
        tbl_userDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_userDetailsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_userDetails);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 380, 240));

        lbl_userError.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        lbl_userError.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(lbl_userError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 380, 40));

        panelMain.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 420, 810));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Marko\\Faks\\5. Godina\\Diplomski rad\\Icons\\issue_book_icon.png")); // NOI18N
        jLabel2.setText("Posudi knjigu");
        panelMain.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 130, -1, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Datum posudbe");
        panelMain.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 490, -1, -1));
        panelMain.add(date_issueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 490, 250, 30));
        panelMain.add(date_dueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 540, 250, 30));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Rok posudbe");
        panelMain.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 540, -1, -1));

        issueBookButton.setBackground(new java.awt.Color(0, 0, 51));
        issueBookButton.setText("Posudi knjigu");
        issueBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueBookButtonActionPerformed(evt);
            }
        });
        panelMain.add(issueBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 600, 430, 80));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setIcon(new javax.swing.ImageIcon("D:\\Marko\\Faks\\5. Godina\\Diplomski rad\\Icons\\back_icon.png")); // NOI18N
        jLabel18.setText("Povratak");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 90));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon("D:\\Marko\\Faks\\5. Godina\\Diplomski rad\\Icons\\issued_books_icon.png")); // NOI18N
        jLabel19.setText("Podatci o knjizi");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 204, 204));
        jLabel20.setText("Količina");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        lbl_quantity.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_quantity.setForeground(new java.awt.Color(204, 204, 204));
        jPanel5.add(lbl_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 250, 30));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 204, 204));
        jLabel21.setText("Ime knjige");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 204, 204));
        jLabel22.setText("Autor");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 204, 204));
        jLabel23.setText("ID knjige");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        lbl_bookId.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_bookId.setForeground(new java.awt.Color(204, 204, 204));
        jPanel5.add(lbl_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 250, 30));

        lbl_bookName.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_bookName.setForeground(new java.awt.Color(204, 204, 204));
        jPanel5.add(lbl_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 250, 30));

        lbl_author.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbl_author.setForeground(new java.awt.Color(204, 204, 204));
        jPanel5.add(lbl_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 250, 30));

        tbl_bookDetails.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID knjige", "Naziv knjige", "Autor", "Količina"
            }
        ));
        tbl_bookDetails.setRowHeight(40);
        tbl_bookDetails.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbl_bookDetailsFocusLost(evt);
            }
        });
        tbl_bookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bookDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bookDetails);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 400, 230));

        lbl_bookError.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        lbl_bookError.setForeground(new java.awt.Color(204, 204, 204));
        jPanel5.add(lbl_bookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, 400, 40));

        panelMain.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 810));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("ID korisnika");
        panelMain.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 390, -1, -1));

        txt_userId.setBackground(new java.awt.Color(51, 51, 51));
        txt_userId.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_userId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_userIdFocusLost(evt);
            }
        });
        txt_userId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userIdActionPerformed(evt);
            }
        });
        panelMain.add(txt_userId, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 430, 200, 40));

        txt_bookId.setBackground(new java.awt.Color(51, 51, 51));
        txt_bookId.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_bookId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookIdFocusLost(evt);
            }
        });
        txt_bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookIdActionPerformed(evt);
            }
        });
        panelMain.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 340, 200, 40));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("ID knjige");
        panelMain.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 300, -1, -1));

        getContentPane().add(panelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1410, 810));

        setSize(new java.awt.Dimension(1427, 845));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void issueBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueBookButtonActionPerformed
        // TODO add your handling code here:
        
        if(lbl_quantity.getText().equals("0")){
            JOptionPane.showMessageDialog(this, "Knjiga nije dostupna");
        }else{
            if(isAlreadyIssued() == false){
            
            if(issueBook() == true){
            JOptionPane.showMessageDialog(this, "Knjiga je uspješno posuđena");
            updateBookCount();
        }else{
            JOptionPane.showMessageDialog(this, "Knjiga nije posuđena");
        }
        }else{
            JOptionPane.showMessageDialog(this, "Ovaj korisnik je već posudio ovu knjigu.");
        }
        }
        
        
        
        
    }//GEN-LAST:event_issueBookButtonActionPerformed

    private void tbl_bookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookDetailsMouseClicked
        // TODO add your handling code here:
        int rowNumber = tbl_bookDetails.getSelectedRow();
        TableModel model = tbl_bookDetails.getModel();
       
        

        txt_bookId.setText(model.getValueAt(rowNumber, 0).toString());
        //txt_book_isbn.setText(model.getValueAt(rowNumber, 1).toString());
        //txt_bookName.setText(model.getValueAt(rowNumber, 2).toString());
        //txt_authorName.setText(model.getValueAt(rowNumber, 3).toString());
        //txt_quantity.setText(model.getValueAt(rowNumber, 4).toString());
        
        if(!txt_bookId.getText().equals("")){
        getBookDetails();
        }
        
        

    }//GEN-LAST:event_tbl_bookDetailsMouseClicked

    private void tbl_userDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_userDetailsMouseClicked
        // TODO add your handling code here:
        int rowNumber = tbl_userDetails.getSelectedRow();
        TableModel model = tbl_userDetails.getModel();

        txt_userId.setText(model.getValueAt(rowNumber, 0).toString());
        //txt_userName.setText(model.getValueAt(rowNumber, 1).toString());
        
        if(!txt_userId.getText().equals("")){
        getUserDetails();
        }

    }//GEN-LAST:event_tbl_userDetailsMouseClicked

    private void txt_userIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userIdActionPerformed

    private void txt_userIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_userIdFocusLost
        // TODO add your handling code here:
        if(!txt_userId.getText().equals("")){
            getUserDetails();
        }
    }//GEN-LAST:event_txt_userIdFocusLost

    private void txt_bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookIdActionPerformed

    private void txt_bookIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookIdFocusLost
        if(!txt_bookId.getText().equals("")){
            getBookDetails();
        }
    }//GEN-LAST:event_txt_bookIdFocusLost

    private void tbl_bookDetailsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_bookDetailsFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_bookDetailsFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatDarkLaf.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_dueDate;
    private com.toedter.calendar.JDateChooser date_issueDate;
    private rojerusan.RSMaterialButtonCircle issueBookButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_bookError;
    private javax.swing.JLabel lbl_bookId;
    private javax.swing.JLabel lbl_bookName;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JLabel lbl_userError;
    private javax.swing.JLabel lbl_userId;
    private javax.swing.JLabel lbl_userName;
    private javax.swing.JPanel panelMain;
    private javax.swing.JTable tbl_bookDetails;
    private javax.swing.JTable tbl_userDetails;
    private javax.swing.JTextField txt_bookId;
    private javax.swing.JTextField txt_userId;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("app-icon.png")));
    }
}
