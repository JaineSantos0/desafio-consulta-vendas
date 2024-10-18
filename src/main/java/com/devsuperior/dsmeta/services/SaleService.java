package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> findTotalSalesBySeller(String minDate, String maxDate) {

		LocalDate[] adjustedDates = adjustDates(minDate, maxDate);
		LocalDate minLocalDate = adjustedDates[0];
		LocalDate maxLocalDate = adjustedDates[1];

		List<SaleSummaryDTO> sales = repository.searchTotalSalesBySeller(minLocalDate, maxLocalDate);

		return sales;
	}

	public Page<SaleReportDTO> findAllSales(Pageable pageable, String minDate, String maxDate, String name) {

		LocalDate[] adjustedDates = adjustDates(minDate, maxDate);
		LocalDate minLocalDate = adjustedDates[0];
		LocalDate maxLocalDate = adjustedDates[1];

		Page<SaleReportDTO> sales = repository.searchAllSales(pageable, minLocalDate, maxLocalDate, name);

		return sales;
	}

	private LocalDate[] adjustDates(String minDate, String maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxLocalDate = (maxDate == null || maxDate.isEmpty()) ? today : LocalDate.parse(maxDate);
		LocalDate minLocalDate = (minDate == null || minDate.isEmpty()) ? maxLocalDate.minusYears(1L) : LocalDate.parse(minDate);

		return new LocalDate[]{minLocalDate, maxLocalDate};
	}
}