package main.java.CardEngineMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
This file is an unholy obelisk of cobbled together code shoved into a constructor. The documentation of this file
is likely to be lackluster.
 */

// https://www.youtube.com/watch?v=MtZqX9Wc8M0 by Career & Tech HQ
// https://www.youtube.com/watch?v=Kmgo00avvEw by Bro Code
// https://www.youtube.com/watch?v=A6sA9KItwpY by Bro Code
// https://www.youtube.com/watch?v=LEJ1kGHSXdA by Bro Code
// https://www.youtube.com/watch?v=mDfBc9ViRrM by Turtle Code
// https://stackoverflow.com/questions/66822118/how-to-use-flatlaf-library-in-swing-application (Response from gthanop)
// https://stackoverflow.com/questions/16497853/scale-a-bufferedimage-the-fastest-and-easiest-way
/**
 * The GUI class handles all of the graphical interface logic outside of card/deck images.
 */
public class GUI extends JFrame {

    // initialize most buttons prior to utilization.
    JButton cardModeButton;
    JButton deckModeButton;
    JButton loadCardButton;
    JButton newCardButton;
    JButton exportCardButton;
    JButton saveCardButton;
    boolean mode = false; // false = deckmode, true = cardmode

    JPanel topBar;
    JPanel cardSideBar;
    JPanel cardModeCenterBox;

    JPanel cardSubSideBar;
    JTextField id = new JTextField("ID");
    JTextField name = new JTextField("Name");
    JTextField type = new JTextField("Type");
    JTextArea desc = new JTextArea("Description");
    JTextField hp = new JTextField("1");
    JTextField atk = new JTextField("2");
    JTextField bld = new JTextField("3");
    JButton applyCardButton;

    Card loadedCard = null;
    JLabel cardImage = null;

    JPanel deckSideBar;
    JPanel deckSubSideBar;
    JPanel deckModeCenterBox;
    JScrollPane decklistDisplay;
    JButton exportDeckButton;
    JButton saveDeckButton;
    JList<Card> decklist;
    DefaultListModel<Card> model;
    JTextField did = new JTextField("ID");
    JTextField dname = new JTextField("Name");
    JButton applyDeckButton;
    JTextField rem = new JTextField("1");
    JButton addCardButton;
    JButton remCardButton;

    JButton loadDeckButton;
    JButton newDeckButton;

    Deck loadedDeck = null;

