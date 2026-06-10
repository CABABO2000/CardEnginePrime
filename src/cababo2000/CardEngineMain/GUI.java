package cababo2000.CardEngineMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// https://www.youtube.com/watch?v=MtZqX9Wc8M0 by Career & Tech HQ
// https://www.youtube.com/watch?v=Kmgo00avvEw by Bro Code
// https://www.youtube.com/watch?v=A6sA9KItwpY by Bro Code
// https://www.youtube.com/watch?v=LEJ1kGHSXdA by Bro Code
// https://stackoverflow.com/questions/66822118/how-to-use-flatlaf-library-in-swing-application (Response from gthanop)
// https://stackoverflow.com/questions/16497853/scale-a-bufferedimage-the-fastest-and-easiest-way
public class GUI extends JFrame {

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
    JTextField hp = new JTextField("0");
    JTextField atk = new JTextField("0");
    JTextField bld = new JTextField("0");
    JButton applyCardButton;

    Card loadedCard = null;
    JLabel cardImage = null;

    public GUI(){ // the constructor

        cardModeButton = new JButton("Card Engine");
        cardModeButton.setFont(new Font("Born2bSportyV2", 0, 25));
        cardModeButton.setForeground(new Color(238, 255, 233));
        cardModeButton.setFocusable(false);
        cardModeButton.addActionListener((e) -> {
            mode = true;
            cardModeButton.setForeground(new Color(238, 255, 233));
            deckModeButton.setForeground(new Color(121, 147, 114));
            cardSideBar.setVisible(true);
            cardModeCenterBox.setVisible(true);
            IO.println("CardMode!");
        });
        cardModeButton.setBackground(new Color(35, 37, 35));
        cardModeButton.setPreferredSize(new Dimension(150, 55));
        cardModeButton.setBorder(new LineBorder(new Color(58, 60, 58)));

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
            IO.println("DeckMode!");

        });
        deckModeButton.setBackground(new Color(35, 37, 35));
        deckModeButton.setPreferredSize(new Dimension(150, 55));
        deckModeButton.setBorder(new LineBorder(new Color(49, 51, 49)));

        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        topBar.setPreferredSize(new Dimension(100, 65));
        topBar.setBackground(new Color(49, 51, 49));
        topBar.add(deckModeButton);
        topBar.add(cardModeButton);

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

                cardImage = new JLabel(new ImageIcon(loadedCard.buildCardImage(false).getScaledInstance((int)(990 / 1.6), (int)(1385 / 1.6), BufferedImage.SCALE_FAST)));
                cardImage.setPreferredSize(new Dimension((int)(990 / 1.6), (int)(1385 / 1.6)));
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

        newCardButton = new JButton("New Card");
        newCardButton.setFont(new Font("basis33", 0, 20));
        newCardButton.setForeground(new Color(238, 255, 233));
        newCardButton.setFocusable(false);
        newCardButton.addActionListener((e) -> {

            loadedCard = new Card();
            loadedCard.setData(id.getText(), name.getText(), type.getText(), desc.getText() ,Integer.parseInt(hp.getText()), Integer.parseInt(atk.getText()), Integer.parseInt(bld.getText()));
            cardImage = new JLabel(new ImageIcon(loadedCard.buildCardImage(false).getScaledInstance((int)(990 / 1.6), (int)(1385 / 1.6), BufferedImage.SCALE_FAST)));
            cardImage.setPreferredSize(new Dimension((int)(990 / 1.6), (int)(1385 / 1.6)));
            cardModeCenterBox.add(cardImage);
            saveCardButton.setVisible(true);
            exportCardButton.setVisible(true);
            cardSubSideBar.setVisible(true);

            cardImage.repaint();

        });
        newCardButton.setBackground(new Color(35, 37, 35));
        newCardButton.setPreferredSize(new Dimension(260, 35));
        newCardButton.setBorder(new LineBorder(new Color(58, 60, 58)));

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

        cardSubSideBar = new JPanel();
        JPanel dummyGap = new JPanel();
        dummyGap.setPreferredSize(new Dimension(240, 2));
        dummyGap.setBackground(new Color(35, 37, 35));
        cardSubSideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cardSubSideBar.setPreferredSize(new Dimension(260, 600));
        cardSubSideBar.setBackground(new Color(35, 37, 35));
        cardSubSideBar.add(dummyGap);
        cardSubSideBar.setVisible(false);

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

        cardModeCenterBox = new JPanel();
        cardModeCenterBox.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        cardModeCenterBox.setBackground(new Color(58, 60, 58));
        cardModeCenterBox.setVisible(false);

        this.setResizable(true);
        this.setLayout(new BorderLayout(1, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(58, 60, 58));
        this.setTitle("Card Engine Prime");
        this.setSize(800, 800);
        this.add(topBar, BorderLayout.NORTH);
        this.add(cardSideBar, BorderLayout.EAST);
        this.add(cardModeCenterBox, BorderLayout.CENTER);

        this.setVisible(true);
    }

}

