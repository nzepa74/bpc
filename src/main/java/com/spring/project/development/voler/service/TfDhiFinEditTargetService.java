package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiFinEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.financial.FinTargetComment;
import com.spring.project.development.voler.entity.financial.formulation.dhi.*;
import com.spring.project.development.voler.repository.financial.FinTargetCommentRepository;
import com.spring.project.development.voler.repository.financial.formulation.dhi.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created By zepaG on 3/21/2022.
 */
@Service
public class TfDhiFinEditTargetService {
    private final TfDhiFinTargetRepository tfDhiFinTargetRepository;
    private final TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository;
    private final TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository;
    private final TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository;
    private final TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository;
    private final TfDhiFinTargetReviewerRemarkRepository tfDhiFinTargetReviewerRemarkRepository;
    private final FinTargetCommentRepository finTargetCommentRepository;
    private final TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository;
    private final TfDhiFinEditTargetDao tfDhiFinEditTargetDao;
    private final TfDhiFinTargetStageRepository tfDhiFinTargetStageRepository;
    private final CommonService commonService;

    public TfDhiFinEditTargetService(TfDhiFinTargetRepository tfDhiFinTargetRepository, TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository, TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository, TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository, TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository, TfDhiFinTargetReviewerRemarkRepository tfDhiFinTargetReviewerRemarkRepository, FinTargetCommentRepository finTargetCommentRepository, TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository, TfDhiFinEditTargetDao tfDhiFinEditTargetDao, TfDhiFinTargetStageRepository tfDhiFinTargetStageRepository, CommonService commonService) {
        this.tfDhiFinTargetRepository = tfDhiFinTargetRepository;
        this.tfDhiFinTarget_aRepository = tfDhiFinTarget_aRepository;
        this.tfDhiFinTargetWriteupRepository = tfDhiFinTargetWriteupRepository;
        this.tfDhiFinTargetWriteup_aRepository = tfDhiFinTargetWriteup_aRepository;
        this.tfDhiFinTargetDocRepository = tfDhiFinTargetDocRepository;
        this.tfDhiFinTargetReviewerRemarkRepository = tfDhiFinTargetReviewerRemarkRepository;
        this.finTargetCommentRepository = finTargetCommentRepository;
        this.tfDhiFinTargetStatusRepository = tfDhiFinTargetStatusRepository;
        this.tfDhiFinEditTargetDao = tfDhiFinEditTargetDao;
        this.tfDhiFinTargetStageRepository = tfDhiFinTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        FinancialTargetDto financialTargetDto = tfDhiFinEditTargetDao.getTarget(targetAuditId);
        if (financialTargetDto != null) {
            responseMessage.setDTO(financialTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfDhiFinEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiFinTarget_a tfDhiFinTarget_a = tfDhiFinTarget_aRepository.findByTargetAuditId(targetAuditId);
        TfDhiFinTargetWriteup_a tfDhiFinTargetWriteup_a = tfDhiFinTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiFinTarget_a != null) {
            responseMessage.setDTO(tfDhiFinTarget_a);
            responseMessage.setWriteupDto(tfDhiFinTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfDhiFinEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiFinTargetDoc> tfDhiFinTargetDocs = tfDhiFinTargetDocRepository.findByTargetId(targetId);
        if (tfDhiFinTargetDocs.size() > 0) {
            responseMessage.setDTO(tfDhiFinTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiFinTargetDoc documentDb = tfDhiFinTargetDocRepository.findByFileId(fileId);
        if (documentDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        FinancialTargetDto financialTargetDto = new FinancialTargetDto();
        financialTargetDto.setTargetId(documentDb.getTargetId());

        TfDhiFinTarget tfDhiFinTargetDb = tfDhiFinTargetRepository.findByTargetId(financialTargetDto.getTargetId());
        financialTargetDto.setCompanyId(tfDhiFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfDhiFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiFinTargetDoc document = new TfDhiFinTargetDoc();
        document.setFileId(fileId);
        tfDhiFinTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfDhiFinTargetDoc document = tfDhiFinTargetDocRepository.findByFileId(fileId);
        String uploadFilePath = document.getFileUrl();
        String fileName = document.getFileName();

        responseMessage = FileUploadToExternalLocation.viewFile(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(document);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;

    }

    public ResponseMessage downloadFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfDhiFinTargetDoc document = tfDhiFinTargetDocRepository.findByFileId(fileId);
        String uploadFilePath = document.getFileUrl();
        String fileName = document.getFileName();

        responseMessage = FileUploadToExternalLocation.fileDownloader(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(document);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage getRemark(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfDhiFinTargetReviewerRemark tfDhiFinTargetReviewerRemark
                = tfDhiFinTargetReviewerRemarkRepository.findByTargetId(targetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfDhiFinTargetReviewerRemark != null) {
            responseMessage.setDTO(tfDhiFinTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfDhiFinTargetReviewerRemark tfDhiFinTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfDhiFinTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfDhiFinTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiFinTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfDhiFinTargetReviewerRemark.setCreatedDate(new Date());
        tfDhiFinTargetReviewerRemarkRepository.save(tfDhiFinTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfDhiFinTargetReviewerRemark tfDhiFinTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfDhiFinTargetReviewerRemark tfDhiFinTargetReviewerRemarkDb
                = tfDhiFinTargetReviewerRemarkRepository.findByRemarkId(tfDhiFinTargetReviewerRemark.getRemarkId());
        tfDhiFinTargetReviewerRemark.setCreatedBy(tfDhiFinTargetReviewerRemarkDb.getCreatedBy());
        tfDhiFinTargetReviewerRemark.setCreatedDate(tfDhiFinTargetReviewerRemarkDb.getCreatedDate());
        tfDhiFinTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiFinTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfDhiFinTargetReviewerRemark.setUpdatedDate(new Date());
        tfDhiFinTargetReviewerRemarkRepository.save(tfDhiFinTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addComment(CurrentUser currentUser, FinTargetComment finTargetComment) {
        ResponseMessage responseMessage = new ResponseMessage();
        finTargetComment.setCommentId(UuidGenerator.generateUuid());
        finTargetComment.setCreatedBy(currentUser.getUserId());
        finTargetComment.setCreatedDate(new Date());
        finTargetCommentRepository.save(finTargetComment);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage closeTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        Integer myRoleId = currentUser.getRoleId();
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiFinTargetStatus dhiFinTargetStatus = new TfDhiFinTargetStatus();
        dhiFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        dhiFinTargetStatus.setTargetId(targetId);
        dhiFinTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        dhiFinTargetStatus.setCreatedBy(currentUser.getUserId());
        dhiFinTargetStatus.setCreatedDate(new Date());
        tfDhiFinTargetStatusRepository.save(dhiFinTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Closed successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage reopenTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        Integer myRoleId = currentUser.getRoleId();
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiFinTargetStatus dhiFinTargetStatus = new TfDhiFinTargetStatus();
        dhiFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        dhiFinTargetStatus.setTargetId(targetId);
        dhiFinTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        dhiFinTargetStatus.setCreatedBy(currentUser.getUserId());
        dhiFinTargetStatus.setCreatedDate(new Date());
        tfDhiFinTargetStatusRepository.save(dhiFinTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiFinTarget tfDhiFinTargetDb = tfDhiFinTargetRepository.findByTargetId(targetId);
        if (tfDhiFinTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        FinancialTargetDto financialTargetDto = new FinancialTargetDto();
        financialTargetDto.setTargetId(targetId);
        financialTargetDto.setCompanyId(tfDhiFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfDhiFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiFinTarget tfDhiFinTarget = new TfDhiFinTarget();
        tfDhiFinTarget.setTargetId(targetId);
//        tfDhiFinTargetRepository.delete(tfDhiFinTarget);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, FinancialTargetDto financialTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiFinTarget tfDhiFinTargetDb = tfDhiFinTargetRepository.findByTargetId(financialTargetDto.getTargetId());
        if (tfDhiFinTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        financialTargetDto.setCompanyId(tfDhiFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfDhiFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        //if creator set dhi proposal from tfDhiFinTargetDb
        int myRoleId = currentUser.getRoleId();
        int creator = SystemRoles.Creator.getValue();

        TfDhiFinTarget tfDhiFinTarget = new ModelMapper().map(financialTargetDto, TfDhiFinTarget.class);
        if (myRoleId == creator) {
            tfDhiFinTarget.setCurYearDhiProposal(tfDhiFinTargetDb.getCurYearDhiProposal());
        }
        tfDhiFinTarget.setYear(tfDhiFinTargetDb.getYear());
        tfDhiFinTarget.setCompanyId(tfDhiFinTargetDb.getCompanyId());
        BigInteger versionNo = getTargetVersionNo(financialTargetDto.getTargetId());
        tfDhiFinTarget.setVersionNo(versionNo);
        tfDhiFinTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiFinTarget.setCreatedBy(tfDhiFinTargetDb.getCreatedBy());
        tfDhiFinTarget.setCreatedDate(tfDhiFinTargetDb.getCreatedDate());
        tfDhiFinTarget.setUpdatedBy(currentUser.getUserId());
        tfDhiFinTarget.setUpdatedDate(new Date());
        tfDhiFinTargetRepository.save(tfDhiFinTarget);

        TfDhiFinTarget_a tfDhiFinTarget_a = new ModelMapper().map(tfDhiFinTarget, TfDhiFinTarget_a.class);
        tfDhiFinTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiFinTarget_aRepository.save(tfDhiFinTarget_a);

        TfDhiFinTargetWriteup tfDhiFinTargetWriteupDb = tfDhiFinTargetWriteupRepository.findByTargetId(financialTargetDto.getTargetId());
        TfDhiFinTargetWriteup tfDhiFinTargetWriteup = new ModelMapper().map(financialTargetDto, TfDhiFinTargetWriteup.class);
        tfDhiFinTargetWriteup.setWriteupId(tfDhiFinTargetWriteupDb.getWriteupId());
        tfDhiFinTargetWriteup.setTargetId(tfDhiFinTarget.getTargetId());
        tfDhiFinTargetWriteup.setCreatedBy(tfDhiFinTargetWriteupDb.getCreatedBy());
        tfDhiFinTargetWriteup.setCreatedDate(tfDhiFinTargetWriteupDb.getCreatedDate());
        tfDhiFinTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiFinTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfDhiFinTargetWriteup.setUpdatedDate(new Date());
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

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        responseMessage.setTargetAuditId(tfDhiFinTarget_a.getTargetAuditId());
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfDhiFinEditTargetDao.getTargetVersionNo(targetId);
        versionNo = versionNo == null ? BigInteger.ZERO : versionNo;
        return versionNo.add(BigInteger.ONE);
    }

    private ResponseMessage validateBeforeEdit(CurrentUser currentUser, FinancialTargetDto financialTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = financialTargetDto.getYear();
        String companyId = financialTargetDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        Integer creatorId = SystemRoles.Creator.getValue();
        Integer reviewerId = SystemRoles.Reviewer.getValue();
        String myCompanyId = currentUser.getCompanyId();
        String targetId = financialTargetDto.getTargetId();
        TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfDhiFinTargetStatus tfDhiFinTargetStatus = tfDhiFinTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
        //only creator, reviewer and admin should able to add
        if (!myRoleId.equals(SystemRoles.Creator.getValue()) && !myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        //if myRoleId is creator, my companyId must equal to selected companyId, latest stage status must be null or R=Reverted
        if (myRoleId.equals(creatorId)) {
            if (!companyId.equals(myCompanyId)) {
                responseMessage.setText("Failed to save due to miss match of company.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
            if (tfDhiFinTargetStage != null) {
                if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfDhiFinTargetStatus.getStatusFlag() == 'X') {
                responseMessage.setText("You are not permitted to edit/delete because this target is closed. Please request reviewer to make changes.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
        }
        //if myRoleId is Reviewer, companyId must exist in company mapping for reviewer id, latest stage status must be S=Submitted
        else if (myRoleId.equals(reviewerId)) {
            //check if it is reviewer belongs to selected company or not
            String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
            if (mappedCompanyId == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because selected company is not mapped to you.");
                return responseMessage;
            }
            // latest stage status must be S=Reverted
            if (tfDhiFinTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
