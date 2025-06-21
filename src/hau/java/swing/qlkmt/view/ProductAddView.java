/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import hau.java.swing.qlkmt.controller.ProductController;

public class ProductAddView extends JDialog {

    public ProductView ownerSP;
    public JTextField idField, nameField, priceField, cpuField, ramField, romField, sizeLaptopField,
            sizeImacField, pinField, powerField, colorField;
    public JComboBox productTypeComboBox;
    public JPanel typePanel, mainPanel, imacPanel, laptopPanel;
    public JButton imageButton,saveButton,cancelButton;
    public JLabel imageLabel;
    ProductController productController = new ProductController(this);

    public ProductAddView(JInternalFrame parrent, JFrame ownerSP, boolean modal) {
        super(ownerSP, modal);
        this.ownerSP = (ProductView) parrent;
        this.setSize(850, 400);
        init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        idField.setText(productController.createIdLaptop());
    }

    private void init() {
        JPanel panelHeader = new JPanel(new BorderLayout(20, 10));
        panelHeader.setBackground(new Color(94,125,178));
        panelHeader.setPreferredSize(new Dimension(850, 40));
        JLabel title = new JLabel("THÊM SẢN PHẨM MỚI");
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

        JPanel rightPanel = new JPanel(new BorderLayout(10, 20));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cột 2
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(220, 230));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(imageLabel, BorderLayout.CENTER);

        imageButton = new JButton("Thêm ảnh");
        rightPanel.add(imageButton, BorderLayout.SOUTH);
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productController.imageAddButtonActionPerformed(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductAddView.class.getName()).log(Level.SEVERE, null, ex);
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
        mainPanel.add(productTypeComboBox, gbc);
        productTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                productController.productTypeAddComboBoxItemStateChanged(evt);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel productIdLabel = new JLabel("Mã sản phẩm");
        productIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(productIdLabel, gbc);

        gbc.gridx = 3;
        idField = new JTextField(15);
        idField.setEditable(false);
        idField.setEnabled(false);
        mainPanel.add(idField, gbc);

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
                    productController.saveAddButtonActinPerfromed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ProductAddView.class.getName()).log(Level.SEVERE, null, ex);
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
                productController.cancelAddButtonActinPerfromed(e);
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

    public JTextField getIdAddField() {
        return idField;
    }

    public JTextField getNameAddField() {
        return nameField;
    }

    public JTextField getPriceAddField() {
        return priceField;
    }

    public JTextField getCpuAddField() {
        return cpuField;
    }

    public JTextField getRamAddField() {
        return ramField;
    }

    public JTextField getRomAddField() {
        return romField;
    }

    public JTextField getSizeLaptopAddField() {
        return sizeLaptopField;
    }

    public JTextField getSizeImacAddField() {
        return sizeImacField;
    }

    public JTextField getPinAddField() {
        return pinField;
    }

    public JTextField getPowerAddField() {
        return powerField;
    }

    public JTextField getColorAddField() {
        return colorField;
    }

    public JComboBox getProductTypeAddComboBox() {
        return productTypeComboBox;
    }

    public JLabel getImageAddLabel() {
        return imageLabel;
    }

    public JPanel getTypeAddPanel() {
        return typePanel;
    }
}
