/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.service;

import java.util.List;
import net.sp1d.chym.loader.bean.Series;
import net.sp1d.chym.loader.bean.User;
import net.sp1d.chym.loader.bean.UserSettings;
import net.sp1d.chym.loader.notifier.NotifyDeliveryType;
import net.sp1d.chym.loader.notifier.NotifyType;
import net.sp1d.chym.loader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sp1D
 */
@Service
public class WebUserService extends UserService {
                   
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        if (user == null) return null;
//      Если P1 и P2 (пароль с веб-формы) заполнены, считаем, что юзер новый
        if (user.getP1() != null && user.getP2() != null && user.getP1().equals(user.getP2())) {
            user.setPassword(passwordEncoder.encode(user.getP1()));
            user.setP1(null);
            user.setP2(null);
            user.setEnabled(true);            
            user.getNotifyTypes().add(NotifyType.NEWSERIES);
            user.getNotifyDeliveryTypes().add(NotifyDeliveryType.EMAIL);
        }
        if (user.getSettings() == null) user.setSettings(new UserSettings());
        return userRepo.save(user);
    }   

    
    public User getPrincipal() {
        return findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    } 
    
    public boolean addFavoriteSeries(User user, Series series) {
        user.getFavorites().add(series);
        save(user);
        return findFavoriteSeries(user).contains(series);
    }
    
    public boolean delFavoriteSeries(User user, Series series) {
        user.getFavorites().remove(series);
        save(user);
        return !findFavoriteSeries(user).contains(series);
    }
       
}
