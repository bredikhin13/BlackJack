package com.company;

/**
 * Created by Павел on 09.10.2014.
 */
public class Card {
    public int number = 0;
    public int suit = 0;
    public int weight = 0;
    public String image;
    public String label;

    public void setNumber(int number) {
        this.number = number;
        if(number>10) {
            this.weight = 10;
            switch (number){
                case 11: this.label = "J";
                    break;
                case 12: this.label = "Q";
                    break;
                case 13: this.label = "K";
                    break;
            }
        }
        else if (number == 1) {
            this.weight = 11;
            this.label = "A";
        }
        else {
            this.weight = number;
            this.label = String.valueOf(number);
        }

    }
}

