package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmOrgMgtListDao;
import com.spring.project.development.voler.dto.OrgMgtSubTargetDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.*;
import com.spring.project.development.voler.repository.orgMgt.formulation.bcpm.*;
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
public class TfBcpmOrgMgtAddTargetService {
    private final TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository;
    private final TfBcpmOrgMgtTargetActivityRepository tfBcpmOrgMgtTargetActivityRepository;
    private final TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository;
    private final TfBcpmOrgMgtTargetWriteupRepository tfBcpmOrgMgtTargetWriteupRepository;
    private final TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository;
    private final TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository;
    private final TfBcpmOrgMgtSubTarget_aRepository tfBcpmOrgMgtSubTarget_aRepository;
    private final TfBcpmOrgMgtSubTargetDocRepository tfBcpmOrgMgtSubTargetDocRepository;
    private final TfBcpmOrgMgtListDao tfBcpmOrgMgtListDao;
    private final CommonService commonService;

    public TfBcpmOrgMgtAddTargetService(TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository, TfBcpmOrgMgtTargetActivityRepository tfBcpmOrgMgtTargetActivityRepository, TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository, TfBcpmOrgMgtTargetWriteupRepository tfBcpmOrgMgtTargetWriteupRepository, TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository, TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository, TfBcpmOrgMgtSubTarget_aRepository tfBcpmOrgMgtSubTarget_aRepository, TfBcpmOrgMgtSubTargetDocRepository tfBcpmOrgMgtSubTargetDocRepository, TfBcpmOrgMgtListDao tfBcpmOrgMgtListDao, CommonService commonService) {
        this.tfBcpmOrgMgtTargetStatusRepository = tfBcpmOrgMgtTargetStatusRepository;
        this.tfBcpmOrgMgtTargetActivityRepository = tfBcpmOrgMgtTargetActivityRepository;
        this.tfBcpmOrgMgtTargetActivity_aRepository = tfBcpmOrgMgtTargetActivity_aRepository;
        this.tfBcpmOrgMgtTargetWriteupRepository = tfBcpmOrgMgtTargetWriteupRepository;
        this.tfBcpmOrgMgtTargetWriteup_aRepository = tfBcpmOrgMgtTargetWriteup_aRepository;
        this.tfBcpmOrgMgtSubTargetRepository = tfBcpmOrgMgtSubTargetRepository;
        this.tfBcpmOrgMgtSubTarget_aRepository = tfBcpmOrgMgtSubTarget_aRepository;
        this.tfBcpmOrgMgtSubTargetDocRepository = tfBcpmOrgMgtSubTargetDocRepository;
        this.tfBcpmOrgMgtListDao = tfBcpmOrgMgtListDao;
        this.commonService = commonService;
    }

