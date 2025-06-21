/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.ImportDetailDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.ImportDao;
import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.model.Computer;
import hau.java.swing.qlkmt.model.ImportInvoice;
import hau.java.swing.qlkmt.view.HomeView;
import hau.java.swing.qlkmt.view.ImportView;
import hau.java.swing.qlkmt.view.ProductListView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thanh
 */
public class ImportController extends Controller {

    public ArrayList<InvoiceDetail> invoiceDetail;
    public ArrayList<Computer> allProduct;
    public HomeView homeView;
    public ImportView importView;
    public JTable importTable, productTable;
    public DecimalFormat formatter = new DecimalFormat("###,###,###");
    public DefaultTableModel productTableModel;
    public JLabel totalpriceLabel;
    public JTextField quantityField, importIdField, searchField;
    public String invoiceId;
    public ProductListView productListView;

    public ImportController(HomeView homeView) {
        this.homeView = homeView;
    }

    public ImportController(ProductListView productListView) {
        this.productListView = productListView;
    }

    //////////////////////phương thức///////////////////////////////////////
    public void loadDataTableProduct(ArrayList<Computer> arrayListComputers) {
        try {
            productTableModel = importView.getProductTableModel();

            allProduct = ComputerDao.getInstance().selectAllExist();
            productTableModel.setRowCount(0);
            for (var i : arrayListComputers) {
                productTableModel.addRow(new Object[]{
                    i.getProductId(), i.getProductName(), i.getQuantity(), formatter.format(i.getPrice()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public void loadDataTableImport() {
        double sum = 0;
        try {
            importTable = importView.getImportTable();
            totalpriceLabel = importView.getPrice();

            DefaultTableModel importTableModel = (DefaultTableModel) importTable.getModel();
            importTableModel.setRowCount(0);

            for (int i = 0; i < invoiceDetail.size(); i++) {
                importTableModel.addRow(new Object[]{
                    i + 1,
                    invoiceDetail.get(i).getProductId(),
                    computerSearch(invoiceDetail.get(i).getProductId()).getProductName(),
                    invoiceDetail.get(i).getQuantity(),
                    formatter.format(invoiceDetail.get(i).getPrice()) + "đ"
                });
                sum += invoiceDetail.get(i).getPrice() * invoiceDetail.get(i).getQuantity();
            }
        } catch (Exception e) {
        }
        totalpriceLabel.setText(formatter.format(sum) + "đ");
    }

    public double totalMoney() {
        double sum = 0;
        for (var i : invoiceDetail) {
            sum += i.getPrice() * i.getQuantity();
        }
        return sum;
    }

    public Computer computerSearch(String productId) {
        for (var i : allProduct) {
            if (productId.equals(i.getProductId())) {
                return i;
            }
        }
        return null;
    }

    public InvoiceDetail detailInvoiceSearch(String productId) {
        for (var i : invoiceDetail) {
            if (productId.equals(i.getProductId())) {
                return i;
            }
        }
        return null;
    }

    public String createID(ArrayList<ImportInvoice> arrayList) {
        int id = arrayList.size() + 1;
        String check = "";
        for (ImportInvoice importInvoice : arrayList) {
            if (importInvoice.getInvoiceId().equals("PN" + id)) {
                check = importInvoice.getInvoiceId();
            }
        }
        while (check.length() != 0) {
            String ch = check;
            id++;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getInvoiceId().equals("PN" + id)) {
                    check = arrayList.get(i).getInvoiceId();
                }
            }
            if (check.equals(ch)) {
                check = "";
            }
        }
        return "PN" + id;
    }

    public Computer searchID(String text) {
        ArrayList<Computer> arrayListComputer = ComputerDao.getInstance().selectAllExist();
        for (var computer : arrayListComputer) {
            if (computer.getProductId().toLowerCase().contains(text.toLowerCase())) {
                return computer;
            }
        }
        return null;
    }

    /////////////////////////////////////////////////////////
    public ImportController(ImportView importView) {
        this.importView = importView;
    }

    public void addButtonActionPerformed(ActionEvent e) {
        productTable = importView.getProductTable();
        quantityField = importView.getQuantityField();
        totalpriceLabel = importView.getPrice();
        invoiceId = importView.getInvoiceId();

        int i_row = productTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(importView, "Vui lòng chọn sản phẩm để nhập hàng!");
        } else {
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    InvoiceDetail InvoiceDetail = detailInvoiceSearch((String) productTable.getValueAt(i_row, 0));
                    if (InvoiceDetail != null) { // nếu sản phẩm đã có bên bảng phiếu nhập
                        InvoiceDetail.setQuantity(InvoiceDetail.getQuantity() + quantity); // cộng tiếp số lượng định thêm bên bảng phiếu nhập
                    } else { // nếu sản phẩm chưa có bên bảng phiếu nhập
                        Computer computerModel = searchID((String) productTable.getValueAt(i_row, 0));
                        // tạo sản phẩm bên bảng phiếu nhập
                        InvoiceDetail invoicedetail = new InvoiceDetail(invoiceId, computerModel.getProductId(), quantity, computerModel.getPrice());
                        invoiceDetail.add(invoicedetail);
                    }
                    loadDataTableImport();
                    totalpriceLabel.setText(formatter.format(totalMoney()) + "đ");
                } else {
                    JOptionPane.showMessageDialog(importView, "Vui lòng nhập số lượng lớn hơn 0");
                }
            } catch (HeadlessException | NumberFormatException evt) {
                JOptionPane.showMessageDialog(importView, "Vui lòng nhập số lượng ở dạng số nguyên");
            }
        }
    }

    public void txtSearchKeyReleased(KeyEvent evt) {
        searchField = importView.getSearchField();

        String search = searchField.getText().toLowerCase();
        ArrayList<Computer> arrayList = new ArrayList<>();
        for (Computer i : allProduct) {
            // ghép mãSP và tênSP vào 1 chuỗi để có thể tìm kiếm cả 2
            if (i.getProductId().concat(i.getProductName()).toLowerCase().contains(search)) {
                arrayList.add(i);
            }
        }
        loadDataTableProduct(arrayList);
    }

    public void refreshButtonActionPerformed(ActionEvent e) {
        try {
            searchField = importView.getSearchField();
            searchField.setText("");
            loadDataTableProduct(allProduct);
            if (importTable != null && importTable.getModel() instanceof DefaultTableModel) {
                DefaultTableModel importTableModel = (DefaultTableModel) importTable.getModel();
                importTableModel.setRowCount(0);
            }
            if (invoiceDetail != null) {
                invoiceDetail.clear();
            }
            totalpriceLabel = importView.getPrice();
            totalpriceLabel.setText("0đ");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi load lại dữ liệu" + ex.getMessage());
        }
    }

    public void editButtonActionPerformed(ActionEvent e) {
        importTable = importView.getImportTable();
        totalpriceLabel = importView.getPrice();

        int i_row = importTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(importView, "Vui lòng chọn sản phẩm để sửa số lượng!");
        } else {
            String editquantity = JOptionPane.showInputDialog(importView, "Nhập số lượng cần thay đổi", "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
            if (editquantity != null) {
                int quantity;
                try {
                    quantity = Integer.parseInt(editquantity);
                    if (quantity > 0) {
                        invoiceDetail.get(i_row).setQuantity(quantity);
                        loadDataTableImport();
                        totalpriceLabel.setText(formatter.format(totalMoney()) + "đ");
                    } else {
                        JOptionPane.showMessageDialog(importView, "Vui lòng nhập số lượng lớn hơn 0");
                    }
                } catch (HeadlessException | NumberFormatException evt) {
                    JOptionPane.showMessageDialog(importView, "Vui lòng nhập số lượng ở dạng số nguyên");
                }
            }

        }
    }

    public void deleteButtonActionPerformed(ActionEvent e) {
        importTable = importView.getImportTable();
        totalpriceLabel = importView.getPrice();

        int i_row = importTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(importView, "Vui lòng chọn sản phẩm để xóa khỏi bảng nhập hàng!");
        } else {
            invoiceDetail.remove(i_row);
            loadDataTableImport();
            totalpriceLabel.setText(formatter.format(totalMoney()) + "0đ");
        }
    }

