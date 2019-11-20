package com.zjcds.cde.scheduler.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author J on 20191119
 */
@Getter
@Setter
public class RepositoryTreeForm {

	private String id;
	private Integer rId;
	private String parent;
	private String text;
	private String icon;
	private Object state;
	private String type;
	private boolean isLasted;
	private String path;
	private Integer repositoryId;

	public RepositoryTreeForm(String id, String parent, String text, String icon, Object state, String type,
                              boolean isLasted, String path) {
		super();
		this.id = id;
		this.parent = parent;
		this.text = text;
		this.icon = icon;
		this.state = state;
		this.type = type;
		this.isLasted = isLasted;
		this.path = path;
	}
	public RepositoryTreeForm() {
	}
	
	@Override
	public String toString() {
		return "RepositoryTree [id=" + id + ", parent=" + parent + ", text=" + text + ", icon=" + icon + ", state="
				+ state + ", type=" + type + ", isLasted=" + isLasted + ", path=" + path + "]";
	}	
}