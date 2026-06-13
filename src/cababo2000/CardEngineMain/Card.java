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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static cababo2000.CEUtils.FontUtils.loadFont;
import static cababo2000.CEUtils.StringUtils.*;

/**
 * The Card class represents an instance of a card. As well, it provides methods that assist in building, saving, and loading card data.
 */
public class Card implements Comparable<Card>{
    // Internal ID, Name, Type, Description, Attack, Starting Health, Blood
    private String[] data = new String[7];

    /*
         Constructors
    */

    public Card() {
        this.data[0] = "NULL";
        this.data[1] = "Name";
        this.data[2] = "Type";
        this.data[3] = "Desc";
        this.data[4] = "0";
        this.data[5] = "0";
        this.data[6] = "0";
    }
    public Card(String id, String name, String type, String desc, int hp, int atk, int bld){
        setData(id, name, type, desc, hp, atk, bld);
    }

    /*
         Utilities
    */

    // https://www.youtube.com/watch?v=pJt-AYrmopo by Scott Rowell
    // https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java (response from Diastrophism)

    /**
     * The saveCardData method stores the data in the Card Object into a .json file. Utilizes the Gson library.
     */
    public void saveCardData(){
        Gson jsonout = new GsonBuilder().setPrettyPrinting().create();
        File f = new File(this.data[0] + ".json");
        try {
            PrintStream output = new PrintStream(f);
            String printout = jsonout.toJson(this);

            output.println(printout);
            IO.println("Trying to save to: " + f.getAbsolutePath());
        } catch (FileNotFoundException fnfe){
            IO.println("File not found.");
        }

    }

