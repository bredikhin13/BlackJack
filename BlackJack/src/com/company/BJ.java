package com.company;

import java.util.Random;

/**
 * Created by Павел on 09.10.2014.
 */
public  class BJ {

    

    public static void shuffle_Deck(Card[] deck){
        Random rand = new Random();
        Card tmp = new Card();
        int r;
        for(int i = deck.length-1; i>0; i--){
            r = rand.nextInt(i);
            tmp = deck[r];
            deck[r] = deck[i];
            deck[i] = tmp;
        }
    }

    public static void init_Deck(Card[] deck ){

        for(int i = 0; i<4;i++) {
            for (int j = 0; j < 13; j++) {
                deck[13 * i + j] = new Card();
                deck[13 * i + j].setNumber(j+1);
                deck[13 * i + j].suit = i;
                deck[13 * i + j].image = 13 * i + j+".jpg";
            }
        }
        shuffle_Deck(deck);
    }

    public static Card get_Card() {
        int k;
        Card c = new Card();
        Random rand = new Random();
        //c.number = rand.nextInt(12) + 1;
        //c.suit = rand.nextInt(3) + 1;
        return c;
    }
}
