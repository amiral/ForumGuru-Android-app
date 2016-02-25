package com.example.omc.forumguru;

public class Items {

	private String itemName;
	private String itemDesc;
	private long iconId;

	public Items(String itemName, long iconId) {
		this.itemName = itemName;

		this.iconId = iconId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public long getIconId() {
		return iconId;
	}

	public void setIconId(long iconId) {
		this.iconId = iconId;
	}
}
