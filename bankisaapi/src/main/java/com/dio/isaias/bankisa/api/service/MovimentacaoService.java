package com.dio.isaias.bankisa.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.isaias.bankisa.api.dto.NovaMovimentacao;
import com.dio.isaias.bankisa.api.model.Correntista;
import com.dio.isaias.bankisa.api.model.Movimentacao;
import com.dio.isaias.bankisa.api.model.MovimentacaoTipo;
import com.dio.isaias.bankisa.api.repository.CorrentistaRepository;
import com.dio.isaias.bankisa.api.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired
	private CorrentistaRepository correntistaRepository;
	public void save(NovaMovimentacao novaMovimentacao) {
		Movimentacao movimentacao = new Movimentacao();
		
		//Double valor = novaMovimentacao.getTipo()==MovimentacaoTipo.RECEITA ?
//novaMovimentacao.getValor()	: novaMovimentacao.getValor() * -1;	
		
		Double valor = novaMovimentacao.getValor();
		if(novaMovimentacao.getTipo() == MovimentacaoTipo.DESPESA)
			valor = valor * -1;
		movimentacao.setDataHora(LocalDateTime.now());
		movimentacao.setDescricao(novaMovimentacao.getDescricao());
		movimentacao.setIdConta(novaMovimentacao.getIdConta());
		movimentacao.setTipo(novaMovimentacao.getTipo());
		movimentacao.setValor(valor);
		
		Correntista correntista = correntistaRepository.findById(novaMovimentacao.getIdConta()).orElse(null);
		if(correntista != null) {
			correntista.getConta().setSaldo(correntista.getConta().getSaldo() + valor);
			correntistaRepository.save(correntista);
		}
		
		repository.save(movimentacao);
		
	}

}
