package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		Position p = new Position(0, 0);
		
		// Movimento Acima
		
		p.setValues(position.getLinha() - 1, position.getColuna());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Moviemnto a Esquerda
		
		p.setValues(position.getLinha(), position.getColuna() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Movimento a Direita
		
		p.setValues(position.getLinha(), position.getColuna() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Movimento a Abaixo
		
		p.setValues(position.getLinha() + 1, position.getColuna());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if(getBoard().positionExists(p) && existePecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
	

}
