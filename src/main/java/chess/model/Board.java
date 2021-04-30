package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class contains all information about the current state of the game.
 * Instances of Board will be used as game history and as parameter for the engine.
 */
public class Board {

    // representing the chess board where squares[0] is "a8" and squares[63] is "h1"
    private int[] squares;
    private List<Integer> capturedPieces;
    // if a pawn moved two spaces when this board was created, it is possible to capture the pawn at this position:
    private int enPassantSquare;
    private int turnColor;
    // is castling with one of the four rooks still possible?
    private boolean CastlingA1Possible = true;
    private boolean CastlingA8Possible = true;
    private boolean CastlingH1Possible = true;
    private boolean CastlingH8Possible = true;
    // cache for possible moves in this position:
    private List<Move> possibleMoves = null;

    /** @return possible moves in this position **/
    public List<Move> getPossibleMoves(){return possibleMoves;}
    /** @param moves set possible moves in this position **/
    public void setPossibleMoves(List<Move> moves){ possibleMoves = moves;}
    /** @return true if castling with A1 rook is possible */
    public boolean isCastlingA1Possible(){return CastlingA1Possible;}
    /** @return true if castling with A8 rook is possible */
    public boolean isCastlingA8Possible(){return CastlingA8Possible;}
    /** @return true if castling with H1 rook is possible */
    public boolean isCastlingH1Possible(){return CastlingH1Possible;}
    /** @return true if castling with H8 rook is possible */
    public boolean isCastlingH8Possible(){return CastlingH8Possible;}
    /** forbid castling **/
    public void forbidCastlingA1(){CastlingA1Possible =false;}
    /** forbid castling **/
    public void forbidCastlingA8(){CastlingA8Possible =false;}
    /** forbid castling **/
    public void forbidCastlingH1(){CastlingH1Possible =false;}
    /** forbid castling **/
    public void forbidCastlingH8(){CastlingH8Possible =false;}


    /**
     * Construct empty Board instance
     */
    public Board() {
        squares = new int[64];
        turnColor = Piece.White;
        enPassantSquare = -1;
        capturedPieces = new ArrayList<>();
    }


    /**
     * Construct Board instance from fen string
     * @param fenString Integer representation starting square
     */
    public Board(String fenString) {
        this();
        squares = Board.squaresFromFENString(fenString);
    }


    /**
     * Used to init the squares field
     * @param fenString the board position as FEN-String
     * @return returns the squares representation for fenString
     */
    private static int[] squaresFromFENString(String fenString) {

        int[] squares = new int[64];
        int position = 0;
        String pieceChars = "kKpPnNbBrRqQ";
        int[] pieceRepresentations = new int[]{
                Piece.King + Piece.Black,
                Piece.King + Piece.White,
                Piece.Pawn + Piece.Black,
                Piece.Pawn + Piece.White,
                Piece.Knight + Piece.Black,
                Piece.Knight + Piece.White,
                Piece.Bishop + Piece.Black,
                Piece.Bishop + Piece.White,
                Piece.Rook + Piece.Black,
                Piece.Rook + Piece.White,
                Piece.Queen + Piece.Black,
                Piece.Queen + Piece.White,
        };

        for (Character s: fenString.toCharArray()) {
            if (s == ' ')
                break;
            // move s squares forward
            if (Character.isDigit(s)) {
                position += Character.getNumericValue(s);
                continue;
            }
            // increase position until next line
            if (s == '/') {
                if (position % 8 != 0) {
                    position += 8 - position % 8;
                }
                continue;
            }
            // add piece to the position
            if (0 <= pieceChars.indexOf(s)) {
                squares[position] = pieceRepresentations[pieceChars.indexOf(s)];
                position += 1;
            }
        }
        return squares;
    }


    /**
     * Construct Board instance from fen string
     * @param fenString board position in Forsyth–Edwards Notation
     * @param turnColor which color is next turn
     */
    public Board(String fenString, int turnColor) {
        this(fenString);
        this.turnColor = turnColor;
    }


    /**
     * Clone Board instance
     * @param board another Board instance
     */
    public Board(Board board) {
        this();
        squares = board.squares.clone();
        capturedPieces = new ArrayList<>();
        capturedPieces.addAll(board.getCapturedPieces());
        // En passant capture only within next move possible
        enPassantSquare = board.getEnPassantSquare();
        turnColor = board.getTurnColor();
        // is castling with one of the four rooks still possible?
        CastlingA1Possible = board.isCastlingA1Possible();
        CastlingA8Possible = board.isCastlingA8Possible();
        CastlingH1Possible = board.isCastlingH1Possible();
        CastlingH8Possible = board.isCastlingH8Possible();
        // assume 
        possibleMoves = board.getPossibleMoves();
    }


    /**
     * Copy board and apply move
     *
     * @param board previous position
     * @param move the move to generate this position
     */
    public Board(Board board, Move move) {
        this(board);
        // capture piece if needed
        if (Piece.getType(getPieceAt(move.getTargetSquare())) != Piece.None) {
            capturedPieces.add(getPieceAt(move.getTargetSquare()));
        }

        enPassantCapture(move);
        forbidCastling(move);
        movePiece(move);
        if (0 < move.getFlag())
            promotePawn(move);

        // reset fields for the next move
        if (move.getFlag() == Move.EnPassantCapture)
            setEnPassantSquare(-1);
        setPossibleMoves(null);

        int nextTurnColor = (getTurnColor() == Piece.Black) ? Piece.White : Piece.Black;
        setTurnColor(nextTurnColor);
    }


