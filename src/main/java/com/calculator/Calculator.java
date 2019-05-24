package com.calculator;

import java.io.*;
import java.util.Stack;

import static com.calculator.InfixToPostfix.isOperator;

/**
 * @author Parisana
 */
public class Calculator {

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter a string that you want to calculate the value of: \n");
            String infixExp = bufferedReader.readLine();
            while (!infixExp.equals("q")) {
                final InfixToPostfix infixToPostfix = new InfixToPostfix();
                final String postfixExpWithUnderscore = infixToPostfix.convertToPostfix(infixExp);
                System.out.println(postfixExpWithUnderscore);
                System.out.println("Result: " + calculate(postfixExpWithUnderscore));

                infixExp = bufferedReader.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

//    _25_34*_23_1+-
//    (300+23)*(43-21)/(84+7)
//     (4+8)*(6-5)/((3-2)*(2+2))
    private static double calculate(String postfixExpWithUnderscore){
        Stack<Double> operandStack = new Stack<>();

        boolean isUnderscore = false;
        StringBuilder numberString = new StringBuilder();

        for (int i=0; i<postfixExpWithUnderscore.length(); i++){
            if (postfixExpWithUnderscore.charAt(i)=='_') {
                isUnderscore = true;
                if (i> 0 && numberString.length()>0) operandStack.push(Double.parseDouble(numberString.toString()));
                numberString.delete(0, numberString.length());
                continue;
            }
            final boolean isOperator = isOperator(postfixExpWithUnderscore.charAt(i));
            if (isUnderscore){
                if (!isOperator) {
                    numberString.append(postfixExpWithUnderscore.charAt(i));
                    continue;
                } else {
                    operandStack.push(Double.parseDouble(numberString.toString()));
                    numberString.delete(0, numberString.length());
                    isUnderscore = false;
                }
            } if (isOperator) {
                final double res = evaluate(operandStack.pop(), operandStack.pop(), postfixExpWithUnderscore.charAt(i));
                operandStack.push(res);
            }
        }

        return operandStack.pop();
    }

    private static double evaluate(Double second, Double first, char operator) {
        switch (operator){
            case '+':
                return first+second;
            case '-':
                return first-second;
            case '*':
                return first*second;
            case '/':
                return first/second;
            case '^':
                return Math.pow(first, second);
            default:
                throw new IllegalArgumentException("Operator not defined for " + operator);

        }
    }

}
