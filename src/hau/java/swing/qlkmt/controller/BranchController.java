/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.model.Branch;
import hau.java.swing.qlkmt.view.BranchUpdateView;
import hau.java.swing.qlkmt.view.BranchView;
import hau.java.swing.qlkmt.view.HomeView;
import hau.java.swing.qlkmt.view.BranchAddView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author thanh
 */
public class BranchController extends Controller{

    public JTable branchTable;
    public DefaultTableModel tableModel;
    public BranchView branchView;
    public JComboBox searchComboBox;
    public JTextField searchField;

    public BranchAddView branchAddView;
    public JTextField branchNameField, branchIdField, phoneField, addressField;
    public BranchView parrent;
    public BranchUpdateView branchUpdateView;

    //////////////////////////phương thức chung///////////////////////
    public void loadDataTable(ArrayList<Branch> branch) {
        try {
            tableModel = branchView.getTableModel();
            tableModel.setRowCount(0);
            for (Branch i : branch) {
                tableModel.addRow(new Object[]{
                    i.getBranchId(), i.getBranchName(), i.getPhoneNumber(), i.getAddress()
                });
            }
        } catch (Exception e) {
        }
    }

    public Branch getBranchSelect() {
        branchTable = branchView.getBranchTable();
        int i_row = branchTable.getSelectedRow();
        Branch branch = BranchDao.getInstance().selectAll().get(i_row);
        return branch;
    }

    public ArrayList<Branch> branchName(String text) {
        ArrayList<Branch> result = new ArrayList<>();
        ArrayList<Branch> arrayListBranch = BranchDao.getInstance().selectAll();
        for (var branch : arrayListBranch) {
            if (branch.getBranchName().toLowerCase().contains(text.toLowerCase())) {
                result.add(branch);
            }
        }
        return result;
    }

    public ArrayList<Branch> branchId(String text) {
        ArrayList<Branch> result = new ArrayList<>();
        ArrayList<Branch> arrayListBranch = BranchDao.getInstance().selectAll();
        for (var branch : arrayListBranch) {
            if (branch.getBranchId().toLowerCase().contains(text.toLowerCase())) {
                result.add(branch);
            }
        }
        return result;
    }

    ///////////////////////////Trang chi nhánh/////////////////////////////////////
    public BranchController(BranchView branchView) {
        this.branchView = branchView;
    }

    public void deleteButtonActionPerformed(ActionEvent evt) throws UnsupportedLookAndFeelException {
        branchTable = branchView.getBranchTable();
        if (branchTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(branchView, "Vui lòng chọn chi nhánh muốn xóa");
        } else {
            int output = JOptionPane.showConfirmDialog(branchView, "Bạn muốn xóa chi nhánh này ?", "Xác nhận xóa chi nhánh", JOptionPane.YES_NO_OPTION);
            if (output == JOptionPane.YES_NO_OPTION) {
                BranchDao.getInstance().delete(getBranchSelect());
                JOptionPane.showMessageDialog(branchView, "Xóa thành công!");
                HomeView homeView = HomeView.getInstance();
                homeView.reloadCenterPanel();
                loadDataTable(BranchDao.getInstance().selectAll());
            }
        }
    }

    public void refreshButtonActionPerformed(ActionEvent evt) {
        searchField = branchView.getSearchField();
        searchComboBox = branchView.getSearchComboBox();
        searchField.setText("");
        searchComboBox.setSelectedIndex(0);
        loadDataTable(BranchDao.getInstance().selectAll());
    }

    public void searchFieldKeyReleased(KeyEvent evt) {
        searchField = branchView.getSearchField();
        searchComboBox = branchView.getSearchComboBox();
        String optional = (String) searchComboBox.getSelectedItem();
        String content = searchField.getText();
        ArrayList<Branch> result = new ArrayList<>();
        switch (optional) {
            case "Mã chi nhánh" ->
                result = branchId(content);
            case "Tên chi nhánh" ->
                result = branchName(content);
        }
        loadDataTable(result);
    }

