package chess.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains methods to test the methods of the Board class
 */
public class BoardTest {

    /**
     * Method to test the expected behavior of the Method Model.squaresFromFENString()
     */
    @Test
    public void squaresFromFENString() {
        String fen = "kqrbnp2/8/7p/4p2N/3p3B/2P1P2R/7Q/7K";
        Board board = new Board(fen);
        int[] expectedPieces = new int[]{
                Piece.King+Piece.Black,
                Piece.Queen+Piece.Black,
                Piece.Rook+Piece.Black,
                Piece.Bishop+Piece.Black,
                Piece.Knight+Piece.Black,
                Piece.Pawn+Piece.Black,
        };
        for (int i=0;i<6;i++) {
            assertEquals(expectedPieces[i], board.getPieceAt(i));
        }
    }

    /**
     * Test makeMove making a move and capturing a piece
     */
    @Test
    public void makeMoveAndCapture() {
        String fen = "Rq6/8/8/8/8/8/8/8";
        Board board = new Board(fen);
        int piece1 = board.getPieceAt(0);
        int piece2 = board.getPieceAt(1);
        Move move = new Move(0, 1);
        Board boardAfterMove = board.makeMove(move);
        assertEquals(piece1, boardAfterMove.getPieceAt(1));
        assertTrue(boardAfterMove.getCapturedPieces().contains(piece2));
    }


    /**
     * Test the different moves that forbid castling
     */
    @Test
    public void makeMoveForbidCastlingOnRookMovement() {
        Board board = new Board();
        board = board.makeMove(new Move(0, 1));
        assertFalse(board.isCastlingA8Possible());
        board = board.makeMove(new Move(7, 1));
        assertFalse(board.isCastlingH8Possible());
        board = board.makeMove(new Move(56, 1));
        assertFalse(board.isCastlingA1Possible());
        board = board.makeMove(new Move(63, 1));
        assertFalse(board.isCastlingH1Possible());
    }

    /**
     *  Test the different moves that forbid castling
     */
    @Test
    public void makeMoveForbidCastlingOnKingMovement() {
        Board board = new Board();
        board = board.makeMove(new Move(4, 1));
        assertFalse(board.isCastlingA8Possible());
        assertFalse(board.isCastlingH8Possible());
        board = board.makeMove(new Move(60, 1));
        assertFalse(board.isCastlingA1Possible());
        assertFalse(board.isCastlingH1Possible());
    }

    /**
     * Test the castling moves
     */
    @Test
    public void makeMoveCastling() {
        String fen = "8/2p3p1/8/8/8/8/8/R3K2R";
        Board board = new Board(fen);

        Move castlingLeft = new Move(60, 58, Move.Castling);
        Move castlingRight = new Move(60, 62, Move.Castling);

        Board boardAfterCastlingLeft = board.makeMove(castlingLeft);
        Board boardAfterCastlingRight = board.makeMove(castlingRight);

        assertEquals(Piece.King, Piece.getType(boardAfterCastlingLeft.getPieceAt(58)));
        assertEquals(Piece.Rook, Piece.getType(boardAfterCastlingLeft.getPieceAt(59)));

        assertEquals(Piece.King, Piece.getType(boardAfterCastlingRight.getPieceAt(62)));
        assertEquals(Piece.Rook, Piece.getType(boardAfterCastlingRight.getPieceAt(61)));
    }


    /**
     * Test en passant capture move
     */
    @Test
    public void makeMoveEnPassantCapture() {
        String fen = "8/8/8/8/p7/8/1P6/8";
        Board board = new Board(fen);
        Move m1 = new Move(49, 33, Move.PawnTwoForward);
        Move m2 = new Move(32, 41, Move.EnPassantCapture);
        System.out.println("Test En Passant Capture:");
        System.out.println(board);
        board = board.makeMove(m1);
        System.out.println(board);
        board = board.makeMove(m2);
        System.out.println(board);
        assertEquals(1, board.getCapturedPieces().size());
        assertEquals(Piece.Pawn, Piece.getType(board.getPieceAt(Coordinate.toIndex("b3"))));
        assertEquals(Piece.None, Piece.getType(board.getPieceAt(Coordinate.toIndex("b4"))));
    }


    /**
     * Test pawn promotion
     */
    @Test
    public void makeMovePromotion() {
        String fen = "8/PPPP4/8/8/8/8/8/8";
        Board board = new Board(fen);
        Move[] promoMoves = new Move[]{
                new Move(8, 0, Move.PromoteToRook),
                new Move(9, 1, Move.PromoteToQueen),
                new Move(10, 2, Move.PromoteToBishop),
                new Move(11, 3, Move.PromoteToKnight)
        };
        for (Move m : promoMoves) {
            board = board.makeMove(m);
        }

        assertEquals(Piece.Rook, Piece.getType(board.getPieceAt(0)));
        assertEquals(Piece.Queen, Piece.getType(board.getPieceAt(1)));
        assertEquals(Piece.Bishop, Piece.getType(board.getPieceAt(2)));
        assertEquals(Piece.Knight, Piece.getType(board.getPieceAt(3)));
    }


    /**
     * Method to test the expected behavior of the Method Model.toString()
     */
    @Test
    public void testToString() {
        String fen = "rn1qkb1r/ppppp1pp/4b3/1N1P2p1/5P2/2n1Q3/PPPP2PP/R1B1KBNR";
        Board b = new Board(fen);
        String goal = "8 ♜ ♞   ♛ ♚ ♝   ♜\n" +
                "7 ♟ ♟ ♟ ♟ ♟   ♟ ♟\n" +
                "6         ♝      \n" +
                "5   ♘   ♙     ♟  \n" +
                "4           ♙    \n" +
                "3     ♞   ♕      \n" +
                "2 ♙ ♙ ♙ ♙     ♙ ♙\n" +
                "1 ♖   ♗   ♔ ♗ ♘ ♖\n" +
                "  a b c d e f g h\n";

        assertEquals(goal, b.toString());
    }

    /**
     * Method to test the expected behavior of the Method Model.getPiecePositionsFor()
     */
    @Test
    public void getPiecePositionsFor() {
        String fen = "kqr5/8/8/8/8/8/8/5RQK";
        Board b = new Board(fen);

        List<Integer> positions = b.getPiecePositionsFor(Piece.Black);
        for (int i=0;i<3;i++) {assertTrue(positions.contains(i));}

        positions = b.getPiecePositionsFor(Piece.White);
        for (int i=61;i<64;i++) {assertTrue(positions.contains(i));}
    }
}