package com.example.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	Page<Conta> findByDataVencimentoAndDescricao(LocalDate dataVencimento, String descricao, Pageable pageable);

	Page<Conta> findByDataVencimento(LocalDate dataVencimento, Pageable pageable);

	Page<Conta> findByDescricao(String descricao, Pageable pageable);

	@Override
	Page<Conta> findAll(Pageable pageable);

	BigDecimal findValorTotalPago(LocalDate dataInicio, LocalDate dataFim);

}
