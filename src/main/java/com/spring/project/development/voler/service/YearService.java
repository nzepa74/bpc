package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CmdFlag;
import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.voler.entity.masterData.Year;
import com.spring.project.development.voler.repository.masterData.YearRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class YearService {

    private final YearRepository yearRepository;

    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    @Transactional
    public ResponseMessage addYear(CurrentUser currentUser, Year year) {
        ResponseMessage responseMessage = new ResponseMessage();
        Year activeYear = yearRepository.findByStatus('A');
        if (activeYear != null) {
            if (year.getStatus() == 'A' && !activeYear.getYear().equals(year.getYear())) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Year" + " " + activeYear.getYear() + " " + "is active year. " + "System can have only one active year.");
                return responseMessage;
            }
        }
        year.setCreatedBy(currentUser.getUserId());
        year.setCreatedDate(new Date());
        year.setCmdFlag(CmdFlag.CREATE.getValue());
        yearRepository.save(year);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional
    public ResponseMessage updateYear(CurrentUser currentUser, Year year) {
        ResponseMessage responseMessage = new ResponseMessage();

        Year activeYear = yearRepository.findByStatus('A');
        if (activeYear != null) {
            if (year.getStatus() == 'A' && !activeYear.getYear().equals(year.getYear())) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Year" + " " + activeYear.getYear() + " " + "is active year. " + "System can have only one active year.");
                return responseMessage;
            }
        }

        Year yearDb = yearRepository.findByYear(year.getYear());
        year.setCreatedBy(yearDb.getCreatedBy());
        year.setCreatedDate(yearDb.getCreatedDate());
        year.setUpdatedBy(currentUser.getUserId());
        year.setUpdatedDate(new Date());
        year.setCmdFlag(CmdFlag.MODIFY.getValue());
        yearRepository.save(year);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public List<Year> getYears() {
        return yearRepository.findAllByOrderByYearDesc();
    }

}
