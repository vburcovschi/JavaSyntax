import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader keypad = new BufferedReader(new InputStreamReader(System.in));
        String outFilename;
        String inFilename;
        int key;
        String value = "";
        System.out.println("Привет! Ты работаешь с программой для шифрования и расшифрования файлов.");
        while (!(value.equals("5"))){
            System.out.println("Доступные операции:");
            System.out.println("1. Зашифровать файл");
            System.out.println("2. Расшифровать файл");
            System.out.println("3. Расшифровать файл брутфорсом");
            System.out.println("4. Расшифровать файл через статистический анализ");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            value = keypad.readLine();
            switch (value) {
                case "1":
                    System.out.print("Введите путь к файлу который нужно зашифровать: ");
                    inFilename = formatPath(keypad.readLine());
                    while (!(Files.isRegularFile(Path.of(inFilename)))){
                        System.out.print("Ошибочный ввод. Введите правильный путь: ");
                        inFilename = formatPath(keypad.readLine());
                    }
                    System.out.print("");
                    System.out.print("Ведите путь к файлу для сохраненния: ");
                    outFilename = formatPath(keypad.readLine());
                    while ((outFilename.isEmpty())){
                        System.out.print("Ошибочный ввод. Введите правильный путь: ");
                        outFilename = formatPath(keypad.readLine());
                    }
                    System.out.print("");
                    System.out.print("Введите ключ для шифрования: ");
                    key = Integer.parseInt(keypad.readLine());
                    Encript.encriptFileByKey(inFilename, outFilename,key);
                    System.out.println("Файл успешно зашифрован. Название файла: "+outFilename);
                    Thread.sleep(3000);
                    break;
                case "2":
                    System.out.print("Введите путь к зашифрованому файлу: ");
                    inFilename = formatPath(keypad.readLine());
                    while (!(Files.isRegularFile(Path.of(inFilename)))){
                        System.out.print("Ошибочный ввод. Введите правильный путь: ");
                        inFilename = formatPath(keypad.readLine());
                    }
                    System.out.print("");
                    System.out.print("Ведите путь к файлу для сохраненния: ");
                    outFilename = formatPath(keypad.readLine());
                    while ((outFilename.isEmpty())){
                        System.out.print("Ошибочный ввод. Введите правильный путь: ");
                        outFilename = formatPath(keypad.readLine());
                    }
                    System.out.print("");
                    System.out.print("Введите ключ для расшифрования: ");
                    key = Integer.parseInt(keypad.readLine());
                    Encript.encriptFileByKey(inFilename, outFilename, -key);
                    System.out.println("Файл успешно pacшифрован. Название файла: "+outFilename);
                    Thread.sleep(3000);
                    break;
                case "3":
                    System.out.print("Введите путь к зашифрованому файлу: ");
                    inFilename = formatPath(keypad.readLine());
                    while (!(Files.isRegularFile(Path.of(inFilename)))){
                        System.out.print("Ошибочный ввод. Введите правильный путь: ");
                        inFilename = formatPath(keypad.readLine());
                    }
                    System.out.print("");
                    System.out.print("Введите длину ключа : ");
                    key = Integer.parseInt(keypad.readLine());
                    int decodeKey =Bruteforce.brutForceDecode(inFilename,key);
                    if(decodeKey>0)
                        System.out.println("Файл успешно расшифрован методом брутфорс. Ключ шифрования: "+decodeKey);
                    else
                        System.out.println("В заданном диапазоне, ключ шифрования не был найден. Попробуйте увеличить размер ключа.");
                    Thread.sleep(3000);
                    break;
                case "4":
                case "5":
                    break;
            }
        }
    }
    private static String formatPath(String path){
        char[] array = path.toCharArray();
        String tmp = "";
        for (char ch: array)
            tmp = (ch=='\\') ? tmp + ch +'\\' : tmp + ch;
        return tmp;
    }
}
