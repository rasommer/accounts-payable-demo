package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Conta;
import com.example.demo.service.ContaService;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@PostMapping
	public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
		Conta novaConta = contaService.addConta(conta);
		return ResponseEntity.ok(novaConta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
		Optional<Conta> contaExistente = contaService.getContaPorId(id);
		if (contaExistente.isPresent()) {
			Conta contaAtualizada = contaService.updateConta(id, conta);
			return ResponseEntity.ok(contaAtualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{id}/situacao")
	public ResponseEntity<Conta> alterarSituacao(@PathVariable Long id, @RequestParam String situacao) {
		Optional<Conta> contaExistente = contaService.getContaPorId(id);
		if (contaExistente.isPresent()) {
			Conta contaAtualizada = contaService.updateSituacao(id, situacao);
			return ResponseEntity.ok(contaAtualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<Page<Conta>> obterContas(@RequestParam(required = false) LocalDate dataVencimento,
			@RequestParam(required = false) String descricao, Pageable pageable) {
		Page<Conta> contas = contaService.getContas(dataVencimento, descricao, pageable);
		return ResponseEntity.ok(contas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Conta> obterContaPorId(@PathVariable Long id) {
		Optional<Conta> conta = contaService.getContaPorId(id);
		return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/total-pago")
	public ResponseEntity<BigDecimal> obterValorTotalPago(@RequestParam LocalDate dataInicio,
			@RequestParam LocalDate dataFim) {
		BigDecimal totalPago = contaService.obterValorTotalPago(dataInicio, dataFim);
		return ResponseEntity.ok(totalPago);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

		try {
			contaService.processCsvFile(file);
			return ResponseEntity.status(HttpStatus.OK)
					.body("Uploaded the file successfully: " + file.getOriginalFilename());
		} catch (IOException io) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body("Could process uploaded the file: " + file.getOriginalFilename() + "! " + io.getMessage());
		}

	}
}
