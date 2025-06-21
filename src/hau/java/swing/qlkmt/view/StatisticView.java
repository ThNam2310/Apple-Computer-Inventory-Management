/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;
/**
 *
 * @author thanh
 */
import com.toedter.calendar.JDateChooser;
import hau.java.swing.qlkmt.controller.StatisticController;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.model.DataProductImportExport;
import hau.java.swing.qlkmt.model.Computer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

public class StatisticView extends JInternalFrameView {

    //biểu đồ
    public JDateChooser jDateChooserFromProduct, jDateChooserToProduct;
    public JComboBox productComboBox;
    public static ArrayList<Computer> allproduct = ComputerDao.getInstance().selectAllQuantity();
    public static ArrayList<DataProductImportExport> arrayListImport, arrayListExport;
    public JLabel numberImportLabel, numberExportLabel, moneyImportLabel, moneyExportLabel;
    public static JPanel southPanel;
    //public JPanel southPanel;

    //nhập
    public JDateChooser jDateChooserFromImport, jDateChooserToImport;
    public JTable importTable;
    public DefaultTableModel tableImportModel;
    public JButton refreshImportButton;
    public JTextField priceToImportField, priceFromImportField;
    public JScrollPane jScrollPaneImport;
    public JLabel invoiceNumberImportLabel, moneyNumberImportLabel;

    //xuất
    public JDateChooser jDateChooserFromExport, jDateChooserToExport;
    public JButton refreshExportButton;
    public JTextField priceFromExportField, priceToExportField;
    public JTable exportTable;
    public DefaultTableModel tableExportModel;
    public JScrollPane jScrollPaneExport;
    public JLabel invoiceNumberExportLabel, moneyNumberExportLabel;
    
    //toolbar
    public JButton producButton;
    public JButton invoiceImportButton;
    public JButton invoiceExportButton;

    public StatisticController statisticController;

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public SimpleDateFormat getFormatDate() {
        return formatDate;
    }

    public StatisticView() {
        statisticController = new StatisticController(this);
        init();
        this.setSize(1180, 800);
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        jDateChooserFromProduct.setDateFormatString("dd//MM//yyyy");
        jDateChooserToProduct.setDateFormatString("dd//MM//yyyy");
        jDateChooserFromImport.setDateFormatString("dd//MM//yyyy");
        jDateChooserToImport.setDateFormatString("dd//MM//yyyy");
        jDateChooserFromExport.setDateFormatString("dd//MM//yyyy");
        jDateChooserToExport.setDateFormatString("dd//MM//yyyy");

        initTablePN();
        statisticController.loadDataTableImport();
        statisticController.loadDataTableExport();
        importTable.setDefaultEditor(Object.class, null);
        exportTable.setDefaultEditor(Object.class, null);
        this.setVisible(true);
        this.setResizable(true);
    }
    
    private void init() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(new Color(94,125,178));
        JPanel cardPanel = new JPanel(new CardLayout());

        producButton = new JButton("Sản phẩm");
        producButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        producButton.setForeground(Color.WHITE);
        toolBar.add(producButton);
        producButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        invoiceImportButton = new JButton("Phiếu Nhập");
        invoiceImportButton.setFont(new Font(null, Font.BOLD, 14));
        invoiceImportButton.setForeground(Color.WHITE);
        toolBar.add(invoiceImportButton);
        invoiceImportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        invoiceExportButton = new JButton("Phiếu Xuất");
        invoiceExportButton.setFont(new Font(null, Font.BOLD, 14));
        invoiceExportButton.setForeground(Color.WHITE);
        toolBar.add(invoiceExportButton);
        invoiceExportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //////Màn của Biểu đồ
        productComboBox = new JComboBox();
        allproduct = ComputerDao.getInstance().selectAllExist();
        for (Computer computerModel : allproduct) {
            productComboBox.addItem(computerModel.getProductId()+ " - " + computerModel.getProductName());
        }
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(new Color(231, 233, 238));
        productPanel.setLayout(new BorderLayout());// Chia productPanel thành 2 phần: Bắc và Nam
        // Phần phía Bắc
        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(new Color(231, 233, 238));
        // Nửa trái phía bắc 
        JPanel searchproduct = new JPanel();
        searchproduct.setBackground(new Color(231, 233, 238));
        searchproduct.add(productComboBox);
        //giữa phía bắc
        JPanel timePanel = new JPanel(new GridBagLayout());
        //timePanel.setPreferredSize(new Dimension(250, 60));
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
        timePanel.setBackground(new Color(231, 233, 238));

