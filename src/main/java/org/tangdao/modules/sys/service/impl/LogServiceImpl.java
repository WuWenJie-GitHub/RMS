package org.tangdao.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.modules.sys.mapper.LogMapper;
import org.tangdao.modules.sys.model.domain.Log;
import org.tangdao.modules.sys.service.ILogService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Service
public class LogServiceImpl  implements ILogService {

	@Autowired
	private LogMapper logMapper;
	
	public void clearAll() {
		this.logMapper.delete(null);
	}
	
	/**
	 * 不使用数据库事务，执行插入日志
	 */
	public void insertLog(Log entity) {
		this.logMapper.insert(entity);
	}
	
	public Log get(String id) {
		if(StringUtils.isBlank(id)) {
			return new Log();
		}
		Log log = this.logMapper.selectById(id);
		if(log!=null) {
			return log;
		}
		return new Log();
	}
	
	public IPage<Log> page(IPage<Log> page, Wrapper<Log> queryWrapper){
		return this.logMapper.selectPage(page, queryWrapper);
	}
}
