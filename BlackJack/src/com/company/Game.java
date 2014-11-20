package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Павел on 10.10.2014.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Game {
    private int bank = 500;
    private int bet = 10;
    private int player_points = 0;
    private int dealer_points = 0;
    private ImagePanel imagePanel, dealer_imagePanel;
    private Card[] deck = new Card[52];
    private JFrame gameForm;
    private JLabel pointsLabel, dealerLabel, betLabel, bankLabel, cardLabel;
    private JButton yesButton, noButton, betButton;
    private int curCard = 0;
    private int count_player_cards = 0;
    private Card[] player_cards;
    private JSlider slider;
    private JPanel sliderPanel;
    private boolean Ace = false;

    Game() {
        bankLabel = new JLabel("YOUR BANK: " + bank);
        betButton = new JButton("Bet");
        betLabel = new JLabel("  YOUR BET: " + bet);
        betLabel.setPreferredSize(new Dimension(120, 10));
        slider = new JSlider(0, bank);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(100);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setValue(10);
        slider.setBackground(Color.green);
        slider.setForeground(Color.black);
        slider.setPreferredSize(new Dimension(400, 40));
        sliderPanel = new JPanel(new FlowLayout());
        sliderPanel.add(slider);
        sliderPanel.add(betLabel);
        sliderPanel.add(betButton);
        sliderPanel.add(bankLabel);
        sliderPanel.setBackground(Color.green);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (slider.getValue() < 10) {
                    slider.setValue(10);
                }
                bet = slider.getValue();
                betLabel.setText("  YOUR BET: " + bet);
            }
        });
        gameForm = new JFrame("Black Jack");
        pointsLabel = new JLabel("your cards");
        dealerLabel = new JLabel("Dealer");
        cardLabel = new JLabel("Next card?  ");
        yesButton = new JButton("Yes");
        yesButton.setEnabled(false);
        noButton = new JButton("No");
        noButton.setEnabled(false);
        dealer_imagePanel = new ImagePanel();
        imagePanel = new ImagePanel();
        Box box = Box.createVerticalBox();
        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        labelPanel.setBackground(Color.green);
        buttonPanel.setBackground(Color.green);
        box.add(imagePanel);
        box.add(labelPanel);
        box.add(dealer_imagePanel);
        labelPanel.add(pointsLabel);
        buttonPanel.add(cardLabel);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        labelPanel.add(sliderPanel);
        labelPanel.add(buttonPanel);
        labelPanel.add(dealerLabel);
        gameForm.setVisible(true);
        gameForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BJ.init_Deck(deck);
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayer_cards(deck[curCard++]);
                imagePanel.repaint();
                if (player_points > 21) {
                    int reply = JOptionPane.showConfirmDialog(null, "OVER 21. RESTART?", "YOU LOOSE", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.NO_OPTION) {
                        return;
                    }
                    if (reply == JOptionPane.YES_OPTION) {
                        gameRestart();
                    }
                }
            }
        });
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                dealer_Game();
            }
        });
        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setBankLabel(bank -= bet);
                betButton.setEnabled(false);
                slider.setEnabled(false);
                yesButton.setEnabled(true);
                noButton.setEnabled(true);
                player_Game();
            }
        });
        gameForm.setContentPane(box);
        gameForm.setBackground(Color.green);
        gameForm.pack();
    }

    private void setSliderTick(int b){
        if (b<=100){
            slider.setMinorTickSpacing(1);
            slider.setMajorTickSpacing(10);
            slider.setLabelTable(slider.createStandardLabels(10));
        }
        if (b>100 && b<=700){
            slider.setMinorTickSpacing(10);
            slider.setMajorTickSpacing(100);
            slider.setLabelTable(slider.createStandardLabels(100));
        }
        if (b>700 && b<=1300){
            slider.setMinorTickSpacing(100);
            slider.setMajorTickSpacing(200);
            slider.setLabelTable(slider.createStandardLabels(100));
        }
        if (b>1300 && b<=10000){
            slider.setMinorTickSpacing(100);
            slider.setMajorTickSpacing(500);
            slider.setLabelTable(slider.createStandardLabels(500));
        }
        if (b>10000){
            slider.setMinorTickSpacing(500);
            slider.setMajorTickSpacing(1000);
            slider.setLabelTable(slider.createStandardLabels(1000));
        }
    }

    private void setBankLabel(int b) {
        bankLabel.setText("YOUR BANK: " + b);
    }

    private void gameRestart() {
        if (bank < 10) {
            JOptionPane.showMessageDialog(null, "Sorry? but you loose all your many. \n Goodbay!", "Oops", JOptionPane.PLAIN_MESSAGE);
            gameForm.dispose();
            System.exit(1);
        }
        setSliderTick(bank);
        BJ.init_Deck(deck);
        curCard = 0;
        Ace = false;
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
        betButton.setEnabled(true);
        slider.setMaximum(bank);
        slider.repaint();
        slider.setEnabled(true);
        count_player_cards = 0;
        player_points = 0;
        dealer_points = 0;
        player_cards = null;
        imagePanel.clearImage();
        dealer_imagePanel.clearImage();
        dealerLabel.setText("Dealer");
        imagePanel.repaint();
        dealer_imagePanel.repaint();
        pointsLabel.setText("Your cards:");
    }


    private void setPlayer_cards(Card c) {
        player_cards[count_player_cards++] = c;
        if (Ace && (c.number == 1)){
            player_points = 21;
            try {
                imagePanel.setImage(ImageIO.read(new File("cards/" + c.image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            write_player_points();
            return;
        }
        if (c.number == 1)
            Ace = true;
        player_points += c.weight;

        if (Ace && player_points > 21) {
            player_points -= 10;
            Ace = false;
        }
        try {
            imagePanel.setImage(ImageIO.read(new File("cards/" + c.image)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        write_player_points();
    }

    private void write_player_points() {
        String s = "Your cards: ";
        for (int i = 0; i < count_player_cards; i++) {
            s += player_cards[i].label + " ";
        }

        s += "Your points: " + player_points;
        pointsLabel.setText(s);
    }

    public void dealer_Game() {
        String s = "Dealer cards ";
        while (dealer_points < 17) {
            dealer_points += deck[curCard].weight;
            try {
                dealer_imagePanel.setImage(ImageIO.read(new File("cards/" + deck[curCard].image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            s += deck[curCard++].label + " ";
        }
        dealer_imagePanel.repaint();
        s += "Dealer points: " + dealer_points;
        dealerLabel.setText(s);
        String message = "";
        if (Ace && (player_points == 21) && (count_player_cards == 2)) {
            message = "BLACK JACK! YOU WIN!";
            bank += bet + 1.5 * bet;
        } else if (dealer_points > 21) {
            message = "CONGRATULATIONS! YOU WIN!";
            bank += 2 * bet;
        } else if (dealer_points == player_points) {
            message = "TIE";
            bank += bet;
        } else if (dealer_points > player_points) {
            message = "CASINO WIN! YOU LOOSE";

        } else {
            message = "CONGRATULATIONS! YOU WIN!";
            bank += 2 * bet;
        }
        setBankLabel(bank);
        message += " RESTART?";


        int reply = JOptionPane.showConfirmDialog(null, message, "Result", JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.NO_OPTION) {
            return;
        } else {
            gameRestart();
        }

    }

    public void player_Game() {
        player_cards = new Card[10];
        setPlayer_cards(deck[curCard++]);
        setPlayer_cards(deck[curCard++]);
        imagePanel.repaint();
    }
}
