package uk.ac.rhul.cs2800;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStandardCalc {
	StandardCalc standardCalc;

	@BeforeEach
	void setup() {
		standardCalc = new StandardCalc();
	}
	
	@Test
	void testIsNumeric() {
		assertEquals(standardCalc.isNumeric("5"), true, "isNumeric() should return true with input 5.");
		assertEquals(standardCalc.isNumeric("?"), false, "isNumeric() should return false with input ?.");

	}

	@Test
	void testIsOperator() {
		assertEquals(standardCalc.isOperator("*"), true, "isOperator() should return true with input *.");
		assertEquals(standardCalc.isOperator("?"), false, "isOperator() should return false with input ?.");

	}
	
	@Test
	void testIsBracket() {
		assertEquals(standardCalc.isLeftBracket("("), true, "isLeftbracket() should return true with input (.");
		assertEquals(standardCalc.isLeftBracket("("), true, "isRightbracket() should return true with input ).");
	}
	
	@Test
	void testIsNotBracket() {
		assertEquals(standardCalc.isLeftBracket("?"), false, "isLeftbracket() should return true with input ?.");
		assertEquals(standardCalc.isLeftBracket("?"), false, "isRightbracket() should return true with input ?.");
	}
	
	@Test
	void testPrecedence() {
		assertEquals(standardCalc.precedence("+"), 0, "precedence() should return 0 with input +.");
		assertEquals(standardCalc.precedence("-"), 0, "precedence() should return 0 with input -.");
		assertEquals(standardCalc.precedence("*"), 1, "precedence() should return 1 with input *.");
		assertEquals(standardCalc.precedence("/"), 1, "precedence() should return 1 with input /.");
		assertThrows(IllegalArgumentException.class, () -> standardCalc.precedence("?"), 
				"precedence() method of StandardCalc should throw an IllegalArgumentException "
				+ "if the input is not an operator.");
	}
	
	@Test
	void testWhichSymbol() {
		assertEquals(standardCalc.whichSymbol("+"), Symbol.PLUS, "whichSymbol() should return an enum "
				+ "Symbol.PLUS with the input '+'");
		assertEquals(standardCalc.whichSymbol("-"), Symbol.MINUS, "whichSymbol() should return an enum "
				+ "Symbol.MINUS with the input '-'");
		assertEquals(standardCalc.whichSymbol("*"), Symbol.TIMES, "whichSymbol() should return an enum "
				+ "Symbol.TIMES with the input '*'");
		assertEquals(standardCalc.whichSymbol("/"), Symbol.DIVIDE, "whichSymbol() should return an enum "
				+ "Symbol.DIVIDE with the input '/'");
	}
	
	@Test
	void testLeftBracketcorrect() throws BadTypeException {
		standardCalc.evaluate("(");
		assertEquals(standardCalc.opStack.size(), 1, "After reading a left bracket, "
				+ "evaluate should push the left bracket to the opStack increasing the size by one");
	}
	
	@Test
	void testOperatorCorrect() throws BadTypeException {
		standardCalc.evaluate("+");
		assertEquals(standardCalc.opStack.size(), 1, "After reading an operator, "
				+ "evaluate should push the operator to the opStack increasing the size by one");
	}
	
	@Test
	void testRightBracketCorrect() throws BadTypeException {
		assertEquals(standardCalc.evaluate("( + - )"), "+ - ", "After reading left bracket, "
				+ "then two operators and then right bracket, evaluate should push the left bracket"
				+ "and two operators onto opStack, but when it reads a right bracket it should pop"
				+ "everything off until it reaches a left bracket and add it to postfix string.");
	}
	
	
}