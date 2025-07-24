package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController extends BaseController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public String loginPage(
			@RequestParam(value = "error", required = false)String error,
			@RequestParam(value = "logout", required = false)String logout,
			Model model){
		LOGGER.info("# LoginController.login");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}

		if (error != null) {
			String mggError = (String) Util.getSession().getAttribute(ConstantesSesion.SESSION_KEY_MSG_SECUIRY_ERROR);
			model.addAttribute("msg", mggError);
		}
		else if (logout != null) {
			model.addAttribute("msg", "Has salido con exito");
		}

		return ConstantesAdminView.VIEW_LOGIN;
	}
	

}