    public void editButtonActionPerformed(ActionEvent e) {
        branchTable = branchView.getBranchTable();
        if (branchTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(branchView, "Vui lòng chọn chi nhánh muốn sửa");
        } else {
            BranchUpdateView branchUpdateView = new BranchUpdateView(branchView, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(branchView), true);
            branchUpdateView.setVisible(true);
        }
    }

    public void addButtonActionPerformed(ActionEvent e) {
        BranchAddView branchAddView = new BranchAddView(branchView, (JFrame) SwingUtilities.getWindowAncestor(branchView), true);
        branchAddView.setVisible(true);
    }

    /////////////////////thêm chi nhánh////////////////////////////////////
    public BranchController(BranchAddView branchAddView) {
        this.branchAddView = branchAddView;
    }

    public void saveAddButtonActionPerformed(ActionEvent e) {
        try {
            parrent = branchAddView.getParrent();
            branchIdField = branchAddView.getBranchIdField();
            branchNameField = branchAddView.getBranchNameField();
            phoneField = branchAddView.getPhoneField();
            addressField = branchAddView.getAddressField();

            String branchId = branchIdField.getText();
            String branchName = branchNameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            if (branchId.equals("") || branchName.equals("") || phone.equals("") || address.equals("")) {
                JOptionPane.showMessageDialog(branchAddView, "Vui lòng nhập đầy đủ thông tin !");
            } else if (BranchDao.getInstance().selectById(branchId) == null) {
                Branch branch = new Branch();
                branch.setBranchId(branchIdField.getText());
                branch.setBranchName(branchNameField.getText());
                branch.setPhoneNumber(phoneField.getText());
                branch.setAddress(addressField.getText());
                BranchDao branchDao = new BranchDao();
                branchDao.getInstance().insert(branch);
                JOptionPane.showMessageDialog(branchAddView, "Thêm chi nhánh thành công !");
                HomeView homeView = HomeView.getInstance();
                homeView.reloadCenterPanel();
                branchAddView.parrent.branchController.loadDataTable(BranchDao.getInstance().selectAll());
                branchAddView.dispose();
            }
        } catch (HeadlessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(branchAddView, "Thêm chi nhánh thất bại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelAddButtonActionPerformed(ActionEvent e) {
        branchAddView.dispose();
    }

    /////////////////////sửa chi nhánh////////////////////////////////////
    public BranchController(BranchUpdateView branchUpdateView) {
        this.branchUpdateView = branchUpdateView;
    }

    public void saveUpdateButtonActionPerformed(ActionEvent e) {
        parrent = branchUpdateView.getParrent();
        branchIdField = branchUpdateView.getBranchIdField();
        branchNameField = branchUpdateView.getBranchNameField();
        phoneField = branchUpdateView.getPhoneField();
        addressField = branchUpdateView.getAddressField();

        String branchId = branchIdField.getText();
        String branchName = branchNameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        try {
            // TODO add your handling code here:
            if (branchId.equals("") || branchName.equals("") || phone.equals("") || address.equals("")) {
                JOptionPane.showMessageDialog(branchUpdateView, "Vui lòng nhập đầy đủ thông tin !");
            } else {
                Branch branch = new Branch();
                branch.setBranchId(branchIdField.getText());
                branch.setBranchName(branchNameField.getText());
                branch.setPhoneNumber(phoneField.getText());
                branch.setAddress(addressField.getText());
                BranchDao branchDao = new BranchDao();
                branchDao.update(branch);
                branchUpdateView.dispose();
                JOptionPane.showMessageDialog(branchUpdateView, "Sửa chi nhánh thành công !");
                HomeView homeView = HomeView.getInstance();
                homeView.reloadCenterPanel();
                branchUpdateView.parrent.branchController.loadDataTable(BranchDao.getInstance().selectAll());
            }
        } catch (HeadlessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(branchUpdateView, "Sửa chi nhánh thất bại !");
        }
    }

    public void cancelUpdateButtonActionPerformed(ActionEvent e) {
        branchUpdateView.dispose();
    }
}
