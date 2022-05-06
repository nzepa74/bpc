package com.spring.project.development.voler.repository.masterData;

import com.spring.project.development.voler.entity.masterData.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YearRepository extends JpaRepository<Year, String> {

    Year findByYear(String year);

    Year findByStatus(Character status);

    List<Year> findAllByOrderByYearDesc();
}
