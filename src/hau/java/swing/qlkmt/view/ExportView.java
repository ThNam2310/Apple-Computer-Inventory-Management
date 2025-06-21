/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.controller.ExportController;
import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.ExportDao;
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
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

public class ExportView extends JInternalFrameView {

    public ExportController exportController;
    public DefaultTableModel tableModel;
    public String invoiceId;

    JTable productTable, exportTable;
    public JTextField quantityField, exportField, searchField;
    public JButton addButton, refreshButton, editButton, deleteButton, exportButton;
    public JLabel totalpriceLabel;
    public JComboBox branchComboBox;

    public ExportView() {
        exportController = new ExportController(this);
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setSize(1180, 500);
        init();
        exportController.allProduct = ComputerDao.getInstance().selectAllExist();
        initTable();
        exportController.loadDataTableProduct(exportController.allProduct);
        exportController.loadBranch();
        productTable.setDefaultEditor(Object.class, null);
        exportTable.setDefaultEditor(Object.class, null);
        invoiceId = exportController.createID(ExportDao.getInstance().selectAll());
        exportField.setText(invoiceId);
        exportController.invoiceDetail = new ArrayList<>();
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

        exportTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        exportTable.getColumnModel().getColumn(2).setPreferredWidth(150);
    }

    private void init() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(585, 50));
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
                exportController.txtSearchKeyReleased(evt);
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
                exportController.refreshButtonActionPerformed(e);
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
        productTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Mã máy", "Tên máy", "Số lượng", "Đơn giá"
                }
        ));
        JScrollPane leftScroll = new JScrollPane(productTable);
        leftScroll.setViewportView(productTable);
        leftScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftTablePanel.add(leftScroll, BorderLayout.CENTER);
        leftPanel.add(leftTablePanel, BorderLayout.CENTER);

        JPanel leftActionPanel = new JPanel();
        leftActionPanel.setPreferredSize(new Dimension(200, 100));
        leftActionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        leftActionPanel.add(new JLabel("Số lượng"));
        quantityField = new JTextField("1", 5);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        leftActionPanel.add(quantityField);

        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(11, 170, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        leftActionPanel.add(addButton);
        leftPanel.add(leftActionPanel, BorderLayout.SOUTH);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportController.addButtonActionPerformed(e);
            }
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(585, 40));
        JPanel rightNorthPanel = new JPanel(new GridBagLayout());
        
        JLabel exportLabel = new JLabel("Mã phiếu xuất");
        exportLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exportLabel.setVerticalAlignment(SwingConstants.CENTER);
        exportField = new JTextField();
        exportField.setEditable(false);
        exportField.setEnabled(false);

        JLabel branchLabel = new JLabel("Tên chi nhánh");
        branchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        branchLabel.setVerticalAlignment(SwingConstants.CENTER);
        branchComboBox = new JComboBox();

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.4;
        rightNorthPanel.add(exportLabel, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 0.6;
        rightNorthPanel.add(exportField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.weightx = 0.4;
        rightNorthPanel.add(branchLabel, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        gbc2.weightx = 0.6;
        rightNorthPanel.add(branchComboBox, gbc2);

        rightPanel.add(rightNorthPanel, BorderLayout.NORTH);

        JPanel rightTablePanel = new JPanel(new BorderLayout());

        exportTable = new JTable();
        exportTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"
                }
        ));
        exportTable.setShowGrid(true);
        JScrollPane rightScroll = new JScrollPane(exportTable);
        rightScroll.setViewportView(exportTable);
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
                exportController.editButtonActionPerformed(e);
            }
        });

        deleteButton = new JButton("Xóa sản phẩm");
        deleteButton.setBackground(new Color(220, 73, 54));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        buttonrightPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportController.deleteButtonActionPerformed(e);
            }
        });

        JPanel rightBottom = new JPanel();
        rightBottom.setPreferredSize(new Dimension(550, 100));
        rightBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        rightPanel.add(rightBottom, BorderLayout.SOUTH);

        JLabel totalLabel = new JLabel("Tổng tiền:");
        totalLabel.setFont(new Font(null, Font.BOLD, 24));
        rightBottom.add(totalLabel);

        totalpriceLabel = new JLabel("0đ");
        totalpriceLabel.setFont(new Font(null, Font.BOLD, 24));
        totalpriceLabel.setForeground(Color.RED);
        rightBottom.add(totalpriceLabel);

        exportButton = new JButton("Xuất hàng");
        exportButton.setBackground(new Color(85, 125, 183));
        exportButton.setForeground(Color.WHITE);
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        rightBottom.add(exportButton);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportController.exportButtonActionPerformed(e);
            }
        });
        southPanel.add(buttonrightPanel, BorderLayout.NORTH);
        southPanel.add(rightBottom, BorderLayout.SOUTH);
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
    public JTable getExportTable() {
        return exportTable;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) productTable.getModel();
    }

    public JComboBox getBranchComboBox() {
        return branchComboBox;
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

    public JTextField getExportField() {
        return exportField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void updateData() {
        exportController.allProduct = ComputerDao.getInstance().selectAllExist();
        exportController.arrayListBranch = BranchDao.getInstance().selectAll();
        exportController.loadBranch();
        exportController.loadDataTableProduct(exportController.allProduct);
        System.out.println(exportController.arrayListBranch);
    }
}
