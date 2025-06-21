/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.dao;

import hau.java.swing.qlkmt.model.ImportInvoice;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import hau.java.swing.qlkmt.database.ConnectionDatabase;
import hau.java.swing.qlkmt.model.Invoice;
import java.sql.SQLException;

/**
 *
 * @author thanh
 */
public class ImportDao implements DAOInterface<ImportInvoice> {

    public static ImportDao getInstance() {
        return new ImportDao();
    }

    @Override
    public int insert(ImportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO PhieuNhap (maPhieu, thoiGianTao, tongTien) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setTimestamp(2, t.getCreateTime());
            ps.setDouble(3, t.getTotalPrice());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int update(ImportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE PhieuNhap SET maPhieu = ?, thoiGianTao = ?, tongTien = ? WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setTimestamp(2, t.getCreateTime());
            ps.setDouble(3, t.getTotalPrice());
            ps.setString(4, t.getInvoiceId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int delete(ImportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM PhieuNhap WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<ImportInvoice> selectAll() {
        ArrayList<ImportInvoice> result = new ArrayList<ImportInvoice>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM PhieuNhap ORDER BY thoiGianTao DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //System.out.println("Truy xuất dữ liệu từ bảng ImportInvoice:");
            while (rs.next()) {
                String invoiceId = rs.getString("maPhieu");
                Timestamp createTime = rs.getTimestamp("thoiGianTao");
                double totalPrice = rs.getDouble("tongTien");
                //System.out.println("maPhieu: " + maPhieu + ", thoiGianTao: " + thoiGianTao + ", tongTien: " + tongTien);
                ImportInvoice pn = new ImportInvoice(invoiceId, createTime, ImportDetailDao.getInstance().selectAll(invoiceId), totalPrice);
                result.add(pn);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ImportInvoice selectById(String t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ImportInvoice result = null;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM PhieuNhap WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String invoiceId = rs.getString("maPhieu");
                Timestamp createTime = rs.getTimestamp("thoiGianTao");
                double totalPrice = rs.getDouble("tongTien");
                result = new ImportInvoice(invoiceId, createTime, ImportDetailDao.getInstance().selectAll(invoiceId), totalPrice);

            }
        } catch (SQLException e) {
        }
        return result;
    }

    public ArrayList<Invoice> selectALLPro() {
        ArrayList<Invoice> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT maPhieu,thoiGianTao,tongTien FROM PhieuNhap UNION SELECT maPhieu,thoiGianTao,tongTien FROM PhieuXuat ORDER BY thoiGianTao DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String invoiceId = rs.getString("maPhieu");
                Timestamp createTime = rs.getTimestamp("thoiGianTao");
                double totalPrice = rs.getDouble("tongTien");
                Invoice p = new Invoice(invoiceId, createTime, ImportDetailDao.getInstance().selectAll(invoiceId), totalPrice);
                result.add(p);
            }
        } catch (SQLException e) {
        }
        return result;
    }

}
