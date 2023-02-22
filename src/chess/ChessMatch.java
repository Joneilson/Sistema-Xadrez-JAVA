package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
//import chess.pieces.Bishop;
import chess.pieces.King;
//import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board tabuleiro;
	private boolean check;
	private boolean checkMate;
	
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

	public boolean getCheck(){
		return check;
	}

	public boolean getCheckMate(){
		return checkMate;
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
		Piece pecaCapturada = makeMove(source, target);
		
		if(testeCheck(currentPlayer)){
			desfazerMovimento(source, target, pecaCapturada);
			throw new ChessException("Você não pode se colocar em check!");
		}

		check = (testeCheck(oponente(currentPlayer))) ? true : false;
		
		if(testeCheckMate(oponente(currentPlayer))){
			checkMate = true;
		}
		else{
			nextTurn();
		}	

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
		ChessPiece p = (ChessPiece)tabuleiro.removePeca(source);
		p.incContMov();
		Piece pecaCapturada = tabuleiro.removePeca(target);
		tabuleiro.colocarPeca(p, target);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	

	private void desfazerMovimento(Position source, Position target, Piece pecaCapturada){
		ChessPiece p = (ChessPiece)tabuleiro.removePeca(target);
		p.decContMov();
		tabuleiro.colocarPeca(p, source);

		if(pecaCapturada != null){
			tabuleiro.colocarPeca(pecaCapturada, target);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color oponente(Color cor){
		return (cor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}


	private ChessPiece king(Color cor){
		List<Piece> listPA = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p: listPA){
			if(p instanceof King){
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não tem nenhum rei " + cor + " no tabuleiro" );

	}


	private boolean testeCheck(Color cor){
		Position posicaoRei = king(cor).getChessPosition().toPosition();
		List<Piece> pecasRivais = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Piece p : pecasRivais){
			boolean [] [] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
				return true;
			}
		}
		return false;
	}


	private boolean testeCheckMate(Color cor){

		if(!testeCheck(cor)){
			return false;
		}
		List<Piece> listCM = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for(Piece p : listCM){
			boolean [][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++){
				for(int j = 0; j < tabuleiro.getColunas(); j++){
					if(mat[i][j]){
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece pecaCapturada = makeMove(source, target);
						boolean testCheck = testeCheck(cor);
						desfazerMovimento(source, target, pecaCapturada);
						if(!testCheck){
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
		
	}
	
	
	private void comecoPartida() {
		
		colocarNovaPeca('h', 7,new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 1,new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('e', 1,new King(tabuleiro, Color.WHITE));
		
		
		
		colocarNovaPeca('a', 8,new King(tabuleiro, Color.BLACK));
		colocarNovaPeca('b', 8,new Rook(tabuleiro, Color.BLACK));
	
	}
}
