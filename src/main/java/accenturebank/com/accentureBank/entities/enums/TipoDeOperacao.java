package accenturebank.com.accentureBank.entities.enums;



public enum TipoDeOperacao {
	
	SAQUE(1, "Saque"), 
	DEPOSITO(2, "Deposito"),
	TRANSFERENCIA(3, "Transferencia");

	private int cod;
	private String descricao;

	private TipoDeOperacao(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;

	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoDeOperacao toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (TipoDeOperacao x : TipoDeOperacao.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}

		}
		throw new IllegalArgumentException("Id invalido: " + cod);

	}
}

