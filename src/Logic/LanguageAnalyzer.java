package Logic;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class implements a way for program to input words from file .
 */
public class LanguageAnalyzer {
    private static String words = "" ;

    /**
     * This method writes text file to String object words .
     */
    public static void getLanguageAsString () {
        Scanner input = null;
        try {
             input = new Scanner(Paths.get("./saves/language/default.txt")) ;
        } catch (IOException e) {
            System.out.println("Can't open path : ./saves/language/default.txt");
        }

        while (input.hasNext()) {
            words += input.next() ;
            words += " " ;
        }
    }

    /**
     * This method returns specific word by its number .
     * @param wordNumber
     * @return
     */
    public static String getWord (int wordNumber) {
        String[] tokenizeStrings = words.split("-") ;

        if ((wordNumber >= tokenizeStrings.length) || (wordNumber < 0))
            System.err.println("Wrong word number !") ;


        return tokenizeStrings[wordNumber] ;
    }
}
