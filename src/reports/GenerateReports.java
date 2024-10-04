package reports;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class GenerateReports {

    private final List<Map<String, Integer>> salesMap = new ArrayList<>();
    private final Map<String, Integer> productMap = new HashMap<>();
    private final Map<String, String> vendedorMap = new HashMap<>();


    public void generateReports(String directoryPath) throws IOException, InterruptedException, ExecutionException {
        List<Path> fileSalesBySeller = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Void>> futures = new ArrayList<>();
        File directory = new File(directoryPath);

        try {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) { // Verifica que sea un archivo regular
                        futures.add(executor.submit(() -> { //Aquí, se utiliza un ExecutorService para ejecutar el método
                            // processFile(path) en un hilo separado para cada archivo. submit envía una tarea para su ejecución
                            // en segundo plano, y Future<Void> se usa para esperar a que la tarea termine.
                            System.out.println("Procesando archivo " + file.getName() + " en el hilo: " + Thread.currentThread().getName());
                            String fileName = file.toPath().getFileName().toString();
                            if (!fileName.contains("salesBy")) {
                                processFile(file.toPath());
                            } else {
                                fileSalesBySeller.add(file.toPath());
                            }
                            return null;
                        }));
                    }

                }
            }

            for (Future<Void> future : futures) {
                future.get();
            }

            executor.shutdown();
            processSaleBySellerFile(fileSalesBySeller);
            generateSalesmanReport();
            generateProductReport();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar reportes: ", e);
        }
    }

    private void processFile(Path path) {
        try{
            String fileName = path.getFileName().toString();
            List<String> lines = Files.readAllLines(path);

            if (fileName.contains("products")) {
                processProductFile(lines);
                System.out.println("Archivo de productos procesado.");

            } else if (fileName.contains("salesmen")) {
                processSalesmanInfoFile(lines);
                System.out.println("Archivo de vendedores procesado.");
            }
        } catch (IOException ex){
            throw new RuntimeException("Error al procesar archivos de productos o vendedaores:" , ex);
        }

    }

    private void processSaleBySellerFile(List<Path> fileSalesBySeller) {
        try{

            for (Path path : fileSalesBySeller) {
                List<String> lines = Files.readAllLines(path);
                processSalesFile(lines);
                System.out.println("Archivo de ventas procesado.");
            }
        }  catch (IOException ex){
            throw new RuntimeException("Error al procesar archivo de ventas por vendedor:" , ex);
        }
    }

    private void processProductFile(List<String> lines) { // Implementa el procesamiento del archivo de productos

        for (String line : lines) {
            String[] parts = line.split(";");
            String idProducto = parts[0].trim();
            String nombreProducto = parts[1].trim();
            int precioProducto = Integer.parseInt(parts[2].trim().replace("$", "").replace(" ", ""));
            productMap.put(idProducto, precioProducto);
        }

    }

    private void processSalesmanInfoFile(List<String> lines) {   // Implementa el procesamiento del archivo de información de vendedores

        for (String line : lines) {
            String[] parts = line.split(";");
            String documento = parts[0].trim() + ";" + parts[1].trim();
            String nombreVendedor = parts[2].trim() + " " + parts[3].trim();
            vendedorMap.put(documento, nombreVendedor);
        }
    }

    private void processSalesFile(List<String> lines) { // Implementa el procesamiento del archivo de ventas por vendedor

        String documentInfo = lines.get(0); // TipoDocumento;NúmeroDocumento
        String seller = "";
        int totalAmount = 0;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(";");
            String idProducto = parts[0].trim();
            int cantidadVendida = Integer.parseInt(parts[1].trim());

            seller = vendedorMap.get(documentInfo);
            totalAmount = totalAmount + productMap.get(idProducto) * cantidadVendida;

            if (salesMap.isEmpty()) {
                System.out.println("la lista salesMap esta vacia");
                // Si la lista está vacía, agrega un nuevo HashMap
                salesMap.add(new HashMap<>());
            }

        }
        salesMap.get(0).merge(seller, totalAmount, Integer::sum);
        //La función merge agrega el monto total de la venta actual (totalAmount) al total acumulado de ventas del vendedor.
        // Si el vendedor ya existe, suma el valor; si no, lo inserta con el valor totalAmount
        //Si el vendedor ya tiene un monto acumulado en salesMap, Integer::sum suma el nuevo totalAmount al monto existente.
    }


    private void generateSalesmanReport() throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/reports/generatedReports/reporteVendedores.csv"))) {

            List<Map.Entry<String, Integer>> salesList = new ArrayList<>();

            // Iterar sobre cada mapa dentro de la lista y agregar todas sus entradas a salesList
            for (Map<String, Integer> sales : salesMap) {
                salesList.addAll(sales.entrySet());
            }

            salesList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
            //La expresión lambda está diseñada para que los vendedores con mayor valor vendido  aparezcan primero en la lista.

            for (Map.Entry<String, Integer> entry : salesList) {
                try {
                    writer.write(entry.getKey() + "; " + entry.getValue());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Error al escribir el reporte de ventas por vendedor: ", e);
                }
            }
        }
    }

    private void generateProductReport() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/reports/generatedReports/reporteProductos.csv"))) {

            List<Map.Entry<String, Integer>> productList = new ArrayList<>(productMap.entrySet());

            // Ordenar la lista por precio de producto en orden descendente
            productList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); //La expresión lambda está diseñada para que los productos con mayor valor  aparezcan primero en la lista.

            for (Map.Entry<String, Integer> entry : productList) {
                writer.write(entry.getKey() + "; " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el reporte de productos: ", e);
        }
    }

    private void validateData(String data) {

    }
}