    private void forbidCastling(Move move) {
        switch (move.getStartSquare()) {
            // rook moved
            case 0: forbidCastlingA8(); break;
            case 7: forbidCastlingH8(); break;
            case 56: forbidCastlingA1(); break;
            case 63: forbidCastlingH1(); break;
            // king moved
            case 4: {
                forbidCastlingA8();
                forbidCastlingH8();
                break;
            }
            case 60: {
                forbidCastlingA1();
                forbidCastlingH1();
                break;
            }
        }
    }


    private void movePiece(Move move) {
        squares[move.getTargetSquare()] = getPieceAt(move.getStartSquare());
        squares[move.getStartSquare()] = Piece.None;
        // move the rook when castling
        if (move.getFlag() == Move.Castling) {
            int rookCurrentPosition, rookNewPosition;
            if (move.getTargetSquare() < move.getStartSquare()) {
                rookCurrentPosition = move.getTargetSquare() - 2;
                rookNewPosition = move.getTargetSquare() + 1;
            } else {
                rookCurrentPosition = move.getTargetSquare() + 1;
                rookNewPosition = move.getTargetSquare() - 1;
            }
            squares[rookNewPosition] = getPieceAt(rookCurrentPosition);
            squares[rookCurrentPosition] = Piece.None;
        }

    }


    /**
     * Implements promoting the pawn
     *
     * @param move the current move
     * **/
    private void promotePawn(Move move) {
        switch (move.getFlag()) {
            case Move.PromoteToBishop:
                squares[move.getTargetSquare()] = Piece.Bishop + turnColor;
                break;
            case Move.PromoteToKnight:
                squares[move.getTargetSquare()] = Piece.Knight + turnColor;
                break;
            case Move.PromoteToQueen:
                squares[move.getTargetSquare()] = Piece.Queen + turnColor;
                break;
            case Move.PromoteToRook:
                squares[move.getTargetSquare()] = Piece.Rook + turnColor;
                break;
        }
    }


    /**
     * Implements en passant capture rule
     *
     * @param move the current move
     * **/
    private void enPassantCapture(Move move) {
        if (0 < enPassantSquare && move.getFlag() == Move.EnPassantCapture) {
            int stepForwardDirection = (turnColor == Piece.White) ? MoveGenerator.UP : MoveGenerator.DOWN;
            enPassantSquare += stepForwardDirection;
            capturedPieces.add(getPieceAt(enPassantSquare));
            squares[enPassantSquare] = Piece.None;
            enPassantSquare = -1;
        }
        if (move.getFlag() == Move.PawnTwoForward) {
            int stepBackDirection = (turnColor == Piece.White) ? MoveGenerator.UP : MoveGenerator.DOWN;
            setEnPassantSquare(move.getTargetSquare() + stepBackDirection);
        }
    }


    /**
     * Returns copy of self and applying move
     * @param move The move to be made.
     * @return returns new Board instance
     */
    public Board makeMove(Move move) {
        return new Board(this, move);
    }


    /**
     * @return all captured pieces
     */
    public List<Integer> getCapturedPieces() { return capturedPieces; }


    /**
     * Who's turn is it
     * @return int of current turn color
     */
    public int getTurnColor() {
        return turnColor;
    }


    /**
     * Set who's turn it is
     * @param turnColor what piece color to set
     */
    public void setTurnColor(int turnColor) {
        this.turnColor = turnColor;
    }


    /**
     * If a pawn moved two spaces when this board was created, it is possible to capture the pawn at this position
     * @return position of the enPassantSquare
     */
    public int getEnPassantSquare() {
        return enPassantSquare;
    }


    /**
     * If a pawn moved two spaces when this board was created, it is possible to capture the pawn at this position
     * @param enPassantSquare set position of the enPassantSquare
     */
    public void setEnPassantSquare(int enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }


    /**
     * Returns the String representation of the board as used by the console client
     * @return the String representing the board according to the squares variable
     */
    @Override
    public String toString() {
        StringBuilder boardAsString = new StringBuilder();
        // iterate through lines
        for (int line = 0; line < 8; line++) {
            boardAsString.append(8 - line).append(" ");
            // iterate trough columns
            for (int column = 0; column < 8; column++) {
                boardAsString.append(Piece.toString(getPieceAt(column + (line * 8))));
                // decide whether to add a space or begin new line
                if (column < 7) {
                    boardAsString.append(" ");
                } else {
                    boardAsString.append("\n");
                }
            }
        }
        boardAsString.append("  a b c d e f g h\n");
        return boardAsString.toString();
    }


    /**
     * Returns the current piece at position squareIndex
     * @param squareIndex where to look for piece
     * @return the int value of peace at squareIndex position
     */
    public int getPieceAt(int squareIndex) {
        return squares[squareIndex];
    }


    /**
     * Returns the indices for all pieces of a certain color
     * @param color of the pieces
     * @return returns List of positions
     */
    public List<Integer> getPiecePositionsFor(int color){
        // TODO write tests
        // TODO write function
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i=0; i < 64; i++) {
            if (Piece.isColor(squares[i], color)) positions.add(i);
        }
        return positions;
    }
}
