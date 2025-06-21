/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author thanh
 */
public class ImportInvoice extends Invoice {

    public ImportInvoice() {
    }

    public ImportInvoice(String invoiceId, Timestamp createTime, ArrayList<InvoiceDetail> invoiDetail, double totalPrice) {
        super(invoiceId, createTime, invoiDetail, totalPrice);
    }

}
