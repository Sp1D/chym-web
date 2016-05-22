/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.controller;

/**
 *
 * @author Sp1D
 */
public class AjaxRequestResult {
    private String description;
    private boolean success;
    
//    GENERATED G&S

    public String getDescription() {
        return description;
    }

    public AjaxRequestResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public AjaxRequestResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }
    
    
}
