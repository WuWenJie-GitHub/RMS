/**
 *
 */
package org.tangdao.modules.state.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tangdao.common.callback.MethodCallback;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.BaseController;
import org.tangdao.common.suports.Page;
import org.tangdao.modules.state.service.CacheService;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.utils.UserUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 
 * @author Ryan Ru(ruyangit@gmail.com)
 */
@Controller
@RequestMapping(value = { "${adminPath}/state/cache" })
public class CacheController extends BaseController {
	
	@Autowired
	private CacheService cacheService;
	
	@GetMapping("/list")
	public String list(User user, Model model) {
		return "modules/state/cacheList";
	}
	
	@PostMapping("/listData")
	public @ResponseBody IPage<Map<String, Object>> listData(String pattern, 
			@RequestParam(required = false, defaultValue="0") Integer pageNo,
			@RequestParam(required = false, defaultValue="20") Integer pageSize,
			HttpServletRequest request) {
		IPage<Map<String, Object>> page = new Page<Map<String,Object>>();
		List<Map<String, Object>> list = cacheService.keys(pattern);
		ListUtils.pageList(list, pageSize, new MethodCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object execute(Object... params) {
				// TODO Auto-generated method stub
				if((Integer)params[1]==(pageNo==0?1:pageNo)) {
					page.setRecords((List<Map<String, Object>>)params[0]);
					page.setCurrent((Integer)params[1]);
					page.setSize((Integer)params[2]);
					page.setPages((Integer)params[3]);
					page.setTotal(list.size());
				}
				return page;
			}
		});
		return page;
	}
	
	/**
	 * 删除缓存
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(String key, @RequestParam(required = false) String hashKey) {
		User user = UserUtils.getUser();
		if (!user.isSuperAdmin()) {
			return renderResult(Global.FALSE, "越权操作，只有超级管理员才能修改此数据！");
		}
		if(StringUtils.isEmpty(hashKey)) {
			cacheService.delete(key);
		}else {
			cacheService.removeHashKey(key, hashKey);
		}
		return renderResult(Global.TRUE, "删除成功！");
	}
	
	@RequestMapping("/form")
	public String value(String key, String dataType, Model model) {
		model.addAttribute("key", key);
		model.addAttribute("dataType", dataType);
		model.addAttribute("ttl", cacheService.getTime(key));
		return "modules/state/cacheForm";
	}

	/**
	 *  keys 列表
	 * @param pattern
	 * @return
	 */
	@RequestMapping(value = { "formData" })
	public @ResponseBody IPage<Map<String, Object>> formData(
			String key, 
			String dataType,
			@RequestParam(required = false, defaultValue="0") Integer pageNo,
			@RequestParam(required = false, defaultValue="20") Integer pageSize,
			HttpServletRequest request) {
		IPage<Map<String, Object>> page = new Page<Map<String,Object>>();
		List<Map<String, Object>> list = this.cacheService.values(key, dataType, 0, 1000);
		ListUtils.pageList(list, pageSize, new MethodCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object execute(Object... params) {
				// TODO Auto-generated method stub
				if((Integer)params[1]==(pageNo==0?1:pageNo)) {
					page.setRecords((List<Map<String, Object>>)params[0]);
					page.setCurrent((Integer)params[1]);
					page.setSize((Integer)params[2]);
					page.setPages((Integer)params[3]);
					page.setTotal(list.size());
				}
				return page;
			}
		});
		return page;
	}
}
