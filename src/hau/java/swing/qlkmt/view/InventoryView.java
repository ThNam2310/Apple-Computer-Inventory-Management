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
import hau.java.swing.qlkmt.controller.InventoryController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ACER
 */
public class InventoryView extends JInternalFrameView {

    DefaultTableModel tableModel;
    JTable productTable;
    public JTextField searchField;
    private JScrollPane scrollPane;
    public JComboBox searchComboBox;
    private JButton refreshButton;
    public InventoryController inventoryController;

    public InventoryView() {
        inventoryController = new InventoryController(this);
        initComponents();
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setSize(1180, 500);
        productTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        productTable.setDefaultEditor(Object.class, null);
        initTable();
        inventoryController.loadDataToTable();
        this.setResizable(false);
        this.setVisible(true);
    }

    // tạo bảng
    public final void initTable() {
        tableModel = new DefaultTableModel();
        String[] headerTable = new String[]{"Mã máy", "Tên máy", "Số lượng", "Đơn giá", "CPU", "RAM", "ROM",
            "Loại máy", "Trạng thái"};
        tableModel.setColumnIdentifiers(headerTable);
        productTable.setModel(tableModel);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(5);
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
    
    public JTextField getsearchField() {
        return searchField;
    }
    
    public void updateData() {
        inventoryController.loadDataToTable();
    }

    private void initComponents() {
        Font fontBold = new Font("Arial", Font.BOLD, 14);
        // Khu vực trên
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        // Tạo panel tìm kiếm
        JPanel search = new JPanel();
        search.setLayout(new FlowLayout(FlowLayout.CENTER, 20, -2));
        search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP));
        search.setPreferredSize(new Dimension(700, 60)); // Kích cỡ

        searchComboBox = new JComboBox<>();
        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Mã máy", "Tên máy"}));

        searchComboBox.setPreferredSize(new Dimension(180, 35));
        searchComboBox.setFont(fontBold);
        searchComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                inventoryController.searchComboBoxPropertyChange(evt);
            }
        });
        searchComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryController.searchComboBoxActionPerformed(e);
            }
        });

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setFont(fontBold);

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryController.searchFieldKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryController.searchFieldKeyReleased(evt);
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
                inventoryController.refreshButtonActionPerformed(evt);
            }
        });

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        topPanel.add(searchComboBox, gbc3);
        gbc3.gridx = 1;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        topPanel.add(searchField, gbc3);
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.weightx = 0.2;
        topPanel.add(refreshButton, gbc3);
//        search.add(searchComboBox);
//        search.add(searchField);
//        search.add(refreshButton);

        // Gắn các panel vào topPanel
        // Khu vực hiển thị nd
        JPanel content = new JPanel(new BorderLayout());
        content.setFont(fontBold);
        //content.setPreferredSize(new Dimension(1140, 650));

        productTable = new JTable();
        productTable.setShowGrid(true);
        scrollPane = new JScrollPane(productTable);
        scrollPane.setBackground(Color.BLUE);

        //scrollPane.setViewportView(productTable);
        //scrollPane.setPreferredSize(new Dimension(1140, 650));
        content.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        pack();
    }

    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Lấy giá trị cột "Số lượng" (giả sử cột này ở index 2)
            Object quantityValue = table.getValueAt(row, 2);

            if (quantityValue instanceof Integer && (Integer) quantityValue == 0) {
                // Nếu số lượng = 0, đổi màu nền thành đỏ
                cell.setBackground(new Color(146, 29, 29));
                cell.setForeground(Color.WHITE);
            } else {
                // Trả về màu mặc định nếu không thỏa điều kiện
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    }

//    public void searchComboBoxActionPerformed(ActionEvent e) {
//        String luachon = searchComboBox.getSelectedItem().toString();
//        String content = searchField.getText();
//        ArrayList<ComputerModel> result = trangTonKhoController.searchOc(luachon, content);
//        trangTonKhoController.loadDataTableSearch(result);
//    }
//
//    public void searchComboBoxPropertyChange(PropertyChangeEvent evt) {
//        String luachon = searchComboBox.getSelectedItem().toString();
//        String content = searchField.getText();
//        ArrayList<ComputerModel> result = trangTonKhoController.searchOc(luachon, content);
//        trangTonKhoController.loadDataTableSearch(result);
//    }
//
//    public void searchFieldKeyPressed(KeyEvent evt) {
//        String luachon = searchComboBox.getSelectedItem().toString();
//        String content = searchField.getText();
//        ArrayList<ComputerModel> result = trangTonKhoController.searchOc(luachon, content);
//        trangTonKhoController.loadDataTableSearch(result);
//    }
//
//    public void searchFieldKeyReleased(KeyEvent evt) {
//        String luachon = searchComboBox.getSelectedItem().toString();
//        String content = searchField.getText();
//        ArrayList<ComputerModel> result = trangTonKhoController.searchOc(luachon, content);
//        trangTonKhoController.loadDataTableSearch(result);
//    }
//
//    public void refreshButtonActionPerformed(ActionEvent evt) {
//        searchComboBox.setSelectedIndex(0);
//        searchField.setText("");
//        trangTonKhoController.loadDataToTable();
//    }
}