    public GUI(){ // the constructor

        // This button toggles the Card making UI on while toggling the Deck making UI off.
        // Note: The two button toggles just hide the UI, so you can switch back and forth
        // without losing data.
        cardModeButton = new JButton("Card Engine");
        cardModeButton.setFont(new Font("Born2bSportyV2", 0, 25));
        cardModeButton.setForeground(new Color(238, 255, 233));
        cardModeButton.setFocusable(false);
        cardModeButton.addActionListener((e) -> {

            mode = true;
            cardModeButton.setForeground(new Color(238, 255, 233));
            deckModeButton.setForeground(new Color(121, 147, 114));
            deckSideBar.setVisible(false);
            deckModeCenterBox.setVisible(false);
            this.remove(deckSideBar);
            this.remove(deckModeCenterBox);
            this.add(cardModeCenterBox, BorderLayout.CENTER);
            this.add(cardSideBar, BorderLayout.EAST);
            cardSideBar.setVisible(true);
            cardModeCenterBox.setVisible(true);
            IO.println("CardMode!");

        });
        cardModeButton.setBackground(new Color(35, 37, 35));
        cardModeButton.setPreferredSize(new Dimension(150, 55));
        cardModeButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button toggles the Deck making UI on and toggles the Card making UI off.
        deckModeButton = new JButton("Deck Engine");
        deckModeButton.setFont(new Font("Born2bSportyV2", 0, 25));
        deckModeButton.setForeground(new Color(238, 255, 233));
        deckModeButton.setFocusable(false);
        deckModeButton.addActionListener((e) -> {

            mode = false;
            deckModeButton.setForeground(new Color(238, 255, 233));
            cardModeButton.setForeground(new Color(121, 147, 114));
            cardSideBar.setVisible(false);
            cardModeCenterBox.setVisible(false);
            this.remove(cardSideBar);
            this.remove(cardModeCenterBox);
            this.add(deckModeCenterBox, BorderLayout.CENTER);
            this.add(deckSideBar, BorderLayout.EAST);
            deckSideBar.setVisible(true);
            deckModeCenterBox.setVisible(true);
            IO.println("DeckMode!");

        });
        deckModeButton.setBackground(new Color(35, 37, 35));
        deckModeButton.setPreferredSize(new Dimension(150, 55));
        deckModeButton.setBorder(new LineBorder(new Color(49, 51, 49)));

        // The top menu bar that houses the Deck/Card mode toggle buttons.
        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        topBar.setPreferredSize(new Dimension(100, 65));
        topBar.setBackground(new Color(49, 51, 49));
        topBar.add(deckModeButton);
        topBar.add(cardModeButton);

        // Card Mode Buttons
        // This button loads a saved Card from your computer.
        loadCardButton = new JButton("Load Card");
        loadCardButton.setFont(new Font("basis33", 0, 20));
        loadCardButton.setForeground(new Color(238, 255, 233));
        loadCardButton.setFocusable(false);
        loadCardButton.addActionListener((e) -> {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION) {
                File chosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                loadedCard = Card.loadCardData(chosenFile);

                if(cardImage != null) cardImage.setIcon(null);
                cardImage = new JLabel(new ImageIcon(loadedCard.buildCardImage(false).getScaledInstance((int)(990 / 1.6), (int)(1385 / 1.6), BufferedImage.SCALE_FAST)));
                cardImage.setPreferredSize(new Dimension((int)(990 / 1.6), (int)(1385 / 1.6)));
                cardModeCenterBox.removeAll();
                cardModeCenterBox.add(cardImage);
                saveCardButton.setVisible(true);
                exportCardButton.setVisible(true);
                cardSubSideBar.setVisible(true);

                id.setText(loadedCard.getID());
                name.setText(loadedCard.getName());
                type.setText(loadedCard.getType());
                desc.setText(loadedCard.getDesc());
                hp.setText(loadedCard.getHP() + "");
                atk.setText(loadedCard.getATK() + "");
                bld.setText(loadedCard.getBLD() + "");
            }

        });
        loadCardButton.setBackground(new Color(35, 37, 35));
        loadCardButton.setPreferredSize(new Dimension(260, 35));
        loadCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button creates a new Card, overriding the Card that was previously being edited.
        newCardButton = new JButton("New Card");
        newCardButton.setFont(new Font("basis33", 0, 20));
        newCardButton.setForeground(new Color(238, 255, 233));
        newCardButton.setFocusable(false);
        newCardButton.addActionListener((e) -> {

            id.setText("ID");
            name.setText("Name");
            type.setText("Type");
            desc.setText("Description");
            hp.setText("1");
            atk.setText("2");
            bld.setText("3");
            loadedCard = null;

            loadedCard = new Card();
            loadedCard.setData(id.getText(), name.getText(), type.getText(), desc.getText() ,Integer.parseInt(hp.getText()), Integer.parseInt(atk.getText()), Integer.parseInt(bld.getText()));
            if(cardImage != null) cardImage.setIcon(null);
            cardImage = new JLabel(new ImageIcon(loadedCard.buildCardImage(false).getScaledInstance((int)(990 / 1.6), (int)(1385 / 1.6), BufferedImage.SCALE_FAST)));
            cardImage.setPreferredSize(new Dimension((int)(990 / 1.6), (int)(1385 / 1.6)));
            cardModeCenterBox.removeAll();
            cardModeCenterBox.add(cardImage);
            saveCardButton.setVisible(true);
            exportCardButton.setVisible(true);
            cardSubSideBar.setVisible(true);

            cardImage.repaint();

        });
        newCardButton.setBackground(new Color(35, 37, 35));
        newCardButton.setPreferredSize(new Dimension(260, 35));
        newCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button outputs a .png of the Card onto the computer.

