package com.example.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Conta;

public interface ContaService {

	Conta addConta(Conta conta);

	Conta updateConta(Long id, Conta conta);

	Conta updateSituacao(Long id, String situacao);

	Page<Conta> getContas(LocalDate dataVencimento, String descricao, Pageable pageable);

	Optional<Conta> getContaPorId(Long id);

	BigDecimal obterValorTotalPago(LocalDate dataInicio, LocalDate dataFim);

	void processCsvFile(MultipartFile file) throws IOException;
}
