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
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The Deck class represents an instance of a deck. As well, it provides methods that assist in building, saving, and loading deck data.
 */
public class Deck {
    private String identifier;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        this("Null", "");
    }

    public Deck(String id, String name){
        this.identifier = id;
    }

    /**
     * The constructDeck method builds a Deck from an array of files.
     * @param files The file array to build from.
     * @return A Deck
     */
    public static Deck constructDeck(String id, String name, File[] files){

        Deck deck = new Deck(id, name);

        for(int i = 0; i < 60; i++){
            deck.cards.set(i, Card.loadCardData(files[i]));
        }
        return deck;
    }

    /**
     * The constructDeck method can alternatively be used to build a deck from an array of Strings. Each String should be a card id that the program can use to reference files of the same name.
     * @param cardids The String array to build from.
     * @return A Deck
     */
    public static Deck constructDeck(String id, String name, String[] cardids){

        Deck deck = new Deck(id, name);

        for(int i = 0; i < cardids.length - 1; i++){
            deck.cards.set(i, Card.loadCardData(cardids[i]));
        }
        return deck;
    }

    // https://www.youtube.com/watch?v=J94lLj_uG3c by Random Code
    /**
     * The loadDeckData method builds a deck from a .json file.
     * @param file The .json file to build from.
     * @return A Deck
     */
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

    /**
     * The loadDeckData method can alternatively be called using the Deck's id, assuming the .json file's name is the Decks's id.
     *
     * Ie; If the Deck's id is "Meep", then the .json's name would have to be "Meep_deck.json" to work.
     *
     * @param identifier The Deck id to build from.
     * @return A Deck
     */
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

    /**
     * The buildDeckImage method builds a card sheet from the Deck's data. The resulting image is intended to be used inside of the application "Tabletop Simulator".
     *
     * Instructions on how to use this can be found on their website:
     * https://kb.tabletopsimulator.com/custom-content/custom-deck/
     *
     * @return A BufferedImage
     */
    public BufferedImage buildDeckImage(boolean write){

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

            if(write) {
                try {
                    ImageIO.write(bi, "png", new File(this.identifier + i + "_deck.png"));
                } catch (IOException ioe) {

                }
            }
            bi = new BufferedImage(9900, 6 * 1385, 1);
            g2 = bi.createGraphics();
        }

        return bi;
    }

    /**
     * The saveDeckData method writes the Deck into a .json file. The resulting file will be named via the following pattern:
     * indentifier_deck.json.
     * For now, all other methods pertaining to reading Decks from files rely on this naming convention.
     */
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

    /**
     * The getCard method returns a Card at a given slot.
     * @return a Card
     */
    public Card getCard(int slot){
        return this.cards.get(slot - 1);
    }

    /**
     * The getName method returns the Deck's name.
     * @return a String
     */
    public String getName(){
        if(this.name == null) return "Name";
        return this.name;
    }

    /**
     * The getID method returns the Deck's internal id.
     * @return a String
     */
    public String getIdentifier(){
        if(this.identifier == null) return "Null";
        return this.identifier;
    }

    public ArrayList<Card> getCardList(){
        ArrayList<Card> copy = this.cards;

        return copy;
    }

    public void addCard(Card c) {
        this.cards.addFirst(c);
    }

    public void remCard(int n){
        if(n <= this.cards.size() & n > 0) this.cards.remove(n - 1);
    }

    public boolean isEmpty(){
        return !this.cards.isEmpty();
    }

    public void setCards(ArrayList<Card> l){
        this.cards = l;
    }

    public void setName(String str){
        if(str == null){
            this.name = "";
        } else{
            this.name = str;
        }
    }

    public void setIdentifier(String str){
        if(str == null){
            this.identifier = "";
        } else{
            this.identifier = str;
        }
    }

    // A very temporary solution; plan to add more sorting types in the future.
    // Also taken from Ch13 lessons.
    public static List<Card> sortByName(Deck d){
        PriorityQueue<Card> heap = new PriorityQueue<>(d.getCardList());
        d.setCards(new ArrayList<>(heap));
        return new ArrayList<>(heap);
    }
}
