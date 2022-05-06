package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.entity.cusService.CusSerTargetComment;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmCusSerEditTargetService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created By zepaG on 3/21/2022.
 */
@RestController
@RequestMapping("api/tfBcpmCusSerEditTarget")
public class TfBcpmCusSerEditTargetController {
    private final CommonService commonService;
    private final TfBcpmCusSerEditTargetService tfBcpmCusSerEditTargetService;

    public TfBcpmCusSerEditTargetController(CommonService commonService, TfBcpmCusSerEditTargetService tfBcpmCusSerEditTargetService) {
        this.commonService = commonService;
        this.tfBcpmCusSerEditTargetService = tfBcpmCusSerEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfBcpmCusSerEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getSubTarget", method = RequestMethod.GET)
    public ResponseMessage getSubTarget(String targetAuditId) {
        return tfBcpmCusSerEditTargetService.getSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfBcpmCusSerEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfBcpmCusSerEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getSubTargetHistory", method = RequestMethod.GET)
    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        return tfBcpmCusSerEditTargetService.getSubTargetHistory(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfBcpmCusSerEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String subTargetId) {
        return tfBcpmCusSerEditTargetService.getAttachments(subTargetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmCusSerEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmCusSerEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String subTargetId) {
        return tfBcpmCusSerEditTargetService.getRemark(subTargetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.addRemark(currentUser, tfBcpmCusSerSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.editRemark(currentUser, tfBcpmCusSerSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, CusSerTargetComment cusSerTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.addComment(currentUser, cusSerTargetComment);
    }

    @RequestMapping(value = "getCusSerComment", method = RequestMethod.GET)
    public ResponseMessage getCusSerComment(String targetId) {
        return commonService.getCusSerComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteSubTarget", method = RequestMethod.POST)
    public ResponseMessage deleteSubTarget(HttpServletRequest request, String subTargetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.deleteSubTarget(currentUser, subTargetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, CustomerServiceTargetDto customerServiceTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmCusSerEditTargetService.editTarget(request, currentUser, customerServiceTargetDto);
    }
}
