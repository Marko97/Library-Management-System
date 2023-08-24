/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marko
 */
public class IssueBookDetails extends javax.swing.JFrame {

    /**
     * Creates new form IssueBookDetails
     */
    DefaultTableModel model;
    public IssueBookDetails() {
        initComponents();
        setIcon();
        setIssueBookDetailsToTable();
    }
    
    
    public void setIssueBookDetailsToTable(){
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from issue_book_details where status = '"+"Posuđena"+"'");
            
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_issueBookDetails = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Marko\\Faks\\5. Godina\\Diplomski rad\\Icons\\issue_book_icon.png")); // NOI18N
        jLabel2.setText("Popis posuđenih knjiga");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, -1, -1));

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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 1110, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 650));

        setSize(new java.awt.Dimension(1392, 693));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void tbl_issueBookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issueBookDetailsMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_issueBookDetailsMouseClicked

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
                new IssueBookDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_issueBookDetails;
    // End of variables declaration//GEN-END:variables

    public void setIcon(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("app-icon.png")));
    }
}
