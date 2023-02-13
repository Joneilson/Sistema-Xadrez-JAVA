package chess;

import boardgame.Board;
import boardgame.Position;
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
	
	private void comecoPartida() {
		
		tabuleiro.colocarPeca(new Rook(tabuleiro, Color.BLACK), new Position(0, 0));
		tabuleiro.colocarPeca(new Rook(tabuleiro, Color.BLACK), new Position(0, 7));
		tabuleiro.colocarPeca(new King(tabuleiro, Color.BLACK),	new Position(7, 4));
		tabuleiro.colocarPeca(new King(tabuleiro, Color.WHITE),	new Position(0, 3));
		tabuleiro.colocarPeca(new Rook(tabuleiro, Color.WHITE), new Position(7, 0));
		tabuleiro.colocarPeca(new Rook(tabuleiro, Color.WHITE), new Position(7, 7));
	}

}
