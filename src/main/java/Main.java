import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

// zadanie niekompletne, program powinien dzialac caly czas sprawdzajac pojawianie sie nowych plików

public class Main {

    public static void main(String[] args) {

        final String COUNTTXT = "count.txt";
        final String HOME = "HOME";
        final String DEV = "DEV";
        final String TEST = "TEST";

        final String userHomeDirectory = System.getProperty("user.home");

        final String home = userHomeDirectory + File.separator + HOME;
        final String dev = userHomeDirectory + File.separator + DEV;
        final String test = userHomeDirectory + File.separator + TEST;
        final String count = userHomeDirectory + File.separator + HOME + File.separator + COUNTTXT;





        File fileHome = new File(home);
        File fileDev = new File(dev);
        File fileTest = new File(test);
        File fileCount = new File(count);

        if (!fileHome.exists() || !fileDev.exists() || !fileTest.exists()) {

            fileHome.mkdir();
            fileDev.mkdir();
            fileTest.mkdir();

        }

        try {
            fileCount.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BasicFileAttributes attrs;

        int xmlCounter = 0;
        int jarCounter = 0;

//                wszystko jest w jednym bloku try
        try {

            File[] homeFilterjar = fileHome.listFiles((dir, name) -> name.endsWith(".jar"));
            File[] homeFilterxml = fileHome.listFiles((dir, name) -> name.endsWith(".xml"));

            for (File path : homeFilterjar) {
//
                jarCounter++;
//                attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
//                FileTime fileTime  = attrs.creationTime();
//                System.out.println(time);
//                        uzycie stringa do parsowania godzin
//                String hh = String.format("%1$tH",new Date(fileTime.toMillis()));
//                int result = Integer.parseInt(hh); //godzina utworzenia pliku
//
//                jeslo godzina parzysta to do dev, jesli nie to do test
//                if(result % 2 == 0) {
//                            uzyles File.renameTo(), lepiej byloby uzyc Files.move()
                    Path pathHome = Paths.get(home +  File.separator + path.getName());
                    Path pathDev = Paths.get(dev +  File.separator + path.getName());
                    Files.move(pathHome, pathDev,StandardCopyOption.REPLACE_EXISTING);
//                } else {
//                    Path pathHome = Paths.get(home +  File.separator + path.getName());
//                    Path pathTest = Paths.get(test +  File.separator + path.getName());
//                    Files.move(pathHome, pathTest,StandardCopyOption.REPLACE_EXISTING);
//                }
            }
//            przenosi wszystkie .xml z home do dev
            for(File path1: homeFilterxml) {
//                xmlCounter++;
                Path pathHome = Paths.get(home +  File.separator + path1.getName());
                Path pathDev = Paths.get(dev +  File.separator + path1.getName());
                Files.move(pathHome, pathDev);

            }

            int totalCounter = xmlCounter + jarCounter;

//            zapisywanie licznikow do pliku count.txt
//            PrintWriter printWriter = new PrintWriter(count);
//            printWriter.println("ile plikow .xml "+xmlCounter);
//            printWriter.println("ile plikow .jar "+jarCounter);
//            printWriter.println("w sumie plikow "+totalCounter);
//            printWriter.close();


//                    catch bez obslugi, tylko zrzut stacktrace
        } catch (IOException e) {
            e.printStackTrace();
        }
}}
