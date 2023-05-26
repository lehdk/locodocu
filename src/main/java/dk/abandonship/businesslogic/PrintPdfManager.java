package dk.abandonship.businesslogic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dk.abandonship.Main;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.*;

public class PrintPdfManager {

    public PrintPdfManager() {

    }

    /**
     * Creates PDF document and puts in data and saves file
     *
     * @param nodeCollection is a collection of custom nodes, that will be printed in the PDF, Order of nodes should the order that it should be written
     * @param path           the path on the device the PDF document should be saved to.
     * @param project        the project the document belongs to.
     * @param documentation  the document that should be printed
     * @throws Exception if an error acours
     */
    public void generatePDF(Collection<DocumentationNode> nodeCollection, String path, Project project, Documentation documentation) throws Exception {

        Document document = new Document();

        try {
            //Makes pdf file document
            PdfWriter.getInstance(document, new FileOutputStream(path + File.separator + "nameDOCtestForPrinting.PDF"));
            document.open();

            var fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

            var projectName = new Paragraph(project.getName(), fontTitle);
            var title = new Paragraph(documentation.getName(), fontTitle);
            var customer = new Paragraph(project.getCustomer().getName(), fontTitle);

            projectName.setAlignment(Element.ALIGN_CENTER);
            title.setAlignment(Element.ALIGN_CENTER);
            customer.setAlignment(Element.ALIGN_CENTER);

            customer.setSpacingAfter(20);

            document.add(title);
            document.add(projectName);
            document.add(customer);

            for (var node : nodeCollection) {
                if (node instanceof DocumentationTextFieldNode) {
                    var par = new Paragraph(((DocumentationTextFieldNode) node).getText());
                    par.setSpacingAfter(10);
                    document.add(par);

                } else if (node instanceof DocumentationLogInNode) {
                    PdfPTable table = new PdfPTable(3);

                    PdfPCell c1 = new PdfPCell(new Phrase("Device Name"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("User Name"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Password"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    table.setHeaderRows(1);

                    table.addCell(((DocumentationLogInNode) node).getDevice());
                    table.addCell(((DocumentationLogInNode) node).getUsername());
                    table.addCell(((DocumentationLogInNode) node).getPassword());

                    table.setSpacingAfter(10);
                    document.add(table);

                } else if (node instanceof DocumentationPictureNode) {
                    var imgPar = new Paragraph(((DocumentationPictureNode) node).getPictureTitle());
                    var imageData = ((DocumentationPictureNode) node).getImageData();

                    Image img = Image.getInstance(imageData);
                    img.scaleAbsolute(300, 300);
                    img.setSpacingAfter(10);

                    document.add(imgPar);
                    document.add(img);
                    document.add(new Paragraph("\n"));

                } else if (node instanceof CanvasDocumentationNode) {
                    document.newPage();

                    var imgPar = new Paragraph("\n\n Technical Drawing\n", fontTitle);
                    var imageData = ((CanvasDocumentationNode) node).getImageData();

                    Image img = Image.getInstance(imageData);
                    img.scaleAbsolute(540, 300);
                    img.setSpacingAfter(10);

                    document.add(imgPar);
                    document.add(img);

                    document.add(new Paragraph("\n"));

                    Path pathMonitor = Path.of(Main.class.getResource("img/Canvas/monitor.png").toURI());
                    Path pathSpeaker = Path.of(Main.class.getResource("img/Canvas/speaker.png").toURI());
                    Path pathWifi = Path.of(Main.class.getResource("img/Canvas/wifi.png").toURI());
                    Path pathJunction = Path.of(Main.class.getResource("img/Canvas/JunctionBoxpng.png").toURI());

                    Image monitor = Image.getInstance(String.valueOf(pathMonitor));
                    Image speaker = Image.getInstance(String.valueOf(pathSpeaker));
                    Image wifi = Image.getInstance(String.valueOf(pathWifi));
                    Image junction = Image.getInstance(String.valueOf(pathJunction));

                    monitor.scaleAbsolute(32, 32);
                    speaker.scaleAbsolute(32, 32);
                    wifi.scaleAbsolute(32, 32);
                    junction.scaleAbsolute(36, 36);

                    document.add(monitor);
                    document.add(new Paragraph("Monitor/Screen"));
                    document.add(speaker);
                    document.add(new Paragraph("Speaker System"));
                    document.add(wifi);
                    document.add(new Paragraph("Wifi router"));
                    document.add(junction);
                    document.add(new Paragraph("JunctionBox (Maneges connections between different installations)"));

                    document.newPage();
                }
            }

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("PDF ERROR: \n" + e);
        } finally {
            document.close();
        }

    }

}
