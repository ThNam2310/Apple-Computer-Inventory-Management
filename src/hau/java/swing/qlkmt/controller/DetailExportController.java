/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import hau.java.swing.qlkmt.dao.ExportDetailDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.util.PdfUtil;
import hau.java.swing.qlkmt.view.DetailExportView;
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
public class DetailExportController extends Controller{

    public DetailExportView detailExportView;
    public JTable invoiceDetailTable;
    public StatisticView parrent;
    public StatisticController statisticController;

    public DetailExportController(DetailExportView detailExportView) {
        this.detailExportView = detailExportView;
    }

    public DetailExportController(StatisticController statisticController) {
        this.statisticController = statisticController;
    }

    public void loadDataTablePro() {
        invoiceDetailTable = detailExportView.getDetailTable();
        parrent = detailExportView.getParrent();

        try {
            ArrayList<InvoiceDetail> arrayListInvoice = ExportDetailDao.getInstance().
                    selectAll(detailExportView.parrent.statisticController.getExportInvoice().getInvoiceId().toString());
            DefaultTableModel defaultTableModel = (DefaultTableModel) invoiceDetailTable.getModel();
            defaultTableModel.setRowCount(0);
            for (int i = 0; i < arrayListInvoice.size(); i++) {
                defaultTableModel.addRow(new Object[]{
                    i + 1,
                    arrayListInvoice.get(i).getProductId(),
                    ComputerDao.getInstance().selectById(arrayListInvoice.get(i).getProductId()).getProductName(),
                    arrayListInvoice.get(i).getQuantity(),
                    detailExportView.parrent.getFormatter().format(arrayListInvoice.get(i).getPrice()) + "đ",
                    detailExportView.parrent.getFormatter().format(arrayListInvoice.get(i).getPrice() * arrayListInvoice.get(i).getQuantity()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public void exportButtonAction(ActionEvent e) throws IOException {
        parrent = detailExportView.getParrent();
        PdfUtil pdf = new PdfUtil();
        pdf.writeExport(detailExportView.parrent.statisticController.getExportInvoice().getInvoiceId());
    }

}
