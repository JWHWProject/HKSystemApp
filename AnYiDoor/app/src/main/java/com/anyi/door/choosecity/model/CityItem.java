package com.anyi.door.choosecity.model;


import com.anyi.door.choosecity.widget.ContactItemInterface;

public class CityItem implements ContactItemInterface
{


	private String nickName;
	private String fullName;
	private int id;
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CityItem(String nickName, String fullName, int id, int type) {
		this.nickName = nickName;
		this.fullName = fullName;
		this.id = id;
		this.type = type;
	}

	public CityItem() {
	}

	@Override
	public String getItemForIndex()
	{
		return fullName;
	}

	@Override
	public String getDisplayInfo()
	{
		return nickName;
	}

	@Override
	public int getDisplayId() {
		return id;
	}

	@Override
	public int getDisplayType() {
		return type;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

}
