package edu.school21.spring.app;

import edu.school21.spring.preprocessor.PreProcessor;
import edu.school21.spring.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.printer.Printer;
import edu.school21.spring.printer.PrinterWithPrefixImpl;
import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix ("Prefix ");
        printer.print ("Hello!") ;

        System.out.println("\n---PRINTER FROM SPRING CONTEXT---");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer sPrinter = context.getBean("printerWithPrefix", Printer.class);
        sPrinter.print("Hello!");

        Printer datePrinter = context.getBean("printerWithDate", Printer.class);
        datePrinter.print("Hello!");
        datePrinter = context.getBean("printerWithDateErr", Printer.class);
        datePrinter.print("Hello!");
    }
}
