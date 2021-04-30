package chess.cli;

import org.junit.jupiter.api.Test;
import chess.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class CliTests {

	/**
	 * Method to test the expected behavior of the Method Cli.testUserInputSyntax()
	 */
	@Test
	public void testValidInput() {
		assertTrue(Cli.testUserInputSyntax("c2-c4"));
		assertTrue(Cli.testUserInputSyntax("c2-h4"));
		assertTrue(Cli.testUserInputSyntax("c2-c4Q"));
		assertTrue(Cli.testUserInputSyntax("c2-c4R"));
		assertTrue(Cli.testUserInputSyntax("beaten"));
		assertTrue(Cli.testUserInputSyntax("help"));
		assertTrue(Cli.testUserInputSyntax("quit"));
		assertFalse(Cli.testUserInputSyntax("c2-c9"));
		assertFalse(Cli.testUserInputSyntax("c2-i1"));
		assertFalse(Cli.testUserInputSyntax("c2-c4K"));
		assertFalse(Cli.testUserInputSyntax("exit"));
		assertFalse(Cli.testUserInputSyntax("blabla"));
	}

	/**
	 * Method to test the expected behavior of the Method Cli.parseUserMoveInput()
	 */
	@Test
	public void parseMoveFromString() {
		assertEquals(Cli.parseUserMoveInput("c2-c4"), new Move(50, 34, 0));
		assertEquals(Cli.parseUserMoveInput("c2-h4"), new Move(50, 39, 0));
		assertEquals(Cli.parseUserMoveInput("a8-h1Q"), new Move(0, 63, 3));
		assertEquals(Cli.parseUserMoveInput("h1-a8R"), new Move(63, 0, 5));
	}

}
