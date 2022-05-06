package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmFinListDao;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.*;
import com.spring.project.development.voler.repository.financial.formulation.bcpm.*;
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
public class TfBcpmFinAddTargetService {
    private final TfBcpmFinTargetRepository tfBcpmFinTargetRepository;
    private final TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository;
    private final TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository;
    private final TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository;
    private final TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository;
    private final TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository;
    private final TfBcpmFinListDao tfBcpmFinListDao;
    private final CommonService commonService;

    public TfBcpmFinAddTargetService(TfBcpmFinTargetRepository tfBcpmFinTargetRepository, TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository, TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository, TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository, TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository, TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository, TfBcpmFinListDao tfBcpmFinListDao, CommonService commonService) {
        this.tfBcpmFinTargetRepository = tfBcpmFinTargetRepository;
        this.tfBcpmFinTarget_aRepository = tfBcpmFinTarget_aRepository;
        this.tfBcpmFinTargetStatusRepository = tfBcpmFinTargetStatusRepository;
        this.tfBcpmFinTargetDocRepository = tfBcpmFinTargetDocRepository;
        this.tfBcpmFinTargetWriteupRepository = tfBcpmFinTargetWriteupRepository;
        this.tfBcpmFinTargetWriteup_aRepository = tfBcpmFinTargetWriteup_aRepository;

        this.tfBcpmFinListDao = tfBcpmFinListDao;
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
            TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinListDao.getLatestStatus(year, companyId);
            if (tfBcpmFinTargetStage != null) {
                if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinListDao.getLatestStatus(year, companyId);
            if (tfBcpmFinTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmFinTarget tfBcpmFinTarget = new ModelMapper().map(financialTargetDto, TfBcpmFinTarget.class);
        tfBcpmFinTarget.setVersionNo(BigInteger.ONE);
        tfBcpmFinTarget.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmFinTarget.setTargetId(UuidGenerator.generateUuid());
        tfBcpmFinTarget.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTarget.setCreatedDate(new Date());
        tfBcpmFinTargetRepository.save(tfBcpmFinTarget);
        TfBcpmFinTarget_a tfBcpmFinTarget_a = new ModelMapper().map(tfBcpmFinTarget, TfBcpmFinTarget_a.class);
        tfBcpmFinTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmFinTarget_aRepository.save(tfBcpmFinTarget_a);

        TfBcpmFinTargetWriteup tfBcpmFinTargetWriteup = new ModelMapper().map(financialTargetDto, TfBcpmFinTargetWriteup.class);
        tfBcpmFinTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfBcpmFinTargetWriteup.setTargetId(tfBcpmFinTarget.getTargetId());
        tfBcpmFinTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmFinTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTargetWriteup.setCreatedDate(new Date());
        tfBcpmFinTargetWriteupRepository.save(tfBcpmFinTargetWriteup);
        TfBcpmFinTargetWriteup_a tfBcpmFinTargetWriteup_a = new ModelMapper().map(tfBcpmFinTargetWriteup, TfBcpmFinTargetWriteup_a.class);
        tfBcpmFinTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmFinTargetWriteup_a.setTargetAuditId(tfBcpmFinTarget_a.getTargetAuditId());
        tfBcpmFinTargetWriteup_aRepository.save(tfBcpmFinTargetWriteup_a);

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
                    TfBcpmFinTargetDoc tfBcpmFinTargetDoc = new TfBcpmFinTargetDoc();
                    tfBcpmFinTargetDoc.setFileId(UuidGenerator.generateUuid());
                    tfBcpmFinTargetDoc.setTargetId(tfBcpmFinTarget.getTargetId());
                    tfBcpmFinTargetDoc.setFileName(filename);
                    tfBcpmFinTargetDoc.setFileExtension(fileExtension);
                    tfBcpmFinTargetDoc.setFileUrl(fileUrl);
                    tfBcpmFinTargetDoc.setFileSize(fileSize.toString());
                    tfBcpmFinTargetDoc.setCreatedBy(currentUser.getUserId());
                    tfBcpmFinTargetDoc.setCreatedDate(new Date());
                    tfBcpmFinTargetDocRepository.save(tfBcpmFinTargetDoc);
                }
            }
        }

        TfBcpmFinTargetStatus bcpmFinTargetStatus = new TfBcpmFinTargetStatus();
        bcpmFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        bcpmFinTargetStatus.setTargetId(tfBcpmFinTarget.getTargetId());
        bcpmFinTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        bcpmFinTargetStatus.setCreatedBy(currentUser.getUserId());
        bcpmFinTargetStatus.setCreatedDate(new Date());
        tfBcpmFinTargetStatusRepository.save(bcpmFinTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
