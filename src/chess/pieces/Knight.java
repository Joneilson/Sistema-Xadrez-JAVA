package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "C";
	}

    private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().peca(position);
		return p == null || p.getCor() != getCor();
	}


    @Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		
		Position p = new Position(0, 0);
		
		
		p.setValues(position.getLinha() - 1, position.getColuna() - 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		p.setValues(position.getLinha() - 2, position.getColuna() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		p.setValues(position.getLinha() - 2, position.getColuna() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		p.setValues(position.getLinha() - 1, position.getColuna() + 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		p.setValues(position.getLinha() + 1, position.getColuna() + 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		p.setValues(position.getLinha() + 2, position.getColuna() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(position.getLinha() + 2, position.getColuna() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(position.getLinha() + 1, position.getColuna() - 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}				
		return mat;
    }

    
    
}
