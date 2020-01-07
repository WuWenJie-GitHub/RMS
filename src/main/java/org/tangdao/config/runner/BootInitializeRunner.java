package org.tangdao.config.runner;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.tangdao.config.worker.hook.ShutdownHookWorker;
import org.tangdao.modules.sys.service.IConfigService;
import org.tangdao.modules.sys.service.IDictDataService;
import org.tangdao.modules.sys.service.IMenuService;

/**
 * 初始化项目依赖的资源，如REDIS/自定义线程开启等
 * 
 * @author ruyang
 * @version V1.0
 * @date 2018年5月3日 上午11:18:55
 */
@Configuration
@Order(2)
public class BootInitializeRunner implements CommandLineRunner {
	
	@Autowired
	private IConfigService             configService;
	@Autowired
	private IDictDataService           dictDataService;
	@Autowired
	private IMenuService               menuService;
    @Resource
    private ThreadPoolTaskExecutor     threadPoolTaskExecutor;

    public static final Lock           LOCK                   = new ReentrantLock();
    public static final Condition      CONDITION              = LOCK.newCondition();

    private final Logger               logger                 = LoggerFactory.getLogger(getClass());

    /**
     * 自定义初始化资源是否完成（因有些服务强依赖某些资源初始化完成，如 rabbit listener 消费）
     */
    public static volatile boolean     isResourceInitFinished = false;

    @Override
    public void run(String... arg0) throws Exception {
        try {
        	initConfig();
        	initDictData();
        	initMenuTreeName();
            registShutdownHook();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        initSignal();

    }
    
    private void initConfig() {
    	configService.reloadToRedis();
    	logger.info("系统配置初始化完成");
    }
    
    private void initDictData() {
    	dictDataService.reloadToRedis();
    	logger.info("系统字典初始化完成");
    }
    
    private void initMenuTreeName() {
    	menuService.reloadMenuTreeNameToRedis();
    	logger.info("系统菜单初始化完成");
    }

    /**
     * 初始化信号源控制
     */
    private void initSignal() {
        LOCK.lock();
        try {
            isResourceInitFinished = true;
            CONDITION.signalAll();
            logger.info("初始化资源信号源标记完成，开始消费..");
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * TODO 注册JVM关闭钩子函数
     */
    private void registShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookWorker(threadPoolTaskExecutor)));
        logger.info("Jvm hook thread has registed");
    }

}
