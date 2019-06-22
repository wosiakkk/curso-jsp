package beans;

public class BeanProduto {

	private Long id;
	private String nome;
	private float quantidade;
	private float valor;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	//resolvendo o problema da edição de valor causado ao usar o jquery mask money
	public String getValorEmTexto() {
		return Float.toString(valor).replace('.', ',');
	}
	
}
