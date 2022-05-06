package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmCusSerListDao;
import com.spring.project.development.voler.dto.CustomerServiceSubTargetDto;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.*;
import com.spring.project.development.voler.repository.cusService.formulation.bcpm.*;
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
public class TfBcpmCusSerAddTargetService {
    private final TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository ;
    private final TfBcpmCusSerTargetActivityRepository tfBcpmCusSerTargetActivityRepository;
    private final TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository;
    private final TfBcpmCusSerTargetWriteupRepository tfBcpmCusSerTargetWriteupRepository;
    private final TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository;
    private final TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository;
    private final TfBcpmCusSerSubTarget_aRepository tfBcpmCusSerSubTarget_aRepository;
    private final TfBcpmCusSerSubTargetDocRepository tfBcpmCusSerSubTargetDocRepository;
    private final TfBcpmCusSerListDao tfBcpmCusSerListDao;
    private final CommonService commonService;

    public TfBcpmCusSerAddTargetService(TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository, TfBcpmCusSerTargetActivityRepository tfBcpmCusSerTargetActivityRepository, TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository, TfBcpmCusSerTargetWriteupRepository tfBcpmCusSerTargetWriteupRepository, TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository, TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository, TfBcpmCusSerSubTarget_aRepository tfBcpmCusSerSubTarget_aRepository, TfBcpmCusSerSubTargetDocRepository tfBcpmCusSerSubTargetDocRepository, TfBcpmCusSerListDao tfBcpmCusSerListDao, CommonService commonService) {
        this.tfBcpmCusSerTargetStatusRepository = tfBcpmCusSerTargetStatusRepository;
        this.tfBcpmCusSerTargetActivityRepository = tfBcpmCusSerTargetActivityRepository;
        this.tfBcpmCusSerTargetActivity_aRepository = tfBcpmCusSerTargetActivity_aRepository;
        this.tfBcpmCusSerTargetWriteupRepository = tfBcpmCusSerTargetWriteupRepository;
        this.tfBcpmCusSerTargetWriteup_aRepository = tfBcpmCusSerTargetWriteup_aRepository;
        this.tfBcpmCusSerSubTargetRepository = tfBcpmCusSerSubTargetRepository;
        this.tfBcpmCusSerSubTarget_aRepository = tfBcpmCusSerSubTarget_aRepository;
        this.tfBcpmCusSerSubTargetDocRepository = tfBcpmCusSerSubTargetDocRepository;
        this.tfBcpmCusSerListDao = tfBcpmCusSerListDao;
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
            TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerListDao.getLatestStatus(year, companyId);
            if (tfBcpmCusSerTargetStage != null) {
                if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerListDao.getLatestStatus(year, companyId);
            if (tfBcpmCusSerTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivity = new ModelMapper().map(customerServiceTargetDto, TfBcpmCusSerTargetActivity.class);
        tfBcpmCusSerTargetActivity.setVersionNo(BigInteger.ONE);
        tfBcpmCusSerTargetActivity.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmCusSerTargetActivity.setTargetId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetActivity.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetActivity.setCreatedDate(new Date());
        tfBcpmCusSerTargetActivityRepository.save(tfBcpmCusSerTargetActivity);
        TfBcpmCusSerTargetActivity_a tfBcpmCusSerTargetActivity_a = new ModelMapper().map(tfBcpmCusSerTargetActivity, TfBcpmCusSerTargetActivity_a.class);
        tfBcpmCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetActivity_aRepository.save(tfBcpmCusSerTargetActivity_a);

        TfBcpmCusSerTargetWriteup tfBcpmCusSerTargetWriteup = new ModelMapper().map(customerServiceTargetDto, TfBcpmCusSerTargetWriteup.class);
        tfBcpmCusSerTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetWriteup.setTargetId(tfBcpmCusSerTargetActivity.getTargetId());
        tfBcpmCusSerTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmCusSerTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetWriteup.setCreatedDate(new Date());
        tfBcpmCusSerTargetWriteupRepository.save(tfBcpmCusSerTargetWriteup);
        TfBcpmCusSerTargetWriteup_a tfBcpmCusSerTargetWriteup_a = new ModelMapper().map(tfBcpmCusSerTargetWriteup, TfBcpmCusSerTargetWriteup_a.class);
        tfBcpmCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetWriteup_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
        tfBcpmCusSerTargetWriteup_aRepository.save(tfBcpmCusSerTargetWriteup_a);

        for (CustomerServiceSubTargetDto subTargetDto : customerServiceTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfBcpmCusSerSubTarget tfBcpmCusSerSubTarget = new ModelMapper().map(subTargetDto, TfBcpmCusSerSubTarget.class);
            tfBcpmCusSerSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfBcpmCusSerSubTarget.setTargetId(tfBcpmCusSerTargetActivity.getTargetId());
            tfBcpmCusSerSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfBcpmCusSerSubTarget.setCreatedBy(currentUser.getUserId());
            tfBcpmCusSerSubTarget.setCreatedDate(new Date());
            tfBcpmCusSerSubTargetRepository.save(tfBcpmCusSerSubTarget);
            TfBcpmCusSerSubTarget_a tfBcpmCusSerSubTarget_a = new ModelMapper().map(tfBcpmCusSerSubTarget, TfBcpmCusSerSubTarget_a.class);
            tfBcpmCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmCusSerSubTarget_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
            tfBcpmCusSerSubTarget_aRepository.save(tfBcpmCusSerSubTarget_a);

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
                        TfBcpmCusSerSubTargetDoc tfBcpmCusSerSubTargetDoc = new TfBcpmCusSerSubTargetDoc();
                        tfBcpmCusSerSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmCusSerSubTargetDoc.setSubTargetId(tfBcpmCusSerSubTarget.getSubTargetId());
                        tfBcpmCusSerSubTargetDoc.setFileName(filename);
                        tfBcpmCusSerSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmCusSerSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmCusSerSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmCusSerSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmCusSerSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmCusSerSubTargetDocRepository.save(tfBcpmCusSerSubTargetDoc);
                    }
                }
            }
        }

        TfBcpmCusSerTargetStatus tfBcpmCusSerTargetStatus = new TfBcpmCusSerTargetStatus();
        tfBcpmCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStatus.setTargetId(tfBcpmCusSerTargetActivity.getTargetId());
        tfBcpmCusSerTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfBcpmCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStatus.setCreatedDate(new Date());
        tfBcpmCusSerTargetStatusRepository.save(tfBcpmCusSerTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
