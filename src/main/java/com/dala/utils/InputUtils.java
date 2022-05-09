package com.dala.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    static Scanner scan = new Scanner(System.in);

    /**
     * Reads an Int between the specified values, min and max are included
     *
     * @param min The minimum Number
     * @param max The maximum Number
     * @return The readed Int
     */
    public static int readIntRange(int min, int max) {
        int integer = 0;
        int repeat = 1;

        do {
            try {
                repeat = 0;
                integer = scan.nextInt();
            } catch (InputMismatchException e) {
                integer = 0;
                repeat = 1;
                scan.nextLine();
            }
        } while (integer < min || integer > max || repeat == 1);

        return integer;
    }

    /**
     * Reads an Int
     *
     * @return The readed Int
     */
    public static int readInt() {
        int integer = 0;
        int repeat = 1;

        do {
            try {
                repeat = 0;
                integer = scan.nextInt();
            } catch (InputMismatchException e) {
                integer = 0;
                repeat = 1;
                scan.nextLine();
            }
        } while (repeat == 1);

        return integer;
    }

    /**
     * Reads a double between the specified values, min and max are included
     *
     * @param min The minimum Number
     * @param max The maximum Number
     * @return The readed double
     */
    public static double readDoubleRange(double min, double max) {
        double dbl = 0;
        int repeat = 1;

        do {
            try {
                repeat = 0;
                dbl = scan.nextDouble();
            } catch (InputMismatchException e) {
                dbl = 0;
                repeat = 1;
                scan.nextLine();
            }
        } while (dbl < min || dbl > max || repeat == 1);

        return dbl;
    }

    /**
     * Reads a double
     *
     * @return The readed double
     */
    public static double readDouble() {
        double dbl = 0;
        int repeat = 1;

        do {
            try {
                repeat = 0;
                dbl = scan.nextDouble();
            } catch (InputMismatchException e) {
                dbl = 0;
                repeat = 1;
                scan.nextLine();
            }
        } while (repeat == 1);

        return dbl;
    }

    /**
     * Reads a String
     *
     * @return The readed String
     */
    public static String readString() {
        String str = "";
        int repeat = 1;

        do {
            try {
                repeat = 0;
                str = scan.nextLine();
            } catch (InputMismatchException e) {
                repeat = 1;
                scan.nextLine();
            }
        } while (repeat == 1);

        return str;
    }

    /**
     * Reads a String that needs to match the provided Regex
     *
     * @param regex The regex the Input needs to match
     * @return The readed String
     */
    public static String readStringRegex(String regex) {
        String str = "";
        int repeat = 1;

        do {
            try {
                repeat = 0;
                str = scan.nextLine();

                if (!str.matches(regex)) {
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                repeat = 1;
                scan.nextLine();
            }
        } while (repeat == 1);

        return str;
    }

    /**
     * Parses a String to an Int
     *
     * @param str The String to parse
     * @return The parsed Int
     */
    public static int parseInteger(String str) {
        int integer = 0;

        try {
            integer = Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }

        return integer;
    }

    /**
     * Checks if the provided char is a letter
     *
     * @param letter The char to check
     * @return True if it is a letter, else false
     */
    public static boolean checkLetter(char letter) {
        return letter > 64 && letter < 91 || letter > 96 && letter < 123;
    }

    /**
     * Checks if the inputed char is a letter
     *
     * @return True if it is a letter, else false
     */
    public static Character checkLetterInput() {
        char letter = '0';
        int repeat = 1;

        do {
            try {
                repeat = 0;
                letter = scan.next().charAt(0);
            } catch (NumberFormatException e) {
                repeat = 1;
            }
        } while (!(letter > 64 && letter < 91 || letter > 96 && letter < 123));

        return letter;
    }
}
