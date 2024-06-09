package com.example.demo.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.CsvMapper;
import com.example.demo.entity.Conta;
import com.example.demo.repository.ContaRepository;
import com.example.demo.service.ContaService;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Override
	public Conta addConta(Conta conta) {
		return contaRepository.save(conta);
	}

	@Override
	public Conta updateConta(Long id, Conta conta) {
		conta.setId(id);
		return contaRepository.save(conta);
	}

	@Override
	public Conta updateSituacao(Long id, String situacao) {
		Conta conta = contaRepository.findById(id).orElseThrow();
		conta.setSituacao(situacao);
		return contaRepository.save(conta);
	}

	@Override
	public Page<Conta> getContas(LocalDate dataVencimento, String descricao, Pageable pageable) {
		if (dataVencimento != null && descricao != null) {
			return contaRepository.findByDataVencimentoAndDescricao(dataVencimento, descricao, pageable);
		} else if (dataVencimento != null) {
			return contaRepository.findByDataVencimento(dataVencimento, pageable);
		} else if (descricao != null) {
			return contaRepository.findByDescricao(descricao, pageable);
		} else {
			return contaRepository.findAll(pageable);
		}
	}

	@Override
	public Optional<Conta> getContaPorId(Long id) {
		return contaRepository.findById(id);
	}

	@Override
	public BigDecimal obterValorTotalPago(LocalDate dataInicio, LocalDate dataFim) {
		return contaRepository.findValorTotalPago(dataInicio, dataFim);
	}

	@Override
	public void processCsvFile(MultipartFile file) throws IOException {
		CsvMapper csvMapper = new CsvMapper();
		csvMapper.csvToContas(file);
	}
}
