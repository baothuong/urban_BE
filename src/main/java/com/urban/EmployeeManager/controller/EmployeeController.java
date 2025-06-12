package com.urban.EmployeeManager.controller;

import com.urban.EmployeeManager.dto.EmployeeCreateDTO;
import com.urban.EmployeeManager.dto.EmployeeDTO;
import com.urban.EmployeeManager.dto.EmployeeUpdateDTO;
import com.urban.EmployeeManager.dto.PasswordChangeDTO;
import com.urban.EmployeeManager.enums.Position;
import com.urban.EmployeeManager.service.EmployeeService;
import com.urban.EmployeeManager.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final FileUploadService fileUploadService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(required = false) Long officeId,
            @RequestParam(required = false) Position position,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EmployeeDTO> employees = employeeService.getAllEmployees(officeId, position, pageable);
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO createDTO) {
        EmployeeDTO employee = employeeService.createEmployee(createDTO);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeUpdateDTO updateDTO) {
        EmployeeDTO employee = employeeService.updateEmployee(id, updateDTO);
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = employeeService.getStatistics();
        return ResponseEntity.ok(stats);
    }


    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id,
                                                 @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        employeeService.changePassword(id, passwordChangeDTO);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    @PostMapping("/{id}/avatar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> uploadAvatar(@PathVariable Long id,
                                                    @RequestParam("file") MultipartFile file) {
        String filename = fileUploadService.uploadAvatar(file);
        EmployeeDTO employee = employeeService.updateEmployeeAvatar(id, filename);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}/avatar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> deleteAvatar(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.updateEmployeeAvatar(id, null);
        return ResponseEntity.ok(employee);
    }
}

