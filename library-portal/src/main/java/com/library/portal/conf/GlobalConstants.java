package com.library.portal.conf;

import org.springframework.beans.factory.annotation.Value;

public class GlobalConstants {

    @Value("${SSO_HOST}")
    private String SSO_HOST;
    @Value("${PORTAL_HOST}")
    private String PORTAL_HOST;
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    public String getCOOKIE_TOKEN_KEY() {
	return COOKIE_TOKEN_KEY;
    }

    public void setCOOKIE_TOKEN_KEY(String cOOKIE_TOKEN_KEY) {
	COOKIE_TOKEN_KEY = cOOKIE_TOKEN_KEY;
    }

    public String getSSO_HOST() {
	return SSO_HOST;
    }

    public void setSSO_HOST(String sSO_HOST) {
	SSO_HOST = sSO_HOST;
    }

    public String getPORTAL_HOST() {
	return PORTAL_HOST;
    }

    public void setPORTAL_HOST(String pORTAL_HOST) {
	PORTAL_HOST = pORTAL_HOST;
    }
}
