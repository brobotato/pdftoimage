import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

public class pdf {
    public static void main(String[] args) throws IOException {
        int imageDPI;
        String fileFormat;

        JTextField DPI = new JTextField("300", 5);
        JTextField format = new JTextField("jpg", 5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("DPI:"));
        myPanel.add(DPI);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("File Format:"));
        myPanel.add(format);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter DPI and File Format", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            imageDPI = Integer.parseInt(DPI.getText());
            fileFormat = format.getText();

            selectPdf(imageDPI, fileFormat);

        }
    }

    //allow images selection for converting
    public static void selectPdf(int dpi, String format) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(false);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            convertPDFToJPG(file.toString(), dpi, format);
        }


    }

    public static void convertPDFToJPG(String src, int dpi, String format) throws IOException {

        try {
            String pdfFilename = src;
            PDDocument document = PDDocument.load(new File(pdfFilename));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCounter = 0;
            for (@SuppressWarnings("unused") PDPage page : document.getPages()) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, dpi, ImageType.RGB);
                ImageIOUtil.writeImage(bim, pdfFilename + "-" + (pageCounter++) + "." + format, dpi);
                // output file as image
            }
            document.close();
        } finally {
        }
    }
}
