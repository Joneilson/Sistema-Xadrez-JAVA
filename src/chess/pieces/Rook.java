package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "T";
		
	}
	

}
