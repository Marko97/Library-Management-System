/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marko
 */
public class ViewAllRecords extends javax.swing.JFrame {

    /**
     * Creates new form ViewAllRecords
     */
    DefaultTableModel model;
    
    public ViewAllRecords() {
        initComponents();
        setIcon();
        setIssueBookDetailsToTable();
    }
    
    public void setIssueBookDetailsToTable(){
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from issue_book_details");
            
            while(resultSet.next()){
                String id = resultSet.getString("id");
                String bookName = resultSet.getString("book_name");
                String userName = resultSet.getString("user_name");
                String issueDate = resultSet.getString("issue_date");
                String dueDate = resultSet.getString("due_date");
                String status = resultSet.getString("status");
                
                Object[] object = {id, bookName, userName, issueDate, dueDate, status};
                model =(DefaultTableModel) tbl_issueBookDetails.getModel();
                model.addRow(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) tbl_issueBookDetails.getModel();
        model.setRowCount(0);
    }
    
    public void search(){
        
        java.util.Date uFromDate = (java.util.Date) date_fromDate.getDate();
        java.util.Date uToDate = (java.util.Date) date_toDate.getDate();
        
        java.sql.Date sqlFromDate = new java.sql.Date(uFromDate.getTime());
        java.sql.Date sqlToDate = new java.sql.Date(uToDate.getTime());
        
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "select * from issue_book_details where issue_date BETWEEN ? and ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setDate(1, sqlFromDate);
            prepareStatement.setDate(2, sqlToDate);
            
            ResultSet resultSet = prepareStatement.executeQuery();
            
            if(resultSet.next() == false){
                JOptionPane.showMessageDialog(this, "Nisu pronađeni zapisi.");
            }else{
                
                while(resultSet.next()){
                String id = resultSet.getString("id");
                String bookName = resultSet.getString("book_name");
                String userName = resultSet.getString("user_name");
                String issueDate = resultSet.getString("issue_date");
                String dueDate = resultSet.getString("due_date");
                String status = resultSet.getString("status");
                
                Object[] object = {id, bookName, userName, issueDate, dueDate, status};
                model =(DefaultTableModel) tbl_issueBookDetails.getModel();
                model.addRow(object);
            }
                
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        date_fromDate = new com.toedter.calendar.JDateChooser();
        date_toDate = new com.toedter.calendar.JDateChooser();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        panelTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_issueBookDetails = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(75, 75, 75));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("Zapisi o knjigama");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Datum posudbe");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Rok posudbe");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));
        jPanel1.add(date_fromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 250, 30));
        jPanel1.add(date_toDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 250, 30));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(0, 0, 51));
        rSMaterialButtonCircle1.setText("Pretraživanje");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 240, 60));

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

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 90));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(51, 0, 51));
        rSMaterialButtonCircle2.setText("Prikaži sve");
        rSMaterialButtonCircle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle2MouseClicked(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 140, 220, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 220));

        panelTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_issueBookDetails.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tbl_issueBookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ime knjige", "Ime korisnika", "Datum posudbe", "Rok posudbe", "Status"
            }
        ));
        tbl_issueBookDetails.setRowHeight(40);
        tbl_issueBookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_issueBookDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_issueBookDetails);

        panelTable.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 1260, 520));

        getContentPane().add(panelTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1480, 640));

        setSize(new java.awt.Dimension(1305, 896));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_issueBookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issueBookDetailsMouseClicked
        // TODO add your handling code here:
        

    }//GEN-LAST:event_tbl_issueBookDetailsMouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        // TODO add your handling code here:
        if(date_fromDate.getDate() != null & date_toDate.getDate() != null){
        clearTable();
        search();
        }else{
            JOptionPane.showMessageDialog(this, "Molim odaberite datum.");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void rSMaterialButtonCircle2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2MouseClicked
        // TODO add your handling code here:
        clearTable();
        setIssueBookDetailsToTable();
    }//GEN-LAST:event_rSMaterialButtonCircle2MouseClicked

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
                new ViewAllRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_fromDate;
    private com.toedter.calendar.JDateChooser date_toDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelTable;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private javax.swing.JTable tbl_issueBookDetails;
    // End of variables declaration//GEN-END:variables

    public void setIcon(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("app-icon.png")));
    }
}
