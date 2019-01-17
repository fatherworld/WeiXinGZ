package com.ys.widgets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class widgetReturn {
	 @SerializedName("errorCode")
	 @Expose
	 private int errorCode;
	 
	 @SerializedName("errmsg")
	 @Expose
	 private String errmsg;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
