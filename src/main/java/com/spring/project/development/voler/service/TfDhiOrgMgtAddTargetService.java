package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiOrgMgtListDao;
import com.spring.project.development.voler.dto.OrgMgtSubTargetDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.*;
import com.spring.project.development.voler.repository.orgMgt.formulation.dhi.*;
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
public class TfDhiOrgMgtAddTargetService {
    private final TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository;
    private final TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository;
    private final TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository;
    private final TfDhiOrgMgtTargetWriteupRepository tfDhiOrgMgtTargetWriteupRepository;
    private final TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository;
    private final TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository;
    private final TfDhiOrgMgtSubTarget_aRepository tfDhiOrgMgtSubTarget_aRepository;
    private final TfDhiOrgMgtSubTargetDocRepository tfDhiOrgMgtSubTargetDocRepository;
    private final TfDhiOrgMgtListDao tfDhiOrgMgtListDao;
    private final CommonService commonService;

    public TfDhiOrgMgtAddTargetService(TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository, TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository, TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository, TfDhiOrgMgtTargetWriteupRepository tfDhiOrgMgtTargetWriteupRepository, TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository, TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository, TfDhiOrgMgtSubTarget_aRepository tfDhiOrgMgtSubTarget_aRepository, TfDhiOrgMgtSubTargetDocRepository tfDhiOrgMgtSubTargetDocRepository, TfDhiOrgMgtListDao tfDhiOrgMgtListDao, CommonService commonService) {
        this.tfDhiOrgMgtTargetStatusRepository = tfDhiOrgMgtTargetStatusRepository;
        this.tfDhiOrgMgtTargetActivityRepository = tfDhiOrgMgtTargetActivityRepository;
        this.tfDhiOrgMgtTargetActivity_aRepository = tfDhiOrgMgtTargetActivity_aRepository;
        this.tfDhiOrgMgtTargetWriteupRepository = tfDhiOrgMgtTargetWriteupRepository;
        this.tfDhiOrgMgtTargetWriteup_aRepository = tfDhiOrgMgtTargetWriteup_aRepository;
        this.tfDhiOrgMgtSubTargetRepository = tfDhiOrgMgtSubTargetRepository;
        this.tfDhiOrgMgtSubTarget_aRepository = tfDhiOrgMgtSubTarget_aRepository;
        this.tfDhiOrgMgtSubTargetDocRepository = tfDhiOrgMgtSubTargetDocRepository;
        this.tfDhiOrgMgtListDao = tfDhiOrgMgtListDao;
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
            TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtListDao.getLatestStatus(year, companyId);
            if (tfDhiOrgMgtTargetStage != null) {
                if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtListDao.getLatestStatus(year, companyId);
            if (tfDhiOrgMgtTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivity = new ModelMapper().map(orgMgtTargetDto, TfDhiOrgMgtTargetActivity.class);
        tfDhiOrgMgtTargetActivity.setVersionNo(BigInteger.ONE);
        tfDhiOrgMgtTargetActivity.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiOrgMgtTargetActivity.setTargetId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetActivity.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetActivity.setCreatedDate(new Date());
        tfDhiOrgMgtTargetActivityRepository.save(tfDhiOrgMgtTargetActivity);
        TfDhiOrgMgtTargetActivity_a tfDhiOrgMgtTargetActivity_a = new ModelMapper().map(tfDhiOrgMgtTargetActivity, TfDhiOrgMgtTargetActivity_a.class);
        tfDhiOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetActivity_aRepository.save(tfDhiOrgMgtTargetActivity_a);

        TfDhiOrgMgtTargetWriteup tfDhiOrgMgtTargetWriteup = new ModelMapper().map(orgMgtTargetDto, TfDhiOrgMgtTargetWriteup.class);
        tfDhiOrgMgtTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetWriteup.setTargetId(tfDhiOrgMgtTargetActivity.getTargetId());
        tfDhiOrgMgtTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiOrgMgtTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetWriteup.setCreatedDate(new Date());
        tfDhiOrgMgtTargetWriteupRepository.save(tfDhiOrgMgtTargetWriteup);
        TfDhiOrgMgtTargetWriteup_a tfDhiOrgMgtTargetWriteup_a = new ModelMapper().map(tfDhiOrgMgtTargetWriteup, TfDhiOrgMgtTargetWriteup_a.class);
        tfDhiOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetWriteup_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
        tfDhiOrgMgtTargetWriteup_aRepository.save(tfDhiOrgMgtTargetWriteup_a);

        for (OrgMgtSubTargetDto subTargetDto : orgMgtTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTarget = new ModelMapper().map(subTargetDto, TfDhiOrgMgtSubTarget.class);
            tfDhiOrgMgtSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfDhiOrgMgtSubTarget.setTargetId(tfDhiOrgMgtTargetActivity.getTargetId());
            tfDhiOrgMgtSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfDhiOrgMgtSubTarget.setCreatedBy(currentUser.getUserId());
            tfDhiOrgMgtSubTarget.setCreatedDate(new Date());
            tfDhiOrgMgtSubTargetRepository.save(tfDhiOrgMgtSubTarget);
            TfDhiOrgMgtSubTarget_a tfDhiOrgMgtSubTarget_a = new ModelMapper().map(tfDhiOrgMgtSubTarget, TfDhiOrgMgtSubTarget_a.class);
            tfDhiOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiOrgMgtSubTarget_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
            tfDhiOrgMgtSubTarget_aRepository.save(tfDhiOrgMgtSubTarget_a);

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
                        TfDhiOrgMgtSubTargetDoc tfDhiOrgMgtSubTargetDoc = new TfDhiOrgMgtSubTargetDoc();
                        tfDhiOrgMgtSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiOrgMgtSubTargetDoc.setSubTargetId(tfDhiOrgMgtSubTarget.getSubTargetId());
                        tfDhiOrgMgtSubTargetDoc.setFileName(filename);
                        tfDhiOrgMgtSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiOrgMgtSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiOrgMgtSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiOrgMgtSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiOrgMgtSubTargetDoc.setCreatedDate(new Date());
                        tfDhiOrgMgtSubTargetDocRepository.save(tfDhiOrgMgtSubTargetDoc);
                    }
                }
            }
        }

        TfDhiOrgMgtTargetStatus tfDhiOrgMgtTargetStatus = new TfDhiOrgMgtTargetStatus();
        tfDhiOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStatus.setTargetId(tfDhiOrgMgtTargetActivity.getTargetId());
        tfDhiOrgMgtTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfDhiOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStatus.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStatusRepository.save(tfDhiOrgMgtTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
