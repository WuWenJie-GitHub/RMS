-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: 192.168.113.105    Database: tangdao_dev
-- ------------------------------------------------------
-- Server version	5.7.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_table` (
  `table_name` varchar(200) NOT NULL DEFAULT '' COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) DEFAULT NULL COMMENT '关联父表外键',
  `tpl_category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `package_name` varchar(500) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `sub_module_name` varchar(30) DEFAULT NULL COMMENT '生成子模块名',
  `function_name` varchar(500) DEFAULT NULL COMMENT '生成功能名',
  `function_name_simple` varchar(100) DEFAULT NULL COMMENT '生成功能名（简写）',
  `function_author` varchar(100) DEFAULT NULL COMMENT '生成功能作者',
  `gen_base_dir` varchar(100) DEFAULT NULL COMMENT '生成基础路径',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  PRIMARY KEY (`table_name`),
  KEY `gen_table_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` VALUES ('sys_employee','员工信息','Employee',NULL,NULL,'crud','org.tangdao.modules','sys',NULL,'员工','员工','ruyang','D:\\my-projects\\springcareful','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03','','{\"treeViewName\":\"\",\"isHaveDelete\":\"1\",\"treeViewCode\":\"\",\"isFileUpload\":\"0\",\"isHaveDisableEnable\":\"1\",\"isImageUpload\":\"0\"}');
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_table_column` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `table_name` varchar(64) DEFAULT NULL COMMENT '归属表',
  `column_name` varchar(200) DEFAULT NULL COMMENT '名称',
  `column_sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `is_update` char(1) DEFAULT NULL COMMENT '是否更新字段',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  PRIMARY KEY (`id`),
  KEY `gen_table_column_status` (`status`),
  KEY `gen_table_column_name` (`column_name`),
  KEY `gen_table_column_sort` (`column_sort`),
  KEY `gen_table_column_table_id` (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
INSERT INTO `gen_table_column` VALUES ('1206504465633398785','sys_employee','emp_code',10,'员工编码','varchar(64)','String','empCode','1','1','1','1','','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465696313345','sys_employee','emp_name',20,'员工姓名','varchar(100)','String','empName','','1','1','1','1','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465713090561','sys_employee','emp_name_en',30,'英文名','varchar(100)','String','empNameEn','','','1','1','1','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465788588033','sys_employee','office_name',50,'机构名称','varchar(100)','String','officeName','','','1','1','1','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465826336770','sys_employee','company_name',70,'公司名称','varchar(200)','String','companyName','','','1','1','1','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465855696897','sys_employee','status',80,'状态（0在职 1删除 2离职）','char(1)','String','status','','1','1','','1','1','EQ','radio','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"sys_status\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"sys_status\"}'),('1206504465876668417','sys_employee','create_by',90,'创建者','varchar(64)','String','createBy','','','1','','','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465922805761','sys_employee','create_time',100,'创建时间','datetime','java.util.Date','createTime','','','1','','','','EQ','datetime','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465939582978','sys_employee','update_by',110,'更新者','varchar(64)','String','updateBy','','','1','','','','EQ','input','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465964748802','sys_employee','update_time',120,'更新时间','datetime','java.util.Date','updateTime','','','1','','1','','EQ','datetime','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1206504465981526017','sys_employee','remarks',130,'备注信息','varchar(500)','String','remarks','','','1','1','','','EQ','textarea','0','system','2019-12-16 17:20:55','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"1\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"12/2/10\",\"dictType\":\"\"}'),('1207481834049486849','sys_employee','office_code',40,'机构编码','varchar(64)','String','officeCode','','','1','1','1','','EQ','input','0','system','2019-12-19 10:04:38','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}'),('1207481834137567233','sys_employee','company_code',60,'公司编码','varchar(64)','String','companyCode','','','1','1','1','','EQ','input','0','system','2019-12-19 10:04:38','system','2019-12-20 10:30:03',NULL,'1','{\"isNewLine\":\"\",\"fieldValid\":\"\",\"dictName\":\"\",\"gridRowCol\":\"6/4/8\",\"dictType\":\"\"}');
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_area`
--

DROP TABLE IF EXISTS `sys_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_area` (
  `area_code` varchar(100) NOT NULL COMMENT '区域编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `tree_leaf` char(1) DEFAULT NULL COMMENT '是否最末级',
  `tree_level` char(1) DEFAULT NULL COMMENT '层次级别',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称',
  `area_type` char(1) DEFAULT NULL COMMENT '区域类型',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`area_code`),
  KEY `idx_sys_area_pc` (`area_code`),
  KEY `idx_sys_area_ts` (`tree_sort`),
  KEY `idx_sys_area_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_area`
--

LOCK TABLES `sys_area` WRITE;
/*!40000 ALTER TABLE `sys_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_company`
--

DROP TABLE IF EXISTS `sys_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_company` (
  `company_code` varchar(64) NOT NULL COMMENT '公司编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `tree_leaf` char(1) DEFAULT NULL COMMENT '是否最末级',
  `tree_level` char(1) DEFAULT NULL COMMENT '层次级别',
  `view_code` varchar(100) DEFAULT NULL COMMENT '公司代码',
  `company_name` varchar(200) NOT NULL COMMENT '公司名称',
  `full_name` varchar(200) DEFAULT NULL COMMENT '公司全称',
  `area_code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_company`
--

LOCK TABLES `sys_company` WRITE;
/*!40000 ALTER TABLE `sys_company` DISABLE KEYS */;
INSERT INTO `sys_company` VALUES ('BJ','ZH','0,ZH,',2,'中国/北京','1','1','','北京','北京市','','0','system','2019-12-18 10:30:57','system','2019-12-18 11:01:43',''),('CP1001','0','0,',10,'**公司','0','0','CP1001','**公司','**股份有限责任公司','','0','system','2019-11-28 15:05:45','system','2019-12-18 11:05:42',''),('CP1001001','CP1001','0,CP1001,',10,'**公司/*****公司','1','1','CP1001001','*****公司','*****分公司','','0','system','2019-11-28 15:06:32','system','2019-12-18 11:05:47',''),('CQ','ZH','0,ZH,',3,'中国/重庆','1','1','','重庆','重庆市','','0','system','2019-12-18 11:01:22','system','2019-12-18 11:01:22',''),('SH','ZH','0,ZH,',1,'中国/上海','1','1','','上海','上海市','','0','system','2019-12-18 10:23:22','system','2019-12-18 10:30:35',''),('ZH','0','0,',1,'中国','0','0','','中国','中华人民共和国','','0','system','2019-12-18 10:22:54','system','2019-12-18 10:22:54','');
/*!40000 ALTER TABLE `sys_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_company_office`
--

DROP TABLE IF EXISTS `sys_company_office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_company_office` (
  `company_code` varchar(64) NOT NULL COMMENT '公司编码',
  `office_code` varchar(64) NOT NULL COMMENT '机构编码',
  PRIMARY KEY (`company_code`,`office_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_company_office`
--

LOCK TABLES `sys_company_office` WRITE;
/*!40000 ALTER TABLE `sys_company_office` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_company_office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `config_name` varchar(100) NOT NULL COMMENT '名称',
  `config_key` varchar(100) NOT NULL COMMENT '参数键',
  `config_value` varchar(1000) DEFAULT NULL COMMENT '参数值',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES ('1044886589073006592','研发工具-代码生成默认包名','gen.defaultPackageName','org.tangdao.modules','system','2018-09-26 17:49:11','system','2019-08-13 15:47:59','新建项目后，修改该键值，在生成代码的时候就不要再修改了','2'),('1044886591556034560','主框架页-桌面仪表盘首页地址','sys.index.desktopUrl','/desktop','system','2018-09-26 17:49:11','system','2019-08-16 14:38:33','主页面的第一个页签首页桌面地址','0'),('1044886594290720768','主框架页-默认皮肤样式名称','sys.index.skinName','default','system','2018-09-26 17:49:12','system','2019-08-16 09:43:40','default，charcoal','2'),('1044886594357829632','用户登录-登录失败多少次数后显示验证码','sys.login.failedNumAfterValidCode','3','system','2018-09-26 17:49:12','system','2019-03-06 16:38:07','设置为0强制使用验证码登录','2'),('1044886594420744192','用户登录-登录失败多少次数后锁定账号','sys.login.failedNumAfterLockAccount','200','system','2018-09-26 17:49:12','system','2019-01-29 17:04:22','登录失败多少次数后锁定账号','2'),('1044886594559156224','账号自助-是否开启用户注册功能','sys.account.registerUser','true','system','2018-09-26 17:49:12','system','2019-08-13 17:33:49','是否开启注册用户功能','2'),('1044886594626265088','账号自助-允许自助注册的用户类型','sys.account.registerUser.userTypes','-1','system','2018-09-26 17:49:12','system','2019-03-06 16:27:09','允许注册的用户类型（多个用逗号隔开，如果注册时不选择用户类型，则第一个为默认类型）','0'),('1044886594684985344','账号自助-验证码有效时间（分钟）','sys.account.validCodeTimeout','10','system','2018-09-26 17:49:12','system','2019-01-29 17:04:30','找回密码时，短信/邮件验证码有效时间（单位：分钟，0表示不限制）','2'),('1044886594739511296','用户管理-账号默认角色-员工类型','sys.user.defaultRoleCodes.employee','default','system','2018-09-26 17:49:12','system','2019-03-06 16:42:21','所有员工用户都拥有的角色权限（适用于菜单授权查询）','0'),('1044886594798231552','用户管理-账号初始密码','sys.user.initPassword','123456','system','2018-09-26 17:49:12','system','2019-03-06 16:42:18','创建用户和重置密码的时候用户的密码','0'),('1044886594848563200','用户管理-初始密码修改策略','sys.user.initPasswordModify','1','system','2018-09-26 17:49:12','system','2019-01-29 17:05:13','0：初始密码修改策略关闭，没有任何提示；1：提醒用户，如果未修改初始密码，则在登录时和点击菜单就会提醒修改密码对话框；2：强制实行初始密码修改，登录后若不修改密码则不能进行系统操作','2');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_data` (
  `id` varchar(64) NOT NULL COMMENT '字典编码',
  `dict_label` varchar(100) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) NOT NULL COMMENT '字典键值',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `dict_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `description` varchar(500) DEFAULT NULL COMMENT '字典描述',
  `css_style` varchar(500) DEFAULT NULL COMMENT 'css样式（如：color:red)',
  `css_class` varchar(500) DEFAULT NULL COMMENT 'css类名（如：red）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES ('1044886605825056768','是','1','sys_yes_no',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606118658048','否','0','sys_yes_no',40,'','color:#aaa;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606181572608','正常','0','sys_status',20,'','color:#449D44;','','0','system','2018-09-26 17:49:15','system','2019-06-28 14:59:44',''),('1044886606248681472','删除','1','sys_status',30,'','color:#f00;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606366121984','停用','2','sys_status',40,'','color:#f00;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606429036544','冻结','3','sys_status',50,'','color:#fa0;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606496145408','待审','4','sys_status',60,'','color:#01AAED;','','0','system','2018-09-26 17:49:15','system','2019-03-18 14:49:10',NULL),('1044886606563254272','驳回','5','sys_status',70,'','color:#FF5722;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606668111872','草稿','9','sys_status',80,'','color:#aaa;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606747803648','显示','1','sys_show_hide',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606810718208','隐藏','0','sys_show_hide',40,'','color:#aaa;','','0','system','2018-09-26 17:49:15','system','2019-08-14 15:59:22',''),('1044886606877827072','简体中文','zh_CN','sys_lang_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886606949130240','英语','en','sys_lang_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607284674560','管理组','1','sys_user_mgr_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607343394816','菜单','1','sys_menu_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607406309376','权限','2','sys_menu_type',40,'','color:#c243d6;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607456641024','默认权重','20','sys_menu_weight',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607515361280','二级管理员','40','sys_menu_weight',40,'','','','0','system','2018-09-26 17:49:15','system','2019-08-15 09:29:23',''),('1044886607569887232','系统管理员','60','sys_menu_weight',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607628607488','超级管理员','80','sys_menu_weight',60,'','color:#c243d6;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607683133440','国家','0','sys_area_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607733465088','省份直辖市','1','sys_area_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607792185344','地市','2','sys_area_type',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607850905600','区县','3','sys_area_type',60,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607918014464','省级公司','1','sys_office_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886607972540416','市级公司','2','sys_office_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608035454976','部门','3','sys_office_type',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608098369536','正常','0','sys_search_status',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608161284096','停用','2','sys_search_status',40,'','color:#f00;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608224198656','男','1','sys_user_sex',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608295501824','女','2','sys_user_sex',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608358416384','正常','0','sys_user_status',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608421330944','停用','2','sys_user_status',40,'','color:#f00;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608475856896','冻结','3','sys_user_status',50,'','color:#fa0;','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608534577152','员工','employee','sys_user_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886608987561984','高管','1','sys_role_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609033699328','中层','2','sys_role_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609084030976','基层','3','sys_role_type',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609134362624','其它','4','sys_role_type',60,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609201471488','未设置','0','sys_role_data_scope',30,'','','','0','system','2018-09-26 17:49:15','system','2019-06-28 14:26:40',''),('1044886609247608832','全部数据权限','1','sys_role_data_scope',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609293746176','自定数据权限','2','sys_role_data_scope',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609348272128','高管','1','sys_post_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609402798080','中层','2','sys_post_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609461518336','基层','3','sys_post_type',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609537015808','其它','4','sys_post_type',60,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609583153152','接入日志','access','sys_log_type',30,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609633484800','修改日志','update','sys_log_type',40,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609675427840','查询日志','select','sys_log_type',50,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1044886609725759488','登录登出','loginLogout','sys_log_type',60,'','','','0','system','2018-09-26 17:49:15','system','2018-09-26 17:49:15',NULL),('1082935122621489152','用户组','0','sys_user_mgr_type',30,'',NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL),('1207863344615165954','私有','0','sys_visibility_level',1,'只能由成员查看组及其项目。','','','0','system','2019-12-20 11:20:37','system','2019-12-20 11:20:37',''),('1207863527444877313','内部','2','sys_visibility_level',2,'任何登录用户都可以查看组和任何内部项目。','','','0','system','2019-12-20 11:21:21','system','2019-12-20 11:21:21',''),('1207863687495323649','公开','1','sys_visibility_level',3,'不需要任何身份验证就可以查看组和任何公共项目。','','','0','system','2019-12-20 11:21:59','system','2019-12-20 11:21:59','');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_type` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES ('1044886602020823040','是否','sys_yes_no','0','system','2018-09-26 17:49:14','system','2019-08-14 14:54:32',''),('1044886602117292032','状态','sys_status','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602142457856','显示隐藏','sys_show_hide','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602213761024','用户管理类型','sys_user_mgr_type','0','system','2018-09-26 17:49:14','system','2019-11-27 16:35:30',''),('1044886602238926848','菜单类型','sys_menu_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602264092672','菜单权重','sys_menu_weight','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602289258496','区域类型','sys_area_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602310230016','机构类型','sys_office_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602335395840','查询状态','sys_search_status','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',''),('1044886602356367360','用户性别','sys_user_sex','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602377338880','用户状态','sys_user_status','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602402504704','用户类型','sys_user_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602423476224','角色分类','sys_role_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602452836352','角色数据范围','sys_role_data_scope','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602478002176','岗位分类','sys_post_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1044886602503168000','日志类型','sys_log_type','0','system','2018-09-26 17:49:14','system','2018-09-26 17:49:14',NULL),('1207862914728366081','可见性级别','sys_visibility_level','0','system','2019-12-20 11:18:55','system','2019-12-20 11:18:55','');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_employee`
--

DROP TABLE IF EXISTS `sys_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_employee` (
  `emp_code` varchar(64) NOT NULL COMMENT '员工编码',
  `emp_name` varchar(100) NOT NULL COMMENT '员工姓名',
  `emp_name_en` varchar(100) DEFAULT NULL COMMENT '英文名',
  `office_code` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `office_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0在职 1删除 2离职）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`emp_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_employee`
--

LOCK TABLES `sys_employee` WRITE;
/*!40000 ALTER TABLE `sys_employee` DISABLE KEYS */;
INSERT INTO `sys_employee` VALUES ('1207531522102456322','test-admin',NULL,'1199948770308718593','天谕神朝','','','0','system','2019-12-19 14:02:29','system','2019-12-20 10:36:07',NULL),('admin','管理员','admin',NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL),('user10_clu3','user10_clu3','user10_clu3','1199948770308718593','天谕神朝','SH','上海','2',NULL,NULL,'system','2019-12-19 13:38:46',NULL);
/*!40000 ALTER TABLE `sys_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_employee_post`
--

DROP TABLE IF EXISTS `sys_employee_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_employee_post` (
  `emp_code` varchar(64) NOT NULL COMMENT '员工编码',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  PRIMARY KEY (`emp_code`,`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_employee_post`
--

LOCK TABLES `sys_employee_post` WRITE;
/*!40000 ALTER TABLE `sys_employee_post` DISABLE KEYS */;
INSERT INTO `sys_employee_post` VALUES ('user10_clu3','ABCD');
/*!40000 ALTER TABLE `sys_employee_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `log_type` varchar(50) NOT NULL COMMENT '日志类型',
  `log_title` varchar(500) NOT NULL COMMENT '日志标题',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_by_name` varchar(100) NOT NULL COMMENT '用户名称',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `request_uri` varchar(500) DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `request_params` longtext COMMENT '操作提交的数据',
  `diff_modify_data` text COMMENT '新旧数据比较结果',
  `biz_key` varchar(64) DEFAULT NULL COMMENT '业务主键',
  `biz_type` varchar(64) DEFAULT NULL COMMENT '业务类型',
  `remote_addr` varchar(255) NOT NULL COMMENT '操作IP地址',
  `server_addr` varchar(255) NOT NULL COMMENT '请求服务器地址',
  `is_exception` char(1) DEFAULT NULL COMMENT '是否异常',
  `exception_info` text COMMENT '异常信息',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称/操作系统',
  `browser_name` varchar(100) DEFAULT NULL COMMENT '浏览器名称',
  `execute_time` decimal(19,0) DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `menu_code` varchar(64) NOT NULL COMMENT '菜单编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_type` char(1) NOT NULL COMMENT '菜单类型（1菜单 2权限 3开发）',
  `menu_href` varchar(1000) DEFAULT NULL COMMENT '链接',
  `menu_target` varchar(20) DEFAULT NULL COMMENT '目标',
  `menu_icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `permission` varchar(1000) DEFAULT NULL COMMENT '权限标识',
  `weight` decimal(4,0) DEFAULT NULL COMMENT '菜单权重',
  `is_show` char(1) NOT NULL COMMENT '是否显示（1显示 0隐藏）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `tree_leaf` char(1) DEFAULT NULL COMMENT '是否最末级',
  `tree_level` char(1) DEFAULT NULL COMMENT '层次级别',
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1044886626813353984','1159363081439408129','0,1159363081439408129,',40,'系统管理/组织管理','组织管理','1','','','la la-shekel','',40,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:41','','0','1'),('1044886627991953408','1044886626813353984','0,1159363081439408129,1044886626813353984,',10,'系统管理/组织管理/用户管理','用户管理','1','/sys/empUser/index','','','',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','0','2'),('1044886628075839488','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',30,'系统管理/组织管理/用户管理/查看','查看','2','','','','sys:user:view',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628151336960','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',40,'系统管理/组织管理/用户管理/编辑','编辑','2','','','','sys:user:edit',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628239417344','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',60,'系统管理/组织管理/用户管理/分配角色','分配角色','2','','','','sys:user:authRole',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628323303424','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',50,'系统管理/组织管理/用户管理/分配数据','分配数据','2','','','','sys:user:authDataScope',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628407189504','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',60,'系统管理/组织管理/用户管理/停用启用','停用启用','2','','','','sys:user:updateStatus',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628507852800','1044886627991953408','0,1159363081439408129,1044886626813353984,1044886627991953408,',70,'系统管理/组织管理/用户管理/重置密码','重置密码','2','','','','sys:user:resetpwd',40,'1','0','system','2018-09-26 17:49:20','system','2019-12-17 09:29:12','','1','3'),('1044886628616904704','1044886626813353984','0,1159363081439408129,1044886626813353984,',50,'系统管理/组织管理/机构管理','机构管理','1','/sys/office/index','','','',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:41','','0','2'),('1044886628709179392','1044886628616904704','0,1159363081439408129,1044886626813353984,1044886628616904704,',30,'系统管理/组织管理/机构管理/查看','查看','2','','','','sys:office:view',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886628801454080','1044886628616904704','0,1159363081439408129,1044886626813353984,1044886628616904704,',40,'系统管理/组织管理/机构管理/编辑','编辑','2','','','','sys:office:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886628893728768','1044886626813353984','0,1159363081439408129,1044886626813353984,',70,'系统管理/组织管理/公司管理','公司管理','1','/sys/company/list','','','',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','0','2'),('1044886628990197760','1044886628893728768','0,1159363081439408129,1044886626813353984,1044886628893728768,',30,'系统管理/组织管理/公司管理/查看','查看','2','','','','sys:company:view',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886629086666752','1044886628893728768','0,1159363081439408129,1044886626813353984,1044886628893728768,',40,'系统管理/组织管理/公司管理/编辑','编辑','2','','','','sys:company:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886629195718656','1044886626813353984','0,1159363081439408129,1044886626813353984,',70,'系统管理/组织管理/岗位管理','岗位管理','1','/sys/post/list','','','',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','0','2'),('1044886629313159168','1044886629195718656','0,1159363081439408129,1044886626813353984,1044886629195718656,',30,'系统管理/组织管理/岗位管理/查看','查看','2','','','','sys:post:view',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886629443182592','1044886629195718656','0,1159363081439408129,1044886626813353984,1044886629195718656,',40,'系统管理/组织管理/岗位管理/编辑','编辑','2','','','','sys:post:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886629623537664','1159364927004471298','0,1159363081439408129,1159364927004471298,',30,'系统管理/系统设置/角色管理','角色管理','1','/sys/role/list','','','sys:role:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','1','2'),('1044886630026190848','1159364927004471298','0,1159363081439408129,1159364927004471298,',36,'系统管理/系统设置/菜单管理','菜单管理','1','/sys/menu/list','','','sys:menu,sys:menu:view,sys:menu:edit',80,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','1','2'),('1044886630252683264','1159364927004471298','0,1159363081439408129,1159364927004471298,',50,'系统管理/系统设置/参数设置','参数设置','1','/sys/config/list','','icon-wrench','sys:config,sys:config:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','1','2'),('1044886630344957952','1159364927004471298','0,1159363081439408129,1159364927004471298,',60,'系统管理/系统设置/字典管理','字典管理','1','/sys/dictType/list','','','',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42','','0','2'),('1044886630454009856','1044886630344957952','0,1159363081439408129,1159364927004471298,1044886630344957952,',30,'系统管理/系统设置/字典管理/类型查看','类型查看','2','','','','sys:dictType:view',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886630550478848','1044886630344957952','0,1159363081439408129,1159364927004471298,1044886630344957952,',40,'系统管理/系统设置/字典管理/类型编辑','类型编辑','2','','','','sys:dictType:edit',80,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886630646947840','1044886630344957952','0,1159363081439408129,1159364927004471298,1044886630344957952,',50,'系统管理/系统设置/字典管理/数据查看','数据查看','2','','','','sys:dictData:view',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886630747611136','1044886630344957952','0,1159363081439408129,1159364927004471298,1044886630344957952,',60,'系统管理/系统设置/字典管理/数据编辑','数据编辑','2','','','','sys:dictData:edit',60,'1','0','system','2018-09-26 17:49:20','system','2019-11-27 16:27:42',NULL,'1','3'),('1044886630856663040','1159364927004471298','0,1159363081439408129,1159364927004471298,',70,'系统管理/系统设置/行政区划','行政区划','1','area','','icon-map','sys:area',60,'0','0','system','2018-09-26 17:49:21','system','2019-11-27 16:27:42','','1','2'),('1044886631477420032','1159365012094316545','0,1159363081439408129,1159365012094316545,',40,'系统管理/系统监控/访问日志','访问日志','1','/sys/log/list','','','sys:log',60,'1','0','system','2018-09-26 17:49:21','system','2019-11-27 16:27:42','','1','2'),('1044886631703912448','1159365012094316545','0,1159363081439408129,1159365012094316545,',60,'系统管理/系统监控/缓存监控','缓存监控','1','/state/cache/list','','','sys:stste:cache',80,'1','0','system','2018-09-26 17:49:21','system','2019-12-19 15:55:47','','1','2'),('1044886631812964352','1159365012094316545','0,1159363081439408129,1159365012094316545,',70,'系统管理/系统监控/服务器监控','服务器监控','1','/state/server/detail','','','sys:state:server',80,'1','0','system','2018-09-26 17:49:21','system','2019-12-19 15:56:34','','1','2'),('1159361830123335682','0','0,',1000,'控制台','控制台','1','','','','',60,'1','0','system','2019-08-08 15:12:55','system','2019-12-19 11:04:34','','0','0'),('1159363081439408129','0','0,',2000,'系统管理','系统管理','1','','','','',60,'1','0','system','2019-08-08 15:17:53','system','2019-11-27 16:27:41','','0','0'),('1159364927004471298','1159363081439408129','0,1159363081439408129,',60,'系统管理/系统设置','系统设置','1','','','la la-gear','',60,'1','0','system','2019-08-08 15:25:13','system','2019-11-27 16:27:42','','0','1'),('1159365012094316545','1159363081439408129','0,1159363081439408129,',70,'系统管理/系统监控','系统监控','1','','','la la-simplybuilt','',60,'1','0','system','2019-08-08 15:25:34','system','2019-11-27 16:27:42','','0','1'),('1163391416070868994','1159365012094316545','0,1159363081439408129,1159365012094316545,',80,'系统管理/系统监控/在线用户','在线用户','1','/state/online/list','','','',60,'1','0','system','2019-08-19 18:05:03','system','2019-12-19 15:55:57','','1','2'),('1163704530989568002','0','0,',9001,'开发者','开发者','1','','','','',80,'1','0','system','2019-08-20 14:49:15','system','2019-08-20 14:49:15','','0','0'),('1163704668399161345','1163704530989568002','0,1163704530989568002,',10,'开发者/图标仓库','图标仓库','1','//tags/iconselect','','la la-dropbox','',80,'1','0','system','2019-08-20 14:49:48','system','2019-08-22 17:34:04','','1','1'),('1163730557321129986','1163704530989568002','0,1163704530989568002,',20,'开发者/生成代码','生成代码','1','/genTable/list','','la la-binoculars','',80,'1','0','system','2019-08-20 16:32:41','system','2019-08-21 09:39:38','','1','1'),('1199939331455385601','1159361830123335682','0,1159361830123335682,',10,'控制台/客户管理','客户管理','1','/cst','','la la-user-secret','',40,'1','0','system','2019-11-28 14:33:25','system','2019-12-19 11:14:16','','1','1'),('1207498640306774017','1159361830123335682','0,1159361830123335682,',2,'控制台/应用管理','应用管理','1','/applications','','la la-connectdevelop','',60,'1','0','system','2019-12-19 11:11:25','system','2019-12-19 11:11:25','开发者账号','1','1'),('1207500615480045569','1159364927004471298','0,1159363081439408129,1159364927004471298,',35,'系统管理/系统设置/系统管理员','系统管理员','1','/sys/admin/list','','','',80,'1','0','system','2019-12-19 11:19:16','system','2019-12-19 11:19:16','','1','2');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_office`
--

DROP TABLE IF EXISTS `sys_office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_office` (
  `office_code` varchar(64) NOT NULL COMMENT '机构编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `tree_leaf` char(1) DEFAULT NULL COMMENT '是否最末级',
  `tree_level` char(1) DEFAULT NULL COMMENT '层次级别',
  `view_code` varchar(100) NOT NULL COMMENT '机构代码',
  `office_name` varchar(100) NOT NULL COMMENT '机构名称',
  `full_name` varchar(200) NOT NULL COMMENT '机构全称',
  `office_type` char(1) NOT NULL COMMENT '机构类型',
  `leader` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(100) DEFAULT NULL COMMENT '办公电话',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `email` varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`office_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_office`
--

LOCK TABLES `sys_office` WRITE;
/*!40000 ALTER TABLE `sys_office` DISABLE KEYS */;
INSERT INTO `sys_office` VALUES ('1199948515316006913','0','0,',10,'昊天仙门','0','0','JG1001','昊天仙门','昊天仙门','1','','','','','','0','system','2019-11-28 15:09:55','system','2019-11-28 15:09:55',''),('1199948770308718593','0','0,',20,'天谕神朝','1','0','JG1002','天谕神朝','天谕神朝','1','','','','','','0','system','2019-11-28 15:10:55','system','2019-11-28 15:10:55',''),('1199948998453690370','1199948515316006913','0,1199948515316006913,',10,'昊天仙门/皇府','1','1','JG1001001','皇府','皇府','2','','','','','','0','system','2019-11-28 15:11:50','system','2019-11-28 15:11:50',''),('1199949146613284866','1199948515316006913','0,1199948515316006913,',20,'昊天仙门/圣府','1','1','JG1001002','圣府','圣府','2','','','','','','0','system','2019-11-28 15:12:25','system','2019-12-18 11:04:39','');
/*!40000 ALTER TABLE `sys_office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_post` (
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
  `post_type` char(1) DEFAULT NULL COMMENT '岗位分类（高管、中层、基层）',
  `post_sort` decimal(10,0) DEFAULT NULL COMMENT '岗位排序（升序）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES ('1001DDS','项目经理','1',10,'0','system','2019-08-26 09:36:38','system','2019-08-28 08:49:46','fabcd'),('ABCD','开发岗','3',10,'0','system','2019-12-18 14:50:54','system','2019-12-18 14:50:54','');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_sort` decimal(10,0) DEFAULT NULL COMMENT '角色排序（升序）',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围设置（0未设置  1全部数据 2自定义数据）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `user_type` varchar(64) DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('corpAdmin','系统管理员',200,'0','0','system','2018-09-26 17:49:18','admin','2019-07-22 18:17:22','客户方使用的管理员角色，客户方管理员，集团管理员',NULL),('default','默认角色',100,'0','0','system','2018-09-26 17:49:18','system','2019-01-29 17:06:11','非管理员用户，共有的默认角色，在参数配置里指定',NULL),('dept','部门经理',40,'0','0','system','2018-09-26 17:49:18','system','2019-09-03 15:27:37','部门经理','employee'),('user','普通员工',30,'0','0','system','2018-09-26 17:49:18','system','2019-09-03 15:27:47','','employee');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `menu_code` varchar(64) NOT NULL COMMENT '菜单编码',
  PRIMARY KEY (`role_code`,`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('corpAdmin','1044886626813353984'),('corpAdmin','1044886627991953408'),('corpAdmin','1044886628075839488'),('corpAdmin','1044886628151336960'),('corpAdmin','1044886628239417344'),('corpAdmin','1044886628323303424'),('corpAdmin','1044886628407189504'),('corpAdmin','1044886628507852800'),('corpAdmin','1044886628616904704'),('corpAdmin','1044886628709179392'),('corpAdmin','1044886628801454080'),('corpAdmin','1044886628893728768'),('corpAdmin','1044886628990197760'),('corpAdmin','1044886629086666752'),('corpAdmin','1044886629195718656'),('corpAdmin','1044886629313159168'),('corpAdmin','1044886629443182592'),('corpAdmin','1044886629623537664'),('corpAdmin','1044886630252683264'),('corpAdmin','1044886630344957952'),('corpAdmin','1044886630454009856'),('corpAdmin','1044886630646947840'),('corpAdmin','1044886630747611136'),('corpAdmin','1044886630856663040'),('corpAdmin','1044886631477420032'),('corpAdmin','1159361830123335682'),('corpAdmin','1159363081439408129'),('corpAdmin','1159364927004471298'),('corpAdmin','1159365012094316545'),('default','1044886626813353984'),('default','1044886627991953408'),('default','1044886628075839488'),('default','1044886628407189504'),('default','1044886628507852800'),('test','1044886629623537664'),('test','1159363081439408129'),('test','1159364746838142977'),('user','1044886626813353984'),('user','1044886627991953408'),('user','1044886628075839488');
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_code` varchar(100) NOT NULL COMMENT '用户编码',
  `username` varchar(100) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `nickname` varchar(100) NOT NULL COMMENT '用户昵称',
  `email` varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(100) DEFAULT NULL COMMENT '办公电话',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别',
  `avatar` varchar(1000) DEFAULT NULL COMMENT '头像路径',
  `sign` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `user_type` varchar(16) NOT NULL COMMENT '用户类型',
  `mgr_type` char(1) NOT NULL COMMENT '管理员类型（0非管理员 1系统管理员）',
  `last_login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `freeze_date` datetime DEFAULT NULL COMMENT '冻结时间',
  `freeze_cause` varchar(200) DEFAULT NULL COMMENT '冻结原因',
  `user_weight` decimal(8,0) DEFAULT '0' COMMENT '用户权重（降序）',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1删除 2停用 3冻结）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1105305559767789568','demo','696b88143b4384907e9ce81540a547ac15d7d3a076863a9f33404825','示例1','service@alibaba.com','188','188',NULL,NULL,'','member','0','172.28.220.60','2019-05-30 13:33:26',NULL,NULL,0,'0','system','2019-03-12 11:12:42','system','2019-06-28 14:52:04','这是一个会员'),('1106506595807027200','test','cf88c985b019702407840e9986554aa88f068afd2dda87e5fccfa853','测试账号','test@alibaba.com','9','11','2',NULL,'10','member','0','192.168.82.219','2019-03-15 18:45:18',NULL,NULL,0,'2','system','2019-03-15 18:45:05','system','2019-06-28 15:07:14','5'),('1115161529677676544','test001','2ccef6d3308b5ccf01a15aa9f4180198838f651652fb3ab79d9a253a','test001','','','','2',NULL,'','member','0','10.10.0.137','2019-04-08 15:56:52',NULL,NULL,0,'2','system','2019-04-08 15:56:42','system','2019-06-28 15:06:53',''),('1207531522102456322','test-admin','316cdaa58c023b4264c17b25a217b57db7c7c1578ef491efe2203edf','test-admin','','','',NULL,NULL,NULL,'employee','0','127.0.0.1','2019-12-19 14:21:27',NULL,NULL,0,'0','system','2019-12-19 13:22:04','system','2019-12-20 10:36:07',''),('admin','admin','5c436c2dad006ec0d64a5f4e1f35187765c5b1100ebf365ce9df1cc5','系统管理员','ruyangit@163.com','15888888888','',NULL,'/images/default.jpg',NULL,'employee','1','172.28.220.60','2019-05-29 17:56:23',NULL,NULL,0,'0','system','2018-09-26 17:49:23','system','2019-12-23 13:51:03','客户方使用的系统管理员，用于一些常用的基础数据配置。'),('system','system','66bb0152d2f77e77319cae1da426797163ed747605b0485f099a55f9','超级管理员','admin@aliyeye.com','13800000000',NULL,NULL,NULL,NULL,'none','0','127.0.0.1','2019-12-25 18:32:22',NULL,NULL,0,'0','system','2018-09-26 17:49:23','system','2019-12-25 18:32:22','开发者使用的最高级别管理员，主要用于开发和调试。'),('user10_clu3','user10','378948624d48effbad692ab75b9d0bc6bfb6e9a41da7b78c4a1c53b3','用户10','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'2','system','2018-09-26 17:49:29','system','2019-12-19 13:38:46',''),('user11_txir','user11','d7cc86d07dceb569eff0c45ac308c7162a0e2fd1dd33ad6f40d6de43','用户11','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user12_tnck','user12','2942b1fbe4e644bcc6c105a0f7c3f6c7e051be6a5724e95522af770d','用户12','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user13_rlpt','user13','d0060fd25260ed3ade3b78053fac43fdb0327a557d1d65b4a19386d3','用户13','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'2','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user15_uf6s','user15','6dd01bf5df7268e2f658a7fc92a9e2fe4471c95246c71bd05319e693','用户15','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user16_wug4','user16','d19b2bc2a6928c69831fcdb3a3067edd46024c0bd4127b54f0a28493','用户16','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2019-12-16 16:35:36',''),('user17_av1l','user17','222d18e062d7480603bf74d29bdc1b9ab3744556c1645492ed5513a0','用户17','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2019-11-28 15:00:06',''),('user18_hm5f','user18','40fbad0b2e8c298cc795aace1a15efefadc81ba71dee8b95118c9758','用户18','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user1_1m54','user1','$2a$08$z/0FrR5Zhu02AkQT4AUzi.u24HTn3sOxAC1J1Av9lG08maVDho5qO','用户01','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:28','system','2018-09-26 17:49:28',NULL),('user20_yegb','user20','b1777ce964d693834595652050db7ab22d20cec7dc848792b3f1cec8','用户20','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user21_szgh','user21','9df34fdc28c484f92020fb20190305e8fa729d403d7a10e214972a68','用户21','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user22_rx15','user22','a27e5ffdf924e2f4bf41ba60f6a7da3072d7fe0248164f37db6d234e','用户22','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user23_iugi','user23','f203800bb330bcd253f44e156afe02647a19da95e0964768fae46811','用户23','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'2','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user2_zxlx','user2','8dd3d9c3fda01fcc581416d44ab8603fd4a1c83dbb15e1cffe158ee7','用户02','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user3_cj63','user3','75be78a17d343f40e361ff792e08877622f1500db9fe4972dcb64584','用户03','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user4_nuef','user4','7bb34ca74bf462f01894afb8e7b6088af7c03bbb8292e2e549782f83','用户04','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user5_iq5n','user5','434552b650676d564fc75d419bba078538fad203d4098e9dc7406826','用户05','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user6_szow','user6','934e0948bd9a8997d74b75af039a649f3c327501c9eea7c155b5c650','用户06','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user7_n6he','user7','555ca388945c274a0ee63fdccce0f33dcb3cd17d0ffa36f131012bb8','用户07','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user8_ldj7','user8','a622f96e011cd166f95b316b452a0c6fc54308cae1c49ee0a90b4352','用户08','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL),('user9_y4ut','user9','33af878ee63b4066a51fefa92125d45d8936fb235dc68ca8f892342f','用户09','user@test.com','18555555555','053188888888',NULL,NULL,NULL,'employee','0',NULL,NULL,NULL,NULL,0,'0','system','2018-09-26 17:49:29','system','2018-09-26 17:49:29',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_post` (
  `user_code` varchar(64) NOT NULL COMMENT '用户编码',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  PRIMARY KEY (`user_code`,`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_code` varchar(100) NOT NULL COMMENT '用户编码',
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`user_code`,`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1105305559767789568','APP'),('1207531522102456322','user'),('admin','corpAdmin'),('user11_txir','user'),('user13_rlpt','user'),('user14_lnb8','dept'),('user16_wug4','user'),('user17_av1l','user'),('user19_ih6s','user'),('user20_yegb','user'),('user21_szgh','dept'),('user22_rx15','user'),('user23_iugi','user'),('user2_zxlx','user'),('user3_cj63','user'),('user4_nuef','dept'),('user5_iq5n','user'),('user6_szow','user'),('user7_n6he','dept'),('user8_ldj7','user'),('user9_y4ut','user');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'tangdao_dev'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-30  8:42:36
