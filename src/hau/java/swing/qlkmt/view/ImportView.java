/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.controller.ImportController;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.ImportDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.Image;


public class ImportView extends JInternalFrameView {

    public DefaultTableModel tableModel;
    public String invoiceId;
    JTable productTable, importTable;
    public JLabel totalLabel, totalpriceLabel;
    public JTextField quantityField, importIdField, searchField;
    public JButton addButton, refreshButton, editButton, deleteButton, importButton;
    public ImportController importController;

    public ImportView() {
        importController = new ImportController(this);
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setSize(1180, 500);
        init();
        importController.allProduct = ComputerDao.getInstance().selectAllExist();
        initTable();
        importController.loadDataTableProduct(importController.allProduct);
        productTable.setDefaultEditor(Object.class, null);
        importTable.setDefaultEditor(Object.class, null);
        invoiceId = importController.createID(ImportDao.getInstance().selectAll());
        importIdField.setText(invoiceId);
        importController.invoiceDetail = new ArrayList<>();
        this.setResizable(false);
        this.setVisible(true);
    }

    public final void initTable() {
        tableModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"Mã máy", "Tên máy", "Số lượng", "Đơn giá"};
        tableModel.setColumnIdentifiers(headerTbl);
        productTable.setModel(tableModel);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        
        importTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        importTable.getColumnModel().getColumn(2).setPreferredWidth(150);
    }
    
    //Phương thức để làm mới dữ liệu
    public void updateData() {
        importController.allProduct = ComputerDao.getInstance().selectAllExist();
        importController.loadDataTableProduct(importController.allProduct);
    }

    private void init() {
        //Tạo panel tìm kiếm
        JPanel leftPanel = new JPanel(new BorderLayout());
        //leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        leftPanel.setPreferredSize(new Dimension(585, 50)); // Kích cỡ
        
        JPanel LeftNorthPanel = new JPanel(new GridBagLayout());
        JPanel searchPanel = new JPanel(new BorderLayout(10, 5));
        JLabel labelSearch = new JLabel("Tìm kiếm");
        labelSearch.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa theo chiều ngang
        labelSearch.setVerticalAlignment(SwingConstants.CENTER);   // Căn giữa theo chiều dọc
        
        searchPanel.add(labelSearch, BorderLayout.CENTER);

       
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                importController.txtSearchKeyReleased(evt);
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
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.refreshButtonActionPerformed(e);
            }
        });
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        LeftNorthPanel.add(searchPanel, gbc3);
        gbc3.gridx = 1;
        gbc3.gridy = 0;
        gbc3.weightx = 0.4;
        LeftNorthPanel.add(searchField, gbc3);
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.weightx = 0.2;
        LeftNorthPanel.add(refreshButton, gbc3);

        leftPanel.add(LeftNorthPanel, BorderLayout.NORTH);

        JPanel leftTablePanel = new JPanel(new BorderLayout());
        productTable = new JTable();
        productTable.setShowGrid(true);
        JScrollPane leftScroll = new JScrollPane(productTable);
        leftScroll.setViewportView(productTable);
        leftScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftTablePanel.add(leftScroll, BorderLayout.CENTER);
        leftPanel.add(leftTablePanel, BorderLayout.CENTER);

        JPanel leftActioPanel = new JPanel();
        leftActioPanel.setPreferredSize(new Dimension(200, 100));
        leftActioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        leftActioPanel.add(new JLabel("Số lượng"));
        quantityField = new JTextField("1", 5);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        leftActioPanel.add(quantityField);

        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(11,170,0));
        addButton.setForeground(Color.WHITE);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.addButtonActionPerformed(e);
            }
        });

        leftActioPanel.add(addButton);
        leftPanel.add(leftActioPanel, BorderLayout.SOUTH);

        //
        JPanel rightPanel = new JPanel(new  BorderLayout());
        //rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        rightPanel.setPreferredSize(new Dimension(585, 50)); // Kích cỡ

        JPanel rightNorthPanel = new JPanel(new GridBagLayout());
        //rightNorthPanel.add(new JLabel("Mã phiếu nhập"));
        importIdField = new JTextField();
        //importIdField.setPreferredSize(new Dimension(300, 30));
        importIdField.setEditable(false);
        importIdField.setEnabled(false);
        //rightNorthPanel.add(importIdField);
        JLabel label = new JLabel("Mã phiếu nhập");
        label.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa theo chiều ngang
        label.setVerticalAlignment(SwingConstants.CENTER);   // Căn giữa theo chiều dọc
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.4;
        rightNorthPanel.add(label, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 0.6;
        rightNorthPanel.add(importIdField, gbc2);
        rightPanel.add(rightNorthPanel , BorderLayout.NORTH);

        JPanel rightTablePanel = new JPanel(new BorderLayout());

        importTable = new JTable();
        importTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"
                }
        ));
        importTable.setShowGrid(true);
        JScrollPane rightScroll = new JScrollPane(importTable);
        rightScroll.setViewportView(importTable);
        rightScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 13, 5));
        rightTablePanel.add(rightScroll, BorderLayout.CENTER);
        rightPanel.add(rightTablePanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());

        JPanel buttonrightPanel = new JPanel();
        buttonrightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        editButton = new JButton("Sửa số lượng");
        editButton.setBackground(new Color(85, 125, 183));
        editButton.setForeground(Color.WHITE);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        buttonrightPanel.add(editButton);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.editButtonActionPerformed(e);
            }
        });

        deleteButton = new JButton("Xóa sản phẩm");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 73, 54));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        buttonrightPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.deleteButtonActionPerformed(e);
            }
        });

        JPanel rightBottom = new JPanel();
        rightBottom.setPreferredSize(new Dimension(590, 100));
        rightBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        totalLabel = new JLabel("Tổng tiền:");
        totalLabel.setFont(new Font(null, Font.BOLD, 24));
        rightBottom.add(totalLabel);

        totalpriceLabel = new JLabel("0đ");
        totalpriceLabel.setFont(new Font(null, Font.BOLD, 24));
        totalpriceLabel.setForeground(Color.RED);
        rightBottom.add(totalpriceLabel);

        importButton = new JButton("Nhập hàng");
        importButton.setForeground(Color.white);
        importButton.setBackground(new Color(85, 125, 183));
        importButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        importButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        rightBottom.add(importButton);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.importButtonActionPerformed(e);
            }
        });
        southPanel.add(buttonrightPanel, BorderLayout.NORTH);
        southPanel.add(rightBottom , BorderLayout.SOUTH);
        rightPanel.add(southPanel, BorderLayout.SOUTH);
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(leftPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(rightPanel, gbc);
        pack();
    }
    
    /////////////////////////////////////////////////////////////
    public JTable getImportTable() {
        return importTable;
    }
    
    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getProductTableModel() {
        return (DefaultTableModel) productTable.getModel();
    }

    public JLabel getPrice() {
        return totalpriceLabel;
    }
    
    public String getInvoiceId() {
        return invoiceId;
    }
    
    public JTextField getQuantityField() {
        return quantityField;
    }
    
    public JTextField getImportIdField() {
        return importIdField;
    }
    
    public JTextField getSearchField() {
        return searchField;
    }
    
    
