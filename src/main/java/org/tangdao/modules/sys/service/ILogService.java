package org.tangdao.modules.sys.service;

import org.tangdao.modules.sys.model.domain.Log;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
public interface ILogService{
	
	public void clearAll();

	public void insertLog(Log entity);
	
	public Log get(String id);
	
	public IPage<Log> page(IPage<Log> page, Wrapper<Log> queryWrapper);
}