    // https://www.youtube.com/watch?v=J94lLj_uG3c by Random Code
    /**
     * The loadCardData method builds a Card Object from a .json file.
     * @param file The file name to load the Card from.
     * @return A Card
     */
    public static Card loadCardData(File file){
        String jsonin = "";
        try{
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine()) {
                jsonin = jsonin + scan.nextLine();
                //IO.println(jsonin);
            }

            Gson gson = new Gson();
            return gson.fromJson(jsonin, Card.class);

        }catch (FileNotFoundException fnf) {
            IO.println("file not found");
        }
        return null;
    }

    /**
     * The loadCardData method can alternatively be called using the Card's <code>identifier</code> instead. Doing so will load the Card from the .json file of the same name.
     * @param identifier The <code>identifier</code> of the Card to load.
     * @return A Card
     */
    public static Card loadCardData(String identifier){
        String jsonin = "";
        try{
            Scanner scan = new Scanner(new File(identifier + ".json"));

            while(scan.hasNextLine()) {
                jsonin = jsonin + scan.nextLine();
                //IO.println(jsonin);
            }

            Gson gson = new Gson();
            return gson.fromJson(jsonin, Card.class);

        }catch (FileNotFoundException fnf) {
            IO.println("file not found");
        }
        return null;
    }

    /**
     * The buildCardImage method outputs an image file of the front face of the Card. The file will be named after the Card's <code>identifier</code>.
     * @param write Whether the method should write the BufferedImage to a .png file.
     * @return A BufferedImage
     */
    // https://www.daniweb.com/programming/software-development/threads/382931/reading-images-faster
    public BufferedImage buildCardImage(boolean write) {
        IO.println("Printing Card...");
        IO.println("- - - - - - -");

        long st = System.nanoTime();
        Graphics2D g2 = null;
        BufferedImage bi = null;
        Font b33 = loadFont("basis33.ttf", 1, 20f);
        Font b2bs = loadFont("Born2bSportyV2.ttf", 1, 20f);

        /*
            Note to self: using the internal resource folder is over 100x faster than reading from an online URL.
         */
        try {
            bi = ImageIO.read(getClass().getResource("/Images/CardFormatV1.png"));
            g2 = bi.createGraphics();
        } catch (IOException ioe) {
            IO.println("IOException has occurred");
        }
        long et = System.nanoTime();
        IO.println("Init took: " + ((et - st) / 100000) + "ms");

        st = System.nanoTime();
        // https://chatgpt.com/share/6a2ca38f-7974-83e8-8481-db688831d17d
        // Utilized ChatGPT to sus out the cause of a NullPointerException.
        // No code was written with ChatGPT.
        g2.setFont(b33.deriveFont(45f));
        g2.setColor(Color.WHITE);
        for (int i = 0; i < (textWrap(getDesc(), 31).size()); i++) {
            g2.drawString(textWrap(getDesc(), 31).get(i), 235, 853 + (i * 45));
        }
        et = System.nanoTime();
        IO.println("Desc took: " + ((et - st) / 100000) + "ms");

        st = System.nanoTime();
        g2.setFont(b33.deriveFont(34f));
        g2.drawString(crudeCenterAlign(getType(), 52), 110, 120);
        et = System.nanoTime();
        IO.println("Type took: " + ((et - st) / 100000) + "ms");

        st = System.nanoTime();
        g2.setFont(b33.deriveFont(80f));
        g2.drawString(crudeCenterAlign(getName(), 25), 74, 79);
        et = System.nanoTime();
        IO.println("Name took: " + ((et - st) / 100000) + "ms");

        st = System.nanoTime();
        g2.setFont(b2bs.deriveFont(0,90f));
        String stat = getHP() + "";
        g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 940);
        stat = getATK() + "";
        g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 1105);
        stat = getBLD() + "";
        g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 1270);
        et = System.nanoTime();
        IO.println("Stats took: " + ((et - st) / 100000) + "ms");
        IO.println("- - - - - - -");
        IO.println("");

        if(write) {
            try {
                ImageIO.write(bi, "png", new File(getID() + ".png"));
            } catch (IOException ioe) {

            }
        }

        return bi;
    }

    /*
         Setters
    */

    public void setData(String id, String name, String type, String desc, int hp, int atk, int bld){
        this.data[0] = id;
        this.data[1] = name;
        this.data[2] = type;
        this.data[3] = desc;
        this.data[4] = hp + "";
        this.data[5] = atk + "";
        this.data[6] = bld + "";
    }

    public void setName(String name){
        if(name == null){
            this.data[1] = "";
        } else{
            this.data[1] = name;
        }
    }
    public void setType(String type){
        if(type == null){
            this.data[2] = "";
        } else{
            this.data[2] = type;
        }
    }
    public void setDesc(String desc){
        if(desc == null){
            this.data[3] = "";
        } else{
            this.data[3] = desc;
        }
    }
    public void setHP(int hp){
        if(hp > 0){
            this.data[4] = "" + 0;
        } else{
            this.data[4] = "" + hp;
        }
    }
    public void setATK(int atk){
        if(atk > 0){
            this.data[5] = "" + 0;
        } else{
            this.data[5] = "" + atk;
        }
    }
    public void setBLD(int bld){
        if(bld > 0){
            this.data[6] = "" + 0;
        } else{
            this.data[6] = "" + bld;
        }
    }

    /*
         Getters
    */

    public String[] getData(){
        String[] copyData = new String[7];
        copyData[0] = this.data[0];
        copyData[1] = this.data[1];
        copyData[2] = this.data[2];
        copyData[3] = this.data[3];
        copyData[4] = this.data[4];
        copyData[5] = this.data[5];
        copyData[6] = this.data[6];

        return copyData;
    }

    public String getID() {
        //Check if the Identifier is Null
        if(this.data[0] == null || this.data[0].isEmpty()) {
            return "Null";
        }

        return this.data[0];
    }
    public String getName() {
        //Check if the Name is Null
        if(this.data[1] == null || this.data[1].isEmpty()) {
            return "Null";
        }

        return this.data[1];
    }
    public String getType() {
        //Check if the Type is Null
        if(this.data[2] == null || this.data[2].isEmpty()) {
            return "Null";
        }

        return this.data[2];
    }
    public String getDesc() {
        //Check if the Description is Null
        if(this.data[3] == null) {
            return "Null";
        }

        return this.data[3];
    }


    /*
     * The methods that grab the numerical stats return ints rather than Strings.
     * This is for comparisons and math in the Deck Engine.
     */
    public int getHP(){
        if(this.data[4] == null || isInt(this.data[4]) || Integer.parseInt(this.data[4]) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.data[4]);
        }
    }

    public int getATK(){
        if(this.data[5] == null || isInt(this.data[5]) || Integer.parseInt(this.data[5]) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.data[5]);
        }
    }

    public int getBLD(){
        if(this.data[6] == null || isInt(this.data[6]) || Integer.parseInt(this.data[6]) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.data[6]);
        }
    }

    @Override
    public int compareTo(Card o) {
        return this.getID().compareTo(o.getID());
    }

    @Override
    public String toString() {
        return Arrays.toString(this.getData());
    }
}
