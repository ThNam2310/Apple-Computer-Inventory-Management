/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.controller.BranchController;
import hau.java.swing.qlkmt.model.Branch;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BranchUpdateView extends JDialog {

    public BranchView parrent;
    private JButton addButton,cancelButton;
    public JTextField branchNameField,branchIdField,phoneField,addressField;
    BranchController branchController = new BranchController(this);

    public BranchUpdateView(JInternalFrame parrent, JFrame owner, boolean modal) {
        super(owner, modal);
        this.parrent = (BranchView) parrent;
        this.setSize(390, 500);
        init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        displayInfor();
    }

    private void displayInfor() {
        Branch branch = parrent.branchController.getBranchSelect();
        branchNameField.setText(branch.getBranchName());
        branchIdField.setText(branch.getBranchId());
        phoneField.setText(branch.getPhoneNumber());
        addressField.setText(branch.getAddress());
    }

    private void init() {
        JPanel panelHeader = new JPanel(new BorderLayout(20,10));
        panelHeader.setBackground(new Color(94,125,178));
        panelHeader.setPreferredSize(new Dimension(850, 40));
        JLabel title = new JLabel("CẬP NHẬT THÔNG TIN");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        panelHeader.add(title);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel branchIdLabel = new JLabel("Mã chi nhánh");
        branchIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        branchIdLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridy = 1;
        branchIdField = new JTextField(40);
        branchIdField.setPreferredSize(new Dimension(70, 30));
        branchIdField.setEnabled(false);
        branchIdField.setEditable(false);
        mainPanel.add(branchIdField, gbc);

        // Hàng 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel branchNameLabel = new JLabel("Tên chi nhánh");
        branchNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        branchNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(branchNameLabel, gbc);

        gbc.gridy = 3;
        branchNameField = new JTextField(40);
        branchNameField.setPreferredSize(new Dimension(70, 30));
        mainPanel.add(branchNameField, gbc);

        // Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel phoneLabel = new JLabel("Số điện thoại");
        phoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(phoneLabel, gbc);

        gbc.gridy = 5;
        phoneField = new JTextField(40);
        phoneField.setPreferredSize(new Dimension(70, 30));
        mainPanel.add(phoneField, gbc);

        // Hàng 4
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel addressLabel = new JLabel("Địa chỉ");
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(addressLabel, gbc);

        gbc.gridy = 7;
        addressField = new JTextField(40);
        addressField.setPreferredSize(new Dimension(70, 30));
        mainPanel.add(addressField, gbc);

        // Thêm khoảng cách giữa Đơn giá và Buttons
        gbc.gridy = 8;
        mainPanel.add(Box.createVerticalStrut(50), gbc);

        // Hàng 4: Buttons
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        addButton = new JButton("Lưu ");
        addButton.setPreferredSize(new Dimension(120, 30));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                branchController.saveUpdateButtonActionPerformed(e);
            }
        });

        cancelButton = new JButton("Hủy ");
        cancelButton.setPreferredSize(new Dimension(120, 30));
        cancelButton.setBackground(null);
        cancelButton.setForeground(Color.gray);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                branchController.cancelUpdateButtonActionPerformed(e);
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);

        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
    }
    
    public BranchView getParrent() {
        return parrent;
    }
    
    public JTextField getBranchIdField() {
        return branchIdField;
    }
    
    public JTextField getBranchNameField() {
        return branchNameField;
    }
    
    public JTextField getPhoneField() {
        return phoneField;
    }
    
    public JTextField getAddressField() {
        return addressField;
    }

//    private void saveUpdateButtonActionPerformed(ActionEvent e) {
//        String maCN = txtmaCN.getText();
//        String tenCN = txttenCN.getText();
//        String sdt = txtsdt.getText();
//        String diaChi = txtdiaChi.getText();
//        try {
//            // TODO add your handling code here:
//            if (maCN.equals("") || tenCN.equals("") || sdt.equals("") || diaChi.equals("")) {
//                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
//                return;
//            } else {
//                Branch cn = new Branch();
//                cn.setmaChiNhanhCuaHang(txtmaCN.getText());
//                cn.setTenChiNhanh(txttenCN.getText());
//                cn.setSdt(txtsdt.getText());
//                cn.setDiaChi(txtdiaChi.getText());
//                BranchDao cnDao = new BranchDao();
//                cnDao.update(cn);
//                this.dispose();
//                JOptionPane.showMessageDialog(this, "Sửa chi nhánh thành công !");
//                HomeView trangChu = HomeView.getInstance();
//                trangChu.reloadCenterPanel();
//                parrent.branchController.loadDataTable(BranchDao.getInstance().selectAll());
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Sửa chi nhánh thất bại !");
//        }
//    }
//
//    private void cancelUpdateButtonActionPerformed(ActionEvent e) {
//        this.dispose();
//    }
}
