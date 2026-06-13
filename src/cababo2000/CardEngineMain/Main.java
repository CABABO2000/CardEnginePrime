package cababo2000.CardEngineMain;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * The Main class is only here to initilize the program.
 */
public class Main {
    public static void main() {
        //Card.buildCardImage(Card.loadCardData("Test"));

        System.setProperty("flatlaf.useNativeLibrary", "false");
        FlatDarkLaf.setup();

        new GUI();

        //Card test = new Card("Testing", "Test", "Test Type", "A test description that should be a desired length for a test description. Hopefully.", 10, 25, 333);
        //test.buildCardImage();

        //Deck test = Deck.loadDeckData("TestDeck");
        //test.buildDeckImage();

        /*try {
            ImageIO.write(test.buildCardImage(), "png", new File(test.getID() + ".png"));
        } catch (IOException ioe) {

        }*/
    }
}