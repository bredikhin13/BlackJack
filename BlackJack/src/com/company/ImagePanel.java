package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Павел on 12.11.2014.
 */
public class ImagePanel extends JPanel {
    private Image[] image = new Image[10];
    private int count = 0;

    ImagePanel(){
        Dimension d = new Dimension(800,200);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setLayout(new FlowLayout());
    }
    public Image getImage() {
        return image[count];
    }

    public void clearImage () {
        image = new Image[10];
        count = 0;
    }
    public void setImage(Image image) {
        this.image[count++] = image;

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int X = 0;
        for(int i = 0;i<count;i++) {
            if (image[i] != null) {
                g.drawImage(image[i], X += 50, 0, getWidth()/7, getHeight(), null);
            }
        }
    }
}