        JLabel jDateFromLabel = new JLabel("Từ ngày:");
        jDateFromLabel.setFont(new Font(null, Font.BOLD, 14));
        jDateChooserFromProduct = new JDateChooser();

        JLabel jDateToLabel = new JLabel("Đến ngày:");
        jDateToLabel.setFont(new Font(null, Font.BOLD, 14));
        jDateChooserToProduct = new JDateChooser();

        // them cac thanh phan vao right panel
        // Thiết lập cho JLabel "Từ"
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Cột đầu tiên
        gbc.gridy = 0; // Hàng đầu tiên
        gbc.weightx = 0.15; // Chiếm 1/4 không gian
        gbc.insets = new Insets(5, 0, 5, 0); // Cách dưới 20px
        gbc.fill = GridBagConstraints.HORIZONTAL; // Giãn theo chiều ngang
        timePanel.add(jDateFromLabel, gbc);

        // Thiết lập cho JDateChooser "Từ"
        gbc.gridx = 1; // Cột thứ hai
        gbc.weightx = 0.85; // Chiếm 3/4 không gian
        gbc.insets = new Insets(5, 0, 5, 0); // Cách dưới 20px
        timePanel.add(jDateChooserFromProduct, gbc);

        // Thiết lập cho JLabel "Đến"
        gbc.gridx = 0; // Cột đầu tiên
        gbc.gridy = 1; // Hàng thứ hai
        gbc.weightx = 0.15; // Chiếm 1/4 không gian
        timePanel.add(jDateToLabel, gbc);

        // Thiết lập cho JDateChooser "Đến"
        gbc.gridx = 1; // Cột thứ hai
        gbc.weightx = 0.85; // Chiếm 3/4 không gian
        timePanel.add(jDateChooserToProduct, gbc);
//        northPanel.add(timePanel, BorderLayout.CENTER);

        //nửa phải phía bắc
        JPanel soLuongNhapXuatPanel = new JPanel(new GridBagLayout());
        soLuongNhapXuatPanel.setBackground(new Color(231, 233, 238));

        JLabel SluongNhapLabel = new JLabel("Số lượng nhập: ");
        SluongNhapLabel.setFont(new Font(null, Font.BOLD, 16));

        JLabel SluongXuatLabel = new JLabel("Số lượng xuất: ");
        SluongXuatLabel.setFont(new Font(null, Font.BOLD, 16));

        JLabel TienNhapLabel = new JLabel("Tổng tiền nhập: ");
        TienNhapLabel.setFont(new Font(null, Font.BOLD, 16));

        JLabel TienXuatLabel = new JLabel("Tổng tiền xuất: ");
        TienXuatLabel.setFont(new Font(null, Font.BOLD, 16));

        numberImportLabel = new JLabel("0");
        numberImportLabel.setFont(new Font(null, Font.BOLD, 16));

        numberExportLabel = new JLabel("0");
        numberExportLabel.setFont(new Font(null, Font.BOLD, 16));

        moneyImportLabel = new JLabel("0đ");
        moneyImportLabel.setFont(new Font(null, Font.BOLD, 20));
        moneyImportLabel.setForeground(Color.RED);

        moneyExportLabel = new JLabel("0đ");
        moneyExportLabel.setFont(new Font(null, Font.BOLD, 20));
        moneyExportLabel.setForeground(Color.RED);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0; // Cột đầu tiên
        gbc2.gridy = 0; // Hàng đầu tiên
        gbc2.weightx = 0.2; // Chiếm 1/4 không gian
        gbc2.fill = GridBagConstraints.HORIZONTAL; // Giãn theo chiều ngang
        soLuongNhapXuatPanel.add(SluongNhapLabel, gbc2);

        gbc2.gridx = 1; // Cột thứ hai
        gbc2.weightx = 0.3; // Chiếm 3/4 không gian
        gbc2.insets = new Insets(5, 0, 5, 0); // Cách dưới 20px
        soLuongNhapXuatPanel.add(numberImportLabel, gbc2);

