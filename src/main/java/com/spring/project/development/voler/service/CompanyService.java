package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.voler.dao.CompanyDao;
import com.spring.project.development.voler.dto.CompanyDto;
import com.spring.project.development.voler.entity.masterData.Company;
import com.spring.project.development.voler.repository.masterData.CompanyRepository;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyDao companyDao;

    public CompanyService(CompanyRepository companyRepository, CompanyDao companyDao) {
        this.companyRepository = companyRepository;
        this.companyDao = companyDao;
    }

    @Transactional
    public ResponseMessage addCompany(CurrentUser currentUser, CompanyDto companyDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();

        Boolean isChecked = companyDto.getIsParent();
        Character isParentChar = isChecked == null || !isChecked ? 'N' : 'Y';
        companyDto.setIsParentCompany(isParentChar);

        Character isParent = companyDao.getParentCompany();
        if (companyDto.getIsParentCompany() == 'Y' && isParent != null) {
            responseMessage.setText("Parent company already registered. Please uncheck Parent Company and try again.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }

        MultipartFile logo = companyDto.getLogoMf();
        if (logo != null) {
            byte[] logoByte = IOUtils.toByteArray(logo.getInputStream());
            String logoName = logo.getOriginalFilename();
            String logoExtension = logoName.substring(logoName.lastIndexOf(".") + 1).toUpperCase();
            if (logoExtension.length() < 2) {
                responseMessage.setText("Please Attach Company Logo.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
            companyDto.setLogo(logoByte);
            companyDto.setLogoExtension(logoExtension);
            companyDto.setLogoName(logoName);
        }

        Company company = new ModelMapper().map(companyDto, Company.class);
        company.setCompanyId(UuidGenerator.generateUuid());
        company.setCmdFlag(CmdFlag.CREATE.getValue());
        company.setCreatedBy(currentUser.getUserId());
        company.setCreatedDate(new Date());
        companyRepository.save(company);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional
    public ResponseMessage updateCompany(CurrentUser currentUser, CompanyDto companyDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();

        Boolean isChecked = companyDto.getIsParent();
        char isParentChar = isChecked == null || !isChecked ? 'N' : 'Y';
        companyDto.setIsParentCompany(isParentChar);

        if (isParentChar == 'Y') {
            Character isParent = companyDao.getParentCompanyByCompanyId(companyDto.getCompanyId());
            isParent = isParent == null || isParent == 'N' ? 'N' : isParent;
            if (isParent != 'Y') {
                Character isParentSet = companyDao.getParentCompany();
                if (isParentSet != null) {
                    responseMessage.setText("Parent company already registered. Please uncheck Parent Company and try again.");
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    return responseMessage;
                }
            }
        }

        Company companyDb = companyRepository.findByCompanyId(companyDto.getCompanyId());

        MultipartFile logo = companyDto.getLogoMf();
        if (logo != null) {
            byte[] logoByte = IOUtils.toByteArray(logo.getInputStream());
            String logoName = logo.getOriginalFilename();
            String logoExtension = logoName.substring(logoName.lastIndexOf(".") + 1).toUpperCase();
            if (logoExtension.length() < 2) {//if logo is not added, get previous logo from db
                companyDto.setLogo(companyDb.getLogo());
                companyDto.setLogoExtension(companyDb.getLogoExtension());
                companyDto.setLogoName(companyDb.getLogoName());
            } else {
                companyDto.setLogo(logoByte);
                companyDto.setLogoExtension(logoExtension);
                companyDto.setLogoName(logoName);
            }
        }

        Company company = new ModelMapper().map(companyDto, Company.class);
        company.setCmdFlag(CmdFlag.MODIFY.getValue());
        company.setCreatedBy(companyDb.getCreatedBy());
        company.setCreatedDate(companyDb.getCreatedDate());
        company.setUpdatedBy(currentUser.getUserId());
        company.setUpdatedDate(new Date());
        companyRepository.save(company);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage getCompanies() {
        ResponseMessage responseMessage = new ResponseMessage();
        List<CompanyDto> companyDtos = companyDao.getCompanies();
        if (companyDtos != null) {
            responseMessage.setDTO(companyDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getByCompanyId(String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        Company companyDto = companyRepository.findByCompanyId(companyId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (companyDto != null) {
            responseMessage.setDTO(companyDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
