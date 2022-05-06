package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiProdSaleListDao;
import com.spring.project.development.voler.dto.ProdSaleSubTargetDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.*;
import com.spring.project.development.voler.repository.prodSale.formulation.dhi.*;
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
public class TfDhiProdSaleAddTargetService {
    private final TfDhiProdSaleTargetStatusRepository tfDhiProdSaleTargetStatusRepository;
    private final TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository;
    private final TfDhiProdSaleTarget_aRepository tfDhiProdSaleTarget_aRepository;
    private final TfDhiProdSaleTargetWriteupRepository tfDhiProdSaleTargetWriteupRepository;
    private final TfDhiProdSaleTargetWriteup_aRepository tfDhiProdSaleTargetWriteup_aRepository;
    private final TfDhiProdSaleSubTargetRepository tfDhiProdSaleSubTargetRepository;
    private final TfDhiProdSaleSubTarget_aRepository tfDhiProdSaleSubTarget_aRepository;
    private final TfDhiProdSaleSubTargetDocRepository tfDhiProdSaleSubTargetDocRepository;
    private final TfDhiProdSaleListDao tfDhiProdSaleListDao;
    private final CommonService commonService;

    public TfDhiProdSaleAddTargetService(TfDhiProdSaleTargetStatusRepository tfDhiProdSaleTargetStatusRepository, TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository, TfDhiProdSaleTarget_aRepository tfDhiProdSaleTarget_aRepository, TfDhiProdSaleTargetWriteupRepository tfDhiProdSaleTargetWriteupRepository, TfDhiProdSaleTargetWriteup_aRepository tfDhiProdSaleTargetWriteup_aRepository, TfDhiProdSaleSubTargetRepository tfDhiProdSaleSubTargetRepository, TfDhiProdSaleSubTarget_aRepository tfDhiProdSaleSubTarget_aRepository, TfDhiProdSaleSubTargetDocRepository tfDhiProdSaleSubTargetDocRepository, TfDhiProdSaleListDao tfDhiProdSaleListDao, CommonService commonService) {
        this.tfDhiProdSaleTargetStatusRepository = tfDhiProdSaleTargetStatusRepository;
        this.tfDhiProdSaleTargetRepository = tfDhiProdSaleTargetRepository;
        this.tfDhiProdSaleTarget_aRepository = tfDhiProdSaleTarget_aRepository;
        this.tfDhiProdSaleTargetWriteupRepository = tfDhiProdSaleTargetWriteupRepository;
        this.tfDhiProdSaleTargetWriteup_aRepository = tfDhiProdSaleTargetWriteup_aRepository;
        this.tfDhiProdSaleSubTargetRepository = tfDhiProdSaleSubTargetRepository;
        this.tfDhiProdSaleSubTarget_aRepository = tfDhiProdSaleSubTarget_aRepository;
        this.tfDhiProdSaleSubTargetDocRepository = tfDhiProdSaleSubTargetDocRepository;
        this.tfDhiProdSaleListDao = tfDhiProdSaleListDao;
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
            TfDhiProdSaleTargetStage tfDhiProdSaleTargetStage = tfDhiProdSaleListDao.getLatestStatus(year, companyId);
            if (tfDhiProdSaleTargetStage != null) {
                if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("Failed to save because target already submitted.");
                    return responseMessage;
                }
                if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfDhiProdSaleTargetStage tfDhiProdSaleTargetStage = tfDhiProdSaleListDao.getLatestStatus(year, companyId);
            if (tfDhiProdSaleTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target not submitted yet. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Failed to save because target is reverted. You will only able to add once the target is submitted by company.");
                return responseMessage;
            }
            if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiProdSaleTarget tfDhiProdSaleTarget = new ModelMapper().map(prodSaleTargetDto, TfDhiProdSaleTarget.class);
        tfDhiProdSaleTarget.setVersionNo(BigInteger.ONE);
        tfDhiProdSaleTarget.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiProdSaleTarget.setTargetId(UuidGenerator.generateUuid());
        tfDhiProdSaleTarget.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleTarget.setCreatedDate(new Date());
        tfDhiProdSaleTargetRepository.save(tfDhiProdSaleTarget);
        TfDhiProdSaleTarget_a tfDhiProdSaleTarget_a = new ModelMapper().map(tfDhiProdSaleTarget, TfDhiProdSaleTarget_a.class);
        tfDhiProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTarget_aRepository.save(tfDhiProdSaleTarget_a);

        TfDhiProdSaleTargetWriteup tfDhiProdSaleTargetWriteup = new ModelMapper().map(prodSaleTargetDto, TfDhiProdSaleTargetWriteup.class);
        tfDhiProdSaleTargetWriteup.setWriteupId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetWriteup.setTargetId(tfDhiProdSaleTarget.getTargetId());
        tfDhiProdSaleTargetWriteup.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiProdSaleTargetWriteup.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetWriteup.setCreatedDate(new Date());
        tfDhiProdSaleTargetWriteupRepository.save(tfDhiProdSaleTargetWriteup);
        TfDhiProdSaleTargetWriteup_a tfDhiProdSaleTargetWriteup_a = new ModelMapper().map(tfDhiProdSaleTargetWriteup, TfDhiProdSaleTargetWriteup_a.class);
        tfDhiProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetWriteup_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
        tfDhiProdSaleTargetWriteup_aRepository.save(tfDhiProdSaleTargetWriteup_a);

        for (ProdSaleSubTargetDto subTargetDto : prodSaleTargetDto.getSubTargetDtos()) {
            TfDhiProdSaleSubTarget tfDhiProdSaleSubTarget = new ModelMapper().map(subTargetDto, TfDhiProdSaleSubTarget.class);
            tfDhiProdSaleSubTarget.setSubTargetId(UuidGenerator.generateUuid());
            tfDhiProdSaleSubTarget.setTargetId(tfDhiProdSaleTarget.getTargetId());
            tfDhiProdSaleSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            tfDhiProdSaleSubTarget.setCreatedBy(currentUser.getUserId());
            tfDhiProdSaleSubTarget.setCreatedDate(new Date());
            tfDhiProdSaleSubTargetRepository.save(tfDhiProdSaleSubTarget);
            TfDhiProdSaleSubTarget_a tfDhiProdSaleSubTarget_a = new ModelMapper().map(tfDhiProdSaleSubTarget, TfDhiProdSaleSubTarget_a.class);
            tfDhiProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiProdSaleSubTarget_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
            tfDhiProdSaleSubTarget_aRepository.save(tfDhiProdSaleSubTarget_a);

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
                        TfDhiProdSaleSubTargetDoc tfDhiProdSaleSubTargetDoc = new TfDhiProdSaleSubTargetDoc();
                        tfDhiProdSaleSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiProdSaleSubTargetDoc.setSubTargetId(tfDhiProdSaleSubTarget.getSubTargetId());
                        tfDhiProdSaleSubTargetDoc.setFileName(filename);
                        tfDhiProdSaleSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiProdSaleSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiProdSaleSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiProdSaleSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiProdSaleSubTargetDoc.setCreatedDate(new Date());
                        tfDhiProdSaleSubTargetDocRepository.save(tfDhiProdSaleSubTargetDoc);
                    }
                }
            }
        }

        TfDhiProdSaleTargetStatus tfDhiProdSaleTargetStatus = new TfDhiProdSaleTargetStatus();
        tfDhiProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetStatus.setTargetId(tfDhiProdSaleTarget.getTargetId());
        tfDhiProdSaleTargetStatus.setStatusFlag('C');// R= Reopen,X=Closed,C=Created
        tfDhiProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetStatus.setCreatedDate(new Date());
        tfDhiProdSaleTargetStatusRepository.save(tfDhiProdSaleTargetStatus);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }
}