        gbc2.gridx = 2;
        gbc2.weightx = 0.2;
        soLuongNhapXuatPanel.add(TienNhapLabel, gbc2);

        gbc2.gridx = 3;
        gbc2.weightx = 0.3;
        soLuongNhapXuatPanel.add(moneyImportLabel, gbc2);

        gbc2.gridy = 1; // Hàng thứ hai
        gbc2.gridx = 0;
        gbc2.weightx = 0.2;
        soLuongNhapXuatPanel.add(SluongXuatLabel, gbc2);

        gbc2.gridx = 1;
        gbc2.weightx = 0.3;
        soLuongNhapXuatPanel.add(numberExportLabel, gbc2);

        gbc2.gridx = 2;
        gbc2.weightx = 0.2;
        soLuongNhapXuatPanel.add(TienXuatLabel, gbc2);

        gbc2.gridx = 3;
        gbc2.weightx = 0.3;
        soLuongNhapXuatPanel.add(moneyExportLabel, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0; // Cột đầu tiên
        gbc3.gridy = 0; // Hàng đầu tiên
        gbc3.weightx = 0.2; // Chiếm 1/4 không gian
        gbc3.fill = GridBagConstraints.HORIZONTAL; // Giãn theo chiều ngang
        northPanel.add(searchproduct, gbc3);

        // Thiết lập cho JDateChooser "Từ"
        gbc3.gridx = 1; // Cột thứ hai
        gbc3.weightx = 0.3; // Chiếm 3/4 không gian
        northPanel.add(timePanel, gbc3);

        // Thiết lập cho JLabel "Đến"
        gbc3.gridx = 2; // Cột đầu tiên
        gbc3.weightx = 0.5; // Chiếm 1/4 không gian
        northPanel.add(soLuongNhapXuatPanel, gbc3);

        southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(231, 233, 238));
        southPanel.setPreferredSize(new Dimension(100, 550));
        productPanel.add(northPanel, BorderLayout.NORTH);
        productPanel.add(southPanel, BorderLayout.CENTER);

        // Lắng nghe sự kiện
        productComboBox.addActionListener(e -> statisticController.checkConditions(productComboBox, jDateChooserFromProduct, jDateChooserToProduct));
        jDateChooserFromProduct.addPropertyChangeListener("date", evt -> statisticController.checkConditions(productComboBox, jDateChooserFromProduct, jDateChooserToProduct));
        jDateChooserToProduct.addPropertyChangeListener("date", evt -> statisticController.checkConditions(productComboBox, jDateChooserFromProduct, jDateChooserToProduct));
        productPanel.setVisible(true);

        /////////////////////////////////Màn của Phiếu Nhập/////////////////////////////////////////
        JPanel invoiceImportPanel = new JPanel(new  BorderLayout());

        JPanel northPanelImportInvoice = new JPanel(new GridBagLayout());
        JPanel headPanel = new JPanel();
        headPanel.setBackground(Color.RED);

        JPanel functionPanel = new JPanel();
        functionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Chức năng", TitledBorder.LEFT, TitledBorder.TOP));

