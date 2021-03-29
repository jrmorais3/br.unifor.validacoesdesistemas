package br.com.aula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.NumeroContaIncorretoException;

public class BancoTest {

	@Test
	public void deveCadastrarConta() 
			throws ContaJaExistenteException, 
			       NumeroContaIncorretoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta);

		// Verificacao
		assertEquals(1, banco.obterContas().size());
	}
	
	@Test (expected = NumeroContaIncorretoException.class)
	public void naoDeveCadastrarContaInvalida() 
			throws ContaJaExistenteException, 
			       NumeroContaIncorretoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, -123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta);

		// Verificacao
		assertEquals(conta.getNumeroConta(), banco.obterContaPorNumero(conta.getNumeroConta()).getNumeroConta());		
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() 
			throws ContaJaExistenteException, 
				   NumeroContaIncorretoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.CORRENTE);

		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		//Verficacao
		Assert.fail();
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarClienteRepetido() 
			throws ContaJaExistenteException, 
			NumeroContaIncorretoException {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		
		Cliente cliente2 = new Cliente("Joao");
		Conta conta2 = new Conta(cliente2, 456, 100, TipoConta.POUPANCA);
		
		Banco banco = new Banco();
		
		// Acao
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);
		
		//Verficacao
		Assert.fail();
	}
}
