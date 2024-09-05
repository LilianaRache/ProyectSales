package baseFiles;

import baseFiles.enums.DocumentType;
import baseFiles.enums.LastNamePerson;
import baseFiles.enums.NamePerson;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    private static final String DIRECTORY_PATH = "src/baseFiles/generatedFiles";

    public GenerateInfoFiles() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }


    public void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        Random random = new Random();
        String fileName = DIRECTORY_PATH +"/salesman_" + name + "_" + id + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String documentType = DocumentType.values()[random.nextInt(DocumentType.values().length)].getCode();
            writer.write(documentType + ";" + id + "\n");

            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(1000);
                int quantitySold = random.nextInt(100);
                writer.write(productId + ";" + quantitySold + "\n");
            }
        }
    }


    public void createSalesManInfoFile(int salesmanCount) throws IOException {
        Random random = new Random();
        String fileName = DIRECTORY_PATH + "/salesmen_info.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < salesmanCount; i++) {
                String documentType = DocumentType.values()[random.nextInt(DocumentType.values().length)].getCode();
                // Generación del ID con exactamente 10 dígitos
                String id = String.format("%010d", random.nextInt(1_000_000_000) + 1_000_000_000);
                String firstName = NamePerson.values()[random.nextInt(NamePerson.values().length)].name();
                String lastName = LastNamePerson.values()[random.nextInt(LastNamePerson.values().length)].name();

                writer.write( documentType + ";" + id + ";" + firstName + ";" + lastName + "\n");
            }
        }
    }


    public void createProductsFile(int productCount) throws IOException {
        Random random = new Random();
        String fileName = DIRECTORY_PATH + "/products_info.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < productCount; i++) {
                int id = random.nextInt(1000);
                String name = "Product" + i;
                int price  = (int) (random.nextDouble() * 100000);
                writer.write( id + ";" + name + ";" + " $" + " "+ price + "\n");
            }
        }
    }



}
