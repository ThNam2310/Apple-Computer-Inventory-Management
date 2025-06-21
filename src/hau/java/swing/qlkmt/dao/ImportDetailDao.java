/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.dao;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.model.InvoiceDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import hau.java.swing.qlkmt.database.ConnectionDatabase;
import java.sql.SQLException;

public class ImportDetailDao implements DAOInterface<InvoiceDetail> {

    public static ImportDetailDao getInstance() {
        return new ImportDetailDao();
    }

    @Override
    public int insert(InvoiceDetail t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO ChiTietPhieuNhap (maPhieu, maMay, soLuong, donGia) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setString(2, t.getProductId());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getPrice());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int update(InvoiceDetail t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE ChiTietPhieuNhap SET maPhieu = ?, maMay = ?, soLuong = ?, donGia = ? WHERE maPhieu = ? AND maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            ps.setString(2, t.getProductId());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getPrice());
            ps.setString(5, t.getInvoiceId());
            ps.setString(6, t.getProductId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int delete(InvoiceDetail t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getInvoiceId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }
    
    public ArrayList<InvoiceDetail> selectAll(String t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<InvoiceDetail> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String invoiceId = rs.getString("maPhieu");
                String productId = rs.getString("maMay");
                int quantity = rs.getInt("soLuong");
                double price  = rs.getDouble("donGia");
                InvoiceDetail ctp = new InvoiceDetail(invoiceId, productId, quantity, price);
                result.add(ctp);
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<InvoiceDetail> selectAll() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<InvoiceDetail> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String invoiceId = rs.getString("maPhieu");
                String productId = rs.getString("maMay");
                int quantity = rs.getInt("soLuong");
                double price  = rs.getDouble("donGia");
                InvoiceDetail ctp = new InvoiceDetail(invoiceId, productId, quantity, price);
                result.add(ctp);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public InvoiceDetail selectById(String t) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        InvoiceDetail result = null;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap WHERE maPhieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String invoiceId = rs.getString("maPhieu");
                String productId = rs.getString("maMay");
                int quantity = rs.getInt("soLuong");
                double price  = rs.getDouble("donGia");
                result = new InvoiceDetail(invoiceId, productId, quantity, price);
            }
        } catch (SQLException e) {
        }
        return result;
    }

}