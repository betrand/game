package com.emb.game.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtil {

    public static final String PLAYER = "player";
    public static final String HUMAN = "O";
    public static final String HUMAN_PLAYER = "YOU";
    public static final String SYSTEM = "X";
    public static final String SYSTEM_PLAYER = "SYSTEM";
    public static final String WIN = "WIN";
    public static final String DRAW = "DRAW";
    public static final String STOP = "STOPPED";
    public static final String PLAYING = "PLAYING...";
    public static final String START = "Please select first Player";
    public static final String DO_SOMETHING = "SYSTEM_TURN";

    public static void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        facesContext().addMessage(null, facesMsg);
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        facesContext().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        facesContext().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return facesContext().getExternalContext().getRequestParameterMap().get(key);
    }

    public static boolean notNull(Object param) {
        return param != null;
    }

    public static boolean notEmpty(String param) {
        return notNull(param) && !param.isEmpty();
    }

    public static boolean Empty(String param) {
        return notNull(param) && param.isEmpty();
    }

    public static FacesContext facesContext() {
        return FacesContext.getCurrentInstance();
    }

}
