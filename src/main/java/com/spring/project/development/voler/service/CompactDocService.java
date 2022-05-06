package com.spring.project.development.voler.service;

import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.voler.dao.CompactDocDao;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.WriteupDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By zepaG on 4/7/2022.
 */
@Service
public class CompactDocService {
    private final CompactDocDao compactDocDao;

    public CompactDocService(CompactDocDao compactDocDao) {
        this.compactDocDao = compactDocDao;
    }

    public ResponseMessage getFinWriteup(Integer stage, String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<WriteupDto> writeupDtos = null;
        if (stage == 1) {
            writeupDtos = compactDocDao.getFinWriteupOne(year, companyId);
        }
        if (writeupDtos != null) {
            responseMessage.setDTO(writeupDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getCusSerWriteup(Integer stage, String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<WriteupDto> writeupDtos = null;
        if (stage == 1) {
            writeupDtos = compactDocDao.getCusSerWriteupOne(year, companyId);
        }
        if (writeupDtos != null) {
            responseMessage.setDTO(writeupDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getOrgMgtWriteup(Integer stage, String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<WriteupDto> writeupDtos = null;
        if (stage == 1) {
            writeupDtos = compactDocDao.getOrgMgtWriteupOne(year, companyId);
        }
        if (writeupDtos != null) {
            responseMessage.setDTO(writeupDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getProdSaleWriteup(Integer stage, String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<WriteupDto> writeupDtos = null;
        if (stage == 1) {
            writeupDtos = compactDocDao.getProdSaleWriteupOne(year, companyId);
        }
        if (writeupDtos != null) {
            responseMessage.setDTO(writeupDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchProdSaleTarget(Integer stage, String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleTargetDto> prodSaleTargetDtos = null;
        if (stage == 1) {
            prodSaleTargetDtos = compactDocDao.searchProdSaleTargetOne(year, companyId);
        }
        if (prodSaleTargetDtos != null) {
            responseMessage.setDTO(prodSaleTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
