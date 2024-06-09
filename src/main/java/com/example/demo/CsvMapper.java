package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Conta;

public class CsvMapper {

	public List<Conta> csvToContas(MultipartFile file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().build())) {

			List<Conta> contas = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Conta conta = new Conta(Long.parseLong(csvRecord.get("id")),
						LocalDate.parse(csvRecord.get("data_vencimento"), DateTimeFormatter.ISO_DATE),
						csvRecord.get("data_pagamento").isEmpty() ? null
								: LocalDate.parse(csvRecord.get("data_pagamento"), DateTimeFormatter.ISO_DATE),
						new BigDecimal(csvRecord.get("valor")), csvRecord.get("descricao"), csvRecord.get("situacao"));

				contas.add(conta);
			}

			return contas;
		}
	}
}
