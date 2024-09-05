package baseFiles;

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
            writer.write("ID;" + id + "\n");
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
                long id = Math.abs(random.nextLong()); // Asegúro que el ID sea positivo
                String firstName = NamePerson.values()[random.nextInt(NamePerson.values().length)].name();
                String lastName = LastNamePerson.values()[random.nextInt(LastNamePerson.values().length)].name();

                writer.write("ID;" + id + ";" + firstName + ";" + lastName + "\n");
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
                double price = random.nextDouble() * 100;
                writer.write("ID;" + id + ";" + name + ";" + price + "\n");
            }
        }
    }



}
