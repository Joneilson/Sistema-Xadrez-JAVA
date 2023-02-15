package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece{
	
	private Color cor;

	public ChessPiece(Board board, Color cor) {
		super(board);
		this.cor = cor;
	}

	public Color getCor() {
		return cor;
	}
	

}
