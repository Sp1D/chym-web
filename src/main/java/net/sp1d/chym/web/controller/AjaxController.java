/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.controller;

import net.sp1d.chym.loader.bean.Series;
import net.sp1d.chym.loader.bean.User;
import net.sp1d.chym.web.service.WebSeriesService;
import net.sp1d.chym.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Sp1D
 */
@Controller
public class AjaxController {
    
    @Autowired 
    WebSeriesService seriesService;
    
    @Autowired
    WebUserService userService;
    
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/fav/toggle/{id}")
    @ResponseBody
    AjaxRequestResult toggleFavorite(@PathVariable long id) {
        Series series = seriesService.findById(id);
        User user = userService.getPrincipal();
        boolean result;
        String desc;
        
        if (user.getFavorites().contains(series)) {
            result = userService.delFavoriteSeries(user, series);
            desc = "fav deletion";
        } else {        
            result = userService.addFavoriteSeries(user, series);
            desc = "fav addition";
        }        
        if (result) return new AjaxRequestResult().setSuccess(true).setDescription(desc);
            else return new AjaxRequestResult().setSuccess(false).setDescription(desc);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/fav/add/{id}")
    @ResponseBody
    AjaxRequestResult addFavorite(@PathVariable long id) {
        Series series = seriesService.findById(id);
        User user = userService.getPrincipal();
        
        if (userService.addFavoriteSeries(user, series)) {
            return new AjaxRequestResult().setSuccess(true);
        } else {
            return new AjaxRequestResult().setSuccess(false);
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/fav/del/{id}")
    @ResponseBody
    AjaxRequestResult delFavorite(@PathVariable long id) {
        Series series = seriesService.findById(id);
        User user = userService.getPrincipal();
        
        if (userService.delFavoriteSeries(user, series)) {
            return new AjaxRequestResult().setSuccess(true);
        } else {
            return new AjaxRequestResult().setSuccess(false);
        }
    }
}
