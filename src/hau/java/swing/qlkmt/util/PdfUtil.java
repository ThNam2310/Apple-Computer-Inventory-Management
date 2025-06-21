/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.util;

import com.itextpdf.text.Chunk;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import java.awt.FileDialog;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.awt.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import hau.java.swing.qlkmt.dao.BranchDao;
import hau.java.swing.qlkmt.dao.ImportDetailDao;
import hau.java.swing.qlkmt.dao.ExportDetailDao;
import hau.java.swing.qlkmt.dao.ComputerDao;
import hau.java.swing.qlkmt.dao.ImportDao;
import hau.java.swing.qlkmt.dao.ExportDao;
import hau.java.swing.qlkmt.model.InvoiceDetail;
import hau.java.swing.qlkmt.model.Computer;
import hau.java.swing.qlkmt.model.ImportInvoice;
import hau.java.swing.qlkmt.model.ExportInvoice;
import java.awt.Desktop;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author thanh
 */
public class PdfUtil {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY-HH:mm");
    Document document = new Document();
    FileOutputStream file;
    JFrame jFrame = new JFrame();
    FileDialog fileDialog = new FileDialog(jFrame, "Xuất PDF", FileDialog.SAVE);
    Font fontData;
    Font fontTitle;
    Font fontHeader;

    public PdfUtil() throws IOException {
        try {
            fontData = new Font(BaseFont.createFont("lib/Open_Sans/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
            fontTitle = new Font(BaseFont.createFont("lib/Open_Sans/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontHeader = new Font(BaseFont.createFont("lib/Open_Sans/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
            System.out.println(new File("lib/Open_Sans/OpenSans-Regular.ttf").getAbsolutePath());
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (DocumentException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường dẫn" + url);
            JOptionPane.showMessageDialog(null, "Không gọi được Document");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph paragraphTitle = new Paragraph(new Phrase(title, fontTitle));
            paragraphTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private String getFile(String name) {
        fileDialog.pack();
        fileDialog.setSize(800, 600);
        fileDialog.validate();
        Rectangle rect = jFrame.getContentPane().getBounds();
        double width = fileDialog.getBounds().getWidth();
        double height = fileDialog.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fileDialog.setLocation(leftCorner);
        fileDialog.setFile(name + ".pdf");
        fileDialog.setVisible(true);
        String url = fileDialog.getDirectory() + fileDialog.getFile();
        if (url.equals("null")) {
            return null;
        }
        return url;
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    ///Phiếu nhập
    public void writeImport(String importId) {
        String url = "";
        try {
            fileDialog.setTitle("In phiếu nhập");
            fileDialog.setLocationRelativeTo(null);
            url = getFile(importId);
            if (url == null) {
                return;
            }
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            setTitle("THÔNG TIN PHIẾU NHẬP");

            ImportInvoice importInvoice = ImportDao.getInstance().selectById(importId);

            Chunk glue = new Chunk(new VerticalPositionMark());// Khoảng trống giữa hàng
            Paragraph para1 = new Paragraph();
            para1.setFont(fontData);
            para1.add("Mã phiếu: " + importInvoice.getInvoiceId());
            para1.add("\nThời gian tạo: " + formatDate.format(importInvoice.getCreateTime()));
            para1.setIndentationLeft(40);
            document.add(para1);
            document.add(Chunk.NEWLINE);//add hàng trống để tạo khoảng cách

            // Tao table cho các chi tiết của hóa đơn
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidths(new float[]{10f, 20f, 15f, 5f, 25f});
            PdfPCell cell;

            //set header cho table chi tiết
            pdfTable.addCell(new PdfPCell(new Phrase("Mã máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tên máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Đơn giá", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("SL", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tổng tiền", fontHeader)));

            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            //truyểnf thông tin từng chi tiết vào table
            for (InvoiceDetail invoiceDetail : ImportDetailDao.getInstance().selectAll(importId)) {
                Computer computerModel = ComputerDao.getInstance().selectById(invoiceDetail.getProductId());
                pdfTable.addCell(new PdfPCell(new Phrase(invoiceDetail.getProductId(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(computerModel.getProductName(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(computerModel.getPrice()) + "đ", fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(invoiceDetail.getQuantity()), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(invoiceDetail.getQuantity() * computerModel.getPrice()) + "đ", fontData)));
            }

            document.add(pdfTable);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + formatter.format(importInvoice.getTotalPrice()) + "đ", fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công " + url);
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }
    }

    public void writeExport(String exportId) {
        String url = "";
        try {
            fileDialog.setTitle("In phiếu Xuất");
            fileDialog.setLocationRelativeTo(null);
            url = getFile(exportId);
            if (url == null) {
                return;
            }
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            setTitle("THÔNG TIN PHIẾU XUẤT");

            ExportInvoice exportInvoice = ExportDao.getInstance().selectById(exportId);

            Paragraph para1 = new Paragraph(new Phrase("Mã phiếu: " + exportInvoice.getInvoiceId(), fontData));
            Paragraph para2 = new Paragraph(new Phrase("Thời gian tạo: " + formatDate.format(exportInvoice.getCreateTime()), fontData));
            Paragraph para3 = new Paragraph();
            para3.setFont(fontData);
            para3.add(String.valueOf("Chi nhánh: " + BranchDao.getInstance().selectById(exportInvoice.getBranch()).getBranchName()));
            para1.setIndentationLeft(40);
            para2.setIndentationLeft(40);
            para3.setIndentationLeft(40);
            document.add(para1);
            document.add(para2);
            document.add(para3);
            document.add(Chunk.NEWLINE);

            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidths(new float[]{10f, 20f, 15f, 5f, 25f});
            PdfPCell cell;

            pdfTable.addCell(new PdfPCell(new Phrase("Mã máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tên máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Đơn giá", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("SL", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tổng tiền", fontHeader)));

            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            for (InvoiceDetail invoiceDetail : ExportDetailDao.getInstance().selectAll(exportId)) {
                Computer computer = ComputerDao.getInstance().selectById(invoiceDetail.getProductId());
                pdfTable.addCell(new PdfPCell(new Phrase(invoiceDetail.getProductId(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(computer.getProductName(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(computer.getPrice()) + "đ", fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(invoiceDetail.getQuantity()), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(invoiceDetail.getQuantity() * computer.getPrice()) + "đ", fontData)));
            }
            document.add(pdfTable);
            document.add(Chunk.NEWLINE);
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + formatter.format(exportInvoice.getTotalPrice()) + "đ", fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + url);
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }

    }
}
