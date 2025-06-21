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
import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.model.Branch;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class BranchView extends JInternalFrameView {

    DefaultTableModel tableModel;
    private static ArrayList<Branch> arrayListBranch;
    JTable branchTable;
    private JComboBox searchComboBox;
    private JTextField searchField;
    private JScrollPane scrollPane;
    private JButton refreshButton;
    public BranchController branchController;

    public BranchView() {
        init();
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setSize(1180, 500);
        branchTable.setDefaultEditor(Object.class, null);
        initTable();
        arrayListBranch = BranchDao.getInstance().selectAll();
        branchController = new BranchController(this);
        branchController.loadDataTable(arrayListBranch);
        this.setResizable(false);
        this.setVisible(true);
    }

    public final void initTable() {
        tableModel = new DefaultTableModel();
        String[] headertbl = new String[]{"Mã chi nhánh", "Tên chi nhánh", "Số điện thoại", "Địa chỉ"};
        tableModel.setColumnIdentifiers(headertbl);
        branchTable.setModel(tableModel);
        branchTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        branchTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        branchTable.getColumnModel().getColumn(2).setPreferredWidth(2);
        branchTable.getColumnModel().getColumn(3).setPreferredWidth(350);
    }

    private void init() {
        Font fontBold = new Font("Arial", Font.BOLD, 14);
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel functionPanel = new JPanel(new GridBagLayout());
//        functionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        functionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Chức năng",
                TitledBorder.LEFT, TitledBorder.TOP));

        JButton addButton = new JButton("Thêm");
        addButton.setHorizontalTextPosition(JButton.CENTER);
        addButton.setVerticalTextPosition(JButton.BOTTOM);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        addButton.setFocusable(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                branchController.addButtonActionPerformed(e);
            }
        });

        JButton editButton = new JButton("Sửa");
        editButton.setHorizontalTextPosition(JButton.CENTER);
        editButton.setVerticalTextPosition(JButton.BOTTOM);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setFocusable(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                branchController.editButtonActionPerformed(e);
            }

        });

        JButton deleteButton = new JButton("Xóa");
        deleteButton.setHorizontalTextPosition(JButton.CENTER);
        deleteButton.setVerticalTextPosition(JButton.BOTTOM);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setFocusable(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    branchController.deleteButtonActionPerformed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(BranchView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.33;
        functionPanel.add(addButton, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 0.33;
        functionPanel.add(editButton, gbc2);
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.weightx = 0.33;
        functionPanel.add(deleteButton, gbc2);

//        functionPanel.add(addButton);
//        functionPanel.add(editButton);
//        functionPanel.add(deleteButton);

        // panel tìm kiếm
        JPanel search = new JPanel(new GridBagLayout());
        //search.setLayout(new FlowLayout(FlowLayout.CENTER, 20, -2));
        search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP));
//        search.setPreferredSize(new Dimension(700, 60)); // Kích cỡ

        searchComboBox = new JComboBox<>();
        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Mã chi nhánh", "Tên chi nhánh"}));
        searchComboBox.setPreferredSize(new Dimension(180, 35));
        searchComboBox.setFont(fontBold);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                branchController.searchFieldKeyReleased(evt);
            }
        });
        //Button Refresh
        ImageIcon originalIcon = new ImageIcon("src\\hau\\java\\swing\\qlkmt\\image\\refresh-button.png");

        // Thay đổi kích thước icon
        Image resizedImage = originalIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        refreshButton = new JButton(resizedIcon);
        refreshButton.setBackground(null);


        // Thay đổi thêm kích thước button (tuỳ chọn)
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchController.refreshButtonActionPerformed(evt);
            }
        });

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        search.add(searchComboBox, gbc3);
        gbc3.gridx = 1;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        search.add(searchField, gbc3);
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.weightx = 0.2;
        search.add(refreshButton, gbc3);

        // Gắn các panel vào topPanel
               // Gắn các panel vào topPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        topPanel.add(functionPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        topPanel.add(search, gbc);

//        topPanel.add(functionPanel, BorderLayout.WEST); // Bên trái
//        topPanel.add(search, BorderLayout.EAST);  // Bên phải

        // Bảng nd
        JPanel content = new JPanel(new  BorderLayout());
        content.setFont(fontBold);
        //content.setPreferredSize(new Dimension(1140, 650));

        branchTable = new JTable();
        branchTable.setShowGrid(true);
        scrollPane = new JScrollPane(branchTable);

        //scrollPane.setViewportView(branchTable);
        //scrollPane.setPreferredSize(new Dimension(1140, 650));
        content.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        //getContentPane().add(content, BorderLayout.SOUTH);
        pack();
    }
    
     public JTable getBranchTable() {
        return branchTable;
    }
    
    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) branchTable.getModel();
    }
    
    public JTextField getSearchField() {
        return searchField;
    }
    
    public JComboBox getSearchComboBox() {
        return searchComboBox;
    }

//    private void deleteButtonActionPerformed(ActionEvent evt) throws UnsupportedLookAndFeelException {
//        if (branchTable.getSelectedRow() == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh muốn xóa");
//        } else {
//            int output = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa chi nhánh này ?", "Xác nhận xóa chi nhánh", JOptionPane.YES_NO_OPTION);
//            if (output == JOptionPane.YES_NO_OPTION) {
//                BranchDao.getInstance().delete(trangChiNhanhController.getChiNhanhSelect());
//                JOptionPane.showMessageDialog(this, "Xóa thành công!");
//                HomeView trangChu = HomeView.getInstance();
//                trangChu.reloadCenterPanel();
//                trangChiNhanhController.loadDataTable(BranchDao.getInstance().selectAll());
//            }
//        }
//    }
//
//    private void refreshButtonActionPerformed(ActionEvent evt) {
//        searchField.setText("");
//        searchComboBox.setSelectedIndex(0);
//        trangChiNhanhController.loadDataTable(BranchDao.getInstance().selectAll());
//    }
//
//    private void searchFieldKeyReleased(KeyEvent evt) {
//        String luachon = (String) searchComboBox.getSelectedItem();
//        String option = searchField.getText();
//        ArrayList<ChiNhanh> result = new ArrayList<>();
//        switch (luachon) {
//            case "Mã chi nhánh":
//                result = trangChiNhanhController.maCN(option);
//                break;
//            case "Tên chi nhánh":
//                result = trangChiNhanhController.tenCN(option);
//                break;
//        }
//        trangChiNhanhController.loadDataTable(result);
//    }
//
//    private void editButtonActionPerformed(ActionEvent e) {
//        if (branchTable.getSelectedRow() == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh muốn sửa");
//        } else {
//            SuaCN edit = new SuaCN(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
//            edit.setVisible(true);
//        }
//    }
//
//    private void addButtonActionPerformed(ActionEvent e) {
//        ThemCN themCN = new ThemCN(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
//        themCN.setVisible(true);
//    }
}
