package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiCusSerListDao;
import com.spring.project.development.voler.dto.CustomerServiceSubTargetDto;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.*;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStage;
import com.spring.project.development.voler.repository.cusService.formulation.dhi.*;
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
public class TfDhiCusSerAddTargetService {
    private final TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository ;
    private final TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository;
    private final TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository;
    private final TfDhiCusSerTargetWriteupRepository tfDhiCusSerTargetWriteupRepository;
    private final TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository;
    private final TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository;
    private final TfDhiCusSerSubTarget_aRepository tfDhiCusSerSubTarget_aRepository;
    private final TfDhiCusSerSubTargetDocRepository tfDhiCusSerSubTargetDocRepository;
    private final TfDhiCusSerListDao tfDhiCusSerListDao;
    private final CommonService commonService;

    public TfDhiCusSerAddTargetService(TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository, TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository, TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository, TfDhiCusSerTargetWriteupRepository tfDhiCusSerTargetWriteupRepository, TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository, TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository, TfDhiCusSerSubTarget_aRepository tfDhiCusSerSubTarget_aRepository, TfDhiCusSerSubTargetDocRepository tfDhiCusSerSubTargetDocRepository, TfDhiCusSerListDao tfDhiCusSerListDao, CommonService commonService) {
        this.tfDhiCusSerTargetStatusRepository = tfDhiCusSerTargetStatusRepository;
        this.tfDhiCusSerTargetActivityRepository = tfDhiCusSerTargetActivityRepository;
        this.tfDhiCusSerTargetActivity_aRepository = tfDhiCusSerTargetActivity_aRepository;
        this.tfDhiCusSerTargetWriteupRepository = tfDhiCusSerTargetWriteupRepository;
        this.tfDhiCusSerTargetWriteup_aRepository = tfDhiCusSerTargetWriteup_aRepository;
        this.tfDhiCusSerSubTargetRepository = tfDhiCusSerSubTargetRepository;
        this.tfDhiCusSerSubTarget_aRepository = tfDhiCusSerSubTarget_aRepository;
        this.tfDhiCusSerSubTargetDocRepository = tfDhiCusSerSubTargetDocRepository;
        this.tfDhiCusSerListDao = tfDhiCusSerListDao;
        this.commonService = commonService;
    }

    private ResponseMessage validateBeforeAdd(CurrentUser currentUser, CustomerServiceTargetDto customerServiceTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = customerServiceTargetDto.getYear();
        String companyId = customerServiceTargetDto.getCompanyId();
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
            TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerListDao.getLatestStatus(year, companyId);
            if (tfDhiCusSerTargetStage != null) {
                if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerListDao.getLatestStatus(year, companyId);
            if (tfDhiCusSerTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is already approved. Please request administrator to add new if it was left out.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addTarget(HttpServletRequest request, CurrentUser currentUser, CustomerServiceTargetDto customerServiceTargetDto) throws IOException {
        ResponseMessage responseMessage = validateBeforeAdd(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivity = new ModelMapper().map(customerServiceTargetDto, TfDhiCusSerTargetActivity.class);
        tfDhiCusSerTargetActivity.setVersionNo(BigInteger.ONE);
        tfDhiCusSerTargetActivity.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiCusSerTargetActivity.setTargetId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetActivity.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetActivity.setCreatedDate(new Date());
        tfDhiCusSerTargetActivityRepository.save(tfDhiCusSerTargetActivity);
        TfDhiCusSerTargetActivity_a tfDhiCusSerTargetActivity_a = new ModelMapper().map(tfDhiCusSerTargetActivity, TfDhiCusSerTargetActivity_a.class);
        tfDhiCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetActivity_aRepository.save(tfDhiCusSerTargetActivity_a);

        TfDhiCusSerTargetWriteup tfDhiCusSerTargetWriteup = new ModelMapper().map(customerServiceTargetDto, TfDhiCusSerTargetWriteup.class);
        tfDhiCusSerTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetWriteup.setTargetId(tfDhiCusSerTargetActivity.getTargetId());
        tfDhiCusSerTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiCusSerTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetWriteup.setCreatedDate(new Date());
        tfDhiCusSerTargetWriteupRepository.save(tfDhiCusSerTargetWriteup);
        TfDhiCusSerTargetWriteup_a tfDhiCusSerTargetWriteup_a = new ModelMapper().map(tfDhiCusSerTargetWriteup, TfDhiCusSerTargetWriteup_a.class);
        tfDhiCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetWriteup_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
        tfDhiCusSerTargetWriteup_aRepository.save(tfDhiCusSerTargetWriteup_a);

        for (CustomerServiceSubTargetDto subTargetDto : customerServiceTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfDhiCusSerSubTarget tfDhiCusSerSubTarget = new ModelMapper().map(subTargetDto, TfDhiCusSerSubTarget.class);
            tfDhiCusSerSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfDhiCusSerSubTarget.setTargetId(tfDhiCusSerTargetActivity.getTargetId());
            tfDhiCusSerSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfDhiCusSerSubTarget.setCreatedBy(currentUser.getUserId());
            tfDhiCusSerSubTarget.setCreatedDate(new Date());
            tfDhiCusSerSubTargetRepository.save(tfDhiCusSerSubTarget);
            TfDhiCusSerSubTarget_a tfDhiCusSerSubTarget_a = new ModelMapper().map(tfDhiCusSerSubTarget, TfDhiCusSerSubTarget_a.class);
            tfDhiCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiCusSerSubTarget_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
            tfDhiCusSerSubTarget_aRepository.save(tfDhiCusSerSubTarget_a);

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
                        TfDhiCusSerSubTargetDoc tfDhiCusSerSubTargetDoc = new TfDhiCusSerSubTargetDoc();
                        tfDhiCusSerSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiCusSerSubTargetDoc.setSubTargetId(tfDhiCusSerSubTarget.getSubTargetId());
                        tfDhiCusSerSubTargetDoc.setFileName(filename);
                        tfDhiCusSerSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiCusSerSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiCusSerSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiCusSerSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiCusSerSubTargetDoc.setCreatedDate(new Date());
                        tfDhiCusSerSubTargetDocRepository.save(tfDhiCusSerSubTargetDoc);
                    }
                }
            }
        }

        TfDhiCusSerTargetStatus tfDhiCusSerTargetStatus = new TfDhiCusSerTargetStatus();
        tfDhiCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStatus.setTargetId(tfDhiCusSerTargetActivity.getTargetId());
        tfDhiCusSerTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfDhiCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStatus.setCreatedDate(new Date());
        tfDhiCusSerTargetStatusRepository.save(tfDhiCusSerTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
