package cababo2000.CardEngineMain;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;

/**
 * The Main class is only here to initilize the program.
 */
public class Main {
    public static void main() {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        FlatDarkLaf.setup();

        // Source - https://stackoverflow.com/a/3680236
        // Posted by Colin Hebert, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-06-12, License - CC BY-SA 3.0
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); // grabbing the screen size.

        new GUI().setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    }
}