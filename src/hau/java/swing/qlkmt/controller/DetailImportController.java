/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.ImportDetailDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.util.PdfUtil;
import hau.java.swing.qlkmt.view.DetailImportView;
import hau.java.swing.qlkmt.view.StatisticView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author thanh
 */
public class DetailImportController extends Controller{
    public DetailImportView detailImportView;
    public JTable invoiceDetailTable;
    public StatisticView parrent;
    public StatisticController statisticController;
    
    public DetailImportController(DetailImportView detailImportView) {
        this.detailImportView = detailImportView;
    }
    
    public DetailImportController (StatisticController statisticController) {
        this.statisticController = statisticController;
    }
    
    public void loadDataTableProduct() {
        invoiceDetailTable = detailImportView.getDetailTable();
        parrent = detailImportView.getParrent();
        try {
            ArrayList<InvoiceDetail> arrayListInvoice = ImportDetailDao.getInstance().
                    selectAll(detailImportView.parrent.statisticController.getInvoiceImport().getInvoiceId().toString());
            DefaultTableModel defaultTableModel = (DefaultTableModel) invoiceDetailTable.getModel();
            defaultTableModel.setRowCount(0);
            for (int i = 0; i < arrayListInvoice.size(); i++) {
                defaultTableModel.addRow(new Object[]{
                    i + 1,
                    arrayListInvoice.get(i).getProductId(),
                    ComputerDao.getInstance().selectById(arrayListInvoice.get(i).getProductId()).getProductName(),
                    arrayListInvoice.get(i).getQuantity(),
                    detailImportView.parrent.getFormatter().format(arrayListInvoice.get(i).getPrice()) + "đ",
                    detailImportView.parrent.getFormatter().format(arrayListInvoice.get(i).getPrice() * arrayListInvoice.get(i).getQuantity()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }
    
    public void exportButtonAction(ActionEvent e) throws IOException {
        parrent = detailImportView.getParrent();
        PdfUtil pdf = new PdfUtil();
        pdf.writeImport(detailImportView.parrent.statisticController.getInvoiceImport().getInvoiceId());
    }
}
