/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.dao.ExportDetailDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.ExportDao;
import hau.java.swing.qlkmt.model.Branch;
import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.model.Computer;
import hau.java.swing.qlkmt.model.ExportInvoice;
import hau.java.swing.qlkmt.view.ExportView;
import hau.java.swing.qlkmt.view.HomeView;
import hau.java.swing.qlkmt.view.ProductListView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
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
public class ExportController extends Controller {

    public ExportView exportView;
    public ArrayList<InvoiceDetail> invoiceDetail;
    public ArrayList<Branch> arrayListBranch = BranchDao.getInstance().selectAll();
    public ArrayList<Computer> allProduct;
    public DecimalFormat formatter = new DecimalFormat("###,###,###");
    public DefaultTableModel tableModel;
    public JComboBox branchComboBox;
    public JLabel totalpriceLabel;
    public JTable exportTable, productTable;
    public String invoiceId;
    public JTextField exportField, searchField, quantityField;
    public ProductListView productListView;
    
    public ExportController(ProductListView productListView) {
        this.productListView = productListView;
    }

    public void loadBranch() {
        branchComboBox = exportView.getBranchComboBox();

        branchComboBox.removeAllItems();
        for (Branch i : arrayListBranch) {
            branchComboBox.addItem(i.getBranchName());
        }
    }

