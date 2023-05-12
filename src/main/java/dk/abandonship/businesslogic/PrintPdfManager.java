package dk.abandonship.businesslogic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class PrintPdfManager {

    public PrintPdfManager() {

    }

    /**
     * Creates PDF document and puts in data and saves file
     * @param nodeCollection is a collection of custom nodes, that will be printed in the PDF, Order of nodes should the order that it should be written
     * @param path the path on the device the PDF document should be saved to.
     * @param project the project the document belongs to.
     * @param documentation the document that should be printed
     * @throws Exception if an error acours
     */
    public void generatePDF(Collection<DocumentationNode> nodeCollection, String path, Project project, Documentation documentation) throws Exception{

        Document document = new Document();

        try {
            //Makes pdf file document
            PdfWriter.getInstance(document, new FileOutputStream(path + File.separator + "nameDOCtestForPrinting.PDF"));
            document.open();

            var FontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

            var projectName = new Paragraph(project.getName(), FontTitle);
            var title = new Paragraph(documentation.getName(), FontTitle);
            var customer = new Paragraph(project.getCustomer().getName(), FontTitle);

            projectName.setAlignment(Element.ALIGN_CENTER);
            title.setAlignment(Element.ALIGN_CENTER);
            customer.setAlignment(Element.ALIGN_CENTER);

            customer.setSpacingAfter(20);

            document.add(title);
            document.add(projectName);
            document.add(customer);

            for (var node : nodeCollection) {
                if(node instanceof DocumentationTextFieldNode){
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
                    //TODO Print images and their tittle
                    var imgPar = new Paragraph(((DocumentationPictureNode) node).getPictureTitle());
                    var imageData = ((DocumentationPictureNode) node).getImageData();

                    Image img = Image.getInstance(imageData);
                    img.scaleAbsolute(300,300);
                    img.setSpacingAfter(10);

                    document.add(imgPar);
                    document.add(img);
                    document.add(new Paragraph("\n"));
                }
            }

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("PDF ERROR: \n" + e);
        } finally {
            document.close();
        }

    }

}
