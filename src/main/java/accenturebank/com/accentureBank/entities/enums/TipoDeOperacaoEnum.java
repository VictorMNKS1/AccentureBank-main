package accenturebank.com.accentureBank.entities.enums;


public enum TipoDeOperacaoEnum {
	
	SAQUE(1, "Saque"), 
	DEPOSITO(2, "Deposito"),
	TRANSFERENCIA(3, "Transferencia");

	private int cod;
	private String descricao;

	private TipoDeOperacaoEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;

	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoDeOperacaoEnum toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (TipoDeOperacaoEnum x : TipoDeOperacaoEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}

		}
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	

	
	
}

