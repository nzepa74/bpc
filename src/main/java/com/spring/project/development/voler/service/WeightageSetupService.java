package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.voler.dao.WeightageSetupDao;
import com.spring.project.development.voler.dto.WeightageSetupDto;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.repository.masterData.WeightageSetupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WeightageSetupService {
    private final WeightageSetupRepository weightageSetupRepository;
    private final WeightageSetupDao weightageSetupDao;

    public WeightageSetupService(WeightageSetupRepository weightageSetupRepository, WeightageSetupDao weightageSetupDao) {
        this.weightageSetupRepository = weightageSetupRepository;
        this.weightageSetupDao = weightageSetupDao;
    }

    public ResponseMessage addWeightage(CurrentUser currentUser, WeightageSetupDto weightageSetupDto) {
        ResponseMessage responseMessage = validateTotalWeight(weightageSetupDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String alreadyExist = weightageSetupDao.alreadyExist(weightageSetupDto.getCompanyId(), weightageSetupDto.getYear());
        if (alreadyExist != null) {
            responseMessage.setText("Already saved. Please update if you want to make changes.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        WeightageSetup weightageSetup = new ModelMapper().map(weightageSetupDto, WeightageSetup.class);
        weightageSetup.setWeightageSetupId(UuidGenerator.generateUuid());
        weightageSetup.setCmdFlag(CmdFlag.CREATE.getValue());
        weightageSetup.setCreatedDate(new Date());
        weightageSetup.setCreatedBy(currentUser.getUserId());
        weightageSetupRepository.save(weightageSetup);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    public ResponseMessage updateWeightage(CurrentUser currentUser, WeightageSetupDto weightageSetupDto) {
        ResponseMessage responseMessage = validateTotalWeight(weightageSetupDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        WeightageSetup weightageSetupDb = weightageSetupRepository.findByWeightageSetupId(weightageSetupDto.getWeightageSetupId());
        WeightageSetup weightageSetup = new ModelMapper().map(weightageSetupDto, WeightageSetup.class);
        weightageSetup.setCmdFlag(CmdFlag.MODIFY.getValue());
        weightageSetup.setYear(weightageSetupDb.getYear());
        weightageSetup.setCompanyId(weightageSetupDb.getCompanyId());
        weightageSetup.setCreatedDate(weightageSetupDb.getCreatedDate());
        weightageSetup.setCreatedBy(weightageSetupDb.getCreatedBy());
        weightageSetup.setUpdatedBy(currentUser.getUserId());
        weightageSetup.setUpdatedDate(new Date());
        weightageSetupRepository.save(weightageSetup);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private ResponseMessage validateTotalWeight(WeightageSetupDto weightageSetupDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        BigDecimal financialWt = weightageSetupDto.getFinancialWt();
        BigDecimal customerWt = weightageSetupDto.getCustomerWt();
        BigDecimal productionWt = weightageSetupDto.getProductionWt();
        BigDecimal orgManagementWt = weightageSetupDto.getOrgManagementWt();
        BigDecimal totalWeight = financialWt.add(customerWt.add(productionWt.add(orgManagementWt)));
        BigDecimal hundred = new BigDecimal("100");
        if (totalWeight.compareTo(hundred) != 0) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Total weight must be 100.");
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWeightage(String year) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<WeightageSetupDto> weightageSetupDtos = weightageSetupDao.getWeightage(year);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (weightageSetupDtos != null) {
            responseMessage.setDTO(weightageSetupDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWeightByWeightageSetupId(String weightageSetupId) {
        ResponseMessage responseMessage = new ResponseMessage();
        WeightageSetup weightageSetup = weightageSetupRepository.findByWeightageSetupId(weightageSetupId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (weightageSetup != null) {
            responseMessage.setDTO(weightageSetup);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