    private ResponseMessage validateBeforeAdd(CurrentUser currentUser, OrgMgtTargetDto orgMgtTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = orgMgtTargetDto.getYear();
        String companyId = orgMgtTargetDto.getCompanyId();
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
            TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtListDao.getLatestStatus(year, companyId);
            if (tfBcpmOrgMgtTargetStage != null) {
                if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtListDao.getLatestStatus(year, companyId);
            if (tfBcpmOrgMgtTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is already approved. Please request administrator to add new if it was left out.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addTarget(HttpServletRequest request, CurrentUser currentUser, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        ResponseMessage responseMessage = validateBeforeAdd(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivity = new ModelMapper().map(orgMgtTargetDto, TfBcpmOrgMgtTargetActivity.class);
        tfBcpmOrgMgtTargetActivity.setVersionNo(BigInteger.ONE);
        tfBcpmOrgMgtTargetActivity.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmOrgMgtTargetActivity.setTargetId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetActivity.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetActivity.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetActivityRepository.save(tfBcpmOrgMgtTargetActivity);
        TfBcpmOrgMgtTargetActivity_a tfBcpmOrgMgtTargetActivity_a = new ModelMapper().map(tfBcpmOrgMgtTargetActivity, TfBcpmOrgMgtTargetActivity_a.class);
        tfBcpmOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetActivity_aRepository.save(tfBcpmOrgMgtTargetActivity_a);

        TfBcpmOrgMgtTargetWriteup tfBcpmOrgMgtTargetWriteup = new ModelMapper().map(orgMgtTargetDto, TfBcpmOrgMgtTargetWriteup.class);
        tfBcpmOrgMgtTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetWriteup.setTargetId(tfBcpmOrgMgtTargetActivity.getTargetId());
        tfBcpmOrgMgtTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmOrgMgtTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetWriteup.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetWriteupRepository.save(tfBcpmOrgMgtTargetWriteup);
        TfBcpmOrgMgtTargetWriteup_a tfBcpmOrgMgtTargetWriteup_a = new ModelMapper().map(tfBcpmOrgMgtTargetWriteup, TfBcpmOrgMgtTargetWriteup_a.class);
        tfBcpmOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetWriteup_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
        tfBcpmOrgMgtTargetWriteup_aRepository.save(tfBcpmOrgMgtTargetWriteup_a);

        for (OrgMgtSubTargetDto subTargetDto : orgMgtTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTarget = new ModelMapper().map(subTargetDto, TfBcpmOrgMgtSubTarget.class);
            tfBcpmOrgMgtSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtSubTarget.setTargetId(tfBcpmOrgMgtTargetActivity.getTargetId());
            tfBcpmOrgMgtSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfBcpmOrgMgtSubTarget.setCreatedBy(currentUser.getUserId());
            tfBcpmOrgMgtSubTarget.setCreatedDate(new Date());
            tfBcpmOrgMgtSubTargetRepository.save(tfBcpmOrgMgtSubTarget);
            TfBcpmOrgMgtSubTarget_a tfBcpmOrgMgtSubTarget_a = new ModelMapper().map(tfBcpmOrgMgtSubTarget, TfBcpmOrgMgtSubTarget_a.class);
            tfBcpmOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtSubTarget_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
            tfBcpmOrgMgtSubTarget_aRepository.save(tfBcpmOrgMgtSubTarget_a);

            //save attachment
            List<MultipartFile> attachedFiles = subTargetDto.getAttachedFiles();
            if (attachedFiles != null) {
                for (MultipartFile multipartFile : attachedFiles) {
                    String filename = multipartFile.getOriginalFilename();
                    Long fileSize = multipartFile.getSize();
                    String fileExtension = Objects.requireNonNull(filename).substring(filename.lastIndexOf(".") + 1, filename.length()).toUpperCase();

                    FileUploadDTO fileUploadDTO = FileUploadToExternalLocation.fileUploadPathRetriever(request);
                    String fileUrl = fileUploadDTO.getUploadFilePath().concat(filename);
                    if (!filename.equals("")) {
                        responseMessage = FileUploadToExternalLocation.fileUploader(multipartFile, filename, "attachFile.properties", request);
                        TfBcpmOrgMgtSubTargetDoc tfBcpmOrgMgtSubTargetDoc = new TfBcpmOrgMgtSubTargetDoc();
                        tfBcpmOrgMgtSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmOrgMgtSubTargetDoc.setSubTargetId(tfBcpmOrgMgtSubTarget.getSubTargetId());
                        tfBcpmOrgMgtSubTargetDoc.setFileName(filename);
                        tfBcpmOrgMgtSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmOrgMgtSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmOrgMgtSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmOrgMgtSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmOrgMgtSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmOrgMgtSubTargetDocRepository.save(tfBcpmOrgMgtSubTargetDoc);
                    }
                }
            }
        }

        TfBcpmOrgMgtTargetStatus tfBcpmOrgMgtTargetStatus = new TfBcpmOrgMgtTargetStatus();
        tfBcpmOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStatus.setTargetId(tfBcpmOrgMgtTargetActivity.getTargetId());
        tfBcpmOrgMgtTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfBcpmOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStatus.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStatusRepository.save(tfBcpmOrgMgtTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
