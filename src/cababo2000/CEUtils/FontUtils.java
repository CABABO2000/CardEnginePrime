package cababo2000.CEUtils;

import java.awt.*;
import java.io.IOException;

public class FontUtils {

    // https://stackoverflow.com/questions/65449685/java-how-to-load-a-custom-font-from-a-resources-folder
    public static Font loadFont(String fontname, int style, float size){
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, FontUtils.class.getResourceAsStream("/Fonts/" + fontname));
            return f.deriveFont(style, size);
        } catch (FontFormatException e) {
            IO.println("A FontFormatException has Occurred");
        } catch (IOException e) {
            IO.println("An IOException has Occurred");
        }
        return null;
    }

}
