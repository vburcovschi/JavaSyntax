import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class DecriptByStatistics {
    public static void decriptFile(LinkedHashMap<Character, Character> corelation, String encriptedFile) {
        String encriptedTExt= readFileContent(encriptedFile);
        StringBuilder decriptedText = new StringBuilder();
        for (int i = 0; i < encriptedTExt.length(); i++)
            for (Map.Entry iter: corelation.entrySet()) {
                Character cor = (Character) iter.getKey();
                Character enc = encriptedTExt.charAt(i);
                if (iter.getKey().equals(encriptedTExt.charAt(i)))
                    decriptedText.append(iter.getValue());
            }
        writeFileContent(decriptedText.toString(),encriptedFile);
    }

    public static LinkedHashMap<Character, Character> getChipher(LinkedHashMap<Character, Integer> statisticMostra, LinkedHashMap<Character, Integer> statisticEncripted) {
        LinkedHashMap<Character, Character> corelation = new LinkedHashMap<>();
        int length = Math.min(statisticMostra.keySet().size(), statisticEncripted.keySet().size());
        LinkedList<Character> mostraList = new LinkedList<>(statisticMostra.keySet());
        LinkedList<Character> encriptList = new LinkedList<>(statisticEncripted.keySet());
        for (int i = 0; i < length; i++) {
            corelation.put(encriptList.get(i),mostraList.get(i));
        }
        return corelation;
    }

    public static LinkedHashMap<Character, Integer> sortMap(LinkedHashMap<Character, Integer> map) {
        LinkedHashMap<Character, Integer> result = new LinkedHashMap<>();
        int[] values = new int[map.values().size()];
        Collection<Integer> values1 = map.values();
        int i = 0;
        for (Integer integer : values1) {
            values[i] = integer;
            i++;
        }
        for (int j = 0; j < values.length; j++)
            for (int k = 0; k < values.length - 1; k++)
                if (values[k] < values[k + 1]) {
                    int tmp = values[k];
                    values[k] = values[k + 1];
                    values[k + 1] = tmp;
                }
        for (int j = 0; j < values.length; j++) {
            for (Character chr : map.keySet()) {
                if (map.get(chr) == values[j]) {
                    result.put(chr, values[j]);
                    break;
                }
            }
        }
        return result;
    }

    public static LinkedHashMap<Character, Integer> getStatisticsFile(String fileName) {
        LinkedHashMap<Character, Integer> statisticAbsolute = new LinkedHashMap<>();
        LinkedHashMap<Character, Integer> statistic = new LinkedHashMap<>();
        String text = readFileContent(fileName);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Integer integer = statisticAbsolute.get(c);
            if (integer == null)
                statisticAbsolute.put(c, 1);
            else {
                integer++;
                statisticAbsolute.put(c, integer);
            }
        }
        for (Character character : statisticAbsolute.keySet()) {
            Integer integer = statisticAbsolute.get(character);
            int i = integer * 10000 / text.length();
            statistic.put(character, i);
        }
        return statistic;
    }

    public static String readFileContent(String inFile) {
        Path path = Path.of(inFile);
        try {
            byte[] bytes = Files.readAllBytes(path);
            String text = new String(bytes);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeFileContent(String text, String inFile) {
        int dotIndex = inFile.lastIndexOf(".");
        String fileNameBeforeDot = inFile.substring(0, dotIndex);
        String fileNameAfterDot = inFile.substring(dotIndex);
        String newFileName = fileNameBeforeDot + "-from stat analiz" + fileNameAfterDot;
        try {
            Files.writeString(Path.of(newFileName), text);
            System.out.println("Результат расшифровки по методу статисчического анализа раположен в файле: "+newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
