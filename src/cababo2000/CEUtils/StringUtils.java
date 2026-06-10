package cababo2000.CEUtils;

import java.util.*;

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
