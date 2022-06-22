package org.rogov.Gui;

import javax.swing.*;
import java.awt.*;

//основная рамка калькулятора
public class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        // создаем титл
        setTitle("Калькулятор");

        // создаем панель
        CalculatorPanel panel = new CalculatorPanel();
        add(panel);
        pack();

        // задаем ширину и высоту всего окна
        int width = 250;
        int height = 350;

        // раскраска окна
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
    }
}

