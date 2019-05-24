package com.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Parisana
 */
public class InfixToPostfixTest {

    private final InfixToPostfix infixToPostfix = new InfixToPostfix();

    @Test
    public void hasHigherPrecedence() {

        assertTrue(infixToPostfix.hasHigherPrecedence('*', '-'));

    }

    @Test
    public void enhance() {

        assertEquals("_25*_34-(_23+_1)", infixToPostfix.enhance("25*34-(23+1)"));

    }

    @Test
    public void convertToPostfix() {

        assertEquals("_A_B_C*_D_E_F^/_G*-_H*+", infixToPostfix.convertToPostfix("A+(B*C-(D/E^F)*G)*H"));

    }
}