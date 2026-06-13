package cababo2000.CEUtils;

import java.util.*;

/**
 * The StringUtils class contains all String/Text processing related utilities used in the main package.
 */
public class StringUtils {

    // https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java (answer from corsiKa (https://stackoverflow.com/users/330057/corsika))
    /**
     * The isInt method checks if a given String is an int.
     * @param str The given String to check.
     * @return A boolean
     */
    public static boolean isInt(String str){

        try{
            Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return true;
        }

        return false;
    }

    /**
     * The textWrap method splits a String into a String List evenly.
     * @param text The String that will be wrapped.
     * @param linelength The length of each line (by characters).
     * @return A String List
     */
    public static List<String> textWrap(String text, int linelength){
        List<String> out = new LinkedList<>();

        int first = 0;
        int last = 0;
        int length = text.length();
        String current = "";
        boolean firstline = true;

        while(last <= length) {
            if (current.length() > linelength) {
                out.add(current);
                current = "";
                first = last - 1;
                last++;
                firstline = false;
            } else {
                current = text.substring(first, last);
                last++;
            }
        }

        out.add(current);

        return out;
    }

    /**
     * The crudeCenterAlign method pads a String based on a specified width using spaces.
     * It's called crude due to unwieldiness.
     *
     * Note: When used with fonts that do not have a standard character width, this method doesn't function properly.
     * @param str The given string to align to the center.
     * @param maxwidth The amount of characters the output is allowed to take.
     * @return A String
     */
    public static String crudeCenterAlign(String str, int maxwidth){
        return crudeCenterAlign(str, (maxwidth - str.length()) / 2, 0);
    }

    private static String crudeCenterAlign(String str, int mw, int dummy){
        if(mw <= 0) return str;
        return " " + crudeCenterAlign(str, mw - 1, 0);
    }

}
