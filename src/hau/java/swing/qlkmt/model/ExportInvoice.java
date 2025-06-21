/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.model;

/**
 *
 * @author thanh
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class ExportInvoice extends Invoice {

    private String branch;

    public ExportInvoice() {
    }

    public ExportInvoice(String branch) {
        this.branch = branch;
    }

    public ExportInvoice(String branch, String invoiceId, Timestamp createTime, ArrayList<InvoiceDetail> invoiceDetail, double totalPrice) {
        super(invoiceId, createTime, invoiceDetail, totalPrice);
        this.branch = branch;
    }

    private ExportInvoice(String invoiceId, Timestamp createTime, ArrayList<InvoiceDetail> invoiceDetail, double totalPrice) {
        throw new UnsupportedOperationException("Not supported yet.");// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

//    @Override
//    public String toString() {
//        return "PhieuXuat{" + "chiNhanh=" + branch + "maPhieu" + this.getInvoiceId()+ '}';
//    }

    @Override
    public String toString() {
        return "ExportInvoice{" + "branch=" + branch + "invoiceId" + this.getInvoiceId() + '}';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.branch);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExportInvoice other = (ExportInvoice) obj;
        return Objects.equals(this.branch, other.branch) && Objects.equals(this.getInvoiceId(), other.getInvoiceId()) && Double.doubleToLongBits(this.getTotalPrice()) != Double.doubleToLongBits(other.getTotalPrice());
    }

}
