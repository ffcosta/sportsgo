package br.com.sportsgo.model.usuario;
import br.com.sportsgo.model.pessoa.Pessoa;

public class Usuario extends Pessoa{
	private String login;
	private String senha;


	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
}