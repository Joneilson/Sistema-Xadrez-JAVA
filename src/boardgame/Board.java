package boardgame;

public class Board {
	
	private int linhas;
	private int colunas;
	private Piece [][] pecas;
	
	public Board(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		
		pecas = new Piece [linhas] [colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public Piece peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	
	public Piece peca(Position position) {
		return pecas[position.getLinha()][position.getColuna()];
	}
	
	public void colocarPeca(Piece peca, Position position) {
		pecas[position.getLinha()][position.getColuna()] = peca;
		peca.position = position;
	}
}
