package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfDhiCusSerListDao;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.*;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.repository.cusService.formulation.dhi.*;
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
public class TfDhiCusSerListService {
    private final TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository;
    private final TfDhiCusSerTargetStageRepository tfDhiCusSerTargetStageRepository;
    private final TfDhiCusSerTargetStageDetailRepository tfDhiCusSerTargetStageDetailRepository;
    private final TfDhiCusSerListDao tfDhiCusSerListDao;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final NotificationService notificationService;
    private final TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository;
    private final TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository;
    private final TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository;

    public TfDhiCusSerListService(TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository, TfDhiCusSerTargetStageRepository tfDhiCusSerTargetStageRepository, TfDhiCusSerTargetStageDetailRepository tfDhiCusSerTargetStageDetailRepository, TfDhiCusSerListDao tfDhiCusSerListDao, CommonService commonService, CommonDao commonDao, NotificationService notificationService, TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository, TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository, TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository) {
        this.tfDhiCusSerTargetWriteup_aRepository = tfDhiCusSerTargetWriteup_aRepository;
        this.tfDhiCusSerTargetStageRepository = tfDhiCusSerTargetStageRepository;
        this.tfDhiCusSerTargetStageDetailRepository = tfDhiCusSerTargetStageDetailRepository;
        this.tfDhiCusSerListDao = tfDhiCusSerListDao;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.notificationService = notificationService;
        this.tfDhiCusSerTargetActivity_aRepository = tfDhiCusSerTargetActivity_aRepository;
        this.tfDhiCusSerTargetStatusRepository = tfDhiCusSerTargetStatusRepository;
        this.tfDhiCusSerSubTargetRepository = tfDhiCusSerSubTargetRepository;
    }


    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceTargetDto> customerServiceTargetDtos = tfDhiCusSerListDao.searchTarget(year, companyId);
        if (customerServiceTargetDtos != null) {
            responseMessage.setDTO(customerServiceTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceTargetDto> customerServiceTargetDtos = tfDhiCusSerListDao.searchByStage(stageId);
        if (customerServiceTargetDtos != null) {
            responseMessage.setDTO(customerServiceTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiCusSerTargetWriteup_a tfDhiCusSerTargetWriteup_a = tfDhiCusSerTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiCusSerTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tfDhiCusSerTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfDhiCusSerListDao.getStages(year, companyId);
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
            TfDhiCusSerSubTarget dhiCusSerSubTarget = tfDhiCusSerSubTargetRepository.findBySubTargetId(subTargetId);
            BigDecimal weightage = dhiCusSerSubTarget.getWeightage();
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerListDao.getLatestStatus(year, companyId);
        if (tfDhiCusSerTargetStage != null) {
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfDhiCusSerTargetActivity_a tfDhiCusSerTargetActivity_a = tfDhiCusSerTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfDhiCusSerTargetActivity_a.getTargetId();
            //get latest status by targetId
            TfDhiCusSerTargetStatus tfDhiCusSerTargetStatus = tfDhiCusSerTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfDhiCusSerTargetStatus.getStatusFlag();
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerListDao.getLatestStatus(year, companyId);
        if (tfDhiCusSerTargetStage != null) {
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerListDao.getLatestStatus(year, companyId);
        if (tfDhiCusSerTargetStage != null) {
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = new TfDhiCusSerTargetStage();
        tfDhiCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStage.setYear(year);
        tfDhiCusSerTargetStage.setCompanyId(companyId);
        tfDhiCusSerTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfDhiCusSerTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStage.setCreatedDate(new Date());
        tfDhiCusSerTargetStageRepository.save(tfDhiCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiCusSerTargetStageDetail tfDhiCusSerTargetStageDetail = new TfDhiCusSerTargetStageDetail();
            tfDhiCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiCusSerTargetStageDetail.setStageId(tfDhiCusSerTargetStage.getStageId());
            tfDhiCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiCusSerTargetStageDetail.setCreatedDate(new Date());
            tfDhiCusSerTargetStageDetailRepository.save(tfDhiCusSerTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = new TfDhiCusSerTargetStage();
        tfDhiCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStage.setYear(year);
        tfDhiCusSerTargetStage.setCompanyId(companyId);
        tfDhiCusSerTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfDhiCusSerTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfDhiCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStage.setCreatedDate(new Date());
        tfDhiCusSerTargetStageRepository.save(tfDhiCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiCusSerTargetStageDetail tfDhiCusSerTargetStageDetail = new TfDhiCusSerTargetStageDetail();
            tfDhiCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiCusSerTargetStageDetail.setStageId(tfDhiCusSerTargetStage.getStageId());
            tfDhiCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiCusSerTargetStageDetail.setCreatedDate(new Date());
            tfDhiCusSerTargetStageDetailRepository.save(tfDhiCusSerTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = new TfDhiCusSerTargetStage();
        tfDhiCusSerTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStage.setYear(year);
        tfDhiCusSerTargetStage.setCompanyId(companyId);
        tfDhiCusSerTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfDhiCusSerTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiCusSerTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStage.setCreatedDate(new Date());
        tfDhiCusSerTargetStageRepository.save(tfDhiCusSerTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiCusSerTargetStageDetail tfDhiCusSerTargetStageDetail = new TfDhiCusSerTargetStageDetail();
            tfDhiCusSerTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiCusSerTargetStageDetail.setStageId(tfDhiCusSerTargetStage.getStageId());
            tfDhiCusSerTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiCusSerTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiCusSerTargetStageDetail.setCreatedDate(new Date());
            tfDhiCusSerTargetStageDetailRepository.save(tfDhiCusSerTargetStageDetail);
        }
//TODO: move all target to bcpm
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Customer Service Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiCusSerList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfDhiCusSerTargetStage != null) {
            responseMessage.setDTO(tfDhiCusSerTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