    public void importButtonActionPerformed(ActionEvent e) {
        importTable = importView.getImportTable();
        invoiceId = importView.getInvoiceId();
        totalpriceLabel = importView.getPrice();
        importIdField = importView.getImportIdField();

        if (invoiceDetail.isEmpty()) {
            JOptionPane.showMessageDialog(importView, "Bạn chưa chọn sản phẩm để nhập hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
            int check = JOptionPane.showConfirmDialog(importView, "Bạn có chắc muốn nhập hàng?", "Xác nhận nhập hàng", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // lấy thời gian hiện tại
                long now = System.currentTimeMillis(); // lấy thời gian hiện tại
                Timestamp timeStamp = new Timestamp(now); // chuyển vào timeStamp để lưu phiếu nhập
                // Tạo phiếu nhập
                ImportInvoice importInvoice = new ImportInvoice(invoiceId, timeStamp, invoiceDetail, totalMoney());
                try {
                    ImportDao.getInstance().insert(importInvoice);
                    ComputerDao computerDao = ComputerDao.getInstance();
                    for (var i : invoiceDetail) {
                        ImportDetailDao.getInstance().insert(i);
                        // cập nhật lại số lượng mới trong kho với mỗi SP
                        computerDao.updateQuantity(i.getProductId(), computerDao.selectById(i.getProductId()).getQuantity() + i.getQuantity());
                    }
                    JOptionPane.showMessageDialog(importView, "Nhập hàng thành công!");
                    loadDataTableProduct(allProduct);
                    DefaultTableModel defaultTableModel = (DefaultTableModel) importTable.getModel();
                    defaultTableModel.setRowCount(0); // cập nhật lại bảng về 0
                    invoiceDetail = new ArrayList<>(); // đặt lại bảng nhập hàng về mới
                    totalpriceLabel.setText("0đ");
                    // tạo 1 ID hóa đơn mới 
                    importView.invoiceId = createID(ImportDao.getInstance().selectAll());
                    importIdField.setText(importView.invoiceId);
                    refreshButtonActionPerformed(e);
                    HomeView home = HomeView.getInstance();
                    home.reloadCenterPanel();
                } catch (HeadlessException | UnsupportedLookAndFeelException evt) {
                    JOptionPane.showMessageDialog(importView, "Đã xảy ra lỗi!");
                }
            }
        }
    }

}
