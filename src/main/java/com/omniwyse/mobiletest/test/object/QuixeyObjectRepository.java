package com.omniwyse.mobiletest.test.object;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.omniwyse.mobiletest.data.DataManager;

public class QuixeyObjectRepository implements ObjectRepository {

	private String id;
	private String name;
	private String parent;
	private String buildXpathRule;
	private String className;
	private int index;
	private String NAF;
	private String text;
	private String resourceId;
	private String contentDesc;

	public QuixeyObjectRepository(XSSFRow row) {
		if (row.getCell(0) != null)
			id = row.getCell(0).toString();
		if (row.getCell(1) != null)
			name = row.getCell(1).toString();
		if (row.getCell(2) != null)
			parent = row.getCell(2).toString();
		if (row.getCell(3) != null)
			buildXpathRule = row.getCell(3).toString();
		if (row.getCell(4) != null)
			className = row.getCell(4).toString();
		if (row.getCell(5) != null)
			index = (Integer.parseInt(row.getCell(5).toString()));
		if (row.getCell(6) != null)
			NAF = row.getCell(6).toString();
		if (row.getCell(7) != null)
			text = row.getCell(7).toString();
		if (row.getCell(8) != null)
			resourceId = row.getCell(8).toString();
		if (row.getCell(9) != null)
			contentDesc = row.getCell(9).toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getBuildXpathRule() {
		return buildXpathRule;
	}

	public void setBuildXpathRule(String buildXpathRule) {
		this.buildXpathRule = buildXpathRule;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String buildXpath() throws NoSuchFieldException, SecurityException {
		if (!buildXpathRule.contains("::") && !buildXpathRule.contains(":"))
			return className;
		if (buildXpathRule.split("::").length == 1) {
			if (buildXpathRule.split(":").length != 1)
				return "//" + className + buildXpathAttributes(buildXpathRule.split(":")[1].split(","));
			else
				return "//" + className;
		}
		String ultimateParent = buildXpathRule.split("::")[0].split(":")[0];
		ObjectRepository parentObject = DataManager.objRepositories.get(parent);
		String xpath = "/" + className + buildXpathAttributes(buildXpathRule.split("::")[1].split(":")[1].split(","));
		while (!((QuixeyObjectRepository) parentObject).getName().equals(ultimateParent)) {
			xpath = "/" + ((QuixeyObjectRepository) parentObject).getClassName() + xpath;
			parentObject = DataManager.objRepositories.get(((QuixeyObjectRepository) parentObject).getParent());
		}
		xpath = "//" + className + ((QuixeyObjectRepository) parentObject).buildXpathAttributes(buildXpathRule.split("::")[0].split(":")[1].split(",")) + xpath;
		return xpath;
	}

	public String buildXpathAttributes(String[] attributes) throws NoSuchFieldException, SecurityException {
		String xpath = "[";
		for (int i = 0; i < attributes.length; i++) {
			if (i != 0)
				xpath = xpath + ",";
			xpath = xpath + "@" + attributes[i] + "=" + "'" + getAttribute(attributes[i]) + "'";
		}
		xpath = xpath + "]";
		return xpath;
	}

	public String getAttribute(String attribute) {
		if (attribute.equals("resource-id"))
			return getResourceId();
		else if (attribute.equals("class"))
			return getClassName();
		else if (attribute.equals("content-desc"))
			return getContentDesc();
		else if (attribute.equals("NAF"))
			return getNAF();
		else if (attribute.equals("text"))
			return getText();
		else if (attribute.equals("index"))
			return "" + getIndex();
		else
			return null;
	}

	public String getNAF() {
		return NAF;
	}

	public void setNAF(String nAF) {
		NAF = nAF;
	}

}
