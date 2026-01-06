package id.unpas.sportify.util;


import id.unpas.sportify.controller.BookingController;
import id.unpas.sportify.controller.LapanganController;
import id.unpas.sportify.controller.PelangganController;
import id.unpas.sportify.model.Booking;
import id.unpas.sportify.model.Lapangan;
import id.unpas.sportify.model.Pelanggan;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Utility class untuk export data ke format PDF
 * Menggunakan library iText
 */
public class PDFExporter {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", new Locale("id", "ID"));

    /**
     * Export data lapangan ke PDF
     */
    public static void exportLapangan(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan Lapangan");
        fileChooser.setSelectedFile(new File("Laporan_Lapangan.pdf"));

        int result = fileChooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new File(file.getAbsolutePath() + ".pdf");
        }

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Header
            addHeader(document, "LAPORAN DATA LAPANGAN");

            // Tabel
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.5f, 2f, 1.5f, 1.5f, 1.2f, 2f});

            // Header tabel
            addTableHeader(table, new String[]{"No", "Nama Lapangan", "Jenis", "Harga/Jam", "Status", "Deskripsi"});

            // Data
            LapanganController controller = new LapanganController();
            List<Lapangan> lapanganList = controller.getAllLapangan();

            int no = 1;
            for (Lapangan lap : lapanganList) {
                table.addCell(createCell(String.valueOf(no++), NORMAL_FONT));
                table.addCell(createCell(lap.getNamaLapangan(), NORMAL_FONT));
                table.addCell(createCell(lap.getJenisLapangan(), NORMAL_FONT));
                table.addCell(createCell(currencyFormat.format(lap.getHargaPerJam()), NORMAL_FONT));
                table.addCell(createCell(lap.getStatusLapangan(), NORMAL_FONT));
                table.addCell(createCell(lap.getDeskripsi() != null ? lap.getDeskripsi() : "-", NORMAL_FONT));
            }

            document.add(table);

            // Footer
            addFooter(document, lapanganList.size());

            document.close();

            JOptionPane.showMessageDialog(parent,
                    "Laporan berhasil disimpan ke:\n" + file.getAbsolutePath(),
                    "Export Berhasil", JOptionPane.INFORMATION_MESSAGE);

            // Buka file PDF
            java.awt.Desktop.getDesktop().open(file);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                    "Gagal membuat laporan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Export data pelanggan ke PDF
     */
    public static void exportPelanggan(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan Pelanggan");
        fileChooser.setSelectedFile(new File("Laporan_Pelanggan.pdf"));

        int result = fileChooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new File(file.getAbsolutePath() + ".pdf");
        }

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Header
            addHeader(document, "LAPORAN DATA PELANGGAN");

            // Tabel
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.5f, 2f, 1.5f, 2f, 2f});

            // Header tabel
            addTableHeader(table, new String[]{"No", "Nama Pelanggan", "No. Telepon", "Email", "Alamat"});

            // Data
            PelangganController controller = new PelangganController();
            List<Pelanggan> pelangganList = controller.getAllPelanggan();

            int no = 1;
            for (Pelanggan pel : pelangganList) {
                table.addCell(createCell(String.valueOf(no++), NORMAL_FONT));
                table.addCell(createCell(pel.getNamaPelanggan(), NORMAL_FONT));
                table.addCell(createCell(pel.getNoTelepon(), NORMAL_FONT));
                table.addCell(createCell(pel.getEmail() != null ? pel.getEmail() : "-", NORMAL_FONT));
                table.addCell(createCell(pel.getAlamat() != null ? pel.getAlamat() : "-", NORMAL_FONT));
            }

            document.add(table);

            // Footer
            addFooter(document, pelangganList.size());

            document.close();

            JOptionPane.showMessageDialog(parent,
                    "Laporan berhasil disimpan ke:\n" + file.getAbsolutePath(),
                    "Export Berhasil", JOptionPane.INFORMATION_MESSAGE);

            // Buka file PDF
            java.awt.Desktop.getDesktop().open(file);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                    "Gagal membuat laporan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Export data booking ke PDF
     */
    public static void exportBooking(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan Booking");
        fileChooser.setSelectedFile(new File("Laporan_Booking.pdf"));

        int result = fileChooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new File(file.getAbsolutePath() + ".pdf");
        }

        try {
            Document document = new Document(PageSize.A4.rotate()); // Landscape
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Header
            addHeader(document, "LAPORAN DATA BOOKING");

            // Tabel
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.4f, 1.5f, 1.5f, 1f, 1f, 1.2f, 0.8f, 1.5f});

            // Header tabel
            addTableHeader(table, new String[]{"No", "Lapangan", "Pelanggan", "Tanggal", "Jam", "Total Harga", "Status", "Keterangan"});

            // Data
            BookingController controller = new BookingController();
            List<Booking> bookingList = controller.getAllBooking();

            int no = 1;
            for (Booking book : bookingList) {
                table.addCell(createCell(String.valueOf(no++), NORMAL_FONT));
                table.addCell(createCell(book.getNamaLapangan(), NORMAL_FONT));
                table.addCell(createCell(book.getNamaPelanggan(), NORMAL_FONT));
                table.addCell(createCell(book.getTanggalBooking().toString(), NORMAL_FONT));
                table.addCell(createCell(book.getJamMulai().toString().substring(0, 5) + "-" +
                        book.getJamSelesai().toString().substring(0, 5), NORMAL_FONT));
                table.addCell(createCell(currencyFormat.format(book.getTotalHarga()), NORMAL_FONT));
                table.addCell(createCell(book.getStatusBooking(), NORMAL_FONT));
                table.addCell(createCell(book.getKeterangan() != null ? book.getKeterangan() : "-", NORMAL_FONT));
            }

            document.add(table);

            // Footer
            addFooter(document, bookingList.size());

            document.close();

            JOptionPane.showMessageDialog(parent,
                    "Laporan berhasil disimpan ke:\n" + file.getAbsolutePath(),
                    "Export Berhasil", JOptionPane.INFORMATION_MESSAGE);

            // Buka file PDF
            java.awt.Desktop.getDesktop().open(file);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                    "Gagal membuat laporan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addHeader(Document document, String title) throws DocumentException {
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);

        // Judul
        Paragraph titlePara = new Paragraph(title, TITLE_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        header.add(titlePara);

        // Sub judul
        Paragraph subTitle = new Paragraph("Aplikasi Booking Lapangan", HEADER_FONT);
        subTitle.setAlignment(Element.ALIGN_CENTER);
        header.add(subTitle);

        // Tanggal cetak
        Paragraph datePara = new Paragraph("Dicetak pada: " + dateFormat.format(new Date()), SMALL_FONT);
        datePara.setAlignment(Element.ALIGN_CENTER);
        header.add(datePara);

        header.setSpacingAfter(20);

        document.add(header);
    }

    private static void addTableHeader(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(66, 139, 202));
            cell.setPadding(8);
            table.addCell(cell);
        }
    }

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static void addFooter(Document document, int totalData) throws DocumentException {
        document.add(new Paragraph(" "));

        Paragraph footer = new Paragraph();
        footer.add(new Paragraph("Total Data: " + totalData + " record(s)", NORMAL_FONT));
        footer.add(new Paragraph(" "));
        footer.add(new Paragraph("--- Akhir Laporan ---", SMALL_FONT));
        footer.setAlignment(Element.ALIGN_CENTER);

        document.add(footer);
    }
}
