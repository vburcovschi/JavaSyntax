import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Encript {
    public static void encriptFileByKey(String inFilename, String outFilename, int key) {
        ArrayList<StringBuilder> texts = new ArrayList<>();
        try(FileReader in = new FileReader(inFilename); BufferedReader reader = new BufferedReader(in)){
            while (reader.ready())
            {
                String line = reader.readLine();
                StringBuilder encodeLine = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    encodeLine.append(encodeChar(line.charAt(i),key));
                }
                texts.add(encodeLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR:" +e.getMessage());
        }
        try {
            Files.deleteIfExists(Path.of(outFilename));
            Path file = Files.createFile(Path.of(outFilename));
            for (StringBuilder encodedText : texts) {
                //System.out.println(encodedText);
                Files.writeString(file,encodedText, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.out.println("ERROR!");
            System.out.println("Невозможно записать данные в файл: "+outFilename);
            System.out.println("Проверьте если есть доступ к файлу или если не занят другим процессом.");
        }

    }

    private static char encodeChar(char ch, int key){
        int ascii = ch;
        ascii +=key;
        return (char) ascii;
    }


}
