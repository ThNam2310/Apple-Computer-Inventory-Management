/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.dao;

import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.database.ConnectionDatabase;
import hau.java.swing.qlkmt.model.DataProductImportExport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author ACER
 */
public class  DataProductImportExportDao  implements DAOInterface <InvoiceDetail> {
    
    public static DataProductImportExportDao getInstance() {
        return new DataProductImportExportDao();
    }
    public ArrayList<DataProductImportExport> getDataProductImport (String productId, Date from, Date to) {
        ArrayList<DataProductImportExport> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection(); 
            String sql = "SELECT DATE(pn.thoiGianTao) AS ngayTao,SUM(ctpn.soLuong) AS tongSoLuong "
                    + "FROM phieunhap pn JOIN chitietphieunhap ctpn "
                    + "ON pn.maPhieu = ctpn.maPhieu "
                    + "WHERE pn.thoiGianTao BETWEEN ? AND ? AND ctpn.maMay = ? "
                    + "GROUP BY DATE(pn.thoiGianTao) "
                    + "ORDER BY ngayTao ASC;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            
            if (con == null || con.isClosed()) {
                System.out.println("Database connection is not established.");
            } else {
                System.out.println("Database connection is active.");
            }

            ResultSet rs = ps.executeQuery();                      
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return null;
            } else {
                while (rs.next()) {      
                    Timestamp createTime = rs.getTimestamp("ngayTao");
                    int quantity = rs.getInt("tongSoLuong");
                    double price = 111;
                    DataProductImportExport importData = new DataProductImportExport(createTime, quantity, price);
                    result.add(importData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    public ArrayList<DataProductImportExport> getDataProductExport (String productId, Date from, Date to) {
        ArrayList<DataProductImportExport> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection(); 
            String sql = "SELECT DATE(px.thoiGianTao) AS ngayTao,SUM(ctpx.soLuong) AS tongSoLuong "
                    + "FROM phieuxuat px JOIN chitietphieuxuat ctpx "
                    + "ON px.maPhieu = ctpx.maPhieu "
                    + "WHERE px.thoiGianTao BETWEEN ? AND ? AND ctpx.maMay = ? "
                    + "GROUP BY DATE(px.thoiGianTao) "
                    + "ORDER BY ngayTao ASC;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            
            if (con == null || con.isClosed()) {
                System.out.println("Database connection is not established.");
            } else {
                System.out.println("Database connection is active.");
            }

            ResultSet rs = ps.executeQuery();                      
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return null;
            } else {
                while (rs.next()) {      
                    Timestamp createTime = rs.getTimestamp("ngayTao");
                    int quantity = rs.getInt("tongSoLuong");
                    double price = 111;
                    DataProductImportExport exportData = new DataProductImportExport(createTime, quantity, price);
                    result.add(exportData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    public int getTotalExport(String productId, Date from, Date to) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection(); 
            String sql = "SELECT SUM(ctpx.soLuong) AS tongSoLuong "
                    + " FROM phieuxuat px JOIN chitietphieuxuat ctpx "
                    + " ON px.maPhieu = ctpx.maPhieu "
                    + " WHERE px.thoiGianTao BETWEEN ? AND ? AND ctpx.maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            ResultSet rs = ps.executeQuery();  
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return 0;
            } else {
                if (rs.next()) {
                    result = rs.getInt("tongSoLuong");
                    System.out.println(result);
                } else {
                    System.out.println("No rows found for the given query.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    public int getTotalImport(String productId, Date from, Date to) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection(); 
            String sql = "SELECT SUM(ctpn.soLuong) AS tongSoLuong "
                    + " FROM phieunhap pn JOIN chitietphieunhap ctpn "
                    + " ON pn.maPhieu = ctpn.maPhieu "
                    + " WHERE pn.thoiGianTao BETWEEN ? AND ? AND ctpn.maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            ResultSet rs = ps.executeQuery();  
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return 0;
            } else {
                if (rs.next()) {
                    result = rs.getInt("tongSoLuong");
                    System.out.println(result);
                } else {
                    System.out.println("No rows found for the given query.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    public long getTotalImportAmount(String productId, Date from, Date to) {
        long result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection(); 
             String sql = "SELECT SUM(ctpx.soLuong * ctpx.donGia) AS tongTien "
                    + " FROM phieunhap px JOIN chitietphieunhap ctpx "
                    + " ON px.maPhieu = ctpx.maPhieu "
                    + " WHERE px.thoiGianTao BETWEEN ? AND ? AND ctpx.maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            ResultSet rs = ps.executeQuery();  
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return 0;
            } else {
                if (rs.next()) {
                    result = rs.getLong("tongTien");
                    System.out.println(result);
                } else {
                    System.out.println("No rows found for the given query.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    public long getTotalExportAmount(String productId, Date from, Date to) {
        long result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection(); 
            String sql = "SELECT SUM(ctpx.soLuong * ctpx.donGia) AS tongTien "
                    + " FROM phieuxuat px JOIN chitietphieuxuat ctpx "
                    + " ON px.maPhieu = ctpx.maPhieu "
                    + " WHERE px.thoiGianTao BETWEEN ? AND ? AND ctpx.maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            ps.setString(3, productId);
            ResultSet rs = ps.executeQuery();  
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
                return 0;
            } else {
                if (rs.next()) {
                    result = rs.getLong("tongTien");
                    System.out.println(result);
                } else {
                    System.out.println("No rows found for the given query.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing query.");
        }
        return result;
    }
    
    @Override
    public int insert(InvoiceDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(InvoiceDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(InvoiceDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<InvoiceDetail> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public InvoiceDetail selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
