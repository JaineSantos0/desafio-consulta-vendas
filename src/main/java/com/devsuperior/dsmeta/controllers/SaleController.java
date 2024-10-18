package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}


	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			Pageable pageable,
			@RequestParam(value = "minDate", required = false, defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", required = false, defaultValue = "") String maxDate,
			@RequestParam(value = "name", required = false, defaultValue = "") String name
	) {
		Page<SaleReportDTO> salesReports = service.findAllSales(pageable, minDate, maxDate, name);
		return ResponseEntity.ok(salesReports);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@RequestParam(value = "minDate", required = false, defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", required = false, defaultValue = "") String maxDate
	) {
		List<SaleSummaryDTO> sallesBySeller = service.findTotalSalesBySeller(minDate, maxDate);
		return ResponseEntity.ok(sallesBySeller);
	}
}