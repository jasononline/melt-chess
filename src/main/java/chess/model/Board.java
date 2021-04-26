package chess.model;

import java.util.ArrayList;

/**
 * The Board class contains all information about the current state of the game.
 * Instances of Board will be used as game history and as parameter for the engine.
 */
public class Board {
    public static final String fileNames = "abcdefgh";
    public static final String rankNames = "12345678";

    // representing the chess board where squares[0] is "a1" and squares[63] is "h8"
    private int[] squares;



    private int turnColor;


    /**
     * Construct empty Board instance
     */
    public Board() {
        this.squares = new int[64];
        this.turnColor = Piece.White;
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
        // TODO write tests
        // TODO write function
    }


    /**
     * Returns new Board instance after making move
     * @param move The move to be made.
     */
    public Board makeMove(Move move) {
        // TODO write tests
        // TODO write function
        return this;
    }


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
