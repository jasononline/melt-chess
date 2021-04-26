package chess.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void squaresFromFENString() {
        String fen = "kqrbnp2/8/7p/4p2N/3p3B/2P1P2R/7Q/7K";
        Board b = new Board(fen);
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
    public void makeMoveForbidCastling() {
        Board board = new Board();
        board = board.makeMove(new Move(0, 1));
        assertFalse(board.isCastlingA8Possible());
        board = board.makeMove(new Move(7, 1));
        assertFalse(board.isCastlingH8Possible());
        board = board.makeMove(new Move(56, 1));
        assertFalse(board.isCastlingA1Possible());
        board = board.makeMove(new Move(63, 1));
        assertFalse(board.isCastlingH1Possible());
        board = new Board();
        board = board.makeMove(new Move(4, 1));
        assertFalse(board.isCastlingA8Possible());
        assertFalse(board.isCastlingH8Possible());
        board = board.makeMove(new Move(60, 1));
        assertFalse(board.isCastlingA1Possible());
        assertFalse(board.isCastlingH1Possible());
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
        board = board.makeMove(m1);
        board = board.makeMove(m2);
        assertEquals(1, board.getCapturedPieces().size());
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
        for (Move m : promoMoves)
            board = board.makeMove(m);

        assertEquals(Piece.Rook, Piece.getType(board.getPieceAt(0)));
        assertEquals(Piece.Queen, Piece.getType(board.getPieceAt(1)));
        assertEquals(Piece.Bishop, Piece.getType(board.getPieceAt(2)));
        assertEquals(Piece.Knight, Piece.getType(board.getPieceAt(3)));
    }

    @Test
    public void positionToIndex() {
    }

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
        String actual = b.toString();

        assertEquals(goal, b.toString());
    }

    @Test
    public void getPiecePositionsFor() {
        String fen = "kqr5/8/8/8/8/8/8/5RQK";
        Board b = new Board(fen);

        ArrayList<Integer> positions = b.getPiecePositionsFor(Piece.Black);
        for (int i=0;i<3;i++) assertTrue(positions.contains(i));

        positions = b.getPiecePositionsFor(Piece.White);
        for (int i=61;i<64;i++) assertTrue(positions.contains(i));
    }
}