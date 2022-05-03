package com.dala.utils;

import java.util.Scanner;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Author: Davide
 Datum: 17.03.2021
 Zeit: 10:43
 Projekt: 20210317-OutputUtils
 Programm: Java Programm
 Beschreibung:
 ----------------------------------------------------------------------
 ***/

public class OutputUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static int[][][] letters = {
            {{0, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}, //A
            {{1, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 0}}, //B
            {{0, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 1, 1, 1, 1,}}, //C
            {{1, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 0}}, //D
            {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}}, //E
            {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}}, //F
            {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 0, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, //G
            {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}, //H
            {{0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}}, //I
            {{1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //J
            {{1, 0, 0, 1, 0}, {1, 0, 1, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 1, 0, 0}, {1, 0, 0, 1, 0}}, //K
            {{1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}}, //L
            {{1, 1, 0, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}, //M
            {{1, 0, 0, 0, 1}, {1, 1, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 1, 1}, {1, 0, 0, 0, 1}}, //N
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //O
            {{1, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}}, //P
            {{0, 1, 1, 0, 0}, {1, 0, 0, 1, 0}, {1, 0, 1, 1, 0}, {1, 0, 0, 1, 0}, {0, 1, 1, 0, 1}}, //Q
            {{1, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 0}, {1, 0, 1, 0, 0}, {1, 0, 0, 1, 0}}, //R
            {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, //S
            {{1, 1, 1, 1, 1}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}}, //T
            {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //U
            {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}}, //V
            {{1, 0, 1, 0, 1}, {1, 0, 1, 0, 1}, {0, 1, 1, 1, 0}, {0, 1, 0, 1, 0}, {0, 1, 0, 1, 0}}, //W
            {{1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 0, 1}}, //X
            {{1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}}, //Y
            {{1, 1, 1, 1, 1}, {0, 0, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 1, 1, 1}}, //Z

            {{0, 0, 1, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}}, //1
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 0, 1, 0, 0}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}}, //2
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 0, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //3
            {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}}, //4
            {{0, 1, 1, 1, 0}, {0, 1, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 1, 0}, {0, 1, 1, 1, 0}}, //5
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //6
            {{1, 1, 1, 1, 1}, {0, 0, 0, 1, 0}, {0, 1, 1, 1, 0}, {0, 1, 0, 0, 0}, {1, 0, 0, 0, 0}}, //7
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //8
            {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {0, 1, 1, 1, 0}}, //9
            {{0, 1, 1, 1, 0}, {1, 1, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 1, 1}, {0, 1, 1, 1, 0}}, //0
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}  //SPACE
    };

    static String[] boxdrawingRoung = {"╭", "╮", "╰", "╯", "─", "│"};
    static String[] boxdrawingShelf = {"╔", "╗", "╚", "╝", "═", "║", "╠", "╣"};
    static String[] boxdrawingSelfmade = boxdrawingShelf;
    static String[] activeBoxdrawing = boxdrawingShelf;

    static Scanner scan = new Scanner(System.in);
    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890 ";

    public static void printBox(String text, int size) {

        int textLength = text.length();

        if (textLength > size) {
            size = textLength;
        }

        System.out.print("\n╔");
        for (int j = 0; j < size; j++) {
            System.out.print("═");
        }
        System.out.println("╗");
        System.out.print("║");

        for (int k = 0; k < (size - textLength) / 2; k++) {
            System.out.print(" ");
        }

        System.out.print(text);

        for (int k = 0; k < (size - textLength) / 2; k++) {
            System.out.print(" ");
        }

        if (textLength % 2 == 1 && size % 2 == 0) {
            System.out.print(" ");
        }

        System.out.println("║");
        System.out.print("╚");
        for (int k = 0; k < size; k++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }

    /***
     *
     * @param text String[] Of the Text, you wanna have in the List
     * @param numbered If true, then list with numbers, when false, then with bulletpoints
     */

    public static void printList(String[] text, boolean numbered) {
        for (int i = 0; i < text.length; i++) {
            if (numbered) {
                System.out.println((i + 1) + ". " + text[i]);
            } else {
                System.out.println("• " + text[i]);
            }
        }
    }

    public static void printShelf(String[] text, int size) {
        int textLength = text.length;

        for (String s : text) {
            if (s.length() > size) {
                size = s.length();
            }
        }


        System.out.print("\n╔");
        for (int j = 0; j < size; j++) {
            System.out.print("═");
        }
        System.out.println("╗");

        for (int n = 0; n < textLength; n++) {
            System.out.print("║");

            for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                System.out.print(" ");
            }

            System.out.print(text[n]);

            for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                System.out.print(" ");
            }

            if (text[n].length() % 2 == 1 && size % 2 == 0) {
                System.out.print(" ");
            }

            System.out.println("║");

            if (textLength > 1 && n < textLength - 1) {
                System.out.print("╠");
                for (int m = 0; m < size; m++) {
                    System.out.print("═");
                }
                System.out.println("╣");
            }
        }

        System.out.print("╚");
        for (int k = 0; k < size; k++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }

    public static int sumNumber(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    public static int printSumNumber(int... numbers) {
        int skip = 0;
        int sum = 0;
        System.out.print(numbers[0]);
        for (int number : numbers) {
            sum += number;
            if (skip > 0) {
                System.out.print(" + " + number);
            }
            skip++;
        }
        System.out.println(" = " + sum);
        return sum;
    }

    public static int checkIfSame(int checkNumber, int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == checkNumber) {
                return i;
            }
        }

        return -1;
    }

    public static void ASCIIArtMultipleLine(String text) {
        //97-122 (-97)

        text = text.toLowerCase();

        String[] split = text.split("");

        for (int i = 0; i < text.length(); i++) {
            int letter = alphabet.indexOf(split[i]);

            if (letter != -1) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (letters[letter][j][k] == 0) {
                            System.out.print(" ");
                        } else {
                            System.out.print("*");
                        }
                    }
                    System.out.println();
                }


            } else {
                System.out.println("\n\n\n\n");
            }
            System.out.println();
        }
    }

    public static void ASCIIArt(String text, String character) {
        text = text.toLowerCase();

        String[] split = text.split("");

        for (int j = 0; j < 5; j++) {

            for (int i = 0; i < text.length(); i++) {
                int letter = alphabet.indexOf(split[i]);
                if (letter != -1) {
                    for (int k = 0; k < 5; k++) {
                        if (letters[letter][j][k] == 0) {
                            System.out.print(" ");
                        } else {
                            System.out.print(character);
                        }
                    }
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static void printMenu(String mode, String position, boolean horizontalMiddleLines, boolean horizontalOuterLines, boolean verticalOuterLines, boolean corners, String... text) {

        changeActiveCharacters(mode);
        //╔ ╗ ╚ ╝ ═ ║ ╠ ╣
        int size = 0;

        int textLength = text.length;

        for (String s : text) {
            if (s.length() > size) {
                size = s.length();
            }
        }

        if (corners) {
            System.out.print(activeBoxdrawing[0]);
        } else {
            System.out.print(" ");
        }

        for (int j = 0; j < size; j++) {
            if (horizontalOuterLines) {
                System.out.print(activeBoxdrawing[4]);
            } else {
                System.out.print(" ");
            }
        }
        if (corners) {
            System.out.println(activeBoxdrawing[1]);
        } else {
            System.out.println();
        }

        for (int n = 0; n < textLength; n++) {
            if (verticalOuterLines) {
                System.out.print(activeBoxdrawing[5]);
            } else {
                System.out.print(" ");
            }

            if (position.equals("middle") || position.equals("right")) {
                for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                    System.out.print(" ");
                }
            }

            if (position.equals("right")) {
                for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                    System.out.print(" ");
                }
            }

            if (position.equals("right")) {
                if (text[n].length() % 2 == 1 && size % 2 == 0) {
                    System.out.print(" ");
                }
            }

            System.out.print(text[n]);

            if (position.equals("left") || position.equals("middle")) {
                for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                    System.out.print(" ");
                }
            }

            if (position.equals("left")) {
                for (int k = 0; k < (size - text[n].length()) / 2; k++) {
                    System.out.print(" ");
                }
            }

            if (position.equals("left") || position.equals("middle")) {
                if (text[n].length() % 2 == 1 && size % 2 == 0) {
                    System.out.print(" ");
                }
            }

            if (verticalOuterLines) {
                System.out.print(activeBoxdrawing[5]);
            }


            if (textLength > 1 && n < textLength - 1) {
                if (verticalOuterLines && !mode.equals("r")) {
                    System.out.print(activeBoxdrawing[6]);
                } else {
                    System.out.print(" ");
                }

                if (horizontalMiddleLines && !(verticalOuterLines)){
                    System.out.print("\n ");
                }

                for (int m = 0; m < size; m++) {
                    if (horizontalMiddleLines && !mode.equals("r")) {
                        System.out.print(activeBoxdrawing[4]);
                    } else {
                        System.out.print(" ");
                    }
                }
                if (verticalOuterLines && !mode.equals("r")) {
                    System.out.println(activeBoxdrawing[7]);
                } else {
                    System.out.println();
                }
            }
        }

        if (corners) {
            System.out.print("\n" + activeBoxdrawing[2]);
        } else {
            System.out.print("\n ");
        }

        for (int k = 0; k < size; k++) {
            if (horizontalOuterLines) {
                System.out.print(activeBoxdrawing[4]);
            } else {
                System.out.print(" ");
            }
        }
        if (corners) {
            System.out.println(activeBoxdrawing[3]);
        }
        System.out.println();
    }

    public static void initSelfmade() {

        //╔ ╗ ╚ ╝ ═ ║ ╠ ╣
        String input;

        System.out.println("Replacement of:");

        for (int i = 0; i < boxdrawingSelfmade.length; i++) {
            System.out.println(boxdrawingSelfmade[i]);
            input = scan.nextLine();
            if (!input.equals("skip")) {
                boxdrawingSelfmade[i] = input;
            } else {
                System.out.println("Skiped!");
            }
        }
    }

    public static void changeActiveCharacters(String mode) {
        if (mode.equals("s")) {
            try {
                activeBoxdrawing = boxdrawingSelfmade;
            } catch (Exception e) {
                activeBoxdrawing = boxdrawingShelf;
            }
        } else if (mode.equals("r")) {
            activeBoxdrawing = boxdrawingRoung;
        } else {
            activeBoxdrawing = boxdrawingShelf;
        }
    }

    private OutputUtils() {
    }
}
