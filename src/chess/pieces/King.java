package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color cor, ChessMatch chessMatch) {
		super(board, cor);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "R";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().peca(position);
		return p == null || p.getCor() != getCor();
	}

	private boolean testTorreRoque(Position position){
		ChessPiece p = (ChessPiece)getBoard().peca(position);
		return p != null && p instanceof Rook && p.getCor() == getCor() && p.getContarMov() == 0;
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
		
		// Roque

		if(getContarMov() == 0 && !chessMatch.getCheck()){
			//Roque lado do rei
			Position posT1 = new Position(position.getLinha(), position.getColuna() + 3);
			if(testTorreRoque(posT1)){
				Position p1 = new Position(position.getLinha(), position.getColuna() + 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() + 2);
				if(getBoard().peca(p1) == null && getBoard().peca(p2) == null){
					mat[position.getLinha()][position.getColuna() + 2] = true;
				}
			}
			//Roque lado da rainha
			Position posT2 = new Position(position.getLinha(), position.getColuna() - 4);
			if(testTorreRoque(posT2)){
				Position p1 = new Position(position.getLinha(), position.getColuna() - 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() - 2);
				Position p3 = new Position(position.getLinha(), position.getColuna() - 3);
				if(getBoard().peca(p1) == null && getBoard().peca(p2) == null && getBoard().peca(p3) == null){
					mat[position.getLinha()][position.getColuna() - 2] = true;
				}
			}
		}



		return mat;
	}

}
