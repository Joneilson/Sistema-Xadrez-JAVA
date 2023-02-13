package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
