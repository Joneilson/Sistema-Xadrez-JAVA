package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board tabuleiro;
	
	private List<Piece> pecasNoTabuleiro = new ArrayList<>();
	private List<Piece> pecasCapturadas = new ArrayList<>();
	
	public ChessMatch() {
		
		tabuleiro = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		comecoPartida();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return tabuleiro.peca(position).movimentosPossiveis();
	}
	
	
	public ChessPiece executarMovimentoXadrez(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		nextTurn();
		
		Piece pecaCapturada = makeMove(source, target);
		return (ChessPiece)pecaCapturada;
		
	}
	
	private void validateSourcePosition(Position position) {
		if(!tabuleiro.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem");
		}
		if(currentPlayer != ((ChessPiece)tabuleiro.peca(position)).getCor()) {
			throw new ChessException("A peça escolhida não é sua");
		}
		
		if(!tabuleiro.peca(position).existeMovimentoPossivel()) {
			throw new ChessException("Não existem movimentos possiveis para a peça escolhida.");
		}
		
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!tabuleiro.peca(source).movimentoPossivel(target)) {
			throw new ChessException("A peça escolhida não pode se mover para a casa de destino");
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = tabuleiro.removePeca(source);
		Piece pecaCapturada = tabuleiro.removePeca(target);
		tabuleiro.colocarPeca(p, target);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
		
	}
	
	
	private void comecoPartida() {
		
		colocarNovaPeca('a', 8,new Rook(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 8,new Rook(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8,new King(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 8,new Queen(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 8, new Bishop(tabuleiro, Color.BLACK));
		
		colocarNovaPeca('e', 1,new King(tabuleiro, Color.WHITE));
		colocarNovaPeca('a', 1,new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('h', 1,new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 1,new Queen(tabuleiro, Color.WHITE));
		colocarNovaPeca('c', 1, new Bishop(tabuleiro, Color.WHITE));
	}
}
