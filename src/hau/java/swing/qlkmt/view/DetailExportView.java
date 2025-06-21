/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

/**
 *
 * @author thanh
 */
import hau.java.swing.qlkmt.controller.DetailExportController;
import hau.java.swing.qlkmt.model.ExportInvoice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

public class DetailExportView extends JDialog {

    DetailExportController detailExportController = new DetailExportController(this);

    public StatisticView parrent;
    public JLabel invoiceIdLabel, timeCreateLabel, timeLabel, idLabel, totalMoneyLabel, branchLabel, branchNameLabel;
    public JTable invoiceDetailTable;
    public JButton exportButton;

    public DetailExportView(JInternalFrame parrent, JFrame owner, boolean modal) {
        super(owner, modal);
        this.parrent = (StatisticView) parrent;
        System.out.println(this.parrent.statisticController.getExportInvoice().getInvoiceId().toString());
        this.setSize(800, 500);
        init();
        setLocationRelativeTo(null);
        ExportInvoice exportInvoice = this.parrent.statisticController.getExportInvoice();
        idLabel.setText(exportInvoice.getInvoiceId());
        totalMoneyLabel.setText(this.parrent.getFormatter().format(exportInvoice.getTotalPrice()) + "đ");
        timeLabel.setText(this.parrent.getFormatDate().format(exportInvoice.getCreateTime()));
        branchNameLabel.setText(exportInvoice.getBranch());
        detailExportController.loadDataTablePro();
        setWidthTable();
    }

    public void setWidthTable() {
        invoiceDetailTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        invoiceDetailTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        invoiceDetailTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        invoiceDetailTable.getColumnModel().getColumn(3).setPreferredWidth(5);
    }

    public JTable getDetailTable() {
        return invoiceDetailTable;
    }

    public StatisticView getParrent() {
        return parrent;
    }

    private void init() {
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        headPanel.setBackground(new Color(94,125,178));
        JLabel title = new JLabel("CHI TIẾT PHIẾU XUẤT");
        title.setForeground(ColorUIResource.WHITE);
        title.setFont(new FontUIResource("Segoe UI", Font.BOLD, 24));
        headPanel.add(title, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));

        invoiceIdLabel = new JLabel("Mã phiếu:");
        invoiceIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel1.add(invoiceIdLabel);

        idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        idLabel.setForeground(Color.red);
        panel1.add(idLabel);
        mainPanel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        timeCreateLabel = new JLabel("Thời gian tạo:");
        timeCreateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel2.add(timeCreateLabel);

        timeLabel = new JLabel("TIME:");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.red);
        panel2.add(timeLabel);
        mainPanel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        branchLabel = new JLabel("Chi nhánh:");
        branchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel3.add(branchLabel);

        branchNameLabel = new JLabel("CN:");
        branchNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        branchNameLabel.setForeground(Color.red);
        panel3.add(branchNameLabel);
        mainPanel.add(panel3);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(new Dimension(700, 290));
        invoiceDetailTable = new JTable();
        invoiceDetailTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "STT", "Mã máy", "Tên máy", "Số lượng", "Đơn giá", "Thành tiền"
                }
        ));
        JScrollPane Scroll = new JScrollPane(invoiceDetailTable);
        Scroll.setViewportView(invoiceDetailTable);
        tablePanel.add(Scroll, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));

        JLabel totalLabel = new JLabel("TỔNG TIỀN: ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalMoneyLabel = new JLabel("...đ");
        totalMoneyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalMoneyLabel.setForeground(Color.red);
        totalPanel.add(totalLabel);
        totalPanel.add(totalMoneyLabel, BorderLayout.EAST);
        bottomPanel.add(totalPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        exportButton = new JButton("Xuất PDF");
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setForeground(Color.WHITE);
        exportButton.setBackground(new Color(20,49,100));
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        exportButton.setFocusable(false);
        buttonPanel.add(exportButton);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    detailExportController.exportButtonAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(DetailExportView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bottomPanel.add(buttonPanel, BorderLayout.WEST);

        getContentPane().add(headPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }
}
