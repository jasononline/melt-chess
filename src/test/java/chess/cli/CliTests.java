package chess.cli;

import chess.model.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CliTests {

	/**
	 * Method to test the expected behavior of the Method Cli.testUserInputSyntax()
	 */
	@Test
	public void testValidInput() {
		assertTrue(Cli.testUserInputSyntax("c2-c4"));
		assertTrue(Cli.testUserInputSyntax("c2-h4"));
		assertTrue(Cli.testUserInputSyntax("c2-c4q"));
		assertTrue(Cli.testUserInputSyntax("c2-c4r"));
		assertTrue(Cli.testUserInputSyntax("beaten"));
		assertTrue(Cli.testUserInputSyntax("help"));
		assertTrue(Cli.testUserInputSyntax("quit"));
		assertFalse(Cli.testUserInputSyntax("c2-c9"));
		assertFalse(Cli.testUserInputSyntax("c2-i1"));
		assertFalse(Cli.testUserInputSyntax("c2-c4K"));
		assertFalse(Cli.testUserInputSyntax("exit"));
		assertFalse(Cli.testUserInputSyntax("blabla"));
	}
}
