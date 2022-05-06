package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfBcpmFinListDao;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.*;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.repository.financial.formulation.bcpm.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 3/21/2022.
 */
@Service
public class TfBcpmFinListService {
    private final TfBcpmFinListDao tfBcpmFinListDao;
    private final TfBcpmFinTargetStageRepository tfBcpmFinTargetStageRepository;
    private final TfBcpmFinTargetStageDetailRepository tfBcpmFinTargetStageDetailRepository;
    private final TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository;
    private final NotificationService notificationService;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository;
    private final TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository;


    public TfBcpmFinListService(TfBcpmFinListDao tfBcpmFinListDao, TfBcpmFinTargetStageRepository tfBcpmFinTargetStageRepository, TfBcpmFinTargetStageDetailRepository tfBcpmFinTargetStageDetailRepository, TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository, NotificationService notificationService, CommonService commonService, CommonDao commonDao, TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository, TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository) {
        this.tfBcpmFinListDao = tfBcpmFinListDao;
        this.tfBcpmFinTargetStageRepository = tfBcpmFinTargetStageRepository;
        this.tfBcpmFinTargetStageDetailRepository = tfBcpmFinTargetStageDetailRepository;
        this.tfBcpmFinTargetWriteup_aRepository = tfBcpmFinTargetWriteup_aRepository;
        this.notificationService = notificationService;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.tfBcpmFinTarget_aRepository = tfBcpmFinTarget_aRepository;
        this.tfBcpmFinTargetStatusRepository = tfBcpmFinTargetStatusRepository;
    }

    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<FinancialTargetDto> financialTargetDtos = tfBcpmFinListDao.searchTarget(year, companyId);
        if (financialTargetDtos != null) {
            responseMessage.setDTO(financialTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<FinancialTargetDto> financialTargetDtos = tfBcpmFinListDao.searchByStage(stageId);
        if (financialTargetDtos != null) {
            responseMessage.setDTO(financialTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmFinTargetWriteup_a tBcpmFinTargetWriteup_a = tfBcpmFinTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tBcpmFinTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tBcpmFinTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfBcpmFinListDao.getStages(year, companyId);
        if (targetStageDtos != null) {
            responseMessage.setDTO(targetStageDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    private ResponseMessage validateTotalWeight(TargetStageDto targetStageDto) {
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        ResponseMessage responseMessage = commonService.getAllocatedWt(year, companyId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        WeightageSetup weightageSetup = (WeightageSetup) responseMessage.getDTO();
        BigDecimal allocatedWeightage = weightageSetup.getFinancialWt();
        BigDecimal totalWeight = new BigDecimal("0.00");

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String targetAuditId = targetStageDetailDto.getTargetAuditId();
            TfBcpmFinTarget_a tfBcpmFinTarget_a = tfBcpmFinTarget_aRepository.findByTargetAuditId(targetAuditId);
            BigDecimal weightage = tfBcpmFinTarget_a.getWeightage();
            if (weightage.compareTo(BigDecimal.ZERO) > 0) {
                totalWeight = weightage.add(totalWeight);
            }
        }
        if (!totalWeight.equals(allocatedWeightage)) {
            responseMessage.setText("Total weight must be equal to " + allocatedWeightage + "%");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeSubmit(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only creator should able to submit
        if (!myRoleId.equals(SystemRoles.Creator.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to submit.");
            return responseMessage;
        }
        //check if it is creator's company or not
        if (!currentUser.getCompanyId().equals(targetStageDto.getCompanyId())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to submit because the target doesn't belong to you.");
            return responseMessage;
        }
        //check already submitted or not, creator should able to submit only if latest status is null or R=Reverted
        TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinListDao.getLatestStatus(year, companyId);
        if (tfBcpmFinTargetStage != null) {
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeRevert(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only reviewer should able to revert
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert.");
            return responseMessage;
        }

        //check if it is reviewer belongs to selected company or not
        String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
        if (mappedCompanyId == null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert because selected company is not mapped to you.");
            return responseMessage;
        }
        //at least one target status need to be reopened or created
        boolean isOpen = false;
        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String targetAuditId = targetStageDetailDto.getTargetAuditId();
            TfBcpmFinTarget_a tfBcpmFinTarget_a = tfBcpmFinTarget_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfBcpmFinTarget_a.getTargetId();
            //get latest status by targetId
            TfBcpmFinTargetStatus tfBcpmFinTargetStatus = tfBcpmFinTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfBcpmFinTargetStatus.getStatusFlag();
            if (statusFlag == 'C' || statusFlag == 'R') {
                isOpen = true;
            }
        }
        if (!isOpen) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("All the targets are closed. Please reopen target(s) that need to be changed.");
            return responseMessage;
        }


        //check already reverted or not, reviewer should able to revert only if latest status is S=Submitted
        TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinListDao.getLatestStatus(year, companyId);
        if (tfBcpmFinTargetStage != null) {
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Target is not submitted yet. Please refresh the page and try again.");
            return responseMessage;
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeApprove(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only reviewer should able to revert
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert.");
            return responseMessage;
        }

        //check if it is reviewer belongs to selected company or not
        String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
        if (mappedCompanyId == null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert because selected company is not mapped to you.");
            return responseMessage;
        }

        //check already reverted or not, reviewer should able to revert only if latest status is S=Submitted
        TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinListDao.getLatestStatus(year, companyId);
        if (tfBcpmFinTargetStage != null) {
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Target is not submitted yet. Please refresh the page and try again.");
            return responseMessage;
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage submit(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateTotalWeight(targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage = validateBeforeSubmit(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfBcpmFinTargetStage tfBcpmFinTargetStage = new TfBcpmFinTargetStage();
        tfBcpmFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmFinTargetStage.setYear(year);
        tfBcpmFinTargetStage.setCompanyId(companyId);
        tfBcpmFinTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfBcpmFinTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTargetStage.setCreatedDate(new Date());
        tfBcpmFinTargetStageRepository.save(tfBcpmFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfBcpmFinTargetStageDetail tfBcpmFinTargetStageDetail = new TfBcpmFinTargetStageDetail();
            tfBcpmFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmFinTargetStageDetail.setStageId(tfBcpmFinTargetStage.getStageId());
            tfBcpmFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmFinTargetStageDetail.setCreatedDate(new Date());
            tfBcpmFinTargetStageDetailRepository.save(tfBcpmFinTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getReviewerId(companyId, SystemRoles.Reviewer.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("Submitted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }


    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage revert(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeRevert(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfBcpmFinTargetStage tfBcpmFinTargetStage = new TfBcpmFinTargetStage();
        tfBcpmFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmFinTargetStage.setYear(year);
        tfBcpmFinTargetStage.setCompanyId(companyId);
        tfBcpmFinTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfBcpmFinTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfBcpmFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTargetStage.setCreatedDate(new Date());
        tfBcpmFinTargetStageRepository.save(tfBcpmFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfBcpmFinTargetStageDetail tfBcpmFinTargetStageDetail = new TfBcpmFinTargetStageDetail();
            tfBcpmFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmFinTargetStageDetail.setStageId(tfBcpmFinTargetStage.getStageId());
            tfBcpmFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmFinTargetStageDetail.setCreatedDate(new Date());
            tfBcpmFinTargetStageDetailRepository.save(tfBcpmFinTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("Reverted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage approve(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeApprove(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfBcpmFinTargetStage tfBcpmFinTargetStage = new TfBcpmFinTargetStage();
        tfBcpmFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmFinTargetStage.setYear(year);
        tfBcpmFinTargetStage.setCompanyId(companyId);
        tfBcpmFinTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfBcpmFinTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmFinTargetStage.setCreatedDate(new Date());
        tfBcpmFinTargetStageRepository.save(tfBcpmFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfBcpmFinTargetStageDetail tfBcpmFinTargetStageDetail = new TfBcpmFinTargetStageDetail();
            tfBcpmFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmFinTargetStageDetail.setStageId(tfBcpmFinTargetStage.getStageId());
            tfBcpmFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmFinTargetStageDetail.setCreatedDate(new Date());
            tfBcpmFinTargetStageDetailRepository.save(tfBcpmFinTargetStageDetail);
        }
//TODO: move all target to bcpm

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("approved successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage checkPermission(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmFinTargetStage tfBcpmFinTargetStage = tfBcpmFinTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfBcpmFinTargetStage != null) {
            responseMessage.setDTO(tfBcpmFinTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
