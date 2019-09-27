package com.example.filefilingapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

@SpringBootApplication
public class FileFilingApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(FileFilingApplication.class, args);



        final String COUNTTXT = "count.txt";
        final String HOME = "HOME";
        final String DEV = "DEV";
        final String TEST = "TEST";

        final String userHomeDirectory = System.getProperty("user.home");

        final String home = userHomeDirectory + File.separator + HOME;
        final String dev = userHomeDirectory + File.separator + DEV;
        final String test = userHomeDirectory + File.separator + TEST;
        final String count = userHomeDirectory + File.separator + HOME + File.separator + COUNTTXT;

        final File fileHome = new File(home);
        final File fileDev = new File(dev);
        final File fileTest = new File(test);
        final File fileCount = new File(count);



        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path pathWatch = Paths.get(home);

        pathWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;



        if (!fileHome.exists() || !fileDev.exists() || !fileTest.exists()) {

            fileHome.mkdir();
            fileDev.mkdir();
            fileTest.mkdir();

        }

        if (!fileCount.exists()) {
            try {
                fileCount.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BasicFileAttributes attrs = null;

        int xmlCounter = 0;
        int jarCounter = 0;


        File[] homeFilterjar = fileHome.listFiles((dir, name) -> name.endsWith(".jar"));
        File[] homeFilterxml = fileHome.listFiles((dir, name) -> name.endsWith(".xml"));



        for (File path : homeFilterjar) {
//
            jarCounter++;

            try {
                attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileTime fileTime = attrs.creationTime();
            int hh = Integer.parseInt(String.format("%1$tH", new Date(fileTime.toMillis())));

            if (hh % 2 == 0) {

                Path pathDev = Paths.get(dev + File.separator + path.getName());
                Path pathHome = Paths.get(home + File.separator + path.getName());

                try {
                    Files.move(pathHome, pathDev, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                Path pathHome = Paths.get(home + File.separator + path.getName());
                Path pathTest = Paths.get(test + File.separator + path.getName());

                try {
                    Files.move(pathHome, pathTest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (File path1 : homeFilterxml) {

            xmlCounter++;
            Path pathHome = Paths.get(home + File.separator + path1.getName());
            Path pathDev = Paths.get(dev + File.separator + path1.getName());

            try {
                Files.move(pathHome, pathDev, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int totalCounter = xmlCounter + jarCounter;

        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                if(event.kind().toString() == "ENTRY_CREATE") {

                    File[] homeWatchFilter = fileHome.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".xml"));
                    for (File file : homeWatchFilter) {
                        System.out.println(file.getName());
                    }

//                    File[] homeWatchFilterxml = fileHome.listFiles((dir, name) -> name.endsWith(".xml"));
                    for (File path : homeWatchFilter) {
                        if (path.getName().endsWith(".jar")) {
//                        jarCounter++;
                                System.out.println(path.getName());
                            try {
                                attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            FileTime fileTime = attrs.creationTime();
                            int hh = Integer.parseInt(String.format("%1$tH", new Date(fileTime.toMillis())));

                            if (hh % 2 == 0) {

                                Path pathDev = Paths.get(dev + File.separator + path.getName());
                                Path pathHome = Paths.get(home + File.separator + path.getName());

                                try {
//                                Files.move(pathHomeWatch, pathDevWatch, StandardCopyOption.REPLACE_EXISTING);
                                    Files.move(pathHome, pathDev, StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {

                                Path pathHome = Paths.get(home + File.separator + path.getName());
                                Path pathTest = Paths.get(test + File.separator + path.getName());

                                try {
                                    Files.move(pathHome, pathTest, StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {

                            xmlCounter++;
                            Path pathHome = Paths.get(home + File.separator + path.getName());
                            Path pathDev = Paths.get(dev + File.separator + path.getName());

                            try {
                                Files.move(pathHome, pathDev, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
            key.reset();
        }

        printWriter.println("ile plikow .xml " + xmlCounter);
        printWriter.println("ile plikow .jar " + jarCounter);
        printWriter.println("w sumie plikow " + totalCounter);
        printWriter.close();
    }
}
