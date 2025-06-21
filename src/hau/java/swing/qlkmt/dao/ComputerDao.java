package hau.java.swing.qlkmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import hau.java.swing.qlkmt.database.ConnectionDatabase;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import hau.java.swing.qlkmt.model.Computer;
import javax.swing.JOptionPane;

public class ComputerDao implements DAOInterface<Computer> {

    public static ComputerDao getInstance() {
        return new ComputerDao();
    }

    @Override
    public int insert(Computer t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Computer t) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE MayTinh SET tenMay = ?, soLuong = ?, gia = ?, tenCpu=?, ram=?, rom=?, "
                    + "trangThai=?, hinhAnh=?, mauSac = ? WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductName());
            ps.setInt(2, t.getQuantity());
            ps.setDouble(3, t.getPrice());
            ps.setString(4, t.getCpu());
            ps.setString(5, t.getRam());
            ps.setString(6, t.getRom());
            ps.setInt(7, t.getStatus());
            ps.setBytes(8, t.getImage());
            ps.setString(9, t.getColor());
            ps.setString(10, t.getProductId());
            result = ps.executeUpdate(sql);
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ComputerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(Computer t) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM MayTinh WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductId());
            result = ps.executeUpdate(sql);
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return result;
    }


    public int updateQuantity(String productId, int quantity) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE MayTinh SET soLuong = ? WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setString(2, productId);
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return result;
    }

    public int deleteStatus(String productId) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            // truy vấn số lượng sản phẩm   
            String checkQuantitysql = "SELECT soLuong FROM MayTinh WHERE maMay = ?";
            PreparedStatement checkPs = con.prepareStatement(checkQuantitysql);
            checkPs.setString(1, productId);
            ResultSet rs =  checkPs.executeQuery();
            
            if(rs.next()) {
                int quantity = rs.getInt("soLuong");
                if(quantity > 0) {
                    System.out.println("Không thể xóa sản phẩm có số lượng lớn hơn 0");
                    JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm này");
                } else {
                    String sql = "UPDATE MayTinh SET trangThai = 0 WHERE maMay = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, productId);
                    result = ps.executeUpdate();
                }
            } else {
                System.out.println("Sản phẩm không tồn tại!");
                JOptionPane.showMessageDialog(null, "Sản phẩm không tồn tại!");
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }
    
        @Override
    public Computer selectById(String t) {
        // TODO Auto-generated method stub
        Computer result = null;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT maMay, tenMay, soLuong, tenCpu,ram,rom,gia,trangThai,hinhAnh, mauSac FROM MayTinh WHERE maMay = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                double price = rs.getDouble("gia");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                result = new Computer(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return result;
    }
    
    @Override
    public ArrayList<Computer> selectAll() {
        ArrayList<Computer> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT maMay, tenMay, soLuong, tenCpu,ram,rom,gia,trangThai,hinhAnh, mauSac FROM MayTinh ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                double price = rs.getDouble("gia");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                Computer cpt = new Computer(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                result.add(cpt);
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    public ArrayList<Computer> selectAllQuantity() { 
        ArrayList<Computer> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT maMay, tenMay, soLuong, tenCpu,ram,rom,gia,trangThai,hinhAnh, mauSac FROM MayTinh Where soLuong > 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                double price = rs.getDouble("gia");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                Computer cpt = new Computer(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                result.add(cpt);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public ArrayList<Computer> selectAllExist() {
        ArrayList<Computer> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT maMay, tenMay, soLuong, tenCpu,ram,rom,gia,trangThai,hinhAnh, mauSac FROM MayTinh WHERE trangThai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                double price = rs.getDouble("gia");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                Computer cpt = new Computer(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                result.add(cpt);
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    public int getQuantity() {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE trangThai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result++;
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

}
