package com.foogolf.tunnel;

import java.util.Objects;

public class TunnelRequest {

	private String cloudPC;
	private String password;

	public String getCloudPC() {
		return cloudPC;
	}
	public void setCloudPC(String cloudPC) {
		this.cloudPC = cloudPC;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		TunnelRequest other = (TunnelRequest) obj;
		return Objects.equals(cloudPC, other.cloudPC) &&
				Objects.equals(password, other.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cloudPC, password);
	}

}
