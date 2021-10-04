package com.jcm.models;

public class LogJson {
	private int idApi;
	private String ipRemote;
	private String userRemote;
	private String passRemote;
	private String hostRemote;
	private String browserRemote;
	private String string;
	private String result;
	private String codProv;
	private String key;
	
	public LogJson() {
		super();
	}

	public LogJson(int idApi, String ipRemote, String userRemote, String passRemote, String hostRemote, String browserRemote,
			String string, String result, String codProv, String key) {
		super();
		this.idApi = idApi;
		this.ipRemote = ipRemote;
		this.userRemote = userRemote;
		this.passRemote = passRemote;
		this.hostRemote = hostRemote;
		this.browserRemote = browserRemote;
		this.string = string;
		this.result = result;
		this.codProv = codProv;
		this.key = key;
	}

	public String getIpRemote() {
		return ipRemote;
	}

	public void setIpRemote(String ipRemote) {
		this.ipRemote = ipRemote;
	}

	public String getUserRemote() {
		return userRemote;
	}

	public void setUserRemote(String userRemote) {
		this.userRemote = userRemote;
	}

	public String getPassRemote() {
		return passRemote;
	}

	public void setPassRemote(String passRemote) {
		this.passRemote = passRemote;
	}

	public String getHostRemote() {
		return hostRemote;
	}

	public void setHostRemote(String hostRemote) {
		this.hostRemote = hostRemote;
	}

	public String getBrowserRemote() {
		return browserRemote;
	}

	public void setBrowserRemote(String browserRemote) {
		this.browserRemote = browserRemote;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCodProv() {
		return codProv;
	}

	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getIdApi() {
		return idApi;
	}

	public void setIdApi(int idApi) {
		this.idApi = idApi;
	}
	
}
