package br.com.aula;

import java.util.ArrayList;
import java.util.List;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.NumeroContaIncorretoException;
import br.com.aula.exception.ValorIncorreto;

public class Banco {

	private List<Conta> contas = new ArrayList<Conta>();

	public Banco() {
	}

	public Banco(List<Conta> contas) {
		this.contas = contas;
	}

	public void cadastrarConta(Conta conta) throws ContaJaExistenteException {

		for (Conta c : contas) {
			boolean isNomeClienteIgual = c.getCliente().getNome().equals(conta.getCliente().getNome());
			boolean isNumeroContaIgual = c.getNumeroConta() == conta.getNumeroConta();

			if (isNomeClienteIgual || isNumeroContaIgual) {
				throw new ContaJaExistenteException();
			}
		}

		this.contas.add(conta);

	}

	public void efetuarTransferencia(int numeroContaOrigem, int numeroContaDestino, int valor)
			throws ContaNaoExistenteException, ContaSemSaldoException, NumeroContaIncorretoException, ValorIncorreto {

		Conta contaOrigem = this.obterContaPorNumero(numeroContaOrigem);
		Conta contaDestino = this.obterContaPorNumero(numeroContaDestino);

		boolean isContaOrigemExistente = contaOrigem != null;
		boolean isContaDestinoExistente = contaDestino != null;

		if (valor < 0) {
			throw new ValorIncorreto();
		}

		if (isContaOrigemExistente && isContaDestinoExistente) {

			boolean isContaOrigemPoupanca = contaOrigem.getTipoConta().equals(TipoConta.POUPANCA);
			boolean isSaldoContaOrigemNegativo = contaOrigem.getSaldo() - valor < 0;

			if (isContaOrigemPoupanca && isSaldoContaOrigemNegativo) {
				throw new ContaSemSaldoException();
			}

			contaOrigem.debitar(valor);
			contaDestino.creditar(valor);

		} else {
			throw new ContaNaoExistenteException();
		}
	}

	public Conta obterContaPorNumero(int numeroConta) throws NumeroContaIncorretoException {

		if (numeroConta < 0) {
			throw new NumeroContaIncorretoException();
		}

		for (Conta c : contas) {
			if (c.getNumeroConta() == numeroConta) {
				return c;
			}
		}
		return null;
	}

	public List<Conta> obterContas() {
		return this.contas;
	}
}
