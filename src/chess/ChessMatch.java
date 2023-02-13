package chess;

import boardgame.Board;
import boardgame.Piece;
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
	
	
	public ChessPiece executarMovimentoXadrez(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		
		Piece pecaCapturada = makeMove(source, target);
		return (ChessPiece)pecaCapturada;
		
	}
	
	private void validateSourcePosition(Position position) {
		if(!tabuleiro.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem");
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = tabuleiro.removePeca(source);
		Piece pecaCapturada = tabuleiro.removePeca(target);
		tabuleiro.colocarPeca(p, target);
		return pecaCapturada;
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
