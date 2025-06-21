/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.IMacDao;
import hau.java.swing.qlkmt.dao.LaptopDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.model.IMac;
import hau.java.swing.qlkmt.model.Laptop;
import hau.java.swing.qlkmt.model.Computer;
import hau.java.swing.qlkmt.view.ProductAddView;
import hau.java.swing.qlkmt.view.HomeView;
import hau.java.swing.qlkmt.view.ImportView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import hau.java.swing.qlkmt.view.ProductView;
import hau.java.swing.qlkmt.view.ProductUpdateView;
import hau.java.swing.qlkmt.view.InventoryView;
import hau.java.swing.qlkmt.view.ExportView;
import hau.java.swing.qlkmt.view.ProductDetailView;
import hau.java.swing.qlkmt.view.ProductListView;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author thanh
 */
public class ProductController extends Controller{

    public InventoryView inventoryView;
    public ExportView exportView;
    public ImportView importView;
    public ProductListView productListView;

    //Trang sản phẩm
    public ProductView productView;
    public DefaultTableModel tableModel;
    public JTable productTable;
    public JComboBox searchComboBox;
    public JTextField searchField;
    DecimalFormat formatter = new DecimalFormat("###,###,###"); //(123456789->123,456,678)

    //Thêm sản phẩm && Sửa sản phẩm
    public ProductAddView productAddView;
    public ProductView ownerSP;
    public JTextField idField, idUpdateField, nameField, priceField, cpuField, ramField, romField, sizeLaptopField,
            sizeImacField, pinField, powerField, colorField;
    public JComboBox productTypeComboBox;
    public JPanel typePanel;
    public JLabel imageLabel;
    String fileName = null;
    byte[] image = null;
    int quantity = 0;
    
    //Sửa sản phẩm
    public ProductUpdateView productUpdateView;
    
    public ProductDetailView productDetailView;

    public ProductController(ImportView importView) {
        this.importView = importView;
    }

    public ProductController(ExportView exportView) {
        this.exportView = exportView;
    }

    public ProductController(InventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }
    
    public ProductController(ProductListView productListView) {
        this.productListView = productListView;
    }

    /////////////////////Trang sản phẩm/////////////////////////////////////////////////
    public ProductController(ProductView productView) {
        this.productView = productView;
    }

    public void addButtonActionPerformed(ActionEvent e) {
        ProductAddView add = new ProductAddView(productView, (JFrame) SwingUtilities.getWindowAncestor(productView), true);
        add.setVisible(true);
    }

    public void removeButtonActionPerformed(ActionEvent e) throws UnsupportedLookAndFeelException {
        productTable = productView.getProductTable();
        if (productTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm cần xoá");
        } else {
            removeComputer();
            HomeView homeView = HomeView.getInstance();
            homeView.reloadCenterPanel();
        }
    }

    public void refreshButtonActionPerformed(ActionEvent evt) {
        searchComboBox = productView.getSearchComboBox();
        searchField = productView.getSearchField();
        searchComboBox.setSelectedIndex(0);
        searchField.setText("");
        loadDataToTable();
    }

    public void viewButtonActionPerformed(ActionEvent e) {
        productTable = productView.getProductTable();
        if (productTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm!");
        } else {
            ProductDetailView view = new ProductDetailView(productView, (JFrame) SwingUtilities.getWindowAncestor(productView), true);
            view.setVisible(true);
        }
    }

    public void editButtonActionPerformed(ActionEvent e) {
        productTable = productView.getProductTable();
        if (productTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm cần sửa");
        } else {
            ProductUpdateView sua = new ProductUpdateView(productView, (JFrame) SwingUtilities.getWindowAncestor(productView), true);
            sua.setVisible(true);
        }
    }

