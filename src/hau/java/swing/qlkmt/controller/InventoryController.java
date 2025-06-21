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
import hau.java.swing.qlkmt.view.InventoryView;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thanh
 */
public class InventoryController extends Controller{

    public InventoryView inventoryView;
    public DefaultTableModel tableModel;
    public DecimalFormat formatter = new DecimalFormat("###,###,###");
    public JTable productTable;
    public JTextField searchField;
    public JComboBox searchComboBox;

    public void loadDataToTable() {
        try {
            tableModel = inventoryView.getTableModel();
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
                    tableModel.addRow(new Object[]{i.getProductId(), i.getProductName(), i.getQuantity(),
                        formatter.format(i.getPrice()) + "đ", i.getCpu(), i.getRam(), i.getRom(), type, i.getStatus()});
                }
            }
        } catch (Exception e) {
        }
    }

    public void loadDataTableSearch(ArrayList<Computer> result) {
        try {
            tableModel = inventoryView.getTableModel();
            tableModel.setRowCount(0);
            for (Computer i : result) {
                String type;
                if (LaptopDao.getInstance().isLaptop(i.getProductId()) == true) {
                    type = "Laptop";
                } else {
                    type = "IMac";
                }
                tableModel.addRow(new Object[]{i.getProductId(), i.getProductName(), i.getQuantity(),
                    formatter.format(i.getPrice()) + "đ", i.getCpu(), i.getRam(), i.getRom(), type, i.getStatus()});

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Computer getComputerModel() {
        productTable = inventoryView.getProductTable();
        tableModel = inventoryView.getTableModel();

        int i_row = productTable.getSelectedRow();
        Computer computerModel = ComputerDao.getInstance().selectById(tableModel.getValueAt(i_row, 0).toString());
        return computerModel;
    }

    public ArrayList<Computer> idSearch(String text) {
        ArrayList<Computer> result = new ArrayList<>();
        ArrayList<Computer> computerModels = ComputerDao.getInstance().selectAllExist();
        for (var computer : computerModels) {
            if (computer.getStatus()== 1) {
                if (computer.getProductId().toLowerCase().contains(text.toLowerCase())) {
                    result.add(computer);
                }
            }
        }
        return result;
    }

    public ArrayList<Computer> nameSearch(String text) {
        ArrayList<Computer> result = new ArrayList<>();
        ArrayList<Computer> computerModels = ComputerDao.getInstance().selectAllExist();
        for (var computer : computerModels) {
            if (computer.getStatus()== 1) {
                if (computer.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    result.add(computer);
                }
            }
        }
        return result;
    }

    public ArrayList<Computer> searchOptionContent(String optional, String content) {
        ArrayList<Computer> result = new ArrayList<>();
        switch (optional) {
            case "Mã máy" ->
                result = idSearch(content);
            case "Tên máy" ->
                result = nameSearch(content);
        }
        return result;
    }

    public boolean checkLap() {
        return LaptopDao.getInstance().isLaptop(getComputerModel().getProductId()) == true;
    }

    public Laptop getDetailLaptop() {
        Laptop laptop = LaptopDao.getInstance().selectById(getComputerModel().getProductId());
        return laptop;
    }

    public boolean checkIMac() {
        return IMacDao.getInstance().isIMac(getComputerModel().getProductId()) == true;
    }

    public IMac getDetailIMac() {
        IMac iMac = IMacDao.getInstance().selectById(getComputerModel().getProductId());
        return iMac;
    }

    ///////////////////////////////////////////////////////
    public InventoryController(InventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }

    public void searchComboBoxActionPerformed(ActionEvent e) {
        searchComboBox = inventoryView.getSearchComboBox();
        searchField = inventoryView.getsearchField();

        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void searchComboBoxPropertyChange(PropertyChangeEvent evt) {
        searchComboBox = inventoryView.getSearchComboBox();
        searchField = inventoryView.getsearchField();

        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void searchFieldKeyPressed(KeyEvent evt) {
        searchComboBox = inventoryView.getSearchComboBox();
        searchField = inventoryView.getsearchField();

        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void searchFieldKeyReleased(KeyEvent evt) {
        searchComboBox = inventoryView.getSearchComboBox();
        searchField = inventoryView.getsearchField();

        String optional = searchComboBox.getSelectedItem().toString();
        String content = searchField.getText();
        ArrayList<Computer> result = searchOptionContent(optional, content);
        loadDataTableSearch(result);
    }

    public void refreshButtonActionPerformed(ActionEvent evt) {
        searchComboBox = inventoryView.getSearchComboBox();
        searchField = inventoryView.getsearchField();

        searchComboBox.setSelectedIndex(0);
        searchField.setText("");
        loadDataToTable();
    }

}
