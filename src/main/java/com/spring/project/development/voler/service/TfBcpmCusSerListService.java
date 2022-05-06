package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfBcpmCusSerListDao;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.*;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.repository.cusService.formulation.bcpm.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created By zepaG on 3/28/2022.
 */
@Service
public class TfBcpmCusSerListService {
    private final TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository;
    private final TfBcpmCusSerTargetStageRepository tfBcpmCusSerTargetStageRepository;
    private final TfBcpmCusSerTargetStageDetailRepository tfBcpmCusSerTargetStageDetailRepository;
    private final TfBcpmCusSerListDao tfBcpmCusSerListDao;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final NotificationService notificationService;
    private final TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository;
    private final TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository;
    private final TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository;

    public TfBcpmCusSerListService(TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository, TfBcpmCusSerTargetStageRepository tfBcpmCusSerTargetStageRepository, TfBcpmCusSerTargetStageDetailRepository tfBcpmCusSerTargetStageDetailRepository, TfBcpmCusSerListDao tfBcpmCusSerListDao, CommonService commonService, CommonDao commonDao, NotificationService notificationService, TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository, TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository, TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository) {
        this.tfBcpmCusSerTargetWriteup_aRepository = tfBcpmCusSerTargetWriteup_aRepository;
        this.tfBcpmCusSerTargetStageRepository = tfBcpmCusSerTargetStageRepository;
        this.tfBcpmCusSerTargetStageDetailRepository = tfBcpmCusSerTargetStageDetailRepository;
        this.tfBcpmCusSerListDao = tfBcpmCusSerListDao;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.notificationService = notificationService;
        this.tfBcpmCusSerTargetActivity_aRepository = tfBcpmCusSerTargetActivity_aRepository;
        this.tfBcpmCusSerTargetStatusRepository = tfBcpmCusSerTargetStatusRepository;
        this.tfBcpmCusSerSubTargetRepository = tfBcpmCusSerSubTargetRepository;
    }


    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceTargetDto> customerServiceTargetDtos = tfBcpmCusSerListDao.searchTarget(year, companyId);
        if (customerServiceTargetDtos != null) {
            responseMessage.setDTO(customerServiceTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceTargetDto> customerServiceTargetDtos = tfBcpmCusSerListDao.searchByStage(stageId);
        if (customerServiceTargetDtos != null) {
            responseMessage.setDTO(customerServiceTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmCusSerTargetWriteup_a tfBcpmCusSerTargetWriteup_a = tfBcpmCusSerTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmCusSerTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tfBcpmCusSerTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfBcpmCusSerListDao.getStages(year, companyId);
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
        BigDecimal allocatedWeightage = weightageSetup.getCustomerWt();
        BigDecimal totalWeight = new BigDecimal("0.00");

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String subTargetId = targetStageDetailDto.getSubTargetId();
            TfBcpmCusSerSubTarget bcpmCusSerSubTarget = tfBcpmCusSerSubTargetRepository.findBySubTargetId(subTargetId);
            BigDecimal weightage = bcpmCusSerSubTarget.getWeightage();
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerListDao.getLatestStatus(year, companyId);
        if (tfBcpmCusSerTargetStage != null) {
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmCusSerTargetActivity_a tfBcpmCusSerTargetActivity_a = tfBcpmCusSerTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfBcpmCusSerTargetActivity_a.getTargetId();
            //get latest status by targetId
            TfBcpmCusSerTargetStatus tfBcpmCusSerTargetStatus = tfBcpmCusSerTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfBcpmCusSerTargetStatus.getStatusFlag();
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerListDao.getLatestStatus(year, companyId);
        if (tfBcpmCusSerTargetStage != null) {
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerListDao.getLatestStatus(year, companyId);
        if (tfBcpmCusSerTargetStage != null) {
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = new TfBcpmCusSerTargetStage();
        tfBcpmCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStage.setYear(year);
        tfBcpmCusSerTargetStage.setCompanyId(companyId);
        tfBcpmCusSerTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfBcpmCusSerTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStage.setCreatedDate(new Date());
        tfBcpmCusSerTargetStageRepository.save(tfBcpmCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmCusSerTargetStageDetail tfBcpmCusSerTargetStageDetail = new TfBcpmCusSerTargetStageDetail();
            tfBcpmCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmCusSerTargetStageDetail.setStageId(tfBcpmCusSerTargetStage.getStageId());
            tfBcpmCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmCusSerTargetStageDetail.setCreatedDate(new Date());
            tfBcpmCusSerTargetStageDetailRepository.save(tfBcpmCusSerTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = new TfBcpmCusSerTargetStage();
        tfBcpmCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStage.setYear(year);
        tfBcpmCusSerTargetStage.setCompanyId(companyId);
        tfBcpmCusSerTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfBcpmCusSerTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfBcpmCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStage.setCreatedDate(new Date());
        tfBcpmCusSerTargetStageRepository.save(tfBcpmCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmCusSerTargetStageDetail tfBcpmCusSerTargetStageDetail = new TfBcpmCusSerTargetStageDetail();
            tfBcpmCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmCusSerTargetStageDetail.setStageId(tfBcpmCusSerTargetStage.getStageId());
            tfBcpmCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmCusSerTargetStageDetail.setCreatedDate(new Date());
            tfBcpmCusSerTargetStageDetailRepository.save(tfBcpmCusSerTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = new TfBcpmCusSerTargetStage();
        tfBcpmCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStage.setYear(year);
        tfBcpmCusSerTargetStage.setCompanyId(companyId);
        tfBcpmCusSerTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfBcpmCusSerTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStage.setCreatedDate(new Date());
        tfBcpmCusSerTargetStageRepository.save(tfBcpmCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmCusSerTargetStageDetail tfBcpmCusSerTargetStageDetail = new TfBcpmCusSerTargetStageDetail();
            tfBcpmCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmCusSerTargetStageDetail.setStageId(tfBcpmCusSerTargetStage.getStageId());
            tfBcpmCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmCusSerTargetStageDetail.setCreatedDate(new Date());
            tfBcpmCusSerTargetStageDetailRepository.save(tfBcpmCusSerTargetStageDetail);
        }
//TODO: move all target to bcpm
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfBcpmCusSerTargetStage != null) {
            responseMessage.setDTO(tfBcpmCusSerTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
