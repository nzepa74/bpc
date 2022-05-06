package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiFinListDao;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.entity.financial.formulation.dhi.*;
import com.spring.project.development.voler.repository.financial.formulation.dhi.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created By zepaG on 3/20/2022.
 */
@Service
public class TfDhiFinAddTargetService {
    private final TfDhiFinTargetRepository tfDhiFinTargetRepository;
    private final TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository;
    private final TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository;
    private final TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository;
    private final TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository;
    private final TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository;
    private final TfDhiFinListDao tfDhiFinListDao;
    private final CommonService commonService;

    public TfDhiFinAddTargetService(TfDhiFinTargetRepository tfDhiFinTargetRepository, TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository, TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository, TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository, TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository, TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository, TfDhiFinListDao tfDhiFinListDao, CommonService commonService) {
        this.tfDhiFinTargetRepository = tfDhiFinTargetRepository;
        this.tfDhiFinTarget_aRepository = tfDhiFinTarget_aRepository;
        this.tfDhiFinTargetStatusRepository = tfDhiFinTargetStatusRepository;
        this.tfDhiFinTargetDocRepository = tfDhiFinTargetDocRepository;
        this.tfDhiFinTargetWriteupRepository = tfDhiFinTargetWriteupRepository;
        this.tfDhiFinTargetWriteup_aRepository = tfDhiFinTargetWriteup_aRepository;

        this.tfDhiFinListDao = tfDhiFinListDao;
        this.commonService = commonService;
    }

    private ResponseMessage validateBeforeAdd(CurrentUser currentUser, FinancialTargetDto financialTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = financialTargetDto.getYear();
        String companyId = financialTargetDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();

        //only creator, reviewer and admin should able to add
        if (!myRoleId.equals(SystemRoles.Creator.getValue()) && !myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }

        //if myRoleId is creator, my companyId must equal to selected companyId, latest stage status must be null or R=Reverted
        if (myRoleId.equals(SystemRoles.Creator.getValue())) {
            // my companyId must equal to selected companyId
            if (!companyId.equals(currentUser.getCompanyId())) {
                responseMessage.setText("Failed to save due to miss match of company.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
            // latest stage status must be null or R=Reverted
            TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinListDao.getLatestStatus(year, companyId);
            if (tfDhiFinTargetStage != null) {
                if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target is already approved. Please request administrator to add new if it was left out.");
                    return responseMessage;
                }
            }
        }
        //if myRoleId is Reviewer, companyId must exist in company mapping for reviewer id, latest stage status must be S=Submitted
        else if (myRoleId.equals(SystemRoles.Reviewer.getValue())) {
            //check if it is reviewer belongs to selected company or not
            String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
            if (mappedCompanyId == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to revert because selected company is not mapped to you.");
                return responseMessage;
            }
            // latest stage status must be S=Reverted
            TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinListDao.getLatestStatus(year, companyId);
            if (tfDhiFinTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is already approved. Please request administrator to add new if it was left out.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addTarget(HttpServletRequest request, CurrentUser currentUser, FinancialTargetDto financialTargetDto) throws IOException {
        ResponseMessage responseMessage = validateBeforeAdd(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfDhiFinTarget tfDhiFinTarget = new ModelMapper().map(financialTargetDto, TfDhiFinTarget.class);
        tfDhiFinTarget.setVersionNo(BigInteger.ONE);
        tfDhiFinTarget.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiFinTarget.setTargetId(UuidGenerator.generateUuid());
        tfDhiFinTarget.setCreatedBy(currentUser.getUserId());
        tfDhiFinTarget.setCreatedDate(new Date());
        tfDhiFinTargetRepository.save(tfDhiFinTarget);
        TfDhiFinTarget_a tfDhiFinTarget_a = new ModelMapper().map(tfDhiFinTarget, TfDhiFinTarget_a.class);
        tfDhiFinTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiFinTarget_aRepository.save(tfDhiFinTarget_a);

        TfDhiFinTargetWriteup tfDhiFinTargetWriteup = new ModelMapper().map(financialTargetDto, TfDhiFinTargetWriteup.class);
        tfDhiFinTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfDhiFinTargetWriteup.setTargetId(tfDhiFinTarget.getTargetId());
        tfDhiFinTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiFinTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfDhiFinTargetWriteup.setCreatedDate(new Date());
        tfDhiFinTargetWriteupRepository.save(tfDhiFinTargetWriteup);
        TfDhiFinTargetWriteup_a tfDhiFinTargetWriteup_a = new ModelMapper().map(tfDhiFinTargetWriteup, TfDhiFinTargetWriteup_a.class);
        tfDhiFinTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiFinTargetWriteup_a.setTargetAuditId(tfDhiFinTarget_a.getTargetAuditId());
        tfDhiFinTargetWriteup_aRepository.save(tfDhiFinTargetWriteup_a);

        List<MultipartFile> attachedFiles = financialTargetDto.getAttachedFiles();
        if (attachedFiles != null) {
            for (MultipartFile multipartFile : attachedFiles) {
                String filename = multipartFile.getOriginalFilename();
                Long fileSize = multipartFile.getSize();
                String fileExtension = Objects.requireNonNull(filename).substring(filename.lastIndexOf(".") + 1, filename.length()).toUpperCase();

                FileUploadDTO fileUploadDTO = FileUploadToExternalLocation.fileUploadPathRetriever(request);
                String fileUrl = fileUploadDTO.getUploadFilePath().concat(filename);
                if (!filename.equals("")) {
                    responseMessage = FileUploadToExternalLocation.fileUploader(multipartFile, filename, "attachFile.properties", request);
                    TfDhiFinTargetDoc tfDhiFinTargetDoc = new TfDhiFinTargetDoc();
                    tfDhiFinTargetDoc.setFileId(UuidGenerator.generateUuid());
                    tfDhiFinTargetDoc.setTargetId(tfDhiFinTarget.getTargetId());
                    tfDhiFinTargetDoc.setFileName(filename);
                    tfDhiFinTargetDoc.setFileExtension(fileExtension);
                    tfDhiFinTargetDoc.setFileUrl(fileUrl);
                    tfDhiFinTargetDoc.setFileSize(fileSize.toString());
                    tfDhiFinTargetDoc.setCreatedBy(currentUser.getUserId());
                    tfDhiFinTargetDoc.setCreatedDate(new Date());
                    tfDhiFinTargetDocRepository.save(tfDhiFinTargetDoc);
                }
            }
        }

        TfDhiFinTargetStatus dhiFinTargetStatus = new TfDhiFinTargetStatus();
        dhiFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        dhiFinTargetStatus.setTargetId(tfDhiFinTarget.getTargetId());
        dhiFinTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        dhiFinTargetStatus.setCreatedBy(currentUser.getUserId());
        dhiFinTargetStatus.setCreatedDate(new Date());
        tfDhiFinTargetStatusRepository.save(dhiFinTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
