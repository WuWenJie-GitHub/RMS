package org.tangdao.modules.sys.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.tangdao.common.codec.EncodeUtils;
import org.tangdao.common.lang.DateUtils;
import org.tangdao.common.suports.BaseController;
import org.tangdao.common.suports.BaseEntity;

/**
 * @ClassName: AdviceController.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ruyang
 * @date 2019年3月1日 上午9:46:17
 * 
 */
@ControllerAdvice
public class AdviceController {
	
	@Autowired
	private GlobalController globalController;
	
	@ExceptionHandler({ BindException.class, ConstraintViolationException.class, ValidationException.class })
	protected String exceptionHandlerTo400Page(Exception exception,HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("exception", exception);
		return globalController.error("400", request, response, model);
	}

	@ExceptionHandler({ AuthenticationException.class, AccessDeniedException.class})
	protected String exceptionHandlerTo403Page(Exception exception,HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("exception", exception);
		if(exception instanceof BadCredentialsException) {
			return globalController.error("401", request, response, model);
		}
		return globalController.error("403", request, response, model);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			public void setAsText(String text) {
				this.setValue(EncodeUtils.xssFilter(text));
			}
		});
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String text) {
				this.setValue(DateUtils.parseDate(text));
			}
		});
		if (binder.getTarget() instanceof BaseEntity) {
			BaseEntity<?> baseEntity = (BaseEntity<?>) binder.getTarget();
			request.setAttribute(BaseController.WEB_DATA_BINDER_SOURCE, baseEntity.clone());
			request.setAttribute(BaseController.WEB_DATA_BINDER_TARGET, baseEntity);
		}
	}
}
