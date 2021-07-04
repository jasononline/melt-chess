package chess.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Contains methods to test the methods of the Cli class
 */
public class CliTests {

	/**
	 * Method to test the expected behavior of the Method Cli.testUserInputSyntax()
	 */
	@Test
	public void testValidInput() {
		assertTrue(Cli.testUserInputSyntax("c2-c4"));
		assertTrue(Cli.testUserInputSyntax("c2-c4r"));
		assertTrue(Cli.testUserInputSyntax("beaten"));
		assertTrue(Cli.testUserInputSyntax("quit"));
		assertFalse(Cli.testUserInputSyntax("exit"));
	}
}
