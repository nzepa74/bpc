package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.entity.orgMgt.OrgMgtTargetComment;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtSubTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmOrgMgtEditTargetService;
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
@RequestMapping("api/tfBcpmOrgMgtEditTarget")
public class TfBcpmOrgMgtEditTargetController {
    private final CommonService commonService;
    private final TfBcpmOrgMgtEditTargetService tfBcpmOrgMgtEditTargetService;

    public TfBcpmOrgMgtEditTargetController(CommonService commonService, TfBcpmOrgMgtEditTargetService tfBcpmOrgMgtEditTargetService) {
        this.commonService = commonService;
        this.tfBcpmOrgMgtEditTargetService = tfBcpmOrgMgtEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfBcpmOrgMgtEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getSubTarget", method = RequestMethod.GET)
    public ResponseMessage getSubTarget(String targetAuditId) {
        return tfBcpmOrgMgtEditTargetService.getSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfBcpmOrgMgtEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfBcpmOrgMgtEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getSubTargetHistory", method = RequestMethod.GET)
    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        return tfBcpmOrgMgtEditTargetService.getSubTargetHistory(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfBcpmOrgMgtEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String subTargetId) {
        return tfBcpmOrgMgtEditTargetService.getAttachments(subTargetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmOrgMgtEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmOrgMgtEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String subTargetId) {
        return tfBcpmOrgMgtEditTargetService.getRemark(subTargetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.addRemark(currentUser, tfBcpmOrgMgtSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.editRemark(currentUser, tfBcpmOrgMgtSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, OrgMgtTargetComment orgMgtTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.addComment(currentUser, orgMgtTargetComment);
    }

    @RequestMapping(value = "getOrgMgtComment", method = RequestMethod.GET)
    public ResponseMessage getOrgMgtComment(String targetId) {
        return commonService.getOrgMgtComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteSubTarget", method = RequestMethod.POST)
    public ResponseMessage deleteSubTarget(HttpServletRequest request, String subTargetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.deleteSubTarget(currentUser, subTargetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmOrgMgtEditTargetService.editTarget(request, currentUser, orgMgtTargetDto);
    }
}
