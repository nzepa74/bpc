package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmProdSaleListDao;
import com.spring.project.development.voler.dto.ProdSaleSubTargetDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.*;
import com.spring.project.development.voler.repository.prodSale.formulation.bcpm.*;
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
public class TfBcpmProdSaleAddTargetService {
    private final TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository;
    private final TfBcpmProdSaleTargetRepository tfBcpmProdSaleTargetRepository;
    private final TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository;
    private final TfBcpmProdSaleTargetWriteupRepository tfBcpmProdSaleTargetWriteupRepository;
    private final TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository;
    private final TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository;
    private final TfBcpmProdSaleSubTarget_aRepository tfBcpmProdSaleSubTarget_aRepository;
    private final TfBcpmProdSaleSubTargetDocRepository tfBcpmProdSaleSubTargetDocRepository;
    private final TfBcpmProdSaleListDao tfBcpmProdSaleListDao;
    private final CommonService commonService;

    public TfBcpmProdSaleAddTargetService(TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository, TfBcpmProdSaleTargetRepository tfBcpmProdSaleTargetRepository, TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository, TfBcpmProdSaleTargetWriteupRepository tfBcpmProdSaleTargetWriteupRepository, TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository, TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository, TfBcpmProdSaleSubTarget_aRepository tfBcpmProdSaleSubTarget_aRepository, TfBcpmProdSaleSubTargetDocRepository tfBcpmProdSaleSubTargetDocRepository, TfBcpmProdSaleListDao tfBcpmProdSaleListDao, CommonService commonService) {
        this.tfBcpmProdSaleTargetStatusRepository = tfBcpmProdSaleTargetStatusRepository;
        this.tfBcpmProdSaleTargetRepository = tfBcpmProdSaleTargetRepository;
        this.tfBcpmProdSaleTarget_aRepository = tfBcpmProdSaleTarget_aRepository;
        this.tfBcpmProdSaleTargetWriteupRepository = tfBcpmProdSaleTargetWriteupRepository;
        this.tfBcpmProdSaleTargetWriteup_aRepository = tfBcpmProdSaleTargetWriteup_aRepository;
        this.tfBcpmProdSaleSubTargetRepository = tfBcpmProdSaleSubTargetRepository;
        this.tfBcpmProdSaleSubTarget_aRepository = tfBcpmProdSaleSubTarget_aRepository;
        this.tfBcpmProdSaleSubTargetDocRepository = tfBcpmProdSaleSubTargetDocRepository;
        this.tfBcpmProdSaleListDao = tfBcpmProdSaleListDao;
        this.commonService = commonService;
    }

    private ResponseMessage validateBeforeAdd(CurrentUser currentUser, ProdSaleTargetDto prodSaleTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = prodSaleTargetDto.getYear();
        String companyId = prodSaleTargetDto.getCompanyId();
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
            TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleListDao.getLatestStatus(year, companyId);
            if (tfBcpmProdSaleTargetStage != null) {
                if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleListDao.getLatestStatus(year, companyId);
            if (tfBcpmProdSaleTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is already approved. Please request administrator to add new if it was left out.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addTarget(HttpServletRequest request, CurrentUser currentUser, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        ResponseMessage responseMessage = validateBeforeAdd(currentUser, prodSaleTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfBcpmProdSaleTarget tfBcpmProdSaleTarget = new ModelMapper().map(prodSaleTargetDto, TfBcpmProdSaleTarget.class);
        tfBcpmProdSaleTarget.setVersionNo(BigInteger.ONE);
        tfBcpmProdSaleTarget.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmProdSaleTarget.setTargetId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTarget.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTarget.setCreatedDate(new Date());
        tfBcpmProdSaleTargetRepository.save(tfBcpmProdSaleTarget);
        TfBcpmProdSaleTarget_a tfBcpmProdSaleTarget_a = new ModelMapper().map(tfBcpmProdSaleTarget, TfBcpmProdSaleTarget_a.class);
        tfBcpmProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTarget_aRepository.save(tfBcpmProdSaleTarget_a);

        TfBcpmProdSaleTargetWriteup tfBcpmProdSaleTargetWriteup = new ModelMapper().map(prodSaleTargetDto, TfBcpmProdSaleTargetWriteup.class);
        tfBcpmProdSaleTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetWriteup.setTargetId(tfBcpmProdSaleTarget.getTargetId());
        tfBcpmProdSaleTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmProdSaleTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetWriteup.setCreatedDate(new Date());
        tfBcpmProdSaleTargetWriteupRepository.save(tfBcpmProdSaleTargetWriteup);
        TfBcpmProdSaleTargetWriteup_a tfBcpmProdSaleTargetWriteup_a = new ModelMapper().map(tfBcpmProdSaleTargetWriteup, TfBcpmProdSaleTargetWriteup_a.class);
        tfBcpmProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetWriteup_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
        tfBcpmProdSaleTargetWriteup_aRepository.save(tfBcpmProdSaleTargetWriteup_a);

        for (ProdSaleSubTargetDto subTargetDto : prodSaleTargetDto.getSubTargetDtos()) {
            TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTarget = new ModelMapper().map(subTargetDto, TfBcpmProdSaleSubTarget.class);
            tfBcpmProdSaleSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfBcpmProdSaleSubTarget.setTargetId(tfBcpmProdSaleTarget.getTargetId());
            tfBcpmProdSaleSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfBcpmProdSaleSubTarget.setCreatedBy(currentUser.getUserId());
            tfBcpmProdSaleSubTarget.setCreatedDate(new Date());
            tfBcpmProdSaleSubTargetRepository.save(tfBcpmProdSaleSubTarget);
            TfBcpmProdSaleSubTarget_a tfBcpmProdSaleSubTarget_a = new ModelMapper().map(tfBcpmProdSaleSubTarget, TfBcpmProdSaleSubTarget_a.class);
            tfBcpmProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmProdSaleSubTarget_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
            tfBcpmProdSaleSubTarget_aRepository.save(tfBcpmProdSaleSubTarget_a);

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
                        TfBcpmProdSaleSubTargetDoc tfBcpmProdSaleSubTargetDoc = new TfBcpmProdSaleSubTargetDoc();
                        tfBcpmProdSaleSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmProdSaleSubTargetDoc.setSubTargetId(tfBcpmProdSaleSubTarget.getSubTargetId());
                        tfBcpmProdSaleSubTargetDoc.setFileName(filename);
                        tfBcpmProdSaleSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmProdSaleSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmProdSaleSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmProdSaleSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmProdSaleSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmProdSaleSubTargetDocRepository.save(tfBcpmProdSaleSubTargetDoc);
                    }
                }
            }
        }

        TfBcpmProdSaleTargetStatus tfBcpmProdSaleTargetStatus = new TfBcpmProdSaleTargetStatus();
        tfBcpmProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStatus.setTargetId(tfBcpmProdSaleTarget.getTargetId());
        tfBcpmProdSaleTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfBcpmProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStatus.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStatusRepository.save(tfBcpmProdSaleTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
