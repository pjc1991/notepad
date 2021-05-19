package com.pjc.notepad.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pjc.notepad.member.service.MemberJoinValidator;
import com.pjc.notepad.member.service.MemberService;
import com.pjc.notepad.member.service.dto.MemberDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberUserController.class);

    private final MemberService memberService;

    private final MemberJoinValidator memberJoinValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(Model model, HttpServletRequest request) {
        LOGGER.info("loginForm RemoteAddr : {}",  request.getRemoteAddr());
        return "member/loginForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
            MemberDto member) {

        LOGGER.info("loginPro RemoteAddr : {}", request.getRemoteAddr());
        LOGGER.info("member m_id : {} ",  member.getMemberId());
        LOGGER.info("member m_pw : {} ", member.getMemberPw());

        member = memberService.login(member);

        String msg;
        String returnTarget = "";

        if (member != null) {
            LOGGER.info("result => {}", member.getMemberStatus());
            request.getSession().setAttribute("currentUser", member.toString());
            msg = "성공적으로 로그인되었습니다. " + member.getMemberId() + "님.";
            returnTarget = "redirect:/note";
        } else {
            msg = "로그인에 실패하셨습니다.";
            returnTarget = "redirect:/login";
        }
        LOGGER.info(msg);
        redirectAttributes.addFlashAttribute("msg", msg);
        return returnTarget;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/member/form", method = RequestMethod.GET)
    public String memberFormGet(HttpServletRequest request, @ModelAttribute("memberDto") MemberDto memberDto,
            BindingResult bindingResult, Model model) {
        LOGGER.info("Member DTO : {}", memberDto);
        // return "redirect:/login";
        return "member/form";
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public String memberPost(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("memberDto") MemberDto memberDto,
            BindingResult bindingResult, Model model) {
        LOGGER.info("memberPost memberId : {}", memberDto.getMemberId());
        memberJoinValidator.validate(memberDto, bindingResult);
        if (!bindingResult.hasErrors()) {
            // Validation success
            int result = memberService.insertMember(memberDto);
            LOGGER.info("result => {}", result);
            if (result < 0) {
                // Database failed
                model.addAttribute("msg", "가입에 실패하셨습니다. ");
                response.setStatus(HttpStatus.BAD_GATEWAY.value()); // TODO : Exception, ExceptionHandler 로 추후 변경
                return "redirect:/member/form";
            }
            return "redirect:/login";
        } else {
            // Validation error
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // TODO : Exception
            model.addAttribute("msg", "입력값에 오류가 있습니다.");
            return "redirect:/member/form";
        }
    }
}
