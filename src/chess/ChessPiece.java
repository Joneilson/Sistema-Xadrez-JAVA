package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color cor;

	public ChessPiece(Board board, Color cor) {
		super(board);
		this.cor = cor;
	}

	public Color getCor() {
		return cor;
	}
	
	protected boolean existePecaInimiga(Position position) {
		ChessPiece p = (ChessPiece) getBoard().peca(position);
		return p != null && p.getCor() != cor;
		
	}
	

}