        // The file SHOULD appear in the same directory as the .jar,
        // but results have been inconsistent across different devices.
        exportCardButton = new JButton("Export Card [Image]");
        exportCardButton.setFont(new Font("basis33", 0, 20));
        exportCardButton.setForeground(new Color(238, 255, 233));
        exportCardButton.setFocusable(false);
        exportCardButton.addActionListener((e) -> {

            try {
                ImageIO.write(loadedCard.buildCardImage(false), "png", new File(loadedCard.getName() + ".png"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        exportCardButton.setBackground(new Color(35, 37, 35));
        exportCardButton.setPreferredSize(new Dimension(260, 35));
        exportCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));
        exportCardButton.setVisible(false);

        // This button outputs a .json file representing the currently active Card
        // onto the computer.
        // Much like the last one, results have varied.
        saveCardButton = new JButton("Export Card [Json]");
        saveCardButton.setFont(new Font("basis33", 0, 20));
        saveCardButton.setForeground(new Color(238, 255, 233));
        saveCardButton.setFocusable(false);
        saveCardButton.addActionListener((e) -> {

            loadedCard.saveCardData();

        });
        saveCardButton.setBackground(new Color(35, 37, 35));
        saveCardButton.setPreferredSize(new Dimension(260, 35));
        saveCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));
        saveCardButton.setVisible(false);

        // The box that houses the buttons/text fields that edit the currently active Card.
        cardSubSideBar = new JPanel();
        JPanel dummyGap = new JPanel(); // The dummy gap is just an invisible box that acts as spacing.
        dummyGap.setPreferredSize(new Dimension(240, 2));
        dummyGap.setBackground(new Color(35, 37, 35));
        cardSubSideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cardSubSideBar.setPreferredSize(new Dimension(260, 600));
        cardSubSideBar.setBackground(new Color(35, 37, 35));
        cardSubSideBar.add(dummyGap);
        cardSubSideBar.setVisible(false);

        // These text fields edit properties of the cards.
        id.setFont(new Font("basis33", Font.ITALIC, 20));
        id.setPreferredSize(new Dimension(240, 35));
        id.setForeground(new Color(238, 255, 233));
        id.setBackground(new Color(49, 51, 49));

        name.setFont(new Font("basis33", Font.ITALIC, 20));
        name.setPreferredSize(new Dimension(240, 35));
        name.setForeground(new Color(238, 255, 233));
        name.setBackground(new Color(49, 51, 49));

        type.setFont(new Font("basis33", Font.ITALIC, 20));
        type.setPreferredSize(new Dimension(240, 35));
        type.setForeground(new Color(238, 255, 233));
        type.setBackground(new Color(49, 51, 49));

        desc.setFont(new Font("basis33", Font.ITALIC, 20));
        desc.setPreferredSize(new Dimension(240, 360));
        desc.setForeground(new Color(238, 255, 233));
        desc.setBackground(new Color(49, 51, 49));
        desc.setLineWrap(true);
        desc.setRows(12);

        hp.setFont(new Font("basis33", Font.ITALIC, 20));
        hp.setPreferredSize(new Dimension(76, 35));
        hp.setForeground(new Color(238, 255, 233));
        hp.setBackground(new Color(49, 51, 49));

        atk.setFont(new Font("basis33", Font.ITALIC, 20));
        atk.setPreferredSize(new Dimension(76, 35));
        atk.setForeground(new Color(238, 255, 233));
        atk.setBackground(new Color(49, 51, 49));

        bld.setFont(new Font("basis33", Font.ITALIC, 20));
        bld.setPreferredSize(new Dimension(76, 35));
        bld.setForeground(new Color(238, 255, 233));
        bld.setBackground(new Color(49, 51, 49));

        cardSubSideBar.add(id);
        cardSubSideBar.add(name);
        cardSubSideBar.add(type);
        cardSubSideBar.add(desc);
        cardSubSideBar.add(hp);
        cardSubSideBar.add(atk);
        cardSubSideBar.add(bld);

        // This button updates the render and the internal data of the currently active Card
        // based on the information in the accompanying text fields.
        applyCardButton = new JButton("Apply");
        applyCardButton.setFont(new Font("basis33", 0, 20));
        applyCardButton.setForeground(new Color(238, 255, 233));
        applyCardButton.setFocusable(false);
        applyCardButton.addActionListener((e) -> {

            loadedCard.setData(id.getText(), name.getText(), type.getText(), desc.getText() ,Integer.parseInt(hp.getText()), Integer.parseInt(atk.getText()), Integer.parseInt(bld.getText()));
            cardImage.setIcon(new ImageIcon(loadedCard.buildCardImage(false).getScaledInstance((int)(990 / 1.6), (int)(1385 / 1.6), BufferedImage.SCALE_FAST)));
            cardImage.repaint();

        });
        applyCardButton.setBackground(new Color(35, 37, 35));
        applyCardButton.setPreferredSize(new Dimension(240, 35));
        applyCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        cardSubSideBar.add(applyCardButton);

        // The location of the Load, New, Both Export buttons, and the sub sidebar.
        cardSideBar = new JPanel();
        cardSideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        cardSideBar.setPreferredSize(new Dimension(300, 65));
        cardSideBar.setBackground(new Color(49, 51, 49));
        cardSideBar.setVisible(false);
        cardSideBar.add(loadCardButton);
        cardSideBar.add(newCardButton);
        cardSideBar.add(exportCardButton);
        cardSideBar.add(saveCardButton);
        cardSideBar.add(cardSubSideBar);

        // The location of the Card render.
        cardModeCenterBox = new JPanel();
        cardModeCenterBox.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        cardModeCenterBox.setBackground(new Color(58, 60, 58));
        cardModeCenterBox.setVisible(false);


        // The Deck Buttons

        // Not a button, the location of the Deck List render.
        deckModeCenterBox = new JPanel();
        deckModeCenterBox.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        deckModeCenterBox.setBackground(new Color(58, 60, 58));
        deckModeCenterBox.setVisible(false);

        // This button applies the ID and Name information internally into the Deck, similar
        // to the Card button equivalent.
        applyDeckButton = new JButton("Apply Name/ID");
        applyDeckButton.setFont(new Font("basis33", 0, 20));
        applyDeckButton.setForeground(new Color(238, 255, 233));
        applyDeckButton.setFocusable(false);
        applyDeckButton.addActionListener((e) -> {

            loadedDeck.setName(dname.getText());
            loadedDeck.setIdentifier(did.getText());

        });
        applyDeckButton.setBackground(new Color(35, 37, 35));
        applyDeckButton.setPreferredSize(new Dimension(240, 35));
        applyDeckButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button loads a saved Deck from your computer.
        loadDeckButton = new JButton("Load Deck");
        loadDeckButton.setFont(new Font("basis33", 0, 20));
        loadDeckButton.setForeground(new Color(238, 255, 233));
        loadDeckButton.setFocusable(false);
        loadDeckButton.addActionListener((e) -> {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION) {
                File chosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                loadedDeck = Deck.loadDeckData(chosenFile);

                Card[] dlarray = loadedDeck.getCardList().toArray(new Card[0]);
                JList<Card> decklist = new JList<>(dlarray);
                decklistDisplay = new JScrollPane(decklist);
                decklistDisplay.setPreferredSize(new Dimension(400, 900));
                deckModeCenterBox.removeAll();
                deckModeCenterBox.add(decklistDisplay);
                saveDeckButton.setVisible(true);
                exportDeckButton.setVisible(true);
                deckSubSideBar.setVisible(true);
            }

        });
        loadDeckButton.setBackground(new Color(35, 37, 35));
        loadDeckButton.setPreferredSize(new Dimension(260, 35));
        loadDeckButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button creates a new Deck, overriding the Deck that was previously being edited.
        newDeckButton = new JButton("New Deck");
        newDeckButton.setFont(new Font("basis33", 0, 20));
        newDeckButton.setForeground(new Color(238, 255, 233));
        newDeckButton.setFocusable(false);
        newDeckButton.addActionListener((e) -> {

            loadedDeck = null;
            decklist = null;
            model = null;

            did.setText("ID");
            dname.setName("Name");
            rem.setText("0");

            loadedDeck = new Deck("ID", "Name");
            decklist = new JList<>();
            model = new DefaultListModel<>();
            model.addAll(loadedDeck.getCardList());
            decklist.setModel(model);
            decklistDisplay = new JScrollPane(decklist);
            decklistDisplay.setPreferredSize(new Dimension(500, 900));

            deckModeCenterBox.removeAll();
            deckModeCenterBox.add(decklistDisplay);
            saveDeckButton.setVisible(true);
            exportDeckButton.setVisible(true);
            deckSubSideBar.setVisible(true);

        });
        newDeckButton.setBackground(new Color(35, 37, 35));
        newDeckButton.setPreferredSize(new Dimension(260, 35));
        newDeckButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button outputs a .png of the Card onto the computer.
        exportDeckButton = new JButton("Export Deck [Image]");
        exportDeckButton.setFont(new Font("basis33", 0, 20));
        exportDeckButton.setForeground(new Color(238, 255, 233));
        exportDeckButton.setFocusable(false);
        exportDeckButton.addActionListener((e) -> {

            loadedDeck.buildDeckImage(true);

        });
        exportDeckButton.setBackground(new Color(35, 37, 35));
        exportDeckButton.setPreferredSize(new Dimension(260, 35));
        exportDeckButton.setBorder(new LineBorder(new Color(58, 60, 58)));
        exportDeckButton.setVisible(false);

