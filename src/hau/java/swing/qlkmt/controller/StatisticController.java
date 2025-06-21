/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.controller;

import com.toedter.calendar.JDateChooser;
import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.DataProductImportExportDao;
import hau.java.swing.qlkmt.dao.ImportDao;
import hau.java.swing.qlkmt.dao.ExportDao;
import hau.java.swing.qlkmt.model.Computer;
import hau.java.swing.qlkmt.model.DataProductImportExport;
import hau.java.swing.qlkmt.model.ImportInvoice;
import hau.java.swing.qlkmt.model.ExportInvoice;
import hau.java.swing.qlkmt.view.StatisticView;
import hau.java.swing.qlkmt.view.DetailImportView;
import hau.java.swing.qlkmt.view.DetailExportView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author thanh
 */
public class StatisticController extends Controller{

    public StatisticView statisticView;
    public DetailImportController detailImportController;
    public DetailExportController detailExportController;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

    public StatisticController(StatisticView statisticView) {
        this.statisticView = statisticView;
    }

    public StatisticController(DetailImportController detailImportController) {
        this.detailImportController = detailImportController;
    }

    public StatisticController(DetailExportController detailExportController) {
        this.detailExportController = detailExportController;
    }
    
    /////////////////////////////Biểu đồ////////////////////////////////////////////
    public JDateChooser jDateChooserFromProduct, jDateChooserToProduct;
    public JComboBox productComboBox;
    public static ArrayList<Computer> allproduct = ComputerDao.getInstance().selectAllQuantity();
    public static ArrayList<DataProductImportExport> arrayListImport, arrayListExport;
    public JLabel numberImportLabel,numberExportLabel,moneyImportLabel,moneyExportLabel;
    //public JPanel southPanel;
    public static JPanel southPanel;
    
    public void loadChart() {
        productComboBox = statisticView.getproductComboBox();
        
        productComboBox.removeAllItems();
        allproduct = ComputerDao.getInstance().selectAllExist();
        for (Computer computerModel : allproduct) {
            productComboBox.addItem(computerModel.getProductId()+ " - " + computerModel.getProductName());
        }
    }
    