    public void loadDataTableProduct(ArrayList<Computer> arrayListComputerModels) {
        try {
            tableModel = exportView.getTableModel();

            allProduct = ComputerDao.getInstance().selectAllExist();
            tableModel.setRowCount(0);
            for (var i : arrayListComputerModels) {
                tableModel.addRow(new Object[]{
                    i.getProductId(), i.getProductName(), i.getQuantity(), formatter.format(i.getPrice()) + "đ"
                });
            }
        } catch (Exception e) {
        }
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

    public void loadDataTableExport() {
        double sum = 0;
        try {
            exportTable = exportView.getExportTable();

            totalpriceLabel = exportView.getPrice();
            DefaultTableModel exportTableModel = (DefaultTableModel) exportTable.getModel();
            exportTableModel.setRowCount(0);

            for (int i = 0; i < invoiceDetail.size(); i++) {
                exportTableModel.addRow(new Object[]{
                    i + 1, invoiceDetail.get(i).getProductId(),
                    computerSearch(invoiceDetail.get(i).getProductId()).getProductName(),
                    invoiceDetail.get(i).getQuantity(),
                    formatter.format(invoiceDetail.get(i).getPrice()) + "đ"
                });
                sum += invoiceDetail.get(i).getPrice();
            }
        } catch (Exception e) {
        }
        totalpriceLabel.setText(formatter.format(sum) + "đ");
    }

    public String createID(ArrayList<ExportInvoice> arrayList) {
        int id = arrayList.size() + 1;
        String check = "";
        for (ExportInvoice phieuXuat : arrayList) {
            if (phieuXuat.getInvoiceId().equals("PX" + id)) {
                check = phieuXuat.getInvoiceId();
            }
        }
        while (check.length() != 0) {
            String ch = check;
            id++;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getInvoiceId().equals("PX" + id)) {
                    check = arrayList.get(i).getInvoiceId();
                }
            }
            if (check.equals(ch)) {
                check = "";
            }
        }
        return "PX" + id;
    }

    public Computer searchID(String text) {
        ArrayList<Computer> arrayList = ComputerDao.getInstance().selectAllExist();
        for (var computer : arrayList) {
            if (computer.getProductId().toLowerCase().contains(text.toLowerCase())) {
                return computer;
            }
        }
        return null;
    }

    /////////////////////////////////////////////////////////
    public ExportController(ExportView exportView) {
        this.exportView = exportView;
    }

    public void addButtonActionPerformed(ActionEvent e) {
        productTable = exportView.getProductTable();
        quantityField = exportView.getQuantityField();
        totalpriceLabel = exportView.getPrice();
        invoiceId = exportView.getInvoiceId();

        int i_row = productTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(exportView, "Vui lòng chọn sản phẩm để xuất hàng!");
        } else {
            int availableQuantity = allProduct.get(i_row).getQuantity();
            if (availableQuantity == 0) {
                JOptionPane.showMessageDialog(exportView, "không đủ số lượng hàng để xuất!");
            } else {
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity > 0) {
                        if (availableQuantity < quantity) {
                            JOptionPane.showMessageDialog(exportView, "Số lượng không đủ");
                        } else {
                            InvoiceDetail INvoiceDetail = detailInvoiceSearch((String) productTable.getValueAt(i_row, 0));
                            if (INvoiceDetail != null) { // Nếu đã có SP trên bảng xuất hàng
                                if (computerSearch((String) productTable.getValueAt(i_row, 0)).getQuantity() < INvoiceDetail.getQuantity() + quantity) {
                                    JOptionPane.showMessageDialog(exportView, "Số lượng máy không đủ"); // thì thêm lần sau phải nhỏ hơn số lượng đang có
                                } else {
                                    INvoiceDetail.setQuantity(INvoiceDetail.getQuantity() + quantity); // fine thì duyệt
                                }
                            } else { // Nếu chưa có sản phẩm ở bảng xuất hàng thì tạo sản phẩm mới đẩy lên
                                Computer computer = searchID((String) productTable.getValueAt(i_row, 0));
                                InvoiceDetail invoicedetail = new InvoiceDetail(invoiceId, computer.getProductId(), quantity, computer.getPrice());
                                invoiceDetail.add(invoicedetail);
                            }
                            loadDataTableExport();
                            totalpriceLabel.setText(formatter.format(totalMoney()) + "0đ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(exportView, "Vui lòng nhập số lượng lớn hơn 0");
                    }
                } catch (HeadlessException | NumberFormatException evt) {
                    JOptionPane.showMessageDialog(exportView, "Vui lòng nhập số lượng ở dạng số nguyên");
                }
            }

        }
    }

    public void txtSearchKeyReleased(KeyEvent evt) {
        searchField = exportView.getSearchField();

        String search = searchField.getText().toLowerCase();
        ArrayList<Computer> computers = new ArrayList<>();
        for (Computer i : allProduct) {
            // ghép mãSP và tênSP vào 1 chuỗi để có thể tìm kiếm cả 2
            if (i.getProductId().concat(i.getProductName()).toLowerCase().contains(search)) {
                computers.add(i);
            }
        }
        loadDataTableProduct(computers);
    }

    public void refreshButtonActionPerformed(ActionEvent e) {
        try {
            searchField = exportView.getSearchField();
            searchField.setText("");

            loadDataTableProduct(allProduct);

            if (exportTable != null && exportTable.getModel() instanceof DefaultTableModel) {
                DefaultTableModel importTableModel = (DefaultTableModel) exportTable.getModel();
                importTableModel.setRowCount(0);
            }
            if (invoiceDetail != null) {
                invoiceDetail.clear();
            }

            totalpriceLabel = exportView.getPrice();
            totalpriceLabel.setText("0đ");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi load lại dữ liệu");
        }
    }

    public void editButtonActionPerformed(ActionEvent e) {
        exportTable = exportView.getExportTable();
        totalpriceLabel = exportView.getPrice();

        int i_row = exportTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(exportView, "Vui lòng chọn sản phẩm để sửa số lượng!");
        } else {
            String editQuantity = JOptionPane.showInputDialog(exportView, "Nhập số lượng cần thay đổi", "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
            if (editQuantity != null) {
                try {
                    int i_rowProduct = productTable.getSelectedRow();
                    int quantity = Integer.parseInt(editQuantity.trim());
                    int availableQuantity = allProduct.get(i_rowProduct).getQuantity();

                    System.out.println("Số lượng hiện tại trong kho: " + availableQuantity);
                    System.out.println("Số lượng cần sửa: " + quantity);

                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(exportView, "Vui lòng nhập số lượng lớn hơn 0");
                    } else if (availableQuantity < quantity) { // nếu sl ở kho nhỏ hơn sl muốn sửa thì lỗi
                        JOptionPane.showMessageDialog(exportView, "Không đủ số lượng để sửa");
                    } else {
                        invoiceDetail.get(i_row).setQuantity(quantity);
                        loadDataTableExport();
                        totalpriceLabel.setText(formatter.format(totalMoney()) + "đ");
                    }
                } catch (HeadlessException | NumberFormatException evt) {
                    JOptionPane.showMessageDialog(exportView, "Vui lòng nhập số lượng ở dạng số nguyên");
                }
            }
        }
    }

    public void deleteButtonActionPerformed(ActionEvent e) {
        exportTable = exportView.getExportTable();
        totalpriceLabel = exportView.getPrice();

        int i_row = exportTable.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(exportView, "Vui lòng chọn sản phẩm để xóa khỏi bảng xuất hàng!");
        } else {
            invoiceDetail.remove(i_row);
            loadDataTableExport();
            totalpriceLabel.setText(formatter.format(totalMoney()) + "0đ");
        }
    }

    public void exportButtonActionPerformed(ActionEvent e) {
        exportTable = exportView.getExportTable();
        invoiceId = exportView.getInvoiceId();
        totalpriceLabel = exportView.getPrice();
        exportField = exportView.getExportField();
        branchComboBox = exportView.getBranchComboBox();

        if (invoiceDetail.isEmpty()) {
            JOptionPane.showMessageDialog(exportView, "Bạn chưa chọn sản phẩm để xuất hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
            int check = JOptionPane.showConfirmDialog(exportView, "Bạn có chắc muốn xuất hàng?", "Xác nhận xuất hàng", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // lấy thời gian hiện tại
                long now = System.currentTimeMillis();
                Timestamp timeStamp = new Timestamp(now);
                // Tạo phiếu xuất
                ExportInvoice exportInvoice = new ExportInvoice(arrayListBranch.get(branchComboBox.getSelectedIndex()).getBranchId(), invoiceId,
                        timeStamp, invoiceDetail, totalMoney());
                try {
                    ExportDao.getInstance().insert(exportInvoice);
                    ComputerDao computerDao = ComputerDao.getInstance();
                    for (var i : invoiceDetail) {
                        ExportDetailDao.getInstance().insert(i);
                        computerDao.updateQuantity(i.getProductId(), computerDao.selectById(i.getProductId()).getQuantity() - i.getQuantity());
                    }
                    JOptionPane.showMessageDialog(exportView, "Xuất hàng thành công!");
                    loadDataTableProduct(allProduct);
                    DefaultTableModel defaultTableModel = (DefaultTableModel) exportTable.getModel();
                    defaultTableModel.setRowCount(0);
                    invoiceDetail = new ArrayList<>();
                    totalpriceLabel.setText("0đ");
                    exportView.invoiceId = createID(ExportDao.getInstance().selectAll());
                    exportField.setText(exportView.invoiceId);
                    refreshButtonActionPerformed(e);
                    HomeView home = HomeView.getInstance();
                    home.reloadCenterPanel();
                } catch (HeadlessException | UnsupportedLookAndFeelException evt) {
                    JOptionPane.showMessageDialog(exportView, "Đã xảy ra lỗi!");
                }
            }
        }
    }

}
