package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.entity.financial.FinTargetComment;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmFinEditTargetService;
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
@RequestMapping("api/tfBcpmFinEditTarget")
public class TfBcpmFinEditTargetController {
    private final CommonService commonService;
    private final TfBcpmFinEditTargetService tfBcpmFinEditTargetService;

    public TfBcpmFinEditTargetController(CommonService commonService, TfBcpmFinEditTargetService tfBcpmFinEditTargetService) {
        this.commonService = commonService;
        this.tfBcpmFinEditTargetService = tfBcpmFinEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfBcpmFinEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfBcpmFinEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfBcpmFinEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfBcpmFinEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String targetId) {
        return tfBcpmFinEditTargetService.getAttachments(targetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmFinEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmFinEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String targetId) {
        return tfBcpmFinEditTargetService.getRemark(targetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.addRemark(currentUser, tfBcpmFinTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.editRemark(currentUser, tfBcpmFinTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, FinTargetComment finTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.addComment(currentUser, finTargetComment);
    }

    @RequestMapping(value = "getFinComment", method = RequestMethod.GET)
    public ResponseMessage getFinComment(String targetId) {
        return commonService.getFinComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, FinancialTargetDto financialTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinEditTargetService.editTarget(request, currentUser, financialTargetDto);
    }
}
