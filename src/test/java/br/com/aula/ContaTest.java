package br.com.aula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.NumeroContaIncorretoException;
import br.com.aula.exception.ValorIncorreto;

public class ContaTest {

	@Test
	public void deveCreditar() throws NumeroContaIncorretoException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 10, TipoConta.CORRENTE);

		// Acao
		c.creditar(5);

		// Verificao
		assertEquals(15, c.getSaldo());
	}
	
	@Test
	public void deveEfetuarTransferenciaContasCorrentes() 
			throws ContaSemSaldoException, 
				   ContaNaoExistenteException, 
				   NumeroContaIncorretoException, 
				   ValorIncorreto {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Acao
		banco.efetuarTransferencia(123, 456, 100);

		// Verificacao
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	@Test
	public void deveEfetuarTransferenciaContasCorrentePoupanca() 
			throws ContaSemSaldoException, 
			ContaNaoExistenteException, 
			NumeroContaIncorretoException,
			ValorIncorreto {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(123, 456, 100);
		
		// Verificacao
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}
	
	@Test
	public void deveConferirExistenciaContaOrigem() 
			throws ContaSemSaldoException, 
			ContaNaoExistenteException, 
			NumeroContaIncorretoException,
			ValorIncorreto {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(123, 456, 100);
		
		// Verificacao
		assertNotNull(banco.obterContaPorNumero(contaOrigem.getNumeroConta()).getNumeroConta());
	}
	
	@Test
	public void deveConferirExistenciaContaDestino() 
			throws ContaSemSaldoException, 
			ContaNaoExistenteException, 
			NumeroContaIncorretoException,
			ValorIncorreto {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(123, 456, 100);
		
		// Verificacao
		assertNotNull(banco.obterContaPorNumero(contaDestino.getNumeroConta()).getNumeroConta());
	}
	
	@Test (expected = ContaSemSaldoException.class)
	public void naoDevePossuirSaldoNegativoContaPoupanca() 
			throws ContaSemSaldoException, 
			ContaNaoExistenteException, 
			NumeroContaIncorretoException,
			ValorIncorreto {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.POUPANCA);
		
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(123, 456, 100);
		
		// Verificacao
		assertTrue(banco.obterContaPorNumero(contaOrigem.getSaldo()).getSaldo() > 0);
	}
	
	@Test (expected = ValorIncorreto.class)
	public void naoDevePermirtirTransferenciaNegativa() 
			throws ContaSemSaldoException, 
			ContaNaoExistenteException, 
			NumeroContaIncorretoException,
			ValorIncorreto {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.POUPANCA);
		
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(123, 456, -100);
		
		// Verificacao
		Assert.fail();
	}
	
	
	
	

}
