import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

// zadanie niekompletne, program powinien dzialac caly czas sprawdzajac pojawianie sie nowych plików

public class Main {

    public static void main(String[] args) {

        final String FILENAME = "count.txt";
        final String HOME = "HOME";
        final String DEV = "DEV";
        final String TEST = "TEST";

        String userHomeDirectory = System.getProperty("user.home");
        System.out.println(userHomeDirectory);
        String userOS = System.getProperty("os.name").toLowerCase();
        System.out.println(userOS);

        if(userOS.contains("windows")) {

            String s1 = userHomeDirectory+"\\"+HOME;
            File file1 = new File(s1);
            file1.mkdir();
            System.out.println(s1);
        }
        File file = new File(HOME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userOS.contains("nix")) {

            String s1 = userHomeDirectory+"/"+HOME;
            File file2 = new File(s1);
            file2.mkdir();
            System.out.println(s1);

        }

        //        jesli katalogi istnieja to niepotrzebnie je zakladamy?
//        BasicFileAttributes attrs;
//
//        int xmlCounter = 0;
//        int jarCounter = 0;
//
//                wszystko jest w jednym bloku try
//        try {
//            tworzenie pliku do odczytu liczniow
//            count.createNewFile();
//
//            filtrowanie plikow z rozszerzeniami .xml albp .jar
//            File [] homeFilterjar = home.listFiles((dir, name) -> name.endsWith(".jar"));
//            File[] homeFilterxml = home.listFiles((dir, name) -> name.endsWith(".xml"));
//
//            for(File path: homeFilterjar) {
//                jarCounter++;
//                attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
//                FileTime time = attrs.creationTime();
//                        uzycie stringa do parsowania godzin
//                String hh = String.format("%1$tH",new Date(time.toMillis()));
//                int result = Integer.parseInt(hh); //godzina utworzenia pliku
//
//                jeslo godzina parzysta to do dev, jesli nie to do test
//                if(result % 2 == 0) {
//                            uzyles File.renameTo(), lepiej byloby uzyc Files.move()
//                    path.renameTo(new File("C:\\DEV\\"+path.getName()));
//                } else {
//                    path.renameTo(new File("C:\\TEST\\"+path.getName()));
//                }
//            }
//            przenosi wszystkie .xml z home do dev
//            for(File path1: homeFilterxml) {
//                xmlCounter++;
//                path1.renameTo(new File("C:\\DEV\\"+path1.getName()));
//            }
//
//            int totalCounter = xmlCounter + jarCounter;
//
//            zapisywanie licznikow do pliku count.txt
//            PrintWriter printWriter = new PrintWriter(count);
//            printWriter.println("ile plikow .xml "+xmlCounter);
//            printWriter.println("ile plikow .jar "+jarCounter);
//            printWriter.println("w sumie plikow "+totalCounter);
//            printWriter.close();
//
//
//                    catch bez obslugi, tylko zrzut stacktrace
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }}
