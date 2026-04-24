package cababo2000.CEUtils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The StringUtils class contains all String/Text processing related utilities used in the main package.
 */
public class StringUtils {

    // https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java (answer from corsiKa (https://stackoverflow.com/users/330057/corsika))
    public static boolean isInt(String str){

        try{
            Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return true;
        }

        return false;
    }

    /**
     * The textWrap method splits a String into a String Array evenly.
     * @param text The String that will be wrapped.
     * @param linelength The length of each line (by characters).
     * @param linecount The size of the String Array (how many lines the String can fill up)
     * @param wordbased Setting wordbased to true will change the wrapping logic that the method uses from character based to word based. When using word based wrapping, lines will be cut short if the last word bleeds over the line length. After which, that last word will then be transferred to the next line.
     * @return A String Array
     */
    public static String[] textWrap(String text, int linelength, int linecount, boolean wordbased){

        String[] wrappedText = new String[linecount];
        Scanner scan = new Scanner(text);

        String line = "";
        String at = "";
        String saveword = "";
        boolean canloop = true;

        Arrays.fill(wrappedText, "");

        int i = 0;

        if (wordbased) {
            StringTokenizer tkn = new StringTokenizer(text);
        } else if (!wordbased){

            int j = 0;

            while(j < text.length()){
                while(j < text.length() && i < linelength){
                    at = text.charAt(j) + "";
                    line = line + at;
                    i++;
                    j++;
                }
                wrappedText[i] = line;
                line = "";
                i = 0;
            }
        }

        return wrappedText;
    }

    /**
     * The crudeCenterAlign method pads a String based on a specified width using spaces.
     * @param str
     * @param maxwidth
     * @return A String
     */
    public static String crudeCenterAlign(String str, int maxwidth){

        String copystr = str;
        int halfwidth = (maxwidth - str.length())/2;

        for(int i = 0; i < halfwidth; i++){
            copystr = " " + copystr;
        }
        for(int i = 0; i < halfwidth - 1; i++){
            copystr = copystr + " ";
        }

        return copystr;
    }

}