        JButton viewButton = new JButton("Xem chi tiết");
        viewButton.setPreferredSize(new Dimension(120, 24));
        viewButton.setFont(new FontUIResource("Segoe UI", Font.PLAIN, 12));
        viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.setFocusable(false);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.viewButtonActionPerformed(e);
            }
        });
        functionPanel.add(viewButton);
        
        refreshImportButton = new JButton("Làm mới");
        ImageIcon originalIcon = new ImageIcon("src\\hau\\java\\swing\\qlkmt\\image\\refresh-button.png");

        // Thay đổi kích thước icon
        Image resizedImage = originalIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        refreshImportButton = new JButton(resizedIcon);
        refreshImportButton.setBackground(null);

        // Thay đổi thêm kích thước button (tuỳ chọn)
        refreshImportButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        refreshImportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        functionPanel.add(refreshImportButton);
        headPanel.add(functionPanel);
        refreshImportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.refreshButtonPNActionPerformed(e);
            }
        });

        //
        JPanel searchDependonPanel = new JPanel();
       
        searchDependonPanel.setBackground(Color.WHITE);
        invoiceImportPanel.add(searchDependonPanel);

        JPanel dependDay = new JPanel();
       
        dependDay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Lọc theo ngày", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel jDateFromLabel1 = new JLabel("Từ");
        dependDay.add(jDateFromLabel1);
        jDateChooserFromImport = new JDateChooser();
        dependDay.add(jDateChooserFromImport);
        jDateChooserFromImport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                statisticController.jDateChooserFromPNPropertyChane(evt);
            }
        });

        jDateChooserFromImport.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.jDateChooserFromPNKeyReleased(evt);
            }
        });

        JLabel jDateToLabel1 = new JLabel("Đến");
        dependDay.add(jDateToLabel1);
        jDateChooserToImport = new JDateChooser();
        dependDay.add(jDateChooserToImport);
        jDateChooserToImport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                statisticController.jDateChooserToPNPropertyChane(evt);
            }
        });
        jDateChooserToImport.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.jDateChooserToPNKeyReleased(evt);
            }
        });

        JPanel dependPrice = new JPanel(new GridBagLayout());
        dependPrice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Lọc theo giá", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel dependPricetoLabel = new JLabel("Từ");
        priceFromImportField = new JTextField(20);

        priceFromImportField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.priceFromImportFieldActionPerformed(e);
            }
        });
        priceFromImportField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.priceFromImportFieldKeyReleased(evt);
            }
        });
        JLabel denpenPricefromLabel = new JLabel("Đến");
        priceToImportField = new JTextField(20);
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.gridx = 0;
        gbc5.gridy = 0;
        gbc5.weightx = 0.15;
        dependPrice.add(dependPricetoLabel, gbc5);
        gbc5.gridx = 1;
        gbc5.gridy = 0;
        gbc5.weightx = 0.35;
        dependPrice.add(priceFromImportField, gbc5);
        gbc5.gridx = 2;
        gbc5.gridy = 0;
        gbc5.weightx = 0.15;
        dependPrice.add(denpenPricefromLabel, gbc5);
        gbc5.gridx = 3;
        gbc5.gridy = 0;
        gbc5.weightx = 0.35;
        dependPrice.add(priceToImportField, gbc5);
        
        priceToImportField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.priceToImportFieldActionPerformed(e);
            }
        });
        priceToImportField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.priceToImportFieldKeyReleased(evt);
            }
        });
        
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.gridx = 0;
        gbc4.gridy = 0;
        gbc4.weightx = 0.3;
        northPanelImportInvoice.add(functionPanel, gbc4);
        gbc4.gridx = 1;
        gbc4.gridy = 0;
        gbc4.weightx = 0.3;
        northPanelImportInvoice.add(dependDay, gbc4);
        gbc4.gridx = 2;
        gbc4.gridy = 0;
        gbc4.weightx = 0.4;
        northPanelImportInvoice.add(dependPrice, gbc4);

        //Table invoice
        JPanel tableInvoicePanel = new JPanel(new BorderLayout());
        tableInvoicePanel.setPreferredSize(new Dimension(1100, 550));
        importTable = new JTable();
        importTable.setShowGrid(true);
        importTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));
        jScrollPaneImport = new JScrollPane(importTable);
        jScrollPaneImport.setViewportView(importTable);
        tableInvoicePanel.add(jScrollPaneImport, BorderLayout.CENTER);
        invoiceImportPanel.add(northPanelImportInvoice, BorderLayout.NORTH);
        invoiceImportPanel.add(tableInvoicePanel , BorderLayout.CENTER);

        //
        JPanel bottomInvoicePanel = new JPanel();
        bottomInvoicePanel.setPreferredSize(new Dimension(1180, 75));
        bottomInvoicePanel.setBackground(Color.WHITE);
        invoiceImportPanel.add(bottomInvoicePanel, BorderLayout.SOUTH);

        JPanel leftbottomPanel = new JPanel();
        leftbottomPanel.setBackground(Color.WHITE);
        bottomInvoicePanel.add(leftbottomPanel, BorderLayout.WEST);

        JPanel rightbottomPanel = new JPanel();
        rightbottomPanel.setBackground(Color.WHITE);
        bottomInvoicePanel.add(rightbottomPanel, BorderLayout.EAST);

        JLabel lblTongPhieu = new JLabel("TỔNG PHIẾU: ");
        leftbottomPanel.add(lblTongPhieu, BorderLayout.WEST);
        lblTongPhieu.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTongPhieu.setForeground(Color.BLACK);

        invoiceNumberImportLabel = new JLabel("0");
        invoiceNumberImportLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        invoiceNumberImportLabel.setForeground(Color.RED);
        leftbottomPanel.add(invoiceNumberImportLabel);

        JLabel lblTongTien = new JLabel("TỔNG TIỀN: ");
        rightbottomPanel.add(lblTongTien, BorderLayout.EAST);
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTongTien.setForeground(Color.BLACK);

        moneyNumberImportLabel = new JLabel("0");
        moneyNumberImportLabel.setFont(new Font("Arial", Font.BOLD, 24));
        moneyNumberImportLabel.setForeground(Color.RED);
        rightbottomPanel.add(moneyNumberImportLabel);

        //////////////////////////////////////Màn của Phiếu Xuất///////////////////////////////////////////////////
        JPanel invoiceExportPanel = new JPanel(new BorderLayout());
        

        JPanel northPanelExportInvoice = new  JPanel(new GridBagLayout());
        JPanel headPanel1 = new JPanel();

        JPanel functionPanel1 = new JPanel();
        functionPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Chức năng", TitledBorder.LEFT, TitledBorder.TOP));

        JButton viewButton1 = new JButton("Xem chi tiết");
        viewButton1.setPreferredSize(new Dimension(120, 24));
        viewButton1.setFont(new FontUIResource("Segoe UI", Font.PLAIN, 12));
        viewButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.setFocusable(false);
        viewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.viewButton1ActionPerformed(e);
            }
        });
        functionPanel1.add(viewButton1);
        headPanel1.add(functionPanel1);

        refreshExportButton = new JButton("Làm mới");
        refreshExportButton = new JButton(resizedIcon);
        refreshExportButton.setBackground(null);
        refreshExportButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        refreshExportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        functionPanel1.add(refreshExportButton);
        headPanel1.add(functionPanel1);
        refreshExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.refreshExportButtonActionPerformed(e);
            }
        });

        //
        JPanel searchDependonPanel1 = new JPanel();
        searchDependonPanel1.setBackground(Color.WHITE);

        JPanel dependDay1 = new JPanel();
        dependDay1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Lọc theo ngày", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel jDateFromLabel2 = new JLabel("Từ");
        dependDay1.add(jDateFromLabel2);
        jDateChooserFromExport = new JDateChooser();
        dependDay1.add(jDateChooserFromExport);
        jDateChooserFromExport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                statisticController.jDateChooserFromExportPropertyChane(evt);
            }
        });

        jDateChooserFromExport.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.jDateChooserFromExportKeyReleased(evt);
            }
        });

        JLabel jDateToLabel2 = new JLabel("Đến");
        dependDay1.add(jDateToLabel2);
        jDateChooserToExport = new JDateChooser();
        dependDay1.add(jDateChooserToExport);
        jDateChooserToExport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                statisticController.jDateChooserToExportPropertyChane(evt);
            }
        });
        jDateChooserToExport.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.jDateChooserToExportKeyReleased(evt);
            }
        });

        JPanel dependPrice1 = new JPanel(new  GridBagLayout());
        dependPrice1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Lọc theo giá", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel dependPricetoLabel1 = new JLabel("Từ");
        priceFromExportField = new JTextField(20);

        priceFromExportField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.priceFromExportFieldActionPerformed(e);
            }
        });
        priceFromExportField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.priceFromExportFieldKeyReleased(evt);
            }
        });

        JLabel denpenPricefromLabel1 = new JLabel("Đến");

        priceToExportField = new JTextField(20);
        
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.fill = GridBagConstraints.HORIZONTAL;
        gbc7.gridx = 0;
        gbc7.gridy = 0;
        gbc7.weightx = 0.15;
        dependPrice1.add(dependPricetoLabel1, gbc7);
        gbc7.gridx = 1;
        gbc7.gridy = 0;
        gbc7.weightx = 0.35;
        dependPrice1.add(priceFromExportField, gbc7);
        gbc7.gridx = 2;
        gbc7.gridy = 0;
        gbc7.weightx = 0.15;
        dependPrice1.add(denpenPricefromLabel1, gbc7);
        gbc7.gridx = 3;
        gbc7.gridy = 0;
        gbc7.weightx = 0.35;
        dependPrice1.add(priceToExportField, gbc7);
        priceToExportField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticController.priceToExportFieldActionPerformed(e);
            }
        });
        priceToExportField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                statisticController.priceToExportFieldKeyReleased(evt);
            }
        });
        
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.fill = GridBagConstraints.HORIZONTAL;
        gbc6.gridx = 0;
        gbc6.gridy = 0;
        gbc6.weightx = 0.3;
        northPanelExportInvoice.add(functionPanel1, gbc6);
        gbc6.gridx = 1;
        gbc6.gridy = 0;
        gbc6.weightx = 0.3;
        northPanelExportInvoice.add(dependDay1, gbc6);
        gbc6.gridx = 2;
        gbc6.gridy = 0;
        gbc6.weightx = 0.4;
        northPanelExportInvoice.add(dependPrice1, gbc6);

        //Table invoice1
        JPanel tableInvoicPanel1 = new JPanel(new BorderLayout());
        tableInvoicPanel1.setPreferredSize(new Dimension(1100, 550));
        exportTable = new JTable();
        exportTable.setShowGrid(true);
        exportTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null}
                },
                new String[]{
                    "STT", "Mã phiếu xuất", "Chi nhánh", "Thời gian tạo", "Tổng tiền"
                }
        ));
        jScrollPaneExport = new JScrollPane(exportTable);
        jScrollPaneExport.setViewportView(exportTable);
        tableInvoicPanel1.add(jScrollPaneExport, BorderLayout.CENTER);
        invoiceExportPanel.add(northPanelExportInvoice, BorderLayout.NORTH);
        invoiceExportPanel.add(tableInvoicPanel1, BorderLayout.CENTER);

        //
        JPanel bottomInvoicePanel1 = new JPanel();
        bottomInvoicePanel1.setPreferredSize(new Dimension(1180, 75));
        bottomInvoicePanel1.setBackground(Color.WHITE);
        invoiceExportPanel.add(bottomInvoicePanel1, BorderLayout.SOUTH);

        JPanel leftbottomPanel1 = new JPanel();
        leftbottomPanel1.setBackground(Color.WHITE);
        bottomInvoicePanel1.add(leftbottomPanel1, BorderLayout.SOUTH);

        JPanel rightbottomPanel1 = new JPanel();
        rightbottomPanel1.setBackground(Color.WHITE);
        bottomInvoicePanel1.add(rightbottomPanel1, BorderLayout.SOUTH);

        JLabel lblTongPhieu1 = new JLabel("TỔNG PHIẾU: ");
        leftbottomPanel1.add(lblTongPhieu1, BorderLayout.WEST);
        lblTongPhieu1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTongPhieu1.setForeground(Color.BLACK);

        invoiceNumberExportLabel = new JLabel("0");
        invoiceNumberExportLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        invoiceNumberExportLabel.setForeground(Color.RED);
        leftbottomPanel1.add(invoiceNumberExportLabel, BorderLayout.WEST);

        JLabel lblTongTien1 = new JLabel("TỔNG TIỀN: ");
        rightbottomPanel1.add(lblTongTien1, BorderLayout.EAST);
        lblTongTien1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTongTien1.setForeground(Color.BLACK);

        moneyNumberExportLabel = new JLabel("0");
        moneyNumberExportLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        moneyNumberExportLabel.setForeground(Color.RED);
        rightbottomPanel1.add(moneyNumberExportLabel, BorderLayout.EAST);

        cardPanel.add(productPanel, "ProductPanel");
        cardPanel.add(invoiceImportPanel, "InvoiceImportPanel");
        cardPanel.add(invoiceExportPanel, "InvoiceExportPanel");

        producButton.addActionListener(e -> {
            CardLayout c1 = (CardLayout) cardPanel.getLayout();
            c1.show(cardPanel, "ProductPanel");
        });

        producButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                producButton.setBackground(new Color(20,49,100));
                invoiceImportButton.setBackground(new Color(94,125,178));
                invoiceExportButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                producButton.setBackground(new Color(20,49,100));
                invoiceImportButton.setBackground(new Color(94,125,178));
                invoiceExportButton.setBackground(new Color(94,125,178));
            }
        });

        ///////////////////////
        invoiceImportButton.addActionListener(e -> {
            CardLayout c1 = (CardLayout) cardPanel.getLayout();
            c1.show(cardPanel, "InvoiceImportPanel");
        });
        invoiceImportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                producButton.setBackground(new Color(94,125,178));
                invoiceImportButton.setBackground(new Color(20,49,100));
                invoiceExportButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                producButton.setBackground(new Color(94,125,178));
                invoiceImportButton.setBackground(new Color(20,49,100));
                invoiceExportButton.setBackground(new Color(94,125,178));
            }
        });

        //////////////////////////
        invoiceExportButton.addActionListener(e -> {
            CardLayout c1 = (CardLayout) cardPanel.getLayout();
            c1.show(cardPanel, "InvoiceExportPanel");
        });
        invoiceExportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                producButton.setBackground(new Color(94,125,178));
                invoiceExportButton.setBackground(new Color(20,49,100));
                invoiceImportButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                producButton.setBackground(new Color(94,125,178));
                invoiceExportButton.setBackground(new Color(20,49,100));
                invoiceImportButton.setBackground(new Color(94,125,178));
            }
        });

        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(cardPanel);
        pack();
    }

    ////////////////////////////Biểu đồ//////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    public JDateChooser getjDateChooserFromProduct() {
        return jDateChooserFromProduct;
    }

    public JDateChooser getjDateChooserToProduct() {
        return jDateChooserToProduct;
    }

    public JComboBox getproductComboBox() {
        return productComboBox;
    }

    public JLabel getnumberImportLabel() {
        return numberImportLabel;
    }

    public JLabel getnumberExportLabel() {
        return numberExportLabel;
    }

    public JLabel getmoneyImportLabel() {
        return moneyImportLabel;
    }

    public JLabel getmoneyExportLabel() {
        return moneyExportLabel;
    }
    
    public static JPanel getsouthPanel() {
        return southPanel;
    }

    ///////////////////////////////Phiếu nhập////////////////////////////
    public final void initTablePN() {
        tableImportModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Mã phiếu nhập", "Thời gian tạo", "Tổng tiền"};
        tableImportModel.setColumnIdentifiers(headerTbl);
        importTable.setModel(tableImportModel);
        importTable.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public JTable getImportTable() {
        return importTable;
    }

    public JTextField getpriceFromImportField() {
        return priceFromImportField;
    }

    public JTextField getpriceToImportField() {
        return priceToImportField;
    }

    public JLabel getinvoiceNumberImportLabel() {
        return invoiceNumberImportLabel;
    }

    public JLabel getmoneyNumberImportLabel() {
        return moneyNumberImportLabel;
    }

    public JDateChooser getjDateChooserFromImport() {
        return jDateChooserFromImport;
    }

    public JDateChooser getjDateChooserToImport() {
        return jDateChooserToImport;
    }

    public DefaultTableModel gettableImportModel() {
        return tableImportModel;
    }

    ////////////// Phiếu Xuất////////////////////////////
    public JTable getExportTable() {
        return exportTable;
    }

    public JTextField getpriceFromExportField() {
        return priceFromExportField;
    }

    public JTextField getpriceToExportField() {
        return priceToExportField;
    }

    public JLabel getinvoiceNumberExporttLabel() {
        return invoiceNumberExportLabel;
    }

    public JLabel getmoneyNumberExportLabel() {
        return moneyNumberExportLabel;
    }

    public JDateChooser getjDateChooserFromExport() {
        return jDateChooserFromExport;
    }

    public JDateChooser getjDateChooserToExport() {
        return jDateChooserToExport;
    }

    public DefaultTableModel gettableExportModel() {
        return (DefaultTableModel) exportTable.getModel();
    }

    public void updateData() {
        statisticController.searchAllFilterImport();
        statisticController.searchAllFilterExport();
        statisticController.loadDataTableImport();
        statisticController.loadDataTableExport();
        allproduct = ComputerDao.getInstance().selectAllExist();
        statisticController.loadChart();
    }
}
