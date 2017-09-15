package com.library.manage.conf;

import org.springframework.beans.factory.annotation.Value;

public class GlobalConstants {

    @Value("${SSO_HOST}")
    private String SSO_HOST;
    @Value("${MANAGE_HOST}")
    private String MANAGE_HOST;
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    public String getSSO_HOST() {
	return SSO_HOST;
    }

    public void setSSO_HOST(String sSO_HOST) {
	SSO_HOST = sSO_HOST;
    }

    public String getMANAGE_HOST() {
	return MANAGE_HOST;
    }

    public void setMANAGE_HOST(String mANAGE_HOST) {
	MANAGE_HOST = mANAGE_HOST;
    }

    public String getCOOKIE_TOKEN_KEY() {
	return COOKIE_TOKEN_KEY;
    }

    public void setCOOKIE_TOKEN_KEY(String cOOKIE_TOKEN_KEY) {
	COOKIE_TOKEN_KEY = cOOKIE_TOKEN_KEY;
    }
}
