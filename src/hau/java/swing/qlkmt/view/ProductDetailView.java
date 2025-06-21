/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.controller.ProductController;
import hau.java.swing.qlkmt.model.IMac;
import hau.java.swing.qlkmt.model.Laptop;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProductDetailView extends JDialog {

    public ProductView ownerSP;
    private JTextField idUpdateField, nameField, priceField, cpuField, ramField, romField, sizeLaptopField,
            sizeImacField, pinField, powerField, colorField;
    public JComboBox productTypeComboBox;
    private JLabel imageLabel;
    DecimalFormat formatter = new DecimalFormat("###,###,###"); //định dạng lại các số thành chuỗi kí tự (theo ý mình)
    private JPanel typePanel, mainPanel, imacPanel, laptopPanel;
    ProductController productController = new ProductController(this);

    public ProductDetailView(JInternalFrame parrent, JFrame ownerSP, boolean modal) {
        super(ownerSP, modal);
        this.ownerSP = (ProductView) parrent;
        this.setSize(850, 400);
        init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        if (this.ownerSP.productController.checkLap()) {
            Laptop laptop = this.ownerSP.productController.getDetailLaptop();
            idUpdateField.setText(laptop.getProductId());
            nameField.setText(laptop.getProductName());
            priceField.setText(formatter.format(laptop.getPrice()) + "đ");
            cpuField.setText(laptop.getCpu());
            ramField.setText(laptop.getRam());
            romField.setText(laptop.getRom());
            productTypeComboBox.setSelectedIndex(0);
            sizeLaptopField.setText(Double.toString(laptop.getMonitorSize()));
            pinField.setText(laptop.getPin());
            byte[] img = laptop.getImage();
            colorField.setText(laptop.getColor());
            if (img != null && img.length > 0) { // Kiểm tra ảnh không null và không rỗng
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(220, 230, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                imageLabel.setIcon(new ImageIcon("")); // Hiển thị ảnh mặc định nếu không có ảnh
            }
        } else if (this.ownerSP.productController.checkIMac()) {
            IMac iMac = this.ownerSP.productController.getDetailIMac();
            idUpdateField.setText(iMac.getProductId());
            nameField.setText(iMac.getProductName());
            priceField.setText(formatter.format(iMac.getPrice()) + "đ");
            cpuField.setText(iMac.getCpu());
            ramField.setText(iMac.getRam());
            romField.setText(iMac.getRom());
            productTypeComboBox.setSelectedIndex(1);
            sizeImacField.setText(Double.toString(iMac.getMonitorSize()));
            powerField.setText(iMac.getPower());
            byte[] img = (iMac.getImage());
            colorField.setText(iMac.getColor());
            if (img != null && img.length > 0) { // Kiểm tra ảnh không null và không rỗng
                //ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lblPic.getWidth(), lblPic.getHeight(), Image.SCALE_SMOOTH));
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(220, 230, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                imageLabel.setIcon(new ImageIcon(""));  // Hiển thị ảnh mặc định nếu không có ảnh
            }
        }
    }

//    private ProductDetailView(JFrame jFrame, boolean b) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    private void init() {
        JPanel panelHeader = new JPanel(new BorderLayout(20, 10));
        panelHeader.setBackground(new Color(94,125,178));
        panelHeader.setPreferredSize(new Dimension(850, 40));
        JLabel title = new JLabel("THÔNG TIN SẢN PHẨM");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        panelHeader.add(title);
        
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        typePanel = new JPanel(new CardLayout());

        imacPanel = new JPanel();
        imacPanel.setLayout(new GridBagLayout());

        laptopPanel = new JPanel();
        laptopPanel.setLayout(new GridBagLayout());

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cột 2 hình ảnh
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(220, 230));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(imageLabel, BorderLayout.CENTER);

        ////////////////////Cột 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel productTypeJLabel = new JLabel("Loại sản phẩm");
        productTypeJLabel.setEnabled(false);
        productTypeJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(productTypeJLabel, gbc);

        gbc.gridx = 1;
        productTypeComboBox = new JComboBox<>();
        productTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Laptop", "IMac"}));
        productTypeComboBox.setEditable(false);
        productTypeComboBox.setEnabled(false);
        mainPanel.add(productTypeComboBox, gbc);
        productTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                productController.productTypeDetailComboBoxItemStateChanged(evt);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel productIdLabel = new JLabel("Mã sản phẩm");
        productIdLabel.setEnabled(false);
        productIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(productIdLabel, gbc);

        gbc.gridx = 3;
        idUpdateField = new JTextField(15);
        idUpdateField.setEditable(false);
        idUpdateField.setEnabled(false);
        mainPanel.add(idUpdateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel productNameLabel = new JLabel("Tên sản phẩm");
        productNameLabel.setEnabled(false);
        productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(productNameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setEditable(false);
        nameField.setEnabled(false);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel priceLabel = new JLabel("Đơn giá");
        priceLabel.setEnabled(false);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(priceLabel, gbc);

        gbc.gridx = 3;
        priceField = new JTextField(15);
        priceField.setEditable(false);
        priceField.setEnabled(false);
        mainPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel cpuLabel = new JLabel("CPU");
        cpuLabel.setEnabled(false);
        cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(cpuLabel, gbc);

        gbc.gridx = 1;
        cpuField = new JTextField(15);
        cpuField.setEditable(false);
        cpuField.setEnabled(false);
        mainPanel.add(cpuField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel ramLabel = new JLabel("RAM");
        ramLabel.setEnabled(false);
        ramLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(ramLabel, gbc);

        gbc.gridx = 3;
        ramField = new JTextField(15);
        ramField.setEditable(false);
        ramField.setEnabled(false);
        mainPanel.add(ramField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel romLabel = new JLabel("ROM");
        romLabel.setEnabled(false);
        romLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(romLabel, gbc);

        gbc.gridx = 1;
        romField = new JTextField(15);
        romField.setEditable(false);
        romField.setEnabled(false);
        mainPanel.add(romField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        JLabel colorLabel = new JLabel("Màu Sắc");
        colorLabel.setEnabled(false);
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(colorLabel, gbc);

        gbc.gridx = 3;
        colorField = new JTextField();
        colorField.setEditable(false);
        colorField.setEnabled(false);
        mainPanel.add(colorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel sizeLaptopLabel = new JLabel("Kích thước màn  ");
        sizeLaptopLabel.setEnabled(false);
        sizeLaptopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        laptopPanel.add(sizeLaptopLabel);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        sizeLaptopField = new JTextField(15);
        sizeLaptopField.setEditable(false);
        sizeLaptopField.setEnabled(false);
        laptopPanel.add(sizeLaptopField);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel sizeImacLabel = new JLabel("Kích thước màn  ");
        sizeImacLabel.setEnabled(false);
        sizeImacLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imacPanel.add(sizeImacLabel);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        sizeImacField = new JTextField(15);
        sizeImacField.setEditable(false);
        sizeImacField.setEnabled(false);
        imacPanel.add(sizeImacField);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel pinLabel = new JLabel("  Dung lượng PIN  ");
        gbc.anchor = GridBagConstraints.CENTER;
        pinLabel.setEnabled(false);
        pinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        laptopPanel.add(pinLabel);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pinField = new JTextField(15);
        pinField.setEditable(false);
        pinField.setEnabled(false);
        laptopPanel.add(pinField);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel powerLabel = new JLabel("  Công suất nguồn  ");
        powerLabel.setEnabled(false);
        powerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imacPanel.add(powerLabel);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        powerField = new JTextField(15);
        powerField.setEditable(false);
        powerField.setEnabled(false);
        imacPanel.add(powerField);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;

        typePanel.add(laptopPanel, "Laptop");
        typePanel.add(imacPanel, "IMac");

        mainPanel.add(typePanel, gbc);

        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        pack();
    }

    public JComboBox getProductTypeDetailComboBox() {
        return productTypeComboBox;
    }
    
    public JPanel getTypeDetailPanel() {
        return typePanel;
    }
}