        // This button outputs a .json file representing the currently active Card
        // onto the computer.
        saveDeckButton = new JButton("Export Deck [Json]");
        saveDeckButton.setFont(new Font("basis33", 0, 20));
        saveDeckButton.setForeground(new Color(238, 255, 233));
        saveDeckButton.setFocusable(false);
        saveDeckButton.addActionListener((e) -> {

            loadedDeck.saveDeckData();

        });
        saveDeckButton.setBackground(new Color(35, 37, 35));
        saveDeckButton.setPreferredSize(new Dimension(260, 35));
        saveDeckButton.setBorder(new LineBorder(new Color(58, 60, 58)));
        saveDeckButton.setVisible(false);

        // These text fields determine the currently active Deck's ID and Name.
        did.setFont(new Font("basis33", Font.ITALIC, 20));
        did.setPreferredSize(new Dimension(240, 35));
        did.setForeground(new Color(238, 255, 233));
        did.setBackground(new Color(49, 51, 49));

        dname.setFont(new Font("basis33", Font.ITALIC, 20));
        dname.setPreferredSize(new Dimension(240, 35));
        dname.setForeground(new Color(238, 255, 233));
        dname.setBackground(new Color(49, 51, 49));

        // This text field determines which Card in the list to remove once the
        // remCardButton is pressed.
        rem.setFont(new Font("basis33", Font.ITALIC, 20));
        rem.setPreferredSize(new Dimension(110, 35));
        rem.setForeground(new Color(238, 255, 233));
        rem.setBackground(new Color(49, 51, 49));

