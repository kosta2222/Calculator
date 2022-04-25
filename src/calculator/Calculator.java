/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Calculator {

    static boolean first_operand_is_roman = false;

    private static boolean is_op(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
                return true;
        }
        return false;
    }

    private static String convert_num_to_roman(int numArabian) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };
        final String s = roman[numArabian];
        return s;
    }

    private static int roman_to_number(String roman) {
        try {
            if (roman.equals("I")) {
                return 1;
            } else if (roman.equals("II")) {
                return 2;
            } else if (roman.equals("III")) {
                return 3;
            } else if (roman.equals("IV")) {
                return 4;
            } else if (roman.equals("V")) {
                return 5;
            } else if (roman.equals("VI")) {
                return 6;
            } else if (roman.equals("VII")) {
                return 7;
            } else if (roman.equals("VIII")) {
                return 8;
            } else if (roman.equals("IX")) {
                return 9;
            } else if (roman.equals("X")) {
                return 10;
            }
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Неверный формат данных");
        }
        return -1;
    }

    private static boolean is_roman(String elem) {
        switch (elem) {
            case "O":
            case "I":
            case "II":
            case "III":
            case "IV":
            case "V":
            case "VI":
            case "VII":
            case "VIII":
            case "IX":
            case "X":
                return true;
        }
        return false;
    }

    private static boolean is_num(String elem) throws Exception {
        switch (elem) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
                return true;
        }
        return false;

    }

   
    private static String calc(String input) throws Exception {
        StringBuilder stack = new StringBuilder(""), sb_out = new StringBuilder("");
        char c_in;

        for (int i = 0; i < input.length(); i++) {
            c_in = input.charAt(i);
            if (is_op(c_in)) {
                sb_out.append(" ");
                stack.append(c_in);
            } else {
                sb_out.append(c_in);
            }
        }
        while (stack.length() > 0) {
            sb_out.append(" ").append(stack.substring(stack.length() - 1));
            stack.setLength(stack.length() - 1);
        }

        int res = vm(sb_out.toString());
        if (first_operand_is_roman) {
            return convert_num_to_roman(res);
        }
        return Integer.toString(res);
    }

    
    private static int vm(String s_in) throws Exception {
        int dA = 0, dB = 0;
        String s_tmp;
        int count_elems = 0;

        Deque<Integer> stack = new ArrayDeque<Integer>();
        StringTokenizer st = new StringTokenizer(s_in);
        while (st.hasMoreTokens()) {
            count_elems++;
            s_tmp = st.nextToken().trim();
//            System.out.println("s_tmp " + s_tmp);
            if (is_op(s_tmp.charAt(0))) {
                dB = stack.pop();
                dA = stack.pop();
                switch (s_tmp.charAt(0)) {
                    case '+':
                        dA += dB;
                        break;
                    case '-':
                        dA -= dB;
                        break;
                    case '/':
                        dA /= dB;
                        break;
                    case '*':
                        dA *= dB;
                        break;
                }
                stack.push(dA);
            } else {
                if (is_roman(s_tmp) && count_elems == 1) {
                    first_operand_is_roman = true;
                    dA = roman_to_number(s_tmp);
                    stack.push(dA);
                } else if (is_roman(s_tmp) && count_elems == 2 && first_operand_is_roman) {
                    dA = roman_to_number(s_tmp);
                    stack.push(dA);
                } else if (is_num(s_tmp) && count_elems == 1 && !first_operand_is_roman ) {
                    dA = Integer.parseInt(s_tmp);
                    stack.push(dA);
                } else if (is_num(s_tmp) && count_elems == 2 && !first_operand_is_roman) {
                    dA = Integer.parseInt(s_tmp);
                    stack.push(dA);
                } else {
                    throw new Exception("Неверный формат ввода двух чисел!");
                }

            }

//            if (count_elems > 3) {
//                throw new Exception("Количество операндов и оператора должно равнятся трем");
//            }

        }

        return stack.pop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        String str = in.nextLine(); 
        System.out.println(Calculator.calc(str));
    }

}
