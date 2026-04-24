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
import java.util.Scanner;

import static cababo2000.CEUtils.StringUtils.*;

/**
 * The Card class represents an instance of a card. As well, it provides methods that assist in building, saving, and loading card data.
 */
public class Card implements Comparable<Card>{
    // Identifier, Name, Type, Description, Attack, Starting Health, Blood
    private String identifier;
    private String name;
    private String type;
    private String desc;
    private String atk;
    private String shp;
    private String bld;
    private String author;

    /*
         Constructors
    */

    public Card() {
        this("Null", "", "", "", 0, 0, 0);
    }
    public Card(String id, String name, String type, String desc, int hp, int atk, int bld){
        setData(id, name, type, desc, hp, atk, bld);
    }

    /*
         Utilities
    */

    // https://www.youtube.com/watch?v=pJt-AYrmopo by Scott Rowell

    /**
     * The saveCardData method stores the data in the Card Object into a .json file. Utilizes the Gson library.
     */
    public void saveCardData(){
        Gson jsonout = new GsonBuilder().setPrettyPrinting().create();
        try {
            PrintStream output = new PrintStream(new File(identifier + ".json"));
            String printout = new String(jsonout.toJson(this));

            output.println(printout);
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
    public BufferedImage buildCardImage(boolean write) {

        URL cardFormatImage = null;
        Graphics2D g2 = null;
        BufferedImage bi = null;

            try {
                cardFormatImage = new URL("https://drive.google.com/uc?export=download&id=1qLYZO8AKTfp9R3sbKc4zRg1Tusp7egzo");
            } catch (MalformedURLException mue) {
                IO.print("Please provide a URL");
            }

            try {
                bi = ImageIO.read(cardFormatImage);
                g2 = bi.createGraphics();
            } catch (IOException ioe) {
                IO.println("IOException has occurred");
            }

            g2.setFont(new Font("basis33", Font.BOLD, 45));
            g2.setColor(Color.WHITE);
            for (int i = 0; i < 12; i++) {
                g2.drawString(textWrap(getDesc(), 34, 12, true)[i], 235, 853 + (i * 45));
            }

            g2.setFont(new Font("Born2bSportyV2", 0, 34));
            g2.drawString(crudeCenterAlign(getType(), 68), 110, 120);

            g2.setFont(new Font("Born2bSportyV2", 0, 80));
            g2.drawString(crudeCenterAlign(getName(), 33), 74, 79);

            g2.setFont(new Font("Born2bSportyV2", 0, 90));
            String stat = getATK() + "";
            g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 940);
            stat = getHP() + "";
            g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 1105);
            stat = getBLD() + "";
            g2.drawString(stat, 145 - ((stat.length() - 1) * 39), 1270);

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
        this.identifier = id;
        this.name = name;
        this.type = type;
        this.desc = desc;
        if(atk < 0){
            this.atk = "" + 0;
        } else{
            this.atk = "" + atk;
        }
        if(hp < 0){
            this.shp = "" + 0;
        }else{
            this.shp = "" + hp;
        }
        if(bld < 0){
            this.bld = "" + 0;
        }else{
            this.bld = "" + bld;
        }
    }

    public void setName(String name){
        if(name == null){
            this.name = "";
        } else{
            this.name = name;
        }
    }
    public void setType(String type){
        if(type == null){
            this.type = "";
        } else{
            this.type = type;
        }
    }
    public void setDesc(String desc){
        if(desc == null){
            this.desc = "";
        } else{
            this.desc = desc;
        }
    }
    public void setATK(int atk){
        if(atk > 0){
            this.atk = "" + 0;
        } else{
            this.atk = "" + atk;
        }
    }
    public void setHP(int hp){
        if(hp > 0){
            this.shp = "" + 0;
        } else{
            this.shp = "" + hp;
        }
    }
    public void setBLD(int bld){
        if(bld > 0){
            this.bld = "" + 0;
        } else{
            this.bld = "" + bld;
        }
    }

    /*
         Getters
    */

    public String[] getData(){
        String[] copyData = new String[7];
        copyData[0] = this.identifier;
        copyData[1] = this.name;
        copyData[2] = this.type;
        copyData[3] = this.desc;
        copyData[4] = this.atk;
        copyData[5] = this.shp;
        copyData[6] = this.bld;

        return copyData;
    }

    public String getID() {
        //Check if the Identifier is Null
        if(this.identifier == null || this.identifier.length() == 0) {
            return "Null";
        }

        return this.identifier;
    }
    public String getName() {
        //Check if the Name is Null
        if(this.name == null || this.name.length() == 0) {
            return "Null";
        }

        return this.name;
    }
    public String getType() {
        //Check if the Type is Null
        if(this.type == null || this.type.length() == 0) {
            return "Null";
        }

        return this.type;
    }
    public String getDesc() {
        //Check if the Description is Null
        if(this.desc == null) {
            return "Null";
        }

        return this.desc;
    }

    public int getATK(){
        if(this.atk == null || isInt(this.atk) || Integer.parseInt(this.atk) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.atk);
        }
    }

    public int getHP(){
        if(this.shp == null || isInt(this.shp) || Integer.parseInt(this.shp) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.shp);
        }
    }

    public int getBLD(){
        if(this.bld == null || isInt(this.bld) || Integer.parseInt(this.bld) < 0) {
            return 0;
        } else {
            return Integer.parseInt(this.bld);
        }
    }

    @Override
    public int compareTo(Card o) {
        return this.getID().compareTo(o.getID());
    }
}