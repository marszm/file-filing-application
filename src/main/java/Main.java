
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        //tworzenie folderow
        File home = new File("C:\\HOME");
        File dev = new File("C:\\DEV");
        File test = new File("C:\\TEST");
        File count = new File("C:\\home\\count.txt");

        if(home.exists())
        home.mkdir();

        if(dev.exists())
        dev.mkdir();

        if(test.exists())
        test.mkdir();

        BasicFileAttributes attrs;

        int xmlCounter = 0;
        int jarCounter = 0;

        try {
            //tworzenie pliku do odczytu liczniow
            count.createNewFile();

            //filtrowanie plikow z rozszerzeniami .xml albp .jar
            File [] homeFilterjar = home.listFiles((dir, name) -> name.endsWith(".jar"));
            File[] homeFilterxml = home.listFiles((dir, name) -> name.endsWith(".xml"));

            for(File path: homeFilterjar) {
                jarCounter++;
                attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
                FileTime time = attrs.creationTime();
                String hh = String.format("%1$tH",new Date(time.toMillis()));
                int result = Integer.parseInt(hh); //godzina utworzenia pliku

                //jeslo godzina parzysta to do dev, jesli nie to do test
                if(result % 2 == 0) {
                    path.renameTo(new File("C:\\DEV\\"+path.getName()));
                } else {
                    path.renameTo(new File("C:\\TEST\\"+path.getName()));
                }
            }
            //przenosi wszystkie .xml z home do dev
            for(File path1: homeFilterxml) {
                xmlCounter++;
                path1.renameTo(new File("C:\\DEV\\"+path1.getName()));
            }

            int totalCounter = xmlCounter + jarCounter;

            //zapisywanie licznikow do pliku count.txt
            PrintWriter printWriter = new PrintWriter(count);
            printWriter.println("ile plikow .xml "+xmlCounter);
            printWriter.println("ile plikow .jar "+jarCounter);
            printWriter.println("w sumie plikow "+totalCounter);
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
