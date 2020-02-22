package com.intercorp.entrevista.Utils;

public class ControllerUtils {

    public static boolean isString(String value) {
        try {
            Integer.parseInt(value);
            return false;
        } catch (NumberFormatException nfe){
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}
