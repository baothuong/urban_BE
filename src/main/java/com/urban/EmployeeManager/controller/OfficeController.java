package com.urban.EmployeeManager.controller;

import com.urban.EmployeeManager.dto.OfficeCreateDTO;
import com.urban.EmployeeManager.dto.OfficeDTO;
import com.urban.EmployeeManager.dto.OfficeUpdateDTO;
import com.urban.EmployeeManager.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeDTO>> getAllOffices() {
        List<OfficeDTO> offices = officeService.getAllOffices();
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> getOfficeById(@PathVariable Long id) {
        OfficeDTO office = officeService.getOfficeById(id);
        return ResponseEntity.ok(office);
    }

    @PostMapping
    public ResponseEntity<OfficeDTO> createOffice(@Valid @RequestBody OfficeCreateDTO createDTO) {
        OfficeDTO office = officeService.createOffice(createDTO);
        return new ResponseEntity<>(office, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDTO> updateOffice(@PathVariable Long id,
                                                  @Valid @RequestBody OfficeUpdateDTO updateDTO) {
        OfficeDTO office = officeService.updateOffice(id, updateDTO);
        return ResponseEntity.ok(office);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }
}

