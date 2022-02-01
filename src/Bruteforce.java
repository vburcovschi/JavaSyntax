import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bruteforce {
    public static int brutForceDecode(String encodedFile, int keyLength) {
        String tmpFileName = System.getProperty("user.dir") + "\\tmp.txt";
        Map<Integer, Integer> statiscs = new HashMap<>();
        for (int i = -keyLength; i < keyLength ; i++) {
            Encript.encriptFileByKey(encodedFile,tmpFileName,i);
            statiscs.put(i, checkFileAfterDecription(tmpFileName));
        }
        //System.out.println(statiscs);
        return Math.abs(getDecriptionKey(statiscs));
    }

    private static int getDecriptionKey(Map<Integer, Integer> statiscs) {
        int maxStatValue = 0;
        int maxStatKey = 0;
        for (Map.Entry<Integer,Integer> values: statiscs.entrySet()) {
            if(maxStatValue<values.getValue()){
                maxStatKey = values.getKey();
                maxStatValue = values.getValue();
            }
        }
        return maxStatKey;
    }

    static int checkFileAfterDecription(String decodedFile) {
        final String[] CRITERIA = {", ",". ",": ","! "," - "," — "};
        int totalRating = 0;
        int foundCount = 0;
        try (FileReader in = new FileReader(decodedFile); BufferedReader reader = new BufferedReader(in)) {
            while (reader.ready()) {
                String line = reader.readLine();
                for (int j = 0; j < CRITERIA.length; j++) {
                    int foundIndex = 0;
                    while (!(foundIndex < 0)) {
                        foundIndex = line.indexOf(CRITERIA[j], foundIndex+1);
                        if (foundIndex > 0)
                            foundCount++;
                    }
                }
                totalRating += foundCount;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalRating;
    }
}
