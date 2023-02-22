package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{
	
	public Bishop(Board board, Color cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		
		Position p = new Position(0, 0);
		
		// Movimento NW
		
		p.setValues(position.getLinha() - 1, position.getColuna() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() - 1);;
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Movimento NE
		
		p.setValues(position.getLinha() + 1, position.getColuna() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() - 1);;
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Movimento SW
		
		p.setValues(position.getLinha() + 1, position.getColuna() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() + 1);;
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Movimento SE
		
		p.setValues(position.getLinha() - 1, position.getColuna() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() + 1);;
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}			
		return mat;
	}

}
