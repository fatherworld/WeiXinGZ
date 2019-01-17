package com.ys.api;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ys.common.webValue;
import com.ys.common.Url;
import com.ys.common.Explains;
public class TranslateResponseDTO
{

    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("translation")
    @Expose
    private List<String> translation = null;
    @SerializedName("web")
    @Expose
    private List<webValue> web = null;
    @SerializedName("dict")
    @Expose
    private Url dict = null;
    
    @SerializedName("webdict")
    @Expose
    private Url webdict = null;
    @SerializedName("basic")
    @Expose
    private Explains basic = null;
    public Url getDict() {
		return dict;
	}

	public void setDict(Url dict) {
		this.dict = dict;
	}

	public Url getWebdict() {
		return webdict;
	}

	public void setWebdict(Url webdict) {
		this.webdict = webdict;
	}

	public Explains getBasic() {
		return basic;
	}

	public void setBasic(Explains basic) {
		this.basic = basic;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	@SerializedName("l")
    @Expose
    private String l = null;

    public List<String> getTranslation() {
		return translation;
	}

	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<webValue> getWeb() {
		return web;
	}

	public void setWeb(List<webValue> web) {
		this.web = web;
	}

	
    public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	
}
