package org.tangdao.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tangdao.common.suports.BaseController;
import org.tangdao.common.web.http.ServletUtils;

@Controller
@RequestMapping(value = "tags")
public class TagsController extends BaseController {

	@RequestMapping({ "treeselect" })
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAllAttributes(ServletUtils.getParameters(request));
		return "tagsview/form/treeselect";
	}

	@RequestMapping({ "iconselect" })
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAllAttributes(ServletUtils.getParameters(request));
		return "tagsview/form/iconselect";
	}
}
