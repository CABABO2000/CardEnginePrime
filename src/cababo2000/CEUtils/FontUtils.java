package cababo2000.CEUtils;

import java.awt.*;
import java.io.IOException;

/**
 * The FontUtils class contains font loading utilities used in the main package.
 */
public class FontUtils {

    /**
     * The loadFont method loads a given font from the Fonts folder based on its filename.
     * @param fontname The given font's filename (Ex; "basis33.ttf").
     * @param style The font's style (Allows any of the font styles that the Font class includes).
     * @param size The size of the font.
     * @return
     */
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
