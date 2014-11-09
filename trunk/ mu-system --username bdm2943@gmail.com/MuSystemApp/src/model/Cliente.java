package model;

import services.validator.Validator;
import dao.annotations.DataAccessClass;
import enums.Estado;
import exceptions.BusinessException;

@DataAccessClass(daoImpl = "dao.implementation.ClienteDaoImpl")
public class Cliente {
	
	private int cod;
	
	private String nome;
	
	private String rg;
	
	private String bairro;
	
	private String cidade;
	
	private Estado uf;
	
	private String endereco;
	
	private String telefone;
	
	private String email;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) throws BusinessException {
		if(Validator.isZero(cod))
			throw new BusinessException("O código de um cliente não pode ser vazio!");
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws BusinessException {
		if(Validator.isEmpty(nome))
			throw new BusinessException("O nome de um cliente não pode ser vazio!");
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) throws BusinessException {
		if(Validator.isEmpty(rg))
			throw new BusinessException("O Rg de um cliente não pode ser vazio!");
		this.rg = rg.replaceAll("\\.","").replaceAll("\\-", "");
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro){
		this.bairro = bairro;
	}

	public String getCidade() {
		
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getUf() {
		if(null == uf)
			return null;
		return uf;
	}
	

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public String getEndereco() {		
		return endereco;
	}

	public void setEndereco(String endereco) throws BusinessException {
		if(Validator.isEmpty(endereco))
			throw new BusinessException("O endereço de um cliente não pode ser vazio!");
		
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {		
		this.telefone = telefone.replaceAll(".","").replace("-", "");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override	
	public String toString() {
		return nome;
	}
	
	@Override
	public boolean equals(Object obj) {		
		Cliente c = (Cliente) obj;		
		return c.hashCode() == this.hashCode();	
	}
	
	@Override
	public int hashCode() {	
		return Integer.valueOf(rg);
	}

}