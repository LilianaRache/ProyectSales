package baseFiles;

import baseFiles.enums.DocumentType;
import baseFiles.enums.LastNamePerson;
import baseFiles.enums.NamePerson;
import entities.Seller;

import java.io.*;
import java.util.*;

/**
 * Clase encargada de generar archivos de información aleatoria sobre vendedores y productos.
 * Los archivos generados se guardan en la carpeta 'generatedFiles'.
 * Esta clase es útil para simular un entorno de ventas y productos en un sistema de gestión.
 */
public class GenerateInfoFiles {

    private static final String DIRECTORY_PATH = "src/baseFiles/generatedFiles";
    Seller seller = new Seller();

    /**
     * Constructor de la clase GenerateInfoFiles.
     * Crea el directorio para guardar los archivos generados si no existe.
     */
    public GenerateInfoFiles() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Genera un archivo con información de varios vendedores, incluyendo ID, nombres y apellidos.
     * Cada línea del archivo contendrá el tipo de documento, ID, nombre y apellido de un vendedor.
     *
     * @param salesmanCount número de vendedores para generar.
     *                     Esto determina la cantidad de líneas en el archivo.
     * @throws IOException si ocurre un error al escribir el archivo, como problemas de acceso a la ruta.
     */

    public boolean createSalesManInfoFile(int salesmanCount) throws IOException{
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
            return true;
        }
    }

    /**
     * Genera un archivo con información de productos aleatorios, incluyendo ID, nombre y precio.
     *
     * @param productCount número de productos para generar.
     * @throws IOException si ocurre un error al escribir el archivo, como problemas de acceso a la ruta.
     */

    public boolean createProductsFile(int productCount) throws IOException {
        Random random = new Random();
        String fileName = DIRECTORY_PATH + "/products_info.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < productCount; i++) {
                int id = i;//random.nextInt(10);
                String name = "Product" + i;
                int price  = (int) (random.nextDouble() * 100000);
                writer.write( id + ";" + name + ";" + " $" + " "+ price + "\n");
            }
        }
        return true;
    }

    /**
     * Genera un archivo con información aleatoria de ventas de un vendedor.
     * El archivo contendrá un encabezado con el tipo de documento y el ID del vendedor,
     * seguido de varias líneas que representan ventas con un ID de producto y la cantidad vendida.
     *
     * @param randomSalesCount número de ventas aleatorias a generar para el vendedor.
     * @param name nombre del vendedor. Se utiliza para nombrar el archivo.
     * @param id número de identificación del vendedor. Se utiliza para nombrar el archivo y dentro del contenido.
     * @throws IOException si ocurre un error al escribir el archivo, como problemas de acceso a la ruta.
     */
    public boolean createSalesxMenFile(int randomSalesCount, String name, long id) throws IOException {

        if (!getInfoSeller()){
            return false;
        }
        Random random = new Random();
        String fileName = DIRECTORY_PATH +"/salesBySeller_" + seller.getName() + "_" + seller.getDni() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(seller.getDocumentType() + ";" + seller.getDni() + "\n");

            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(10);
                int quantitySold = random.nextInt(100);
                writer.write(productId + ";" + quantitySold + "\n");
            }
            return true;
        }
    }

    private boolean getInfoSeller() throws IOException {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        for (File file: files){
            if (file.getName().equals("salesmen_info.txt")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String[] infoSeller = br.readLine().split(";");
                    seller.setDocumentType(infoSeller[0]);
                    seller.setDni(Integer.parseInt(infoSeller[1]));
                    seller.setName(infoSeller[2]);
                    seller.setLastName(infoSeller[3]);
                    System.out.println("Seller: " + seller);
                }
            }
        }
       return true;
    }



}
