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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ProductUpdateView extends JDialog {

    public ProductView ownerSP;
    public JTextField idUpdateField, nameField, priceField, cpuField, ramField, romField, sizeLaptopField,
            sizeImacField, pinField, powerField, colorField;
    public JComboBox productTypeComboBox;
    public JPanel typePanel, mainPanel, imacPanel, laptopPanel;
    public JButton imageButton, saveButton, cancelButton;
    public JLabel imageLabel;
    ProductController productController = new ProductController(this);
    
    String fileName = null;
    public byte[] image = null;
    public int quantity = 0;
    
    DecimalFormat formatter = new DecimalFormat("0");

    public ProductUpdateView(JInternalFrame parrent, JFrame ownerSP, boolean modal) {
        super(ownerSP, modal);
        this.ownerSP = (ProductView) parrent;
        this.setSize(850, 400);
        init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        if (this.ownerSP.productController.checkLap()) {
            Laptop laptopModel = this.ownerSP.productController.getDetailLaptop();
            idUpdateField.setText(laptopModel.getProductId());
            nameField.setText(laptopModel.getProductName());
            priceField.setText(formatter.format(laptopModel.getPrice()));
            cpuField.setText(laptopModel.getCpu());
            ramField.setText(laptopModel.getRam());
            romField.setText(laptopModel.getRom());
            productTypeComboBox.setSelectedIndex(0);
            sizeLaptopField.setText(Double.toString(laptopModel.getMonitorSize()));
            pinField.setText(laptopModel.getPin());
            quantity = laptopModel.getQuantity();
            byte[] img = laptopModel.getImage();
            colorField.setText(laptopModel.getColor());
            if (img != null && img.length > 0) { // Kiểm tra ảnh không null và không rỗng
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(220, 240, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                // Hiển thị ảnh mặc định nếu không có ảnh
                imageLabel.setIcon(new ImageIcon(""));
            }
            if (image == null) {
                image = laptopModel.getImage();
            }
        } else if (this.ownerSP.productController.checkIMac()) {
            IMac iMacModel = this.ownerSP.productController.getDetailIMac();
            idUpdateField.setText(iMacModel.getProductId());
            nameField.setText(iMacModel.getProductName());
            priceField.setText(formatter.format(iMacModel.getPrice()));
            cpuField.setText(iMacModel.getCpu());
            ramField.setText(iMacModel.getRam());
            romField.setText(iMacModel.getRom());
            productTypeComboBox.setSelectedIndex(1);
            sizeImacField.setText(Double.toString(iMacModel.getMonitorSize()));
            powerField.setText(iMacModel.getPower());
            quantity = iMacModel.getQuantity();
            colorField.setText(iMacModel.getColor());
            byte[] img = (iMacModel.getImage());
            if (img != null && img.length > 0) { // Kiểm tra ảnh không null và không rỗng
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(220, 240, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                // Hiển thị ảnh mặc định nếu không có ảnh
                imageLabel.setIcon(new ImageIcon(""));
            }
            if (image == null) {
                image = iMacModel.getImage();
            }
        }
    }
    
    private void init() {
        JPanel panelHeader = new JPanel(new BorderLayout(20, 10));
        panelHeader.setBackground(new Color(94,125,178));
        panelHeader.setPreferredSize(new Dimension(850, 40));
        JLabel title = new JLabel("SỬA SẢN PHẨM MỚI");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        panelHeader.add(title);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        typePanel = new JPanel(new CardLayout(0, 5));

        imacPanel = new JPanel();
        imacPanel.setLayout(new GridBagLayout());

        laptopPanel = new JPanel();
        laptopPanel.setLayout(new GridBagLayout());

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cột 2
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(220, 240));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(imageLabel, BorderLayout.CENTER);

        imageButton = new JButton("Sửa ảnh");
        rightPanel.add(imageButton, BorderLayout.SOUTH);
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //productController.imageUpdateButtonActionPerformed(e);
                    imageUpdateButtonActionPerformed(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductUpdateView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        ////////////////////Cột 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel productTypeJLabel = new JLabel("Loại sản phẩm");
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
                productController.productTypeUpdateComboBoxItemStateChanged(evt);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel productIdLabel = new JLabel("Mã sản phẩm");
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
        productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(productNameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel priceLabel = new JLabel("Đơn giá");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(priceLabel, gbc);

        gbc.gridx = 3;
        priceField = new JTextField(15);
        mainPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel cpuLabel = new JLabel("CPU");
        cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(cpuLabel, gbc);

        gbc.gridx = 1;
        cpuField = new JTextField(15);
        mainPanel.add(cpuField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel ramLabel = new JLabel("RAM");
        ramLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(ramLabel, gbc);

        gbc.gridx = 3;
        ramField = new JTextField(15);
        mainPanel.add(ramField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel romLabel = new JLabel("ROM");
        romLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(romLabel, gbc);

        gbc.gridx = 1;
        romField = new JTextField(15);
        mainPanel.add(romField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        JLabel colorLabel = new JLabel("Màu Sắc");
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(colorLabel, gbc);

        gbc.gridx = 3;
        colorField = new JTextField();
        mainPanel.add(colorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel sizeLaptopLabel = new JLabel("Kích thước màn  ");
        sizeLaptopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        laptopPanel.add(sizeLaptopLabel);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        sizeLaptopField = new JTextField(15);
        laptopPanel.add(sizeLaptopField);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel sizeImacLabel = new JLabel("Kích thước màn  ");
        sizeImacLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imacPanel.add(sizeImacLabel);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        sizeImacField = new JTextField(15);
        imacPanel.add(sizeImacField);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel pinLabel = new JLabel("  Dung lượng PIN  ");
        gbc.anchor = GridBagConstraints.CENTER;
        pinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        laptopPanel.add(pinLabel);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pinField = new JTextField(15);
        laptopPanel.add(pinField);
        mainPanel.add(laptopPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel powerLabel = new JLabel("  Công suất nguồn  ");
        powerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imacPanel.add(powerLabel);
        mainPanel.add(imacPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        powerField = new JTextField(15);
        imacPanel.add(powerField);
        mainPanel.add(imacPanel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(120, 30));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    productController.saveUpdateButtonActinPerfromed(evt);
                    //saveUpdateButtonActinPerfromed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ProductUpdateView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        cancelButton = new JButton("Hủy bỏ");
        cancelButton.setPreferredSize(new Dimension(120, 30));
        cancelButton.setBackground(null);
        cancelButton.setForeground(Color.gray);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productController.cancelUpdateButtonActinPerfromed(e);
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;

        typePanel.add(laptopPanel, "Laptop");
        typePanel.add(imacPanel, "IMac");

        mainPanel.add(buttonPanel, gbc);
        mainPanel.add(typePanel, gbc);

        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    /////////////////////////////////////////////////////
    
    public ProductView getownerSP() {
        return ownerSP;
    }
    
    public JTextField getIdUpdateField() {
        return idUpdateField;
    }

    public JTextField getNameUpdateField() {
        return nameField;
    }

    public JTextField getPriceUpdateField() {
        return priceField;
    }

    public JTextField getCpuUpdateField() {
        return cpuField;
    }

    public JTextField getRamUpdateField() {
        return ramField;
    }

    public JTextField getRomUpdateField() {
        return romField;
    }

    public JTextField getSizeLaptopUpdateField() {
        return sizeLaptopField;
    }

    public JTextField getSizeImacUpdateField() {
        return sizeImacField;
    }

    public JTextField getPinUpdateField() {
        return pinField;
    }

    public JTextField getPowerUpdateField() {
        return powerField;
    }

    public JTextField getColorUpdateField() {
        return colorField;
    }

    public JComboBox getProductTypeUpdateComboBox() {
        return productTypeComboBox;
    }

    public JLabel getImageUpdateLabel() {
        return imageLabel;
    }

    public JPanel getTypeUpdatePanel() {
        return typePanel;
    }
    
    public byte[] getUpdateImage() {
        return image;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void imageUpdateButtonActionPerformed(ActionEvent e) throws SQLException {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        // Kiểm tra nếu người dùng nhấn "Cancel"
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile(); // Lấy file được chọn
            if (f != null) { // Kiểm tra nếu file không null
                fileName = f.getAbsolutePath();
                try {
                    BufferedImage bi = ImageIO.read(new File(fileName));
                    if (bi != null) { // Kiểm tra ảnh hợp lệ
                        Image images = bi.getScaledInstance(220, 230, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(images);
                        imageLabel.setIcon(icon);
                    } else {
                        System.out.println("Hình ảnh không hợp lệ!");
                    }
                } catch (IOException ev) {
                }
            }
        }
        try {
            File imageFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readnum; (readnum = fileInputStream.read(buf)) != -1;) {
                byteArrayOutputStream.write(buf, 0, readnum);
            }
            image = byteArrayOutputStream.toByteArray();
        } catch (IOException ev) {
            JOptionPane.showMessageDialog(null, ev);
        }
    }
}
