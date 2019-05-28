package com.calculator;

import java.util.Stack;

/**
 * @author Parisana
 */

// A+ (B*C-(D/E^F)*G)*H
public class InfixToPostfix {

    private final Stack<Character> operatorStack = new Stack<>();

    /**
     * checks if the first character has higher precedence compared to the second
     *
     * @return true if the fist character has higher precedence than the second, otherwise false
     * */
    public static boolean hasHigherPrecedence(Character first, Character second){

        if (first == null || second == null){
            throw new UnsupportedOperationException("The characters cannot be null!");
        }

        return priorityLevelOfOperator(first)-priorityLevelOfOperator(second)>=0;
    }

    private static byte priorityLevelOfOperator(Character c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private boolean isLeftParenthesis(Character c){
        return c=='(' ||
                c=='{' ||
                c=='[';
    }
    private boolean isRightParenthesis(Character c){
        return c==')' ||
                c=='}' ||
                c==']';
    }

    public String convertToPostfix(String infixExpression){

        final String enhanced = enhance(infixExpression);

        final StringBuilder resultStringBuilder = new StringBuilder();
        for (int i=0; i<enhanced.length(); i++){
            final char c = enhanced.charAt(i);
            if (c == '_')
                resultStringBuilder.append(c);
            if (Character.isLetterOrDigit(c))   // if the character is an operand add it to the output
                resultStringBuilder.append(c);
//            if the token is an operator or a
            else if (isLeftParenthesis(c)) {
                if (i>0 && (!isOperator(enhanced.charAt(i-1)) && !isLeftParenthesis(enhanced.charAt(i-1))))
                    throw new IllegalArgumentException("There should be an operator before a left parentheses, at position: " + i + " of string: " + enhanced);
                operatorStack.push(c);
            }
            else if (isOperator(c)) {
                if (i==0 || i==enhanced.length()-1)
                    throw new IllegalArgumentException("The operator should not be at the start or end of the expression, at position: " + i);
                if (isOperator(enhanced.charAt(i+1)))
                    throw new IllegalArgumentException("Two operators should not occur consecutively, at position: " + i);
                while (!operatorStack.isEmpty() && !isLeftParenthesis(operatorStack.peek()) && hasHigherPrecedence(operatorStack.peek(), c))
                    resultStringBuilder.append(operatorStack.pop());
                operatorStack.push(c);
            }else if (isRightParenthesis(c)){
                if (i>0 && isOperator(enhanced.charAt(i-1)))
                    throw new IllegalArgumentException("There should not be an operator, at position: " + (i-1));
                while (!operatorStack.isEmpty() && !isLeftParenthesis(operatorStack.peek()))
                    resultStringBuilder.append(operatorStack.pop());
                final Character poppedChar = operatorStack.pop();
                if (!isLeftParenthesis(poppedChar)){
                    throw new IllegalArgumentException("The expression might be malformed!");
                }
            }
        }

        while (!operatorStack.isEmpty()){
            final Character pop = operatorStack.pop();
            if (isLeftParenthesis(pop))
                throw new IllegalArgumentException("The expression might contain extra left parentheses");
            resultStringBuilder.append(pop);
        }
        return resultStringBuilder.toString();

    }

    public static boolean isOperator(char c) {
        return c=='-' || c== '+' || c=='*' || c=='/' || c=='^';
    }

    /**
     * puts an underscore '_' at the beginning of each word that is not a operator, so as to make it easy to identify a number
     * e.g 25*34-(23+1) becomes _25*_34-(_23+_1)
     * */
    public static String enhance(String input){
        return input.replaceAll("(\\w+)", "_$1");
    }

}
