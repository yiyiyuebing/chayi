package pub.makers.shop.store.vo;

import pub.makers.shop.store.entity.BankCard;

import java.io.Serializable;

public class BankCardExtendVo implements Serializable {

	private String id;
	private String name;
	private String card;
	private String bankName;
	private String bankCard;
	private String mobile;
	private String shopId;
	private String bankAddress;
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public BankCard toBankCard() {
		BankCard bankCard = new BankCard();
		bankCard.setBankCardId(this.getId() == null ? null : Long.valueOf(this.getId()));
		bankCard.setName(this.getName());
		bankCard.setIdCard(this.getCard());
		bankCard.setBankName(this.getBankName());
		bankCard.setBankCard(this.getBankCard());
		bankCard.setMobile(this.getMobile());
		bankCard.setConnectId(this.getShopId() == null ? null : Long.valueOf(this.getShopId()));
		bankCard.setBankAddress(this.getBankAddress());
		return bankCard;
	}
}