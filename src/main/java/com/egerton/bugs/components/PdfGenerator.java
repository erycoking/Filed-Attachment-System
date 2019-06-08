package com.egerton.bugs.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

@Component
public class PdfGenerator{

    @Autowired
    private TemplateEngine templateEngine;

    //Method to generate pdf
    public  byte[] createPdf(PdfContent pdfContent, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Assert.notNull(pdfContent.getTemplateName(), "The templateName can not be null");

        //create a web context
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariables(pdfContent.getVariables());

        String templateHtml = templateEngine.process(pdfContent.getTemplateName(), context);

        ByteArrayOutputStream outputStream = null;
        byte[] pdfAsBytes = null;

        String baseUrl = FileSystems.getDefault().getPath("src/main/resources/templates/mails").toUri().toString();
        System.out.println(baseUrl);

        try {

            outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(templateHtml, baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream,false);
            renderer.finishPDF();
            pdfAsBytes = outputStream.toByteArray();
            return pdfAsBytes;

        }catch (IOException io){
            System.err.println(io.getMessage());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            if(outputStream != null) {
                outputStream.close();
            }
        }

        return null;
    }

    //Method to generate pdf for attachment
    public DataSource generatePdf(PdfContent pdfContent){
        Assert.notNull(pdfContent.getTemplateName(), "The templateName can not be null");
        Context context = new Context();
        context.setVariables(pdfContent.getVariables());

        //Process thymeleaf template to for use in generating pdf
        String templateHtml = templateEngine.process(pdfContent.getTemplateName(), context);

        ByteArrayOutputStream outputStream1 = null;
        DataSource dataSource = null;
        try{

            outputStream1 = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            //Construct the text body part of the pdf
            renderer.setDocumentFromString(templateHtml);
            renderer.layout();

            //write pdf content to the output stream
            renderer.createPDF(outputStream1,false);

            renderer.finishPDF();


            byte[] bytes = outputStream1.toByteArray();

            //Construct pdf body part
            dataSource = new ByteArrayDataSource(bytes,"application/pdf");
        }catch (Exception e){

        }finally {
            return dataSource;
        }


    }

}





