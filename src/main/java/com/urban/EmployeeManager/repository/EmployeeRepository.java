package com.urban.EmployeeManager.repository;

import com.urban.EmployeeManager.model.Employee;
import com.urban.EmployeeManager.enums.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<Employee> findByOfficeId(Long officeId);
    List<Employee> findByPosition(Position position);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:officeId IS NULL OR e.office.id = :officeId) AND " +
            "(:position IS NULL OR e.position = :position)")
    Page<Employee> findByFilters(@Param("officeId") Long officeId,
                                 @Param("position") Position position,
                                 Pageable pageable);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.position = :position")
    long countByPosition(@Param("position") Position position);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.office.id = :officeId")
    long countByOfficeId(@Param("officeId") Long officeId);
}
