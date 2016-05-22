/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.service;

import java.util.List;
import net.sp1d.chym.loader.bean.Series;
import net.sp1d.chym.loader.bean.User;
import net.sp1d.chym.loader.service.SeriesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sp1D
 */
@Service
public class WebSeriesService extends SeriesService {
    @Transactional
    public Page<Series> findAll(Pageable pageable){
        return seriesRepo.findAll(pageable);    
    }
    
    @Transactional
    public List<Series> findFavoriteSeriesByUser(User user) {
        return seriesRepo.findFavoriteSeriesByUser(user);
    }
}
