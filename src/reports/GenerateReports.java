package reports;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class GenerateReports {

    public void generateReports(String directoryPath) throws IOException, InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Void>> futures = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                futures.add(executor.submit(() -> {
                    processFile(path);
                    return null;
                }));
            });
        }

        for (Future<Void> future : futures) {
            future.get();
        }

        executor.shutdown();
        generateSalesmanReport();
        generateProductReport();
    }

    private void processFile(Path path) {

    }

    private void generateSalesmanReport() {

    }

    private void generateProductReport() {

    }

    private void validateData(String data) {

    }
}

