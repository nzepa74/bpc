package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmFinEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.financial.FinTargetComment;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.*;
import com.spring.project.development.voler.repository.financial.FinTargetCommentRepository;
import com.spring.project.development.voler.repository.financial.formulation.bcpm.*;
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
public class TfBcpmFinEditTargetService {
    private final TfBcpmFinTargetRepository tfBcpmFinTargetRepository;
    private final TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository;
    private final TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository;
    private final TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository;
    private final TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository;
    private final TfBcpmFinTargetReviewerRemarkRepository tfBcpmFinTargetReviewerRemarkRepository;
    private final FinTargetCommentRepository finTargetCommentRepository;
    private final TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository;
    private final TfBcpmFinEditTargetDao tfBcpmFinEditTargetDao;
    private final TfBcpmFinTargetStageRepository tfBcpmFinTargetStageRepository;
    private final CommonService commonService;

    public TfBcpmFinEditTargetService(TfBcpmFinTargetRepository tfBcpmFinTargetRepository, TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository, TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository, TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository, TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository, TfBcpmFinTargetReviewerRemarkRepository tfBcpmFinTargetReviewerRemarkRepository, FinTargetCommentRepository finTargetCommentRepository, TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository, TfBcpmFinEditTargetDao tfBcpmFinEditTargetDao, TfBcpmFinTargetStageRepository tfBcpmFinTargetStageRepository, CommonService commonService) {
        this.tfBcpmFinTargetRepository = tfBcpmFinTargetRepository;
        this.tfBcpmFinTarget_aRepository = tfBcpmFinTarget_aRepository;
        this.tfBcpmFinTargetWriteupRepository = tfBcpmFinTargetWriteupRepository;
        this.tfBcpmFinTargetWriteup_aRepository = tfBcpmFinTargetWriteup_aRepository;
        this.tfBcpmFinTargetDocRepository = tfBcpmFinTargetDocRepository;
        this.tfBcpmFinTargetReviewerRemarkRepository = tfBcpmFinTargetReviewerRemarkRepository;
        this.finTargetCommentRepository = finTargetCommentRepository;
        this.tfBcpmFinTargetStatusRepository = tfBcpmFinTargetStatusRepository;
        this.tfBcpmFinEditTargetDao = tfBcpmFinEditTargetDao;
        this.tfBcpmFinTargetStageRepository = tfBcpmFinTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        FinancialTargetDto financialTargetDto = tfBcpmFinEditTargetDao.getTarget(targetAuditId);
        if (financialTargetDto != null) {
            responseMessage.setDTO(financialTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfBcpmFinEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmFinTarget_a tfBcpmFinTarget_a = tfBcpmFinTarget_aRepository.findByTargetAuditId(targetAuditId);
        TfBcpmFinTargetWriteup_a tfBcpmFinTargetWriteup_a = tfBcpmFinTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmFinTarget_a != null) {
            responseMessage.setDTO(tfBcpmFinTarget_a);
            responseMessage.setWriteupDto(tfBcpmFinTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfBcpmFinEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmFinTargetDoc> tfBcpmFinTargetDocs = tfBcpmFinTargetDocRepository.findByTargetId(targetId);
        if (tfBcpmFinTargetDocs.size() > 0) {
            responseMessage.setDTO(tfBcpmFinTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmFinTargetDoc documentDb = tfBcpmFinTargetDocRepository.findByFileId(fileId);
        if (documentDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        FinancialTargetDto financialTargetDto = new FinancialTargetDto();
        financialTargetDto.setTargetId(documentDb.getTargetId());

        TfBcpmFinTarget tfBcpmFinTargetDb = tfBcpmFinTargetRepository.findByTargetId(financialTargetDto.getTargetId());
        financialTargetDto.setCompanyId(tfBcpmFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfBcpmFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmFinTargetDoc document = new TfBcpmFinTargetDoc();
        document.setFileId(fileId);
        tfBcpmFinTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfBcpmFinTargetDoc document = tfBcpmFinTargetDocRepository.findByFileId(fileId);
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
        TfBcpmFinTargetDoc document = tfBcpmFinTargetDocRepository.findByFileId(fileId);
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
        TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark
                = tfBcpmFinTargetReviewerRemarkRepository.findByTargetId(targetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfBcpmFinTargetReviewerRemark != null) {
            responseMessage.setDTO(tfBcpmFinTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfBcpmFinTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfBcpmFinTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmFinTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTargetReviewerRemark.setCreatedDate(new Date());
        tfBcpmFinTargetReviewerRemarkRepository.save(tfBcpmFinTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemarkDb
                = tfBcpmFinTargetReviewerRemarkRepository.findByRemarkId(tfBcpmFinTargetReviewerRemark.getRemarkId());
        tfBcpmFinTargetReviewerRemark.setCreatedBy(tfBcpmFinTargetReviewerRemarkDb.getCreatedBy());
        tfBcpmFinTargetReviewerRemark.setCreatedDate(tfBcpmFinTargetReviewerRemarkDb.getCreatedDate());
        tfBcpmFinTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmFinTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfBcpmFinTargetReviewerRemark.setUpdatedDate(new Date());
        tfBcpmFinTargetReviewerRemarkRepository.save(tfBcpmFinTargetReviewerRemark);
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
        TfBcpmFinTargetStatus bcpmFinTargetStatus = new TfBcpmFinTargetStatus();
        bcpmFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        bcpmFinTargetStatus.setTargetId(targetId);
        bcpmFinTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        bcpmFinTargetStatus.setCreatedBy(currentUser.getUserId());
        bcpmFinTargetStatus.setCreatedDate(new Date());
        tfBcpmFinTargetStatusRepository.save(bcpmFinTargetStatus);
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
        TfBcpmFinTargetStatus bcpmFinTargetStatus = new TfBcpmFinTargetStatus();
        bcpmFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
        bcpmFinTargetStatus.setTargetId(targetId);
        bcpmFinTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        bcpmFinTargetStatus.setCreatedBy(currentUser.getUserId());
        bcpmFinTargetStatus.setCreatedDate(new Date());
        tfBcpmFinTargetStatusRepository.save(bcpmFinTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmFinTarget tfBcpmFinTargetDb = tfBcpmFinTargetRepository.findByTargetId(targetId);
        if (tfBcpmFinTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        FinancialTargetDto financialTargetDto = new FinancialTargetDto();
        financialTargetDto.setTargetId(targetId);
        financialTargetDto.setCompanyId(tfBcpmFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfBcpmFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmFinTarget tfBcpmFinTarget = new TfBcpmFinTarget();
        tfBcpmFinTarget.setTargetId(targetId);
//        tfBcpmFinTargetRepository.delete(tfBcpmFinTarget);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, FinancialTargetDto financialTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmFinTarget tfBcpmFinTargetDb = tfBcpmFinTargetRepository.findByTargetId(financialTargetDto.getTargetId());
        if (tfBcpmFinTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        financialTargetDto.setCompanyId(tfBcpmFinTargetDb.getCompanyId());
        financialTargetDto.setYear(tfBcpmFinTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, financialTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        //if creator set bcpm proposal from tfBcpmFinTargetDb
        int myRoleId = currentUser.getRoleId();
        int creator = SystemRoles.Creator.getValue();

        TfBcpmFinTarget tfBcpmFinTarget = new ModelMapper().map(financialTargetDto, TfBcpmFinTarget.class);
        if (myRoleId == creator) {
            tfBcpmFinTarget.setCurYearDhiProposal(tfBcpmFinTargetDb.getCurYearDhiProposal());
        }
        tfBcpmFinTarget.setYear(tfBcpmFinTargetDb.getYear());
        tfBcpmFinTarget.setCompanyId(tfBcpmFinTargetDb.getCompanyId());
        BigInteger versionNo = getTargetVersionNo(financialTargetDto.getTargetId());
        tfBcpmFinTarget.setVersionNo(versionNo);
        tfBcpmFinTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmFinTarget.setCreatedBy(tfBcpmFinTargetDb.getCreatedBy());
        tfBcpmFinTarget.setCreatedDate(tfBcpmFinTargetDb.getCreatedDate());
        tfBcpmFinTarget.setUpdatedBy(currentUser.getUserId());
        tfBcpmFinTarget.setUpdatedDate(new Date());
        tfBcpmFinTargetRepository.save(tfBcpmFinTarget);

        TfBcpmFinTarget_a tfBcpmFinTarget_a = new ModelMapper().map(tfBcpmFinTarget, TfBcpmFinTarget_a.class);
        tfBcpmFinTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmFinTarget_aRepository.save(tfBcpmFinTarget_a);

        TfBcpmFinTargetWriteup tfBcpmFinTargetWriteupDb = tfBcpmFinTargetWriteupRepository.findByTargetId(financialTargetDto.getTargetId());
        TfBcpmFinTargetWriteup tfBcpmFinTargetWriteup = new ModelMapper().map(financialTargetDto, TfBcpmFinTargetWriteup.class);
        tfBcpmFinTargetWriteup.setWriteupId(tfBcpmFinTargetWriteupDb.getWriteupId());
        tfBcpmFinTargetWriteup.setTargetId(tfBcpmFinTarget.getTargetId());
        tfBcpmFinTargetWriteup.setCreatedBy(tfBcpmFinTargetWriteupDb.getCreatedBy());
        tfBcpmFinTargetWriteup.setCreatedDate(tfBcpmFinTargetWriteupDb.getCreatedDate());
        tfBcpmFinTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmFinTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfBcpmFinTargetWriteup.setUpdatedDate(new Date());
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

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        responseMessage.setTargetAuditId(tfBcpmFinTarget_a.getTargetAuditId());
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfBcpmFinEditTargetDao.getTargetVersionNo(targetId);
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
        TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfBcpmFinTargetStatus tfBcpmFinTargetStatus = tfBcpmFinTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfBcpmFinTargetStage != null) {
                if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfBcpmFinTargetStatus.getStatusFlag() == 'X') {
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
            if (tfBcpmFinTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
