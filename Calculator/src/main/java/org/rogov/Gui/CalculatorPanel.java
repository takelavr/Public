package org.rogov.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;


// отработка кнопок и операций
public class CalculatorPanel extends JPanel {

    // дисплей с ответом
    private JButton display;

    // панель
    private JPanel panel;
    private BigDecimal result;

    private String lastCommand;
    private boolean start;


    // создаем саму панель калькулятора
    public CalculatorPanel() {
        setLayout(new BorderLayout());
        result = BigDecimal.ZERO;
        lastCommand = "=";
        start = true;

        //значение при запуске калькулятора
        display = new JButton("0");
        display.setEnabled(false);

        // размер шрифта в дисплее
        //располагаем дисплей по верху рамки
        display.setFont(display.getFont().deriveFont(50f));
        add(display, BorderLayout.NORTH);

        // создаем слушателя и команду
        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        // создаем понель с кнопками и даем им значение и признак
        // и размер(количество столбец строка)
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("÷", command);
        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);
        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("–", command);
        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        // располагаем по центру. JFrame сама разобьем на длину и ширину исходя из размера самого окна
        add(panel, BorderLayout.CENTER);
    }

    // метод для создания кнопок и из слушателей
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        // задаем размер шрифта и присваиваем ей значение слушателя
        button.setFont(button.getFont().deriveFont(20f));
        button.addActionListener(listener);
        panel.add(button);
    }

    //  отработка событий нажатия как вставки данных (чисел)
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();

            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

    // отработка нажатий операторов
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else lastCommand = command;
            } else {
                calculate(new BigDecimal(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }

    // основная отработка комманд на калькуляторе через бибилиотеку BigDecimal и вывод его в дисплей
    public void calculate(BigDecimal x) {
        if (lastCommand.equals("+")) result = result.add(x);
        else if (lastCommand.equals("-")) result = result.subtract(x);
        else if (lastCommand.equals("*")) result = result.multiply(x);
        else if (lastCommand.equals("/")) result = result.divide(x);
        else if (lastCommand.equals("=")) result = x;
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            result = BigDecimal.ZERO;
        }
        display.setText(result.toString());
    }
}
