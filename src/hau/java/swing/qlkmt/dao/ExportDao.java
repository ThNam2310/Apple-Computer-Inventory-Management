/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.dao;

/**
 *
 * @author thanh
 */
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import hau.java.swing.qlkmt.database.ConnectionDatabase;
import hau.java.swing.qlkmt.model.ExportInvoice;
import java.sql.SQLException;

public class ExportDao implements DAOInterface<ExportInvoice>{
    public static ExportDao getInstance() {
        return new ExportDao();
    }

    @Override
    public int insert(ExportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO PhieuXuat (maPhieu, thoiGianTao, maChiNhanhCuaHang, tongTien) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setTimestamp(2, t.getCreateTime());
            ps.setString(3, t.getBranch());
            ps.setDouble(4, t.getTotalPrice());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int update(ExportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE PhieuXuat SET maPhieu = ?, thoiGianTao = ?, maChiNhanhCuaHang = ?, tongTien = ? WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setTimestamp(2, t.getCreateTime());
            ps.setString(3, t.getBranch());
            ps.setDouble(4, t.getTotalPrice());
            ps.setString(5, t.getInvoiceId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int delete(ExportInvoice t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM PhieuXuat WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<ExportInvoice> selectAll() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<ExportInvoice> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM PhieuXuat ORDER BY thoiGianTao DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String invoiceId = rs.getString("maPhieu");
                Timestamp createTime = rs.getTimestamp("thoiGianTao");
                String branchId = rs.getString("maChiNhanhCuaHang");
                double totalPrice = rs.getDouble("tongTien");
                ExportInvoice px = new ExportInvoice(branchId, invoiceId, createTime, ExportDetailDao.getInstance().selectAll(invoiceId), totalPrice);
                result.add(px);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ExportInvoice selectById(String t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ExportInvoice result = null;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM PhieuXuat WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String invoiceId = rs.getString("maPhieu");
                Timestamp createTime = rs.getTimestamp("thoiGianTao");
                String branchId = rs.getString("maChiNhanhCuaHang");
                double totalPrice = rs.getDouble("tongTien");
                result = new ExportInvoice(branchId, invoiceId, createTime, ExportDetailDao.getInstance().selectAll(invoiceId), totalPrice);
            }
        } catch (SQLException e) {
        }
        return result;
    }
}
