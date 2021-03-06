package chess.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Run some tests for scoring a position
 */
class ScoreGeneratorTest {

	String startposition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

	/**
	 * Run some tests for scoring a position
	 */
	@Test
	public void scoreGenerator() {
		ScoreGenerator sg = new ScoreGenerator(new EngineBoard(startposition));

		// expect score to be zero in symmetric starting position:
		System.out.println(sg.getScore());
		assertEquals(0, sg.getScore());

		// test on some starting position breaking the symmetry
		sg = new ScoreGenerator(new EngineBoard("rnbqk2r/ppp1bppp/3p1n2/4p3/8/5NP1/PPPPPPBP/RNBQ1RK1"));
		System.out.println(sg.getScore());
		assertEquals(154, sg.getScore());
	}
}