    public void searchComboBoxPropertyChange(PropertyChangeEvent evt) {
        searchComboBox = productView.getSearchComboBox();
        searchField = productView.getSearchField();
        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void searchFieldKeyPressed(KeyEvent evt) {
        searchComboBox = productView.getSearchComboBox();
        searchField = productView.getSearchField();
        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void searchFieldKeyReleased(KeyEvent evt) {
        searchComboBox = productView.getSearchComboBox();
        searchField = productView.getSearchField();
        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void loadDataToTable() {
        try {
            tableModel = productView.getTableModel();
            ComputerDao computerDao = new ComputerDao();
            ArrayList<Computer> arrayList = computerDao.selectAll();
            tableModel.setRowCount(0);
            for (Computer i : arrayList) {
                if (i.getStatus()== 1) {
                    String type;
                    if (LaptopDao.getInstance().isLaptop(i.getProductId()) == true) {
                        type = "Laptop";
                    } else {
                        type = "IMac";
                    }
                    tableModel.addRow(new Object[]{i.getProductId(), i.getProductName(), 
                        formatter.format(i.getPrice()) + "đ", i.getCpu(), i.getRam(), i.getRom(), type});
                }
            }
        } catch (Exception e) {
        }
    }
    
    ///////////Phương thức chung/////////////////////////////////
    public ArrayList<Computer> searchOptionContent(String optional, String content) {
        ArrayList<Computer> result = new ArrayList<>();
        switch (optional) {
            case "Mã máy" ->
                result = searchProductId(content);
            case "Tên máy" ->
                result = searchProductName(content);
        }
        return result;
    }

    public void removeComputer() {
        productTable = productView.getProductTable();
        int luaChon = JOptionPane.showConfirmDialog(productView, "Bạn có muốn xóa sản phẩm này?", "Xóa sản phẩm", JOptionPane.YES_NO_OPTION);
        if (luaChon == JOptionPane.YES_OPTION) {
            Computer remove = getMayTinhSelect();
            ComputerDao.getInstance().deleteStatus(remove.getProductId());
        }
        loadDataToTable();
    }

    public Computer getMayTinhSelect() {
        productTable = productView.getProductTable();
        tableModel = productView.getTableModel();
        int i_row = productTable.getSelectedRow();
        Computer computer = ComputerDao.getInstance().selectById(tableModel.getValueAt(i_row, 0).toString());
        return computer;
    }

    public void loadDataTableSearch(ArrayList<Computer> result) {
        try {
            tableModel = productView.getTableModel();
            tableModel.setRowCount(0);
            for (Computer i : result) {
                String type;
                if (LaptopDao.getInstance().isLaptop(i.getProductId()) == true) {
                    type = "Laptop";
                } else {
                    type = "IMac";
                }
                tableModel.addRow(new Object[]{i.getProductId(), i.getProductName(), 
                    formatter.format(i.getPrice()) + "đ", i.getCpu(), i.getRam(), i.getRom(), type});

            }
        } catch (Exception e) {
        }
    }

    public ArrayList<Computer> searchProductId(String text) {
        ArrayList<Computer> result = new ArrayList<>();
        ArrayList<Computer> arrayList = ComputerDao.getInstance().selectAllExist();
        for (var computer : arrayList) {
            if (computer.getStatus()== 1) {
                if (computer.getProductId().toLowerCase().contains(text.toLowerCase())) {
                    result.add(computer);
                }
            }
        }
        return result;
    }

    public ArrayList<Computer> searchProductName(String text) {
        ArrayList<Computer> result = new ArrayList<>();
        ArrayList<Computer> arrayList = ComputerDao.getInstance().selectAllExist();
        for (var computer : arrayList) {
            if (computer.getStatus() == 1) {
                if (computer.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    result.add(computer);
                }
            }
        }
        return result;
    }

    public boolean checkLap() {
        return LaptopDao.getInstance().isLaptop(getMayTinhSelect().getProductId()) == true;
    }

    public Laptop getDetailLaptop() {
        Laptop laptop = LaptopDao.getInstance().selectById(getMayTinhSelect().getProductId());
        return laptop;
    }

    public boolean checkIMac() {
        return IMacDao.getInstance().isIMac(getMayTinhSelect().getProductId()) == true;
    }

    public IMac getDetailIMac() {
        IMac iMac = IMacDao.getInstance().selectById(getMayTinhSelect().getProductId());
        return iMac;
    }

    public String createIdImac() {
        ArrayList<Computer> arrayList = ComputerDao.getInstance().selectAll();
        ArrayList<Computer> imacAll = new ArrayList<>();
        for (Computer computer : arrayList) {
            if (computer.getProductId().contains("IM")) {
                imacAll.add(computer);
            }
        }
        int i = imacAll.size();
        String check = "check";
        while (check.length() != 0) {
            i++;
            for (Computer computer : imacAll) {
                if (computer.getProductId().equals("IM" + i)) {
                    check = "";
                }
            }
            if (check.length() == 0) {
                check = "check";
            } else {
                check = "";
            }
        }
        return "IM" + i;
    }

    public String createIdLaptop() {
        ArrayList<Computer> arrayList = ComputerDao.getInstance().selectAll();
        ArrayList<Computer> laptopAll = new ArrayList<>();
        for (Computer computer : arrayList) {
            if (computer.getProductId().contains("LP")) {
                laptopAll.add(computer);
            }
        }
        int i = laptopAll.size();
        String check = "check";
        while (check.length() != 0) {
            i++;
            for (Computer computer : laptopAll) {
                if (computer.getProductId().equals("LP" + i)) {
                    check = "";
                }
            }
            if (check.length() == 0) {
                check = "check";
            } else {
                check = "";
            }
        }
        return "LP" + i;
    }

    ////////////////////////////Thêm sản phẩm////////////////////////////////////
    public ProductController(ProductAddView productAddView) {
        this.productAddView = productAddView;
    }

    public void productTypeAddComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        productTypeComboBox = productAddView.getProductTypeAddComboBox();
        typePanel = productAddView.getTypeAddPanel();
        idField = productAddView.getIdAddField();
        if (productTypeComboBox.getSelectedItem().equals("Laptop")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.first(typePanel);
            idField.setText(createIdLaptop());
        }
        if (productTypeComboBox.getSelectedItem().equals("IMac")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.last(typePanel);
            idField.setText(createIdImac());
        }
    }

    public void saveAddButtonActinPerfromed(ActionEvent evt) throws UnsupportedLookAndFeelException {
        idField = productAddView.getIdAddField();
        nameField = productAddView.getNameAddField();
        priceField = productAddView.getPriceAddField();
        cpuField = productAddView.getCpuAddField();
        ramField = productAddView.getRamAddField();
        romField = productAddView.getRomAddField();
        sizeLaptopField = productAddView.getSizeLaptopAddField();
        sizeImacField = productAddView.getSizeImacAddField();
        pinField = productAddView.getPinAddField();
        powerField = productAddView.getPowerAddField();
        colorField = productAddView.getColorAddField();
        productTypeComboBox = productAddView.getProductTypeAddComboBox();

        String productId = idField.getText();
        String productName = nameField.getText();
        double price = 0;
        double monitorSize = 0;
        String cpu = cpuField.getText();
        String ram = ramField.getText();
        String rom = romField.getText();
        int status = 1;
        String color = colorField.getText();

        try {
            price = Double.parseDouble(priceField.getText());
            if (price <= 0) {
                JOptionPane.showMessageDialog(productAddView, "Hãy nhập đơn giá với số lớn hơn 0 ");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productAddView, "Nhập đơn giá ở dạng số!");
            return;
        }

        if (productTypeComboBox.getSelectedItem().equals("Laptop")) {
            try {
                monitorSize = Double.parseDouble(sizeLaptopField.getText());
                if (monitorSize <= 0) {
                    JOptionPane.showMessageDialog(productAddView, "Hãy nhập kích thước màn với số lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productAddView, "Nhập kích thước màn ở dạng số!");
                return;
            }
            String pin = pinField.getText();
            if (productId.equals("") || productName.equals("") || cpu.equals("")
                    || ram.equals("") || rom.equals("") || pin.equals("")) {
                JOptionPane.showMessageDialog(productAddView, "Vui lòng nhập đầy đủ thông tin !");
                return;
            } else {
                Laptop laptopModel = new Laptop(monitorSize, pin, productId, productName, 0, price, cpu, ram, rom, status, image, color);
                try {
                    LaptopDao.getInstance().insert(laptopModel);
                    productAddView.dispose();
                    JOptionPane.showMessageDialog(productAddView, "Thêm sản phẩm thành công");
                    HomeView homeView = HomeView.getInstance();
                    homeView.reloadCenterPanel();
                    productAddView.ownerSP.productController.loadDataToTable();
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(productAddView, "Thêm sản phẩm thất bại");
                    return;
                }
            }
        }

        if (productTypeComboBox.getSelectedItem().equals("IMac")) {
            try {
                monitorSize = Double.parseDouble(sizeImacField.getText());
                if (monitorSize <= 0) {
                    JOptionPane.showMessageDialog(productAddView, "Hãy nhập kích thước màn với số lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productAddView, "Nhập kích thước màn ở dạng số!");
                return;
            }
            String power = powerField.getText();
            if (productId.equals("") || productName.equals("") || cpu.equals("")
                    || ram.equals("") || rom.equals("") || power.equals("")) {
                JOptionPane.showMessageDialog(productAddView, "Vui lòng nhập đầy đủ thông tin !");
            } else {
                IMac iMacModel = new IMac(monitorSize, power, productId, productName, 0, price, cpu, ram, rom, status, image, color);
                IMacDao.getInstance().insert(iMacModel);
                productAddView.dispose();
                JOptionPane.showMessageDialog(productAddView, "Thêm sản phẩm thành công");
                HomeView homeView = HomeView.getInstance();
                homeView.reloadCenterPanel();
                productAddView.ownerSP.productController.loadDataToTable();
            }
        }
    }

    public void cancelAddButtonActinPerfromed(ActionEvent e) {
        productAddView.dispose();
    }

    // thêm ảnh
    public void imageAddButtonActionPerformed(ActionEvent e) throws SQLException {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        imageLabel = productAddView.getImageAddLabel();
        // Kiểm tra nếu người dùng nhấn "Cancel"
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile(); // Lấy file được chọn
            if (f != null) { // Kiểm tra nếu file không null
                fileName = f.getAbsolutePath();
                try {
                    BufferedImage bi = ImageIO.read(new File(fileName));
                    if (bi != null) { // Kiểm tra ảnh hợp lệ
                        Image images = bi.getScaledInstance(220, 235, Image.SCALE_SMOOTH);
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
    
    public ProductController(ProductUpdateView productUpdateView) {
        this.productUpdateView = productUpdateView;
    }

    /////////////////////////////////////////Sửa sản phẩm////////////////////////////

    public void productTypeUpdateComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        productTypeComboBox = productUpdateView.getProductTypeUpdateComboBox();
        typePanel = productUpdateView.getTypeUpdatePanel();
        if (productTypeComboBox.getSelectedItem().equals("Laptop")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.first(typePanel);
        }
        if (productTypeComboBox.getSelectedItem().equals("IMac")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.last(typePanel);
        }
    }
    
    public void cancelUpdateButtonActinPerfromed(ActionEvent e) {
        productUpdateView.dispose();
    }

    public void saveUpdateButtonActinPerfromed(ActionEvent evt) throws UnsupportedLookAndFeelException {
        idUpdateField = productUpdateView.getIdUpdateField();
        nameField = productUpdateView.getNameUpdateField();
        priceField = productUpdateView.getPriceUpdateField();
        cpuField = productUpdateView.getCpuUpdateField();
        ramField = productUpdateView.getRamUpdateField();
        romField = productUpdateView.getRomUpdateField();
        sizeLaptopField = productUpdateView.getSizeLaptopUpdateField();
        sizeImacField = productUpdateView.getSizeImacUpdateField();
        pinField = productUpdateView.getPinUpdateField();
        powerField = productUpdateView.getPowerUpdateField();
        colorField = productUpdateView.getColorUpdateField();
        productTypeComboBox = productUpdateView.getProductTypeUpdateComboBox();
        quantity = productUpdateView.getQuantity();
        image = productUpdateView.getUpdateImage();
        
        ownerSP = productUpdateView.getownerSP();

        String productId = idUpdateField.getText();
        String productName = nameField.getText();
        double price = 0;
        double monitorSize = 0;
        String cpu = cpuField.getText();
        String ram = ramField.getText();
        String rom = romField.getText();
        int status = 1;
        String color = colorField.getText();

        try {
            price = Double.parseDouble(priceField.getText());
            if (price <= 0) {
                JOptionPane.showMessageDialog(productUpdateView, "Hãy nhập đơn giá với số lớn hơn 0 ");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productUpdateView, "Nhập đơn giá ở dạng số!");
            return;
        }

        if (productTypeComboBox.getSelectedItem().equals("Laptop")) {
            try {
                monitorSize = Double.parseDouble(sizeLaptopField.getText());
                if (monitorSize <= 0) {
                    JOptionPane.showMessageDialog(productUpdateView, "Hãy nhập kích thước màn với số lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productUpdateView, "Nhập kích thước màn ở dạng số!");
                return;
            }
            String pin = pinField.getText();
            if (productId.equals("") || productName.equals("") || cpu.equals("")
                    || ram.equals("") || rom.equals("") || pin.equals("")) {
                JOptionPane.showMessageDialog(productUpdateView, "Vui lòng nhập đầy đủ thông tin !");
                return;
            } else {
                Laptop laptopModel = new Laptop(monitorSize, pin, productId, productName, quantity , price, cpu, ram, rom, status, image, color);
                try {
                    LaptopDao.getInstance().update(laptopModel);
                    productUpdateView.dispose();
                    JOptionPane.showMessageDialog(productUpdateView, "Sửa sản phẩm thành công");
                    HomeView homeView = HomeView.getInstance();
                    homeView.reloadCenterPanel();
                    ownerSP.productController.loadDataToTable();
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(productUpdateView, "Sửa sản phẩm thất bại");
                    return;
                }
            }
        }
        if (productTypeComboBox.getSelectedItem().equals("IMac")) {
            try {
                monitorSize = Double.parseDouble(sizeImacField.getText());
                if (monitorSize <= 0) {
                    JOptionPane.showMessageDialog(productUpdateView, "Hãy nhập kích thước màn với số lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productUpdateView, "Nhập kích thước màn ở dạng số!");
                return;
            }
            String power = powerField.getText();
            if (productId.equals("") || productName.equals("") || cpu.equals("")
                    || ram.equals("") || rom.equals("") || power.equals("")) {
                JOptionPane.showMessageDialog(productUpdateView, "Vui lòng nhập đầy đủ thông tin !");
            } else {
                IMac iMacModel = new IMac(monitorSize, power, productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                IMacDao.getInstance().update(iMacModel);
                productUpdateView.dispose();
                JOptionPane.showMessageDialog(productUpdateView, "Sửa sản phẩm thành công");
                HomeView homeView = HomeView.getInstance();
                homeView.reloadCenterPanel();
                productUpdateView.ownerSP.productController.loadDataToTable();
            }
        }
    }
    
    ///////////////////////////Chi tiết sản phẩm///////////////////////////////////////
    public ProductController(ProductDetailView productDetailView) {
        this.productDetailView = productDetailView;
    }
    
    public void productTypeDetailComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        productTypeComboBox = productDetailView.getProductTypeDetailComboBox();
        typePanel = productDetailView.getTypeDetailPanel();
        if (productTypeComboBox.getSelectedItem().equals("Laptop")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.first(typePanel);
        }
        if (productTypeComboBox.getSelectedItem().equals("IMac")) {
            CardLayout productCategory = (CardLayout) typePanel.getLayout();
            productCategory.last(typePanel);
        }
    }
}
