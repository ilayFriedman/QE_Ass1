import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Ass1 {
    static ArrayList<String> names = importAllTheNames();

    ///##### import #####
    private static ArrayList<String> importAllTheNames() {
        ArrayList<String> names = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./namesList.json"));
            JSONObject jObj = (JSONObject) obj;
            JSONArray namesList = (JSONArray) jObj.get("list");
            Iterator<String> iterator = namesList.iterator();
            while (iterator.hasNext()) {
                names.add(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    ///##### 1.1 #####
    private static void CountSpecificString(String str) {
        int count = 0;
        for (String name : names) {
            if (name.contains(str))
                count++;
        }
        System.out.println(count);
    }

    ///##### 1.2 #####
    private static void CountAllStrings(int N) {
        if (N < 0) {
            System.out.println("Please enter a valid number");
        } else {
            for (String name : names) {
                // make list of all N's substring
                ArrayList<String> allOccurenrces = new ArrayList<>();
                int startIndex = 0;
                while (startIndex + N <= name.length()) {
                    allOccurenrces.add(name.substring(startIndex, startIndex + N));
                    startIndex++;
                }

                //make an Iterate SET
                Set<String> setSubs = new HashSet<>(allOccurenrces);

                // iterate all the set and printing and counting result
                for (String subName : setSubs) {
                    System.out.println(subName + ":" + Collections.frequency(allOccurenrces, subName));
                }

                System.out.println("------------------");
            }
        }
    }

    ///##### 1.3 #####
    private static HashMap CountMaxString(int N) {
        if (N < 0) {
            System.out.println("Please enter a valid number");
            return null;
        } else {
            // make a dict of all the subs in all the namesList
            HashMap<String, Integer> allSubsDict = new HashMap<>();


            for (String name : names) {
                ArrayList<String> allOccurenrces = new ArrayList<>();
                // make list of all N's substring
                int startIndex = 0;
                while (startIndex + N <= name.length()) {
                    allOccurenrces.add(name.substring(startIndex, startIndex + N));
                    startIndex++;
                }

                //make an Iterate SET
                Set<String> setSubs = new HashSet<>(allOccurenrces);

                // iterate all the set and printing and counting result
                for (String subName : setSubs) {
                    if (allSubsDict.containsKey(subName.toLowerCase()))
                        allSubsDict.put(subName.toLowerCase(), allSubsDict.get(subName.toLowerCase()) + Collections.frequency(allOccurenrces, subName.toLowerCase()));
                    else
                        allSubsDict.put(subName.toLowerCase(), Collections.frequency(allOccurenrces, subName.toLowerCase()));
                }

//            System.out.println(allSubsDict);
            }
            // returns the max value in the dict
            for (String key : allSubsDict.keySet()) {
                if (allSubsDict.get(key) == Collections.max(allSubsDict.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getValue())
                    System.out.println(key);
            }
            return allSubsDict;
        }

    }

    ///##### 1.4 #####
    private static void AllIncludesString(String str) {
        for (String name : names) {
            if (str.contains(name))
                System.out.println(name);
        }
    }

    ///##### Bonus ####
    private static void GenerateName() {
        //---MAKE FIRST LETTER----

        // get the MAX first letter in names
        HashMap<Character, Integer> freq = new HashMap<>();

        // creating dict of freq first letters
        for (String name : names) {
            if (freq.containsKey(name.toLowerCase().charAt(0)))
                freq.put(name.toLowerCase().charAt(0), freq.get(name.toLowerCase().charAt(0)) + 1);
            else
                freq.put(name.toLowerCase().charAt(0), 1);
        }
        char firstLetter = Collections.max(freq.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
//        System.out.println(firstLetter);



        //--- make rest of name ---

        String generatedName = String.valueOf(firstLetter).toUpperCase();
        char currLetter = firstLetter;
        for(int i=0; i<5; i++){
            currLetter = startWith(currLetter);
            generatedName = generatedName + currLetter;
        }
        System.out.println(generatedName);


    }

    private static char startWith(char letter) {

        HashMap<String, Integer> subFreq = new HashMap<>();


        for (String name : names) {
            ArrayList<String> allOccurenrces = new ArrayList<>();
            // make list of all N's substring
            int startIndex = 0;
            while (startIndex + 2 <= name.length()) {
                allOccurenrces.add(name.substring(startIndex, startIndex + 2));
                startIndex++;
            }

            //make an Iterate SET
            Set<String> setSubs = new HashSet<>(allOccurenrces);

            // iterate all the set and printing and counting result
            for (String subName : setSubs) {
                if (subFreq.containsKey(subName.toLowerCase()) && subName.charAt(0) == letter)
                    subFreq.put(subName.toLowerCase(), subFreq.get(subName.toLowerCase()) + Collections.frequency(allOccurenrces, subName.toLowerCase()));
                else if (subName.charAt(0) == letter)
                    subFreq.put(subName.toLowerCase(), Collections.frequency(allOccurenrces, subName.toLowerCase()));
            }


        }
//        System.out.println(subFreq);
        return (Collections.max(subFreq.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey().charAt(1));

    }


        public static void main (String[]args){

            // import all the names
            // clear duplicates
            Set<String> set = new HashSet<>(names);
            names.clear();
            names.addAll(set);

            try{
                switch (args[0]){
                    case "CountSpecificString": CountSpecificString(args[1]);
                        break;
                    case "CountAllStrings": CountAllStrings(Integer.parseInt(args[1]));
                        break;
                    case "CountMaxString" : CountMaxString(Integer.parseInt(args[1]));
                        break;
                    case "AllIncludesString" : AllIncludesString(args[1]);
                        break;
                    case "GenerateName": GenerateName();
                        break;
                    default:
                        System.out.println("No Function in that name. ");
                }
            } catch (NumberFormatException  e){
                System.out.println("OOPS! please put valid input!");
            }catch (ArrayIndexOutOfBoundsException  e){
                System.out.println("OOPS! No input received! ");
            }

        }
    }
