package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().peca(position);
		return p == null || p.getCor() != getCor();
	}
	
	
	
	
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		
		Position p = new Position(0, 0);
		
		// Acima
		
		p.setValues(position.getLinha() - 1, position.getColuna());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Abaixo
		
		p.setValues(position.getLinha() + 1, position.getColuna());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Esquerda
		
		p.setValues(position.getLinha(), position.getColuna() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		// Direita
		
		p.setValues(position.getLinha(), position.getColuna() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		// nw
		
		p.setValues(position.getLinha() - 1, position.getColuna() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// ne
		
		p.setValues(position.getLinha() - 1, position.getColuna() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sw
		
		p.setValues(position.getLinha() + 1, position.getColuna() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// se
		
		p.setValues(position.getLinha() + 1, position.getColuna() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}				
		return mat;
	}

}
