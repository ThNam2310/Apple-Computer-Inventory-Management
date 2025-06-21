/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import hau.java.swing.qlkmt.controller.ProductController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class ProductView extends JInternalFrameView {

    DefaultTableModel tableModel;
    JTable productTable;
    private JTextField searchField;
    private JScrollPane scrollPane;
    private JComboBox searchComboBox;
    private JButton addButton, removeButton, viewButton, editButton, refreshButton;
    public ProductController productController;
    public JTabbedPane tabbedPane;

    public ProductView() {
        initComponents();
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setSize(1180, 500);
        productController = new ProductController(this);
        productTable.setDefaultEditor(Object.class, null);
        productController.loadDataToTable();
        initTable();
        this.setResizable(false);
        this.setVisible(true);
    }

    // tạo bảng
    public final void initTable() {
        tableModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"Mã máy", "Tên máy", "Đơn giá", "CPU", "RAM", "ROM",
            "Loại máy"};
        tableModel.setColumnIdentifiers(headerTbl);
        productTable.setModel(tableModel);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(5);
    }

    public final void initComponents() {
        Font fontBold = new Font("Arial", Font.BOLD, 14);
        JPanel topPanel = new JPanel(new GridBagLayout());
        // Tao panel chức năng
        JPanel function = new JPanel(new GridBagLayout());
        //function.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        function.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Chức năng",
                TitledBorder.LEFT, TitledBorder.TOP));
        
        addButton = new JButton("Thêm");
        addButton.setBorderPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        addButton.addActionListener((ActionEvent e) -> {
            productController.addButtonActionPerformed(e);
        });

        removeButton = new JButton("Xóa");
        removeButton.setBorderPainted(false);
        removeButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.setFocusable(false);
        removeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        removeButton.addActionListener((ActionEvent e) -> {
            try {
                productController.removeButtonActionPerformed(e);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        editButton = new JButton("Sửa");
        editButton.setBorderPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setFocusable(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productController.editButtonActionPerformed(e);
            }
        });

        viewButton = new JButton("Xem chi tiết");
        viewButton.setBorderPainted(false);
        viewButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.setFocusable(false);
        viewButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productController.viewButtonActionPerformed(e);
            }
        });

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.25;
        function.add(addButton, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 0.25;
        function.add(editButton, gbc2);
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.weightx = 0.25;
        function.add(removeButton, gbc2);
        gbc2.gridx = 3;
        gbc2.gridy = 0;
        gbc2.weightx = 0.25;
        function.add(viewButton, gbc2);

        // Tạo panel tìm kiếm
        JPanel search = new JPanel(new GridBagLayout());
        search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP));

        searchComboBox = new JComboBox<>();
        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Mã máy", "Tên máy"}));

        searchComboBox.setPreferredSize(new Dimension(180, 30));
        searchComboBox.setFont(fontBold);

        searchComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                productController.searchComboBoxPropertyChange(evt);
            }
        });

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productController.searchFieldKeyPressed(evt);
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productController.searchFieldKeyReleased(evt);
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
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productController.refreshButtonActionPerformed(evt);
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        topPanel.add(function, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2;
        topPanel.add(search, gbc);

        // Khu vực hiển thị nd
        JPanel content = new JPanel(new BorderLayout()); // Đặt BorderLayout cho content
        content.setFont(fontBold);
        productTable = new JTable();
        productTable.setShowGrid(true);
        scrollPane = new JScrollPane(productTable); // Tạo JScrollPane chứa JTable
        content.add(scrollPane, BorderLayout.CENTER); // Thêm scrollPane vào giữa (CENTER) của content

        // Thêm content vào cửa sổ chính hoặc bố cục
        getContentPane().add(content, BorderLayout.CENTER); // content chiếm toàn bộ vùng trung tâm
        getContentPane().add(topPanel, BorderLayout.NORTH);
        pack();
    }

    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) productTable.getModel();
    }

    public JComboBox getSearchComboBox() {
        return searchComboBox;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void loadDataToTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
