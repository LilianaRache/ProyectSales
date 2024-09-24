import baseFiles.GenerateInfoFiles;
import reports.GenerateReports;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Inicia creacion de archivos");

        GenerateInfoFiles infoFiles = new GenerateInfoFiles();
        GenerateReports reports = new GenerateReports();

        try {
            infoFiles.createSalesMenFile(10, "LUCIA", 1838148448);
            infoFiles.createSalesManInfoFile(5);
            infoFiles.createProductsFile(20);

            reports.generateReports("src/baseFiles/generatedFiles");

            System.out.println("Ejecucion exitosa!");

        } catch (IOException e) {
            System.out.println("Ocurrio un error en el proceso: " + e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}