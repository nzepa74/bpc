package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.entity.info.CommentPolicy;
import com.spring.project.development.voler.service.CommentPolicyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/commentPolicy")
public class CommentPolicyController {
    private final CommentPolicyService commentPolicyService;

    public CommentPolicyController(CommentPolicyService commentPolicyService) {
        this.commentPolicyService = commentPolicyService;
    }

//    @PreAuthorize("hasAuthority('25-ADD')")
    @RequestMapping(value = "/addCommentPolicy", method = RequestMethod.POST)
    public ResponseMessage addCommentPolicy(HttpServletRequest request, CommentPolicy commentPolicy) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return commentPolicyService.addCommentPolicy(currentUser, commentPolicy);
    }

//    @PreAuthorize("hasAuthority('25-EDIT')")
    @RequestMapping(value = "/editCommentPolicy", method = RequestMethod.POST)
    public ResponseMessage editCommentPolicy(HttpServletRequest request, CommentPolicy commentPolicy) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return commentPolicyService.editCommentPolicy(currentUser, commentPolicy);
    }

    @RequestMapping(value = "/getCommentPolicy", method = RequestMethod.GET)
    public ResponseMessage getCommentPolicy() {
        return commentPolicyService.getCommentPolicy();
    }
}
