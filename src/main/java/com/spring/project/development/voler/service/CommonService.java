package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.helper.Enum.CommonStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dto.CommentDto;
import com.spring.project.development.voler.dto.EvaluationScoreDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetActivity;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTarget;
import com.spring.project.development.voler.entity.masterData.Company;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget;
import com.spring.project.development.voler.repository.cusService.formulation.dhi.TfDhiCusSerTargetActivityRepository;
import com.spring.project.development.voler.repository.financial.formulation.dhi.TfDhiFinTargetRepository;
import com.spring.project.development.voler.repository.masterData.CompanyRepository;
import com.spring.project.development.voler.repository.masterData.WeightageSetupRepository;
import com.spring.project.development.voler.repository.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivityRepository;
import com.spring.project.development.voler.repository.prodSale.formulation.dhi.TfDhiProdSaleTargetRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By zepaG on 3/16/2022.
 */
@Service
public class CommonService {
    private final CommonDao commonDao;
    private final WeightageSetupRepository weightageSetupRepository;
    private final TfDhiFinTargetRepository tfDhiFinTargetRepository;
    private final TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository;
    private final TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository;
    private final TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository;
    private final CompanyRepository companyRepository;

    public CommonService(CommonDao commonDao, WeightageSetupRepository weightageSetupRepository, TfDhiFinTargetRepository tfDhiFinTargetRepository, TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository, TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository, TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository, CompanyRepository companyRepository) {
        this.commonDao = commonDao;
        this.weightageSetupRepository = weightageSetupRepository;
        this.tfDhiFinTargetRepository = tfDhiFinTargetRepository;
        this.tfDhiCusSerTargetActivityRepository = tfDhiCusSerTargetActivityRepository;
        this.tfDhiOrgMgtTargetActivityRepository = tfDhiOrgMgtTargetActivityRepository;
        this.tfDhiProdSaleTargetRepository = tfDhiProdSaleTargetRepository;
        this.companyRepository = companyRepository;
    }

    public List<DropdownDTO> getStatus() {
        List<DropdownDTO> dropdownDTOs = new ArrayList<>();

        DropdownDTO dropdownDTOActiveStatus = new DropdownDTO();
        dropdownDTOActiveStatus.setValueChar(CommonStatus.Active.getValue());
        dropdownDTOActiveStatus.setText(CommonStatus.Active.getText());
        dropdownDTOs.add(dropdownDTOActiveStatus);

        DropdownDTO dropdownDTOInActiveStatus = new DropdownDTO();
        dropdownDTOInActiveStatus.setValueChar(CommonStatus.Inactive.getValue());
        dropdownDTOInActiveStatus.setText(CommonStatus.Inactive.getText());
        dropdownDTOs.add(dropdownDTOInActiveStatus);

        return dropdownDTOs;
    }

    public List<DropdownDTO> getCompanies(CurrentUser currentUser) {
        Integer myRoleId = currentUser.getRoleId();
        Integer creatorId = SystemRoles.Creator.getValue();
        if (myRoleId.equals(creatorId)) {
            return commonDao.getMyCompany(currentUser.getCompanyId());
        } else {
            return commonDao.getAllCompanies();
        }
    }

    public List<DropdownDTO> roleList() {
        return commonDao.roleList();
    }

    public List<DropdownDTO> getYearList() {
        return commonDao.getYearList();
    }

    public ResponseMessage getAllocatedWt(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        WeightageSetup weightageSetup = weightageSetupRepository.findByYearAndCompanyId(year, companyId);
        if (weightageSetup != null) {
            responseMessage.setDTO(weightageSetup);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public List<TfDhiFinTarget> getFinKpi() {
        return tfDhiFinTargetRepository.findAll();
    }

    public ResponseMessage getFinComment(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CommentDto> commentDtos = commonDao.getFinComment(targetId);
        if (commentDtos != null) {
            responseMessage.setDTO(commentDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getCusSerComment(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CommentDto> commentDtos = commonDao.getCusSerComment(targetId);
        if (commentDtos != null) {
            responseMessage.setDTO(commentDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getOrgMgtComment(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CommentDto> commentDtos = commonDao.getOrgMgtComment(targetId);
        if (commentDtos != null) {
            responseMessage.setDTO(commentDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getProdSaleComment(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CommentDto> commentDtos = commonDao.getProdSaleComment(targetId);
        if (commentDtos != null) {
            responseMessage.setDTO(commentDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public List<TfDhiCusSerTargetActivity> getTargetActivity() {
        return tfDhiCusSerTargetActivityRepository.findAll();
    }

    public List<TfDhiOrgMgtTargetActivity> getOrgMgtTargetActivity() {
        return tfDhiOrgMgtTargetActivityRepository.findAll();
    }
    public List<TfDhiProdSaleTarget> getProdSaleTarget() {
        return tfDhiProdSaleTargetRepository.findAll();
    }

    public ResponseMessage getCompanyInfo(String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        Company company = companyRepository.findByCompanyId(companyId);
        if (company != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(company);
        }
        return responseMessage;
    }

    public ResponseMessage evaluationScore(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<EvaluationScoreDto> evaluationScoreDtos = commonDao.getEvaluationScore(year, companyId);
        responseMessage.setDTO(evaluationScoreDtos);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage getAllCompanyScore(String year) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<EvaluationScoreDto> evaluationScoreDtos = commonDao.getAllCompanyScore(year);
        responseMessage.setDTO(evaluationScoreDtos);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public String getCompanyIdByShortName(String sName) {
        Company company = companyRepository.findByShortName(sName);
        return company.getCompanyId();
    }

    public String getMappedCompanyId(String userId, String companyId) {
        return commonDao.getMappedCompanyId(userId, companyId);
    }

    public BigInteger isNewComment(CurrentUser currentUser) {
        return commonDao.isNewComment(currentUser.getUserId());
    }
}
