package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board tabuleiro;
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		comecoPartida();
	}
	
	
	public ChessPiece[][] getPecas(){
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		
		return mat;
	}
	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).toPosition());
	}
	
	
	private void comecoPartida() {
		
		colocarNovaPeca('a', 8,new Rook(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 8,new Rook(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8,new King(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 1,new King(tabuleiro, Color.WHITE));
		colocarNovaPeca('a', 1,new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('h', 1,new Rook(tabuleiro, Color.WHITE));
	}
}
