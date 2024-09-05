import baseFiles.GenerateInfoFiles;
import reports.GenerateReports;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        GenerateInfoFiles infoFiles = new GenerateInfoFiles();
        //GenerateReports reports = new GenerateReports();

        try {
            infoFiles.createSalesMenFile(10, "John Doe", 123456789L);
            infoFiles.createSalesManInfoFile(5);
            infoFiles.createProductsFile(20);

            //reports.generateReports("path/to/directory");
        } catch (IOException e) {
            System.out.println("Ocurrio un error en el proceso: " + e.getMessage());
        }
    }
}