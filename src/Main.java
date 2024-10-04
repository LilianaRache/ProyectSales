import baseFiles.GenerateInfoFiles;
import reports.GenerateReports;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Inicia creacion de archivos");

        GenerateInfoFiles infoFiles = new GenerateInfoFiles();
        GenerateReports reports = new GenerateReports();

        try {

            boolean processFileSalesMan =  infoFiles.createSalesManInfoFile(5);
            boolean processFileProducts = infoFiles.createProductsFile(20);
            boolean processFileSalesMen = infoFiles.createSalesxMenFile(10);

            if (processFileSalesMan && processFileProducts && processFileSalesMen) {
                reports.generateReports("src/baseFiles/generatedFiles");
                System.out.println("Ejecucion exitosa!");
            } else {
                System.out.println("Algunas operaciones fallaron. No se generarán reportes.");
            }

        } catch (IOException e) {
            throw new IOException("Ocurrio un error en el proceso: ", e);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Ocurrio un error en el proceso: ", e);
        }
    }
}