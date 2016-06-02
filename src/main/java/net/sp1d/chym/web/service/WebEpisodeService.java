/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sp1d.chym.loader.bean.Episode;
import net.sp1d.chym.loader.bean.Series;
import net.sp1d.chym.loader.service.EpisodeService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sp1D
 */
@Service
public class WebEpisodeService extends EpisodeService {
    public List<Episode> findBySeriesAndSeasonNOrderByEpisodeNDesc(Series series, int seasonN) {
        return episodeRepo.findBySeriesAndSeasonNOrderByEpisodeNDesc(series, seasonN);        
    }
    
    public Map<Integer, List<Episode>> getEpisodesMapBySeriesOrderByEpisodeNDesc(Series series) {
        Map<Integer, List<Episode>> result = new HashMap<>();
        for (Integer seasonN : series.getSeasons()) {
            result.put(seasonN, findBySeriesAndSeasonNOrderByEpisodeNDesc(series, seasonN));
        }
        return result;
    }
           
}