    public void checkConditions(JComboBox<String> productComboBox, JDateChooser jDateChooserFromProduct, JDateChooser jDateChooserToProduct) {
        numberImportLabel = statisticView.getnumberImportLabel();
        numberExportLabel = statisticView.getnumberExportLabel();
        moneyImportLabel = statisticView.getmoneyImportLabel();
        moneyExportLabel = statisticView.getmoneyExportLabel();
        southPanel = StatisticView.getsouthPanel();
        
        String selectedItem = (String) productComboBox.getSelectedItem();
        Date fromAll = jDateChooserFromProduct.getDate();
        Date toAll = jDateChooserToProduct.getDate();
        
        //kiểm tra xem cả 3 thành phần đã được chọn hay chưa
        if (selectedItem != null && fromAll != null && toAll != null) {
            
            if (fromAll.before(toAll)) {
                Date from = ChangeFrom(jDateChooserFromProduct.getDate());
                Date to = ChangeTo(jDateChooserToProduct.getDate());
                String[] parts = selectedItem.split(" - ");//tách chuối để lấy mã sản phẩm
                String productId = parts[0]; // tách để lấy thông tin từ mã sản phẩm
                int totalNumberExport = DataProductImportExportDao.getInstance().getTotalExport(productId, from, to);
                int totalNumberImport = DataProductImportExportDao.getInstance().getTotalImport(productId, from, to);
                long totalMoneyImport = DataProductImportExportDao.getInstance().getTotalImportAmount(productId, from, to);
                long totalMoneyExport = DataProductImportExportDao.getInstance().getTotalExportAmount(productId, from, to);

                String totalNumberExportField = String.valueOf(totalNumberExport);
                String totalNumberImportField = String.valueOf(totalNumberImport);

                double totalMoneyImportField = Double.valueOf(totalMoneyImport);
                double totalMoneyExportField = Double.valueOf(totalMoneyExport);
                
                if (createBarChartPanel(productId, from, to) == null) {
                    southPanel.removeAll();
                    JOptionPane.showMessageDialog(statisticView, "Sản phẩm này không có dữ liệu nhập xuất trong khoảng thời gian bạn chọn!");
                    numberExportLabel.setText("0");
                    numberImportLabel.setText("0");
                    moneyImportLabel.setText("đ");
                    moneyExportLabel.setText("đ");
                    southPanel.revalidate();
                    southPanel.repaint();
                }
                
                southPanel.removeAll();
                southPanel.add(createBarChartPanel(productId, from, to), BorderLayout.CENTER);
                numberExportLabel.setText(totalNumberExportField);
                numberImportLabel.setText(totalNumberImportField);
                moneyImportLabel.setText(formatter.format(totalMoneyImportField) + "đ");
                moneyExportLabel.setText(formatter.format(totalMoneyExportField) + "đ");

                southPanel.revalidate();
                southPanel.repaint();
            } else if (fromAll.after(toAll)) {
                southPanel.removeAll();
                JOptionPane.showMessageDialog(statisticView, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                jDateChooserFromProduct.setCalendar(null);
                jDateChooserToProduct.setCalendar(null);
                numberExportLabel.setText("0");
                numberImportLabel.setText("0");
                moneyImportLabel.setText("đ");
                moneyExportLabel.setText("đ");
                southPanel.revalidate();
                southPanel.repaint();
            }
        }
    }
    
    public JPanel createBarChartPanel(String productId, Date from, Date to) {
        // Lấy dữ liệu
        arrayListImport = DataProductImportExportDao.getInstance().getDataProductImport(productId, from, to);
        arrayListExport = DataProductImportExportDao.getInstance().getDataProductExport(productId, from, to);

        if (arrayListImport == null && arrayListExport == null) {
            return null;
        }
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");

        System.out.println(from);
        System.out.println(to);

        int maxValue = 0;
        // Tạo dataset cho BarChart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        TreeMap<Date, List<String>> sortedMap = new TreeMap<>();

        if (arrayListImport == null && arrayListExport != null) {
            System.out.println("null 1 bên");
            for (var phieuXuat : arrayListExport) {
                String timeCreate = simpleDateFormat.format(Date.from(phieuXuat.getCreateTime().toInstant()));
                int numberExport = phieuXuat.getQuantity();
                if (numberExport > maxValue) {
                    maxValue = numberExport;
                }
                dataset.addValue(numberExport, "Xuất", timeCreate);
            }
        } else if (arrayListImport != null && arrayListExport == null) {
            System.out.println("null 1 bên");
            for (var phieuNhap : arrayListImport) {
                String timeCreate = simpleDateFormat.format(Date.from(phieuNhap.getCreateTime().toInstant()));
                int numberImport = phieuNhap.getQuantity();
                if (numberImport > maxValue) {
                    maxValue = numberImport;
                }
                dataset.addValue(numberImport, "Nhập", timeCreate);
            }
        } else {
            for (var importInvoice : arrayListImport) {
                Date timeCreate = Date.from(importInvoice.getCreateTime().toInstant());
                String type = "Nhập";
                int numberImport = importInvoice.getQuantity();
                if (numberImport > maxValue) {
                    maxValue = numberImport;
                }
                double price = importInvoice.getPrice();
                System.out.println(timeCreate);
                System.out.println(importInvoice.getQuantity());
                System.out.println(importInvoice.getPrice());
                sortedMap.computeIfAbsent(timeCreate, k -> new ArrayList<>())
                        .add(String.format("%s,%d,%.2f", type, numberImport, price));
            }
            for (var exportInvoice : arrayListExport) {
                Date timeCreate = Date.from(exportInvoice.getCreateTime().toInstant());
                String type = "Xuất";
                int numberExport = exportInvoice.getQuantity();
                if (numberExport > maxValue) {
                    maxValue = numberExport;
                }
                double price = exportInvoice.getPrice();
                System.out.println(timeCreate);
                System.out.println(exportInvoice.getQuantity());
                System.out.println(exportInvoice.getPrice());
                sortedMap.computeIfAbsent(timeCreate, k -> new ArrayList<>())
                        .add(String.format("%s,%d,%s", type, numberExport, price));
            }
            for (Map.Entry<Date, List<String>> entry : sortedMap.entrySet()) {
                Date tgTao = entry.getKey();
                List<String> values = entry.getValue();

                String currentDate = simpleDateFormat.format(tgTao);

                for (String value : values) {
                    String[] parts = value.split(",");
                    String type = parts[0]; // "Nhập" hoặc "Xuất"
                    int number = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    System.out.println(currentDate);
                    System.out.println(number);
                    System.out.println(price);
                    dataset.addValue(number, type, currentDate);
                }
            }
        }
        // Sử dụng Calendar để duyệt qua các ngày
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        // Tạo biểu đồ cột
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ nhập xuất", // Tiêu đề
                "Ngày", // Trục X
                "Số lượng", // Trục Y
                dataset, // Dữ liệu
                PlotOrientation.VERTICAL,
                true, // Hiển thị legend
                true, // Tooltips
                false // URLs
        );

        // Tùy chỉnh hiển thị
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();

        // Tùy chỉnh màu sắc
        renderer.setSeriesPaint(0, Color.RED);  // Nhập
        renderer.setSeriesPaint(1, Color.BLUE); // Xuất

        plot.setRenderer(renderer);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Hiển thị số nguyên
        yAxis.setRange(0, maxValue*1.1); // Thiết lập phạm vi trục Y

        return new ChartPanel(barChart);
    }
    
    

