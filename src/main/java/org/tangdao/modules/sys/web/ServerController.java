package org.tangdao.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tangdao.common.suports.BaseController;

/**
 * @ClassName: ServerController.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ruyang
 * @date 2019年1月30日 上午9:57:57
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/stateServer")
public class ServerController extends BaseController {
	
	@RequestMapping(value = { "index" })
	public String index(Model model) throws Exception {
		return "modules/sys/serverIndex";
	}
}
