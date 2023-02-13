package boardgame;

public class Board {
	
	private int linhas;
	private int colunas;
	private Piece [][] pecas;
	
	public Board(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new BoardException("Erro criando tabuleiro: Necessario pelo menos 1 linha e 1 coluna");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		
		pecas = new Piece [linhas] [colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Piece peca(int linha, int coluna) {
		if(!positionExists(linha, coluna)) {
			throw new BoardException("Posição inexistente no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Piece peca(Position position) {
		return pecas[position.getLinha()][position.getColuna()];
	}
	
	public void colocarPeca(Piece peca, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça nessa posição: "+ position);
		}
		pecas[position.getLinha()][position.getColuna()] = peca;
		peca.position = position;
	}
	
	public Piece removePeca(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição inexistente no tabuleiro");
		}
		if(peca(position) == null) {
			return null;
		}
		Piece aux = peca(position);
		aux.position = null;
		pecas[position.getLinha()][position.getColuna()] = null;
		return aux;
	}
	
	private boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getLinha(), position.getColuna());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição inexistente no tabuleiro");
		}
		return peca(position) != null;
	}
	
}
