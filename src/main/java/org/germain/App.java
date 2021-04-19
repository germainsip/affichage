package org.germain;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        try {
            XWPFDocument document = new XWPFDocument(OPCPackage.open("Salut.docx"));
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    text = text.replace("${nom}", "Germain");
                    run.setText(text,0);
                    System.out.println(text);
                }
            }
            document.write(new FileOutputStream("output.docx"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }



}
