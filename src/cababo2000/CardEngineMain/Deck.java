package cababo2000.CardEngineMain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Deck class represents an instance of a deck. As well, it provides methods that assist in building, saving, and loading deck data.
 */
public class Deck {
    private String identifier;
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        this("Null", "");
    }

    public Deck(String id, String name){
        this.identifier = id;
        for(int i = 0; i < 1000; i++){
            this.cards.add(i, new Card());
        }
    }

    public static Deck constructDeck(String id, String name, File[] files){

        Deck deck = new Deck(id, name);

        for(int i = 0; i < 60; i++){
            deck.cards.set(i, Card.loadCardData(files[i]));
        }
        return deck;
    }

    public static Deck constructDeck(String id, String name, String[] cardids){

        Deck deck = new Deck(id, name);

        for(int i = 0; i < 60; i++){
            deck.cards.set(i, Card.loadCardData(cardids[i]));
        }
        return deck;
    }

    // https://www.youtube.com/watch?v=J94lLj_uG3c by Random Code
    public static Deck loadDeckData(File file){
        String jsonin = "";
        try{
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine()) {
                jsonin = jsonin + scan.nextLine();
            }

            Gson gson = new Gson();
            return gson.fromJson(jsonin, Deck.class);

        }catch (FileNotFoundException fnf) {
            IO.println("file not found");
        }
        return null;
    }

    public static Deck loadDeckData(String identifier){
        String jsonin = "";
        try{
            Scanner scan = new Scanner(new File(identifier + "_deck.json"));

            while(scan.hasNextLine()) {
                jsonin = jsonin + scan.nextLine();
            }

            Gson gson = new Gson();
            return gson.fromJson(jsonin, Deck.class);

        }catch (FileNotFoundException fnf) {
            IO.println("file not found");
        }
        return null;
    }

    public BufferedImage buildDeckImage(){

        BufferedImage bi = new BufferedImage(9900, 6 * 1385, 1);
        Graphics2D g2 = bi.createGraphics();
        int l = 0;
        BufferedImage cc = this.cards.get(l).buildCardImage(false);

        double m = Math.ceil((double)this.cards.size() / 10);
        for(int i = 0; i < Math.ceil((double)this.cards.size() / 60); i++){
            IO.println("jeepers");
            for(int j = 0; j < m & j < 6; j++){
                IO.println("meep");
                for(int k = 0; k < 10 & this.cards.size() - l > 0; k++){
                    if (l > 0 && !(this.cards.get(l).compareTo(this.cards.get(l - 1)) == 0)) {
                        cc = this.cards.get(l).buildCardImage(false);
                    }
                    IO.println("bogos binted: " + l + " " + m);
                    g2.drawImage(cc, null, k * 990, j * 1385);
                    l++;
                }
            }

            m -= 6;

            try {
                ImageIO.write(bi, "png", new File(this.identifier + i + "_deck.png"));
            } catch (IOException ioe) {

            }
            bi = new BufferedImage(9900, 6 * 1385, 1);
            g2 = bi.createGraphics();
        }

        return null;
    }

    public void saveDeckData(){

        Gson jsonout = new GsonBuilder().setPrettyPrinting().create();
        try {
            PrintStream output = new PrintStream(new File(this.identifier + "_deck.json"));
            String printout = new String(jsonout.toJson(this));

            output.println(printout);
        } catch (FileNotFoundException fnfe){
            IO.println("File not found.");
        }

    }

    public Card getCard(int slot){

        return this.cards.get(slot - 1);

    }
}