        // This button adds a card into the list.
        addCardButton = new JButton("Add a Card");
        addCardButton.setFont(new Font("basis33", 0, 20));
        addCardButton.setForeground(new Color(238, 255, 233));
        addCardButton.setFocusable(false);
        addCardButton.addActionListener((e) -> {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setMultiSelectionEnabled(true);
            int response = fileChooser.showOpenDialog(null);
            Card addedcard;

            if(response == JFileChooser.APPROVE_OPTION) {
                for(File f : fileChooser.getSelectedFiles()) {
                    if(!Card.loadCardData(new File(f.getAbsolutePath())).getID().equals("NULL")) {
                        addedcard = Card.loadCardData(f);

                        loadedDeck.addCard(addedcard);
                    }
                }
                model.clear();
                model.addAll(Deck.sortByName(loadedDeck));
            }

        });
        addCardButton.setBackground(new Color(35, 37, 35));
        addCardButton.setPreferredSize(new Dimension(240, 35));
        addCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // This button removes the Card at the specified index from the list.
        remCardButton = new JButton("Remove Card:");
        remCardButton.setFont(new Font("basis33", 0, 20));
        remCardButton.setForeground(new Color(238, 255, 233));
        remCardButton.setFocusable(false);
        remCardButton.addActionListener((e) -> {

            loadedDeck.remCard(Integer.parseInt(rem.getText()));

            model.clear();
            model.addAll(Deck.sortByName(loadedDeck));

        });
        remCardButton.setBackground(new Color(35, 37, 35));
        remCardButton.setPreferredSize(new Dimension(110, 35));
        remCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

        // The location of the buttons/text fields that edit the properties of the
        // currently active Deck.
        deckSubSideBar = new JPanel();
        deckSubSideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        deckSubSideBar.setPreferredSize(new Dimension(260, 600));
        deckSubSideBar.setBackground(new Color(35, 37, 35));
        deckSubSideBar.add(dummyGap);
        deckSubSideBar.add(did);
        deckSubSideBar.add(dname);
        deckSubSideBar.add(applyDeckButton);
        deckSubSideBar.add(dummyGap);
        deckSubSideBar.add(addCardButton);
        deckSubSideBar.add(remCardButton);
        deckSubSideBar.add(rem);
        deckSubSideBar.setVisible(false);

        // Location of the sub sidebar, Load, New, and Export buttons.
        deckSideBar = new JPanel();
        deckSideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        deckSideBar.setPreferredSize(new Dimension(300, 65));
        deckSideBar.setBackground(new Color(49, 51, 49));
        deckSideBar.setVisible(false);
        deckSideBar.add(loadDeckButton);
        deckSideBar.add(newDeckButton);
        deckSideBar.add(exportDeckButton);
        deckSideBar.add(saveDeckButton);
        deckSideBar.add(deckSubSideBar);

        this.setResizable(true);
        this.setLayout(new BorderLayout(1, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(58, 60, 58));
        this.setTitle("Card Engine Prime");
        this.setSize(800, 800);
        this.add(topBar, BorderLayout.NORTH);

        this.setVisible(true);
    }

}

