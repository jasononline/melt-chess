package chess.model;

import java.util.ArrayList;

/**
 * The Board class contains all information about the current state of the game.
 * Instances of Board will be used as game history and as parameter for the engine.
 */
public class Board {

    // representing the chess board where squares[0] is "a1" and squares[63] is "h8"
    private int[] squares;
    private ArrayList<Integer> capturedPieces;
    // if a pawn moved two spaces when this board was created, it is possible to capture the pawn at this position:
    private int enPassantSquare;
    private int turnColor;
    // is castling with one of the four rooks still possible?
    private boolean CastlingA1Possible = true;
    private boolean CastlingA8Possible = true;
    private boolean CastlingH1Possible = true;
    private boolean CastlingH8Possible = true;

    public boolean isCastlingA1Possible(){return CastlingA1Possible;}
    public boolean isCastlingA8Possible(){return CastlingA8Possible;}
    public boolean isCastlingH1Possible(){return CastlingH1Possible;}
    public boolean isCastlingH8Possible(){return CastlingH8Possible;}
    public void forbidCastlingA1(){CastlingA1Possible =false;}
    public void forbidCastlingA8(){CastlingA8Possible =false;}
    public void forbidCastlingH1(){CastlingH1Possible =false;}
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
        for (Character s: fenString.toCharArray()) {
            if (Character.isDigit(s)) {
                position += Character.getNumericValue(s);
                continue;
            }

            switch (s) {
                case 'k':
                    squares[position] = Piece.King + Piece.Black;
                    break;
                case 'K':
                    squares[position] = Piece.King + Piece.White;
                    break;
                case 'p':
                    squares[position] = Piece.Pawn + Piece.Black;
                    break;
                case 'P':
                    squares[position] = Piece.Pawn + Piece.White;
                    break;
                case 'n':
                    squares[position] = Piece.Knight + Piece.Black;
                    break;
                case 'N':
                    squares[position] = Piece.Knight + Piece.White;
                    break;
                case 'b':
                    squares[position] = Piece.Bishop + Piece.Black;
                    break;
                case 'B':
                    squares[position] = Piece.Bishop + Piece.White;
                    break;
                case 'r':
                    squares[position] = Piece.Rook + Piece.Black;
                    break;
                case 'R':
                    squares[position] = Piece.Rook + Piece.White;
                    break;
                case 'q':
                    squares[position] = Piece.Queen + Piece.Black;
                    break;
                case 'Q':
                    squares[position] = Piece.Queen + Piece.White;
                    break;
                case '/':
                    // increase position until nextline
                    if (position % 8 == 0) {
                        position -= 1;
                    } else {
                        position += 8 - position % 8;
                    }
                    break;
            }
            position += 1;
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
        squares = board.squares;
        capturedPieces = board.getCapturedPieces();
        // En passant capture only within next move possible
        enPassantSquare = -1;
        turnColor = board.getTurnColor();
        // is castling with one of the four rooks still possible?
        CastlingA1Possible = board.isCastlingA1Possible();
        CastlingA8Possible = board.isCastlingA8Possible();
        CastlingH1Possible = board.isCastlingH1Possible();
        CastlingH8Possible = board.isCastlingH8Possible();
    }


    /**
     * Returns copy of self and applying move
     * @param move The move to be made.
     */
    public Board makeMove(Move move) {
        Board newBoard = new Board(this);
        int piece = newBoard.getPieceAt(move.getStartSquare());
        // capture piece
        if (Piece.getType(newBoard.getPieceAt(move.getTargetSquare())) != Piece.None) {
            capturedPieces.add(newBoard.getPieceAt(move.getTargetSquare()));
        }
        // en passant capture
        if (0 < enPassantSquare && move.getFlag() == Move.EnPassantCapture) {
            capturedPieces.add(newBoard.getPieceAt(enPassantSquare));
            newBoard.squares[enPassantSquare] = Piece.None;
        }
        if (move.getFlag() == Move.PawnTwoForward) {
            int stepBackDirection = (turnColor == Piece.Black) ? MoveGenerator.UP : MoveGenerator.DOWN;
            newBoard.enPassantSquare = move.getTargetSquare() + stepBackDirection;
        }

        // forbid castling if needed
        switch (move.getStartSquare()) {
            // rook moved
            case 0: newBoard.forbidCastlingA8(); break;
            case 7: newBoard.forbidCastlingH8(); break;
            case 56: newBoard.forbidCastlingA1(); break;
            case 63: newBoard.forbidCastlingH1(); break;
            // king moved
            case 4: {
                newBoard.forbidCastlingA8();
                newBoard.forbidCastlingH8();
                break;
            }
            case 60: {
                newBoard.forbidCastlingA1();
                newBoard.forbidCastlingH1();
                break;
            }
        }

        // move piece
        newBoard.squares[move.getTargetSquare()] = newBoard.getPieceAt(move.getStartSquare());
        newBoard.squares[move.getStartSquare()] = Piece.None;

        // promote pawn
        switch (move.getFlag()) {
            case Move.PromoteToBishop:
                newBoard.squares[move.getTargetSquare()] = Piece.Bishop + turnColor;
                break;
            case Move.PromoteToKnight:
                newBoard.squares[move.getTargetSquare()] = Piece.Knight + turnColor;
                break;
            case Move.PromoteToQueen:
                newBoard.squares[move.getTargetSquare()] = Piece.Queen + turnColor;
                break;
            case Move.PromoteToRook:
                newBoard.squares[move.getTargetSquare()] = Piece.Rook + turnColor;
                break;
        }

        int nextTurnColor = (newBoard.getTurnColor() == Piece.Black) ? Piece.White : Piece.Black;
        newBoard.setTurnColor(nextTurnColor);

        return newBoard;
    }


    /**
     *
     * @return all captured pieces
     */
    public ArrayList<Integer> getCapturedPieces() { return capturedPieces; }


    /**
     * Who's turn is it
     * @return int of current turn color
     */
    public int getTurnColor() {
        return turnColor;
    }


    /**
     * Set who's turn is it
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
     * Returns the index in square array for position
     * @param position position string e.g.: "e5"
     */
    public int positionToIndex(String position){
        // TODO write tests
        // TODO write function
        return 0;
    }


    /**
     * Returns the String representation of the board as used by the console client
     * @return the String representing the board according to the sqares variable
     */
    public String toString() {
        String boardAsString = "";
        // iterate through lines
        for (int line = 0; line < 8; line++) {
            boardAsString += String.valueOf(8 - line) + " ";
            // iterate trough columns
            for (int column = 0; column < 8; column++) {
                boardAsString += Piece.toString(getPieceAt(column + (line * 8)));
                // decide whether to add a space or begin new line
                if (column < 7) {
                    boardAsString += " ";
                } else {
                    boardAsString += "\n";
                }
            }
        }
        boardAsString += "  a b c d e f g h\n";
        return boardAsString;
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
     */
    public ArrayList<Integer> getPiecePositionsFor(int color){
        // TODO write tests
        // TODO write function
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i=0; i < 64; i++) {
            if (Piece.isColor(squares[i], color)) positions.add(i);
        }
        return positions;
    }

}
