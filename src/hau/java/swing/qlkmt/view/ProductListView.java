/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.view;

import hau.java.swing.qlkmt.controller.ExportController;
import hau.java.swing.qlkmt.controller.ImportController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author thanh
 */
public class ProductListView extends JInternalFrameView {

    private JPanel centerPanel;
    public JButton productButton, importButton, exportButton;
    public ProductView productView;
    public ImportView importView;
    public ExportView exportView;
    public ImportController importController;
    public ExportController exportController;
    

    public ProductListView() {
        importController = new ImportController(this);
        exportController = new ExportController(this);
        this.init();
        this.setSize(1180, 800);
        BasicInternalFrameUI gui = (BasicInternalFrameUI) this.getUI();
        gui.setNorthPane(null);
        this.setVisible(true);
        ProductView tsp = new ProductView();
        centerPanel.add(tsp).setVisible(true);
    }

    private void init() {
        productView = new ProductView();
        importView = new ImportView();
        exportView = new ExportView();
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(new Color(94,125,178));
        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(1000, 750));
        
        CardLayout cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout);
        centerPanel.add(productView, "TrangSP");
        centerPanel.add(importView, "TrangNhapHang");
        centerPanel.add(exportView, "TrangXuatHang");
        
        productButton = new JButton("Danh sách sản phẩm");
        productButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        productButton.setForeground(Color.WHITE);
        toolBar.add(productButton);
        productButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        importButton = new JButton("Nhập hàng");
        importButton.setFont(new Font(null, Font.BOLD, 14));
        importButton.setForeground(Color.WHITE);
        toolBar.add(importButton);
        importButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exportButton = new JButton("Xuất hàng");
        exportButton.setFont(new Font(null, Font.BOLD, 14));
        exportButton.setForeground(Color.WHITE);
        toolBar.add(exportButton);
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangSP");
            }
        });

        productButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                productButton.setBackground(new Color(20,49,100));
                importButton.setBackground(new Color(94,125,178));
                exportButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                productButton.setBackground(new Color(20,49,100));
                importButton.setBackground(new Color(94,125,178));
                exportButton.setBackground(new Color(94,125,178));
            }
        });
        ////////////////////////////////////
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangNhapHang");
            }
        });

        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                importButton.setBackground(new Color(20,49,100));
                productButton.setBackground(new Color(94,125,178));
                exportButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                importButton.setBackground(new Color(20,49,100));
                productButton.setBackground(new Color(94,125,178));
                exportButton.setBackground(new Color(94,125,178));
            }
        });
        ////////////////////////////////////
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangXuatHang");
            }
        });

        exportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                exportButton.setBackground(new Color(20,49,100));
                productButton.setBackground(new Color(94,125,178));
                importButton.setBackground(new Color(94,125,178));
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                exportButton.setBackground(new Color(20,49,100));
                productButton.setBackground(new Color(94,125,178));
                importButton.setBackground(new Color(94,125,178));
            }
        });
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        pack();
    }
}
