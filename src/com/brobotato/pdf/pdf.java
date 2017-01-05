package com.brobotato.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.PDFToImage;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class pdf {
    public static void main(String[] args) throws IOException {

        selectPdf();

    }

    //allow images selection for converting
    public static void selectPdf() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(false);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            convertPDFToJPG(file.toString());
        }


    }

    public static void convertPDFToJPG(String src) throws IOException {

        try {
            String pdfFilename = src;
            PDDocument document = PDDocument.load(new File(pdfFilename));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCounter = 0;
            for (@SuppressWarnings("unused") PDPage page : document.getPages()) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, pdfFilename + "-" + (pageCounter++) + ".jpg", 300);
                // output file as image
            }
            document.close();
        } finally {
        }
    }
}
