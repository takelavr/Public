package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//это класс - обработчик взаимодействия с Консолью
public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));


    // метод отвечает за писанину сообщений
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    //метод считывает с консоли строки
    public static String readString() {
        while (true) {
            try {
                String buf = bis.readLine();
                if (buf != null)
                    return buf;
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
    }

    //метод считывает с консоли числа
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        }
    }
}