//    private void addButtonActionPerformed(ActionEvent e) {
//        ProductController trangSPController = new ProductController(this);
//        int i_row = productTable.getSelectedRow();
//        if (i_row == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để nhập hàng!");
//        } else {
//            int soluong;
//            try {
//                soluong = Integer.parseInt(quantityField.getText());
//                if (soluong > 0) {
//                    InvoiceDetail chiTietPhieu = importController.tkCTPhieu((String) productTable.getValueAt(i_row, 0));
//                    if (chiTietPhieu != null) {
//                        chiTietPhieu.setSoLuong(chiTietPhieu.getSoLuong() + soluong);
//                    } else {
//                        Computer mt = trangSPController.searchID((String) productTable.getValueAt(i_row, 0));
//                        InvoiceDetail ctp = new InvoiceDetail(invoiceId, mt.getMaMay(), soluong, mt.getGia());
//                        importController.invoiceDetail.add(ctp);
//                    }
//                    importController.loadDataTableNhapHang();
//                    totalpriceLabel.setText(importController.formatter.format(importController.tongTien()) + "đ");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
//                }
//            } catch (HeadlessException | NumberFormatException evt) {
//                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên");
//            }
//        }
//    }
//
//    private void txtSearchKeyReleased(KeyEvent evt) {
//        String search = searchField.getText().toLowerCase();
//        ArrayList<ComputerModel> mt = new ArrayList<>();
//        for (Computer i : importController.allProduct) {
//            if (i.getMaMay().concat(i.getTenMay()).toLowerCase().contains(search)) {
//                mt.add(i);
//            }
//        }
//        importController.loadDataTableProduct(mt);
//    }
//
//    private void refreshButtonActionPerformed(ActionEvent e) {
//        searchField.setText("");
//        importController.loadDataTableProduct(importController.allProduct);
//    }
//
//    private void editButtonActionPerformed(ActionEvent e) {
//        int i_row = importTable.getSelectedRow();
//        if (i_row == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa số lượng!");
//        } else {
//            String editSL = JOptionPane.showInputDialog(this, "Nhập số lượng cần thay đổi", "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
//            if (editSL != null) {
//                int soluong;
//                try {
//                    soluong = Integer.parseInt(editSL);
//                    if (soluong > 0) {
//                        importController.invoiceDetail.get(i_row).setSoLuong(soluong);
//                        importController.loadDataTableNhapHang();
//                        totalpriceLabel.setText(importController.formatter.format(importController.tongTien()) + "đ");
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
//                    }
//                } catch (HeadlessException | NumberFormatException evt) {
//                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên");
//                }
//            }
//
//        }
//    }
//
//    private void deleteButtonActionPerformed(ActionEvent e) {
//        int i_row = importTable.getSelectedRow();
//        if (i_row == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa khỏi bảng nhập hàng!");
//        } else {
//            importController.invoiceDetail.remove(i_row);
//            importController.loadDataTableNhapHang();
//            totalpriceLabel.setText(importController.formatter.format(importController.tongTien()) + "đ");
//        }
//    }
//
//    private void importButtonActionPerformed(ActionEvent e) {
//        if (importController.invoiceDetail.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm để nhập hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//        } else {
//            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn nhập hàng?", "Xác nhận nhập hàng", JOptionPane.YES_NO_OPTION);
//            if (check == JOptionPane.YES_OPTION) {
//                // lấy thời gian hiện tại
//                long now = System.currentTimeMillis();
//                Timestamp timeStamp = new Timestamp(now);
//                // Tạo phiếu nhập
//                PhieuNhap pn = new PhieuNhap(invoiceId, timeStamp, importController.invoiceDetail, importController.tongTien());
//                try {
//                    ImportDao.getInstance().insert(pn);
//                    ComputerDao mtdao = ComputerDao.getInstance();
//                    for (var i : importController.invoiceDetail) {
//                        ChiTietPhieuNhapDao.getInstance().insert(i);
//                        mtdao.updateSoLuong(i.getMaMay(), mtdao.selectById(i.getMaMay()).getSoLuong() + i.getSoLuong());
//                    }
//                    JOptionPane.showMessageDialog(this, "Nhập hàng thành công!");
//                    importController.loadDataTableProduct(importController.allProduct);
//                    DefaultTableModel dtm = (DefaultTableModel) importTable.getModel();
//                    dtm.setRowCount(0);
//                    importController.invoiceDetail = new ArrayList<>();
//                    totalpriceLabel.setText("0");
//                    this.invoiceId = importController.createID(ImportDao.getInstance().selectAll());
//                    importIdField.setText(this.invoiceId);
//                    refreshButtonActionPerformed(e);
//                    HomeView homeView = HomeView.getInstance();
//                    homeView.reloadCenterPanel();
//                } catch (HeadlessException | UnsupportedLookAndFeelException evt) {
//                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi!");
//                }
//            }
//        }
//    }
}