    ///////////////////phiếu nhập//////////////////////////
    public JTable importTable;
    public JButton refreshImportButton;
    public JTextField priceFromImportField, priceToImportField;
    public JScrollPane jScrollPaneImport;
    public JLabel invoiceNumberImportLabel, moneyNumberImportLabel;
    public JDateChooser jDateChooserFromImport, jDateChooserToImport;
    public DefaultTableModel tableImportModel;

    public void loadDataTableImport() {
        try {
            tableImportModel = statisticView.gettableImportModel();

            ArrayList<ImportInvoice> arrayListInvoice = ImportDao.getInstance().selectAll();
            tableImportModel.setRowCount(0);
            for (int i = 0; i < arrayListInvoice.size(); i++) {
                //Date thoiGianTao = arrayListInvoice.get(i).getCreateTime();
//                System.out.println("Thời gian tạo gốc: " + thoiGianTao);
//                System.out.println("Thời gian tạo sau format: " + formatDate.format(thoiGianTao));
                tableImportModel.addRow(new Object[]{
                    i + 1, arrayListInvoice.get(i).getInvoiceId(), 
                    formatDate.format(arrayListInvoice.get(i).getCreateTime()),
                    formatter.format(arrayListInvoice.get(i).getTotalPrice()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public void loadDataTableSearchImport(ArrayList<ImportInvoice> result) {
        tableImportModel = statisticView.gettableImportModel();

        try {
            tableImportModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                tableImportModel.addRow(new Object[]{
                    i + 1, result.get(i).getInvoiceId(), 
                    formatDate.format(result.get(i).getCreateTime()),
                    formatter.format(result.get(i).getTotalPrice()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public String createIdImport(ArrayList<ImportInvoice> arrayList) {
        int id = arrayList.size() + 1;
        String check = "";
        for (ImportInvoice invoiceImport : arrayList) {
            if (invoiceImport.getInvoiceId().equals("PN" + id)) {
                check = invoiceImport.getInvoiceId();
            }
        }
        while (check.length() != 0) {
            id++;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getInvoiceId().equals("PN" + id)) {
                    check = arrayList.get(i).getInvoiceId();
                }
            }
        }
        return "PN" + id;
    }
    
    public void viewButtonActionPerformed(ActionEvent e) {
        importTable = statisticView.getImportTable();

        if (importTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(statisticView, "Vui lòng chọn phiếu!");
        } else {
            DetailImportView detailImportView = new DetailImportView(statisticView, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(statisticView), true);
            detailImportView.setVisible(true);
        }
    }

    public void refreshButtonPNActionPerformed(ActionEvent e) {
        jDateChooserFromImport = statisticView.getjDateChooserFromImport();
        jDateChooserToImport = statisticView.getjDateChooserToImport();
        priceFromImportField = statisticView.getpriceFromImportField();
        priceToImportField = statisticView.getpriceToImportField();

        loadDataTableImport();
        jDateChooserFromImport.setCalendar(null);
        jDateChooserToImport.setCalendar(null);
        priceToImportField.setText("");
        priceFromImportField.setText("");
    }

    public void jDateChooserFromPNPropertyChane(PropertyChangeEvent evt) {
        searchAllFilterImport();
    }

    public void jDateChooserFromPNKeyReleased(KeyEvent evt) {
        searchAllFilterImport();
    }

    public void jDateChooserToPNPropertyChane(PropertyChangeEvent evt) {
        searchAllFilterImport();
    }

    public void jDateChooserToPNKeyReleased(KeyEvent evt) {
        searchAllFilterImport();
    }

    public void priceFromImportFieldActionPerformed(ActionEvent e) {
        searchAllFilterImport();
    }

    public void priceFromImportFieldKeyReleased(KeyEvent evt) {
        searchAllFilterImport();
    }

    public void priceToImportFieldActionPerformed(ActionEvent e) {
        searchAllFilterImport();
    }

    public void priceToImportFieldKeyReleased(KeyEvent evt) {
        priceToImportField = statisticView.priceToImportField;

        searchAllFilterImport();
        System.out.println(priceToImportField.getText());
    }

    public ImportInvoice getInvoiceImport() {
        importTable = statisticView.getImportTable();
        tableImportModel = statisticView.gettableImportModel();

        int i_row = importTable.getSelectedRow();
        ImportInvoice invoiceImport = ImportDao.getInstance().selectById(tableImportModel.getValueAt(i_row, 1).toString());
        return invoiceImport;
    }

    public boolean checkDateImport(Date dateTest, Date star, Date end) {
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    public void searchAllFilterImport() {
        ArrayList<ImportInvoice> result = new ArrayList<>();
        if (result.isEmpty()) {
            result = ImportDao.getInstance().selectAll();
        }
        Iterator<ImportInvoice> iterator = result.iterator();

        jDateChooserFromImport = statisticView.getjDateChooserFromImport();
        jDateChooserToImport = statisticView.getjDateChooserToImport();
        priceFromImportField = statisticView.getpriceFromImportField();
        priceToImportField = statisticView.getpriceToImportField();
        invoiceNumberImportLabel = statisticView.getinvoiceNumberImportLabel();
        moneyNumberImportLabel = statisticView.getmoneyNumberImportLabel();

        System.out.println("From Date: " + jDateChooserFromImport.getDate());
        System.out.println("To Date: " + jDateChooserToImport.getDate());

        if (jDateChooserFromImport.getDate() != null || jDateChooserToImport.getDate() != null) {
            Date from;
            Date to;
            if (jDateChooserFromImport.getDate() != null && jDateChooserToImport.getDate() == null) {
                from = ChangeFrom(jDateChooserFromImport.getDate());
                to = ChangeTo(new Date());
                while (iterator.hasNext()) {
                    ImportInvoice phieu = iterator.next();
                    if (!checkDateImport(phieu.getCreateTime(), from, to)) {
                        iterator.remove();
                    }
                }
            } else if (jDateChooserToImport.getDate() != null && jDateChooserFromImport.getDate() == null) {
                try {
                    String sDate1 = "01/01/2024";
                    from = ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                    to = ChangeTo(jDateChooserToImport.getDate());
                    while (iterator.hasNext()) {
                        ImportInvoice phieu = iterator.next();
                        if (!checkDateImport(phieu.getCreateTime(), from, to)) {
                            iterator.remove();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(StatisticView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                from = ChangeFrom(jDateChooserFromImport.getDate());
                to = ChangeTo(jDateChooserToImport.getDate());
                if (from.getTime() > to.getTime()) {
                    JOptionPane.showMessageDialog(statisticView, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    jDateChooserFromImport.setCalendar(null);
                    jDateChooserToImport.setCalendar(null);
                } else {
                    while (iterator.hasNext()) {
                        ImportInvoice phieu = iterator.next();
                        if (!checkDateImport(phieu.getCreateTime(), from, to)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        ArrayList<ImportInvoice> result1 = new ArrayList<>();
        if (priceFromImportField.getText().length() > 0 || priceToImportField.getText().length() > 0) {
            double a;
            double b;
            if (priceFromImportField.getText().length() > 0 && priceToImportField.getText().length() == 0) {
                a = Double.parseDouble(priceFromImportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice()>= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (priceFromImportField.getText().length() == 0 && priceToImportField.getText().length() > 0) {;
                b = Double.parseDouble(priceToImportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (priceFromImportField.getText().length() > 0 && priceToImportField.getText().length() > 0) {
                a = Double.parseDouble(priceFromImportField.getText());
                b = Double.parseDouble(priceToImportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice() >= a && result.get(i).getTotalPrice() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        if (priceFromImportField.getText().length() > 0 || priceToImportField.getText().length() > 0) {
            loadDataTableSearchImport(result1);
            double sum = 0;
            for (ImportInvoice pn : result1) {
                sum += pn.getTotalPrice();
            }
            invoiceNumberImportLabel.setText(result1.size() + "");
            moneyNumberImportLabel.setText(formatter.format(sum) + "đ");
        } else {
            loadDataTableSearchImport(result);
            double sum = 0;
            for (ImportInvoice pn : result) {
                sum += pn.getTotalPrice();
            }
            invoiceNumberImportLabel.setText(result.size() + "");
            moneyNumberImportLabel.setText(formatter.format(sum) + "đ");
        }
    }

    /////////////////////////////////phiếu xuất//////////////////////////////////////////
    public JTable exportTable;
    public JButton refreshExportButton;
    public JTextField priceToExportField;
    public JTextField priceFromExportField;
    public JScrollPane jScrollPaneExport;
    public JLabel invoiceNumberExportLabel;
    public JLabel moneyNumberExportLabel;
    public JDateChooser jDateChooserFromExport, jDateChooserToExport;
    public DefaultTableModel tableExportModel;

    public void loadDataTableExport() {
        tableExportModel = statisticView.gettableExportModel();
        exportTable = statisticView.getExportTable();

        try {
            tableExportModel = (DefaultTableModel) exportTable.getModel();
            ArrayList<ExportInvoice> invoice = ExportDao.getInstance().selectAll();
            tableExportModel.setRowCount(0);
            for (int i = 0; i < invoice.size(); i++) {
                tableExportModel.addRow(new Object[]{
                    i + 1, invoice.get(i).getInvoiceId(), BranchDao.getInstance().selectById(invoice.get(i).getBranch()).getBranchName(),
                    formatDate.format(invoice.get(i).getCreateTime()), formatter.format(invoice.get(i).getTotalPrice()) + "đ"
                });
            }
            exportTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        } catch (Exception e) {
        }
    }

    public void loadDataTableSearchExport(ArrayList<ExportInvoice> result) {
        tableExportModel = statisticView.gettableExportModel();

        try {
            tableExportModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                tableExportModel.addRow(new Object[]{
                    i + 1, result.get(i).getInvoiceId(), BranchDao.getInstance().selectById(result.get(i).getBranch()).getBranchName(),
                    formatDate.format(result.get(i).getCreateTime()), formatter.format(result.get(i).getTotalPrice()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }
    
    public String createIdExport(ArrayList<ExportInvoice> arrayListExport) {
        int id = arrayListExport.size() + 1;
        String check = "";
        for (ExportInvoice exportInvoice : arrayListExport) {
            if (exportInvoice.getInvoiceId().equals("PX" + id)) {
                check = exportInvoice.getInvoiceId();
            }
        }
        while (check.length() != 0) {
            id++;
            for (int i = 0; i < arrayListExport.size(); i++) {
                if (arrayListExport.get(i).getInvoiceId().equals("PX" + id)) {
                    check = arrayListExport.get(i).getInvoiceId();
                }
            }
        }
        return "PX" + id;
    }

    public void viewButton1ActionPerformed(ActionEvent e) {
        exportTable = statisticView.getExportTable();

        if (exportTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(statisticView, "Vui lòng chọn phiếu!");
        } else {
            DetailExportView a = new DetailExportView(statisticView, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(statisticView), true);
            a.setVisible(true);
        }
    }

    public void refreshExportButtonActionPerformed(ActionEvent e) {
        jDateChooserFromExport = statisticView.getjDateChooserFromExport();
        jDateChooserToExport = statisticView.getjDateChooserToExport();
        priceFromExportField = statisticView.getpriceFromExportField();
        priceToExportField = statisticView.getpriceToExportField();

        loadDataTableExport();
        jDateChooserFromExport.setCalendar(null);
        jDateChooserToExport.setCalendar(null);
        priceToExportField.setText("");
        priceFromExportField.setText("");
    }

    public void jDateChooserFromExportPropertyChane(PropertyChangeEvent evt) {
        searchAllFilterExport();
    }

    public void jDateChooserFromExportKeyReleased(KeyEvent evt) {
        searchAllFilterExport();
    }

    public void jDateChooserToExportPropertyChane(PropertyChangeEvent evt) {
        searchAllFilterExport();
    }

    public void jDateChooserToExportKeyReleased(KeyEvent evt) {
        searchAllFilterExport();
    }

    public void priceFromExportFieldActionPerformed(ActionEvent e) {
        searchAllFilterExport();
    }

    public void priceFromExportFieldKeyReleased(KeyEvent evt) {
        searchAllFilterExport();
    }

    public void priceToExportFieldActionPerformed(ActionEvent e) {
        searchAllFilterExport();
    }

    public void priceToExportFieldKeyReleased(KeyEvent evt) {
        priceToExportField = statisticView.getpriceToExportField();

        searchAllFilterExport();
        System.out.println(priceToExportField.getText());
    }

    public ExportInvoice getExportInvoice() {
        exportTable = statisticView.getExportTable();
        tableExportModel = statisticView.gettableExportModel();

        int i_row = exportTable.getSelectedRow();
        ExportInvoice exportInvoice = ExportDao.getInstance().selectById(tableExportModel.getValueAt(i_row, 1).toString());
        return exportInvoice;
    }

    public boolean checkDateExport(Date dateTest, Date star, Date end) {
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    public void searchAllFilterExport() {
        ArrayList<ExportInvoice> result = new ArrayList<>();
        if (result.isEmpty()) {
            result = ExportDao.getInstance().selectAll();
        }

        Iterator<ExportInvoice> iterator = result.iterator();

        jDateChooserFromExport = statisticView.getjDateChooserFromExport();
        jDateChooserToExport = statisticView.getjDateChooserToExport();
        priceFromExportField = statisticView.getpriceFromExportField();
        priceToExportField = statisticView.getpriceToExportField();
        invoiceNumberExportLabel = statisticView.getinvoiceNumberExporttLabel();
        moneyNumberExportLabel = statisticView.getmoneyNumberExportLabel();

        if (jDateChooserFromExport.getDate() != null || jDateChooserToExport.getDate() != null) {
            Date from;
            Date to;
            if (jDateChooserFromExport.getDate() != null && jDateChooserToExport.getDate() == null) {
                from = ChangeFrom(jDateChooserFromExport.getDate());
                to = ChangeTo(new Date());
                while (iterator.hasNext()) {
                    ExportInvoice phieu = iterator.next();
                    if (!checkDateExport(phieu.getCreateTime(), from, to)) {
                        iterator.remove();
                    }
                }
            } else if (jDateChooserToExport.getDate() != null && jDateChooserFromExport.getDate() == null) {
                try {
                    String sDate1 = "01/01/2024";
                    from = ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                    to = ChangeTo(jDateChooserToExport.getDate());
                    while (iterator.hasNext()) {
                        ExportInvoice phieu = iterator.next();
                        if (!checkDateExport(phieu.getCreateTime(), from, to)) {
                            iterator.remove();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(StatisticView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                from = ChangeFrom(jDateChooserFromExport.getDate());
                to = ChangeTo(jDateChooserToExport.getDate());
                if (from.getTime() > to.getTime()) {
                    JOptionPane.showMessageDialog(statisticView, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    jDateChooserFromExport.setCalendar(null);
                    jDateChooserToExport.setCalendar(null);
                } else {
                    while (iterator.hasNext()) {
                        ExportInvoice phieu = iterator.next();
                        if (!checkDateExport(phieu.getCreateTime(), from, to)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        ArrayList<ExportInvoice> result1 = new ArrayList<>();
        if (priceFromExportField.getText().length() > 0 || priceToExportField.getText().length() > 0) {
            double a;
            double b;
            if (priceFromExportField.getText().length() > 0 && priceToExportField.getText().length() == 0) {
                a = Double.parseDouble(priceFromExportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice()>= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (priceFromExportField.getText().length() == 0 && priceToExportField.getText().length() > 0) {;
                b = Double.parseDouble(priceToExportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (priceFromExportField.getText().length() > 0 && priceToExportField.getText().length() > 0) {
                a = Double.parseDouble(priceFromExportField.getText());
                b = Double.parseDouble(priceToExportField.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTotalPrice() >= a && result.get(i).getTotalPrice() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        if (priceFromExportField.getText().length() > 0 || priceToExportField.getText().length() > 0) {
            loadDataTableSearchExport(result1);
            double sum = 0;
            for (ExportInvoice px : result1) {
                sum += px.getTotalPrice();
            }
            invoiceNumberExportLabel.setText(result1.size() + "");
            moneyNumberExportLabel.setText(formatter.format(sum) + "đ");
        } else {
            loadDataTableSearchExport(result);
            double sum = 0;
            for (ExportInvoice px : result) {
                sum += px.getTotalPrice();
            }
            invoiceNumberExportLabel.setText(result.size() + "");
            moneyNumberExportLabel.setText(formatter.format(sum) + "đ");
        }
    }
    
    public Date ChangeFrom(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();
        System.out.println("ChangeFrom: Input = " + date + ", Result = " + result);
        return result;
    }

    public Date ChangeTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date result = calendar.getTime();
        System.out.println("ChangeTo: Input = " + date + ", Result = " + result);
        return result;
    }
}
