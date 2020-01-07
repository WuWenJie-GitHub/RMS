package org.tangdao.modules.gen.model.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name="config")
public class GenConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<GenCategory> tplCategoryList;	// 代码模板分类
	private List<GenDict> javaTypeList;		// Java类型
	private List<GenDict> queryTypeList;		// 查询类型
	private List<GenDict> showTypeList;		// 显示类型
	private List<GenDict> fieldValidList;		// 显示类型
	private List<GenDict> gridRowColList;		// 显示类型

	public GenConfig() {
		super();
	}

	@XmlElementWrapper(name = "tplCategory")
	@XmlElement(name = "category")
	public List<GenCategory> getTplCategoryList() {
		return tplCategoryList;
	}

	@XmlElementWrapper(name = "javaType")
	@XmlElement(name = "dict")
	public List<GenDict> getJavaTypeList() {
		return javaTypeList;
	}

	public void setJavaTypeList(List<GenDict> javaTypeList) {
		this.javaTypeList = javaTypeList;
	}

	@XmlElementWrapper(name = "queryType")
	@XmlElement(name = "dict")
	public List<GenDict> getQueryTypeList() {
		return queryTypeList;
	}

	public void setQueryTypeList(List<GenDict> queryTypeList) {
		this.queryTypeList = queryTypeList;
	}

	@XmlElementWrapper(name = "showType")
	@XmlElement(name = "dict")
	public List<GenDict> getShowTypeList() {
		return showTypeList;
	}

	public void setShowTypeList(List<GenDict> showTypeList) {
		this.showTypeList = showTypeList;
	}
	
	@XmlElementWrapper(name = "fieldValid")
	@XmlElement(name = "dict")
	public List<GenDict> getFieldValidList() {
		return fieldValidList;
	}
	
	@XmlElementWrapper(name = "gridRowCol")
	@XmlElement(name = "dict")
	public List<GenDict> getGridRowColList() {
		return gridRowColList;
	}


}
