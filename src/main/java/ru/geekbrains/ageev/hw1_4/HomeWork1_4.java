package ru.geekbrains.ageev.hw1_4;

import java.util.Random;
import java.util.Scanner;

public class HomeWork1_4 {

    // Написал код с нуля по лекции
    static final char DOT_HUMAN = 'X'; // Фишка человека (можно выбирать другой символ)
    static final char DOT_AI = '0'; // Фишка компьютера (можно выбирать другой символ, отличный от фишки человека)
    static final char DOT_EMPTY = '•'; // Признак пустой ячейки поля

    static final int WIN_COUNT = 3; // Выигрышная комбинация

    static final Scanner scanner = new Scanner(System.in); // Для чтения вводимых человеком координат
    static final Random random = new Random(); // Рандомайзер хода компьютера

    static char[][] field; // Двумерный массив, хранящий текущее состояние игрового поля
    static int fieldSizeX; // Размерность игрового поля по координате X
    static int fieldSizeY; // Размерность игрового поля по координате Y

    /**
     * Инициализация игрового поля
     */
    static void initialize (){
        // Задаем размерность игрового поля
        fieldSizeX = 5;
        fieldSizeY = 5;
        // Инициализация массива, описывающего игровое поле
        field = new char[fieldSizeX][fieldSizeY];
        // Проинициализируем массив, описывающий игровое поле
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    static void printField (){
        // Верхний левый угол игрового поля
        System.out.print("┌");
        // Шапка игрового поля
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            /*if (i % 2 == 0){
                System.out.print("-");
            }
            else {
                System.out.println((i / 2) + 1);
            }*/
            System.out.print((i % 2) == 0 ? "–" : i / 2 + 1); // Тернарная операция взамен кода выше
        }
        System.out.println();
        // Тело игрового поля
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();            
        }
        // Нижняя граница игрового поля
        for (int i = 0; i <= fieldSizeX * 2 + 1; i++) {
            System.out.print("‾");
        }
        System.out.println();
    }

    /**
     * Обработка хода человека
     */
    static void humanTurn(){
        int x, y;
        // Делаем, пока выполняется условие ниже
        do {
            System.out.printf("Введите координаты хода X и Y через пробел от 1 до %d и %d соответственно", fieldSizeX, fieldSizeY);
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        // Условие
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        // Присваиваем по координате фишку человека
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Обработка хода компьютера
     */
    static void aiTurn(){
        int x, y;
        // Делаем, пока выполняется условие ниже
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        // Условие (только проверка на занято/не занято)
        while (!isCellEmpty(x, y));
        // Присваиваем по координате фишку компьютера
        field[x][y] = DOT_AI;

    }

    /**
     * Проверка, является ли ячейка пустой
     * @param x Координата "x" ячейки
     * @param y Координата "y" ячейки
     * @return Возвращает истину, если ячейка пустая
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка введенных координат, находятся ли в пределах поля
     * @param x Координата "x" ячейки
     * @param y Координата "y" ячейки
     * @return Возвращает истину, если координаты внутри поля
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка на ничью (всё поле заполнено фишками)
     * @return Возвращает истину, если всё поле заполнено (ничья)
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    static boolean checkWin(char c) {
        // Проверка по трем горизонталям
        if(field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if(field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if(field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        // Проверка по вертикалям
        if(field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if(field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if(field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        // Проверка по диагоналям
        if(field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if(field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

        return false;
    }

    static boolean checkWinV2(char c){
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (checkWinDot(c, x, y, WIN_COUNT))
                    return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки состояния игры (версия 2)
     * @param c Фишка игрока
     * @param x Координата хода Х
     * @param y Координата хода У
     * @param winCount Выигрыш
     * @return Возвращает true, если игрок выиграл
     */
    static boolean checkWinDot(char c, int x, int y, int winCount){
        //Здесь пытался по одной диагонали проверить
        // Инициализирую индивидуальный счетчик для проверки по одной диагонали
        int counterDiagLeft = 0;
        for (int i = x; i < x + winCount; i++) {
            // Нахожу количество фишек по условию
            if (field[i][i] == c){
                counterDiagLeft++;
                System.out.println("DL"); // Добавил печать для наглядности работы кода, когда пытался разобраться
            }
        }
        // Если досичтали до победного количества, возвращаем true
        if (counterDiagLeft == winCount){
            return true;
        }
        //Здесь пытался по другой диагонали проверить, логика написания кода аналогична
        int counterDiagRight = 0;
        for (int i = x; i < x + winCount; i++) {
            if (field[i][x + winCount - i] == c){
                counterDiagRight++;
                System.out.println("DR"); // Добавил печать для наглядности работы кода, когда пытался разобраться
            }
        }
        if (counterDiagRight == winCount){
            return true;
        }
        //Здесь пытался по горизонтали проверить, логика написания кода аналогична
        int counterHorizontal = 0;
        for (int i = x; i < x + winCount; i++) {
            if (field[i][y] == c){
                counterHorizontal++;
                System.out.println("H"); // Добавил печать для наглядности работы кода, когда пытался разобраться
            }
        }
        if (counterHorizontal == winCount){
            return true;
        }
        //Здесь пытался по вертикали проверить, логика написания кода аналогична
        int counterVertical = 0;
        for (int i = y; i < y + winCount; i++) {
            if (field[x][i] == c){
                counterVertical++;
                System.out.println("V"); // Добавил печать для наглядности работы кода, когда пытался разобраться
            }
        }
        if (counterVertical == winCount){
            return true;
        }
        return false;
    }

    /**
     * Метод проверки состояния игры
     * @param c Фишка игрока
     * @param message Победный слоган
     * @return Возващает true - завершение игры
     */
    static boolean gameCheck(char c, String message){
        if (checkWinV2(c)){
            System.out.println(message);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        // Продожаем игру
        return false;
    }

    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!")){
                    break;
                }
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Победил компьютер!")){
                    break;
                }
            }
            System.out.println("Желаете сыграть еще раз?");
            if(!scanner.next().equalsIgnoreCase("Y")){
                break;
            }
        }


    }



}
