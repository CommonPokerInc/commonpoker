
package com.poker.common.entity;
/*
 * author FrankChan
 * description 
 * time 2014-8-25
 *
 */
public class UserInfo {
	private String name;
	
	private String id;
	
	private boolean isQuit ;
	
	private int baseMoney;
	
	private int aroundChip;
	
	private int aroundSumChip = 0;

	public int getAroundSumChip() {
        return aroundSumChip;
    }

    public void setAroundSumChip(int aroundSumChip) {
        this.aroundSumChip = aroundSumChip;
    }

    public boolean isQuit() {
		return isQuit;
	}

	public void setQuit(boolean isQuit) {
		this.isQuit = isQuit;
	}

	public int getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(int baseMoney) {
		this.baseMoney = baseMoney;
	}

	public int getAroundChip() {
		return aroundChip;
	}

	public void setAroundChip(int aroundChip) {
		this.aroundChip = aroundChip;
	}

	public String getId() {
        return id;
    }

    public void setId(String iD) {
        id = iD;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


