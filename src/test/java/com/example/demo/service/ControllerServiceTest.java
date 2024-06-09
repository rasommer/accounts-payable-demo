package com.example.demo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.configuration.ContaServiceTestContextConfiguration;
import com.example.demo.entity.Conta;
import com.example.demo.repository.ContaRepository;

@RunWith(SpringRunner.class)
@Import(ContaServiceTestContextConfiguration.class)
@SpringBootTest
public class ControllerServiceTest {

	@Autowired
	private ContaService contaService;

	@MockBean
	private ContaRepository contaRepository;

	private Conta conta;

	@Before
	public void setUp() {
		conta = new Conta(1L, LocalDate.now(), null, BigDecimal.valueOf(1000), "Test", "Pending");
	}

	@Test
	public void testAddConta() {
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta createdConta = contaService.addConta(conta);

		assertNotNull(createdConta);
		assertEquals(conta.getId(), createdConta.getId());
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	public void testUpdateConta() {
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta updatedConta = contaService.updateConta(conta.getId(), conta);

		assertNotNull(updatedConta);
		assertEquals(conta.getId(), updatedConta.getId());
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	public void testUpdateSituacao() {
		when(contaRepository.findById(conta.getId())).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta updatedConta = contaService.updateSituacao(conta.getId(), "Paid");

		assertNotNull(updatedConta);
		assertEquals("Paid", updatedConta.getSituacao());
		verify(contaRepository, times(1)).findById(conta.getId());
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	public void testGetContas() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Conta> contas = Arrays.asList(conta);
		Page<Conta> page = new PageImpl<>(contas, pageable, contas.size());

		when(contaRepository.findAll(pageable)).thenReturn(page);

		Page<Conta> result = contaService.getContas(null, null, pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		verify(contaRepository, times(1)).findAll(pageable);
	}

	@Test
	public void testGetContaPorId() {
		when(contaRepository.findById(conta.getId())).thenReturn(Optional.of(conta));

		Optional<Conta> result = contaService.getContaPorId(conta.getId());

		assertTrue(result.isPresent());
		assertEquals(conta.getId(), result.get().getId());
		verify(contaRepository, times(1)).findById(conta.getId());
	}

	@Test
	public void testObterValorTotalPago() {
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = LocalDate.of(2023, 12, 31);
		BigDecimal totalPago = BigDecimal.valueOf(5000);

		when(contaRepository.findValorTotalPago(startDate, endDate)).thenReturn(totalPago);

		BigDecimal result = contaService.obterValorTotalPago(startDate, endDate);

		assertNotNull(result);
		assertEquals(totalPago, result);
		verify(contaRepository, times(1)).findValorTotalPago(startDate, endDate);
	}
}
