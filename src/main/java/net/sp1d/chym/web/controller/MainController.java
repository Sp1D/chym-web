/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sp1d.chym.web.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sp1d.chym.loader.bean.Series;
import net.sp1d.chym.loader.bean.SortDirection;

import net.sp1d.chym.loader.bean.SortOrder;
import net.sp1d.chym.loader.bean.User;
import net.sp1d.chym.loader.service.UserService;
import net.sp1d.chym.web.service.WebSeriesService;
import net.sp1d.chym.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sp1D
 */
@Controller
public class MainController {
    
    @Autowired
    WebUserService userService;
    
    @Autowired
    WebSeriesService seriesService;
    
    @Autowired
    Environment env;
    
    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String showShows(Model model, HttpServletRequest request) {
        User user = userService.getPrincipal();
        Map<String, Object> chParams = checkParameters(request.getParameterMap(), user, request);
        Direction direction = Direction.DESC;
        int cols = env.getProperty("application.default.columns", Integer.class);

        boolean favoritesFirst = (boolean) chParams.get("favoritesFirst");
        int page = (int) chParams.get("page");
        int pagesize = (int) chParams.get("pageSize");
        String sortProperty = (String) chParams.get("sortProperty");
        SortOrder curSort = (SortOrder) chParams.get("sortOrder");
        if (curSort == SortOrder.TITLE) {
            direction = Direction.ASC;
        }        
        Sort sort = new Sort(direction, sortProperty);

        Page<Series> seriesPage;
        if (favoritesFirst && user != null) {
//            moviesPage = movieRepo.findFavoritesAndAll(user, new PageRequest(page, pagesize, sort));
            seriesPage = seriesService.findAll(new PageRequest(page, pagesize, sort));
        } else {
            seriesPage = seriesService.findAll(new PageRequest(page, pagesize, sort));

        }
        List<Series> series = new LinkedList<>(seriesPage.getContent());

//        if (favoritesFirst && user != null && user.getFavorites() != null) {
//            movies.removeAll(user.getFavorites());
//            movies.addAll(0, user.getFavorites());
//        }

//        Number of rows on the main page depending of columns number
        int rows = series.size() % cols > 0 ? series.size() / cols + 1 : series.size() / 3;

        model.addAttribute("MovieFullList", series);
        model.addAttribute("rows", rows);
        model.addAttribute("cols", cols);
        model.addAttribute("pageSize", pagesize);
        model.addAttribute("sortOrder", curSort.toString().toLowerCase());
//        model.addAttribute("sortValues", SortOrder.stringValues());
        model.addAttribute("favoritesFirst", favoritesFirst);
        model.addAttribute("pagesTotal", seriesPage.getTotalPages());
        model.addAttribute("pagesCurrent", seriesPage.getNumber());
        model.addAttribute("pagesFirst", seriesPage.isFirst());
        model.addAttribute("pagesLast", seriesPage.isLast());
        model.addAttribute("user", user);

//        if (user != null) {
//            Page<MovieFull> testlist = movieRepo.findFavoritesAndAll(user, new PageRequest(page, pagesize, sort));
//            model.addAttribute("testlist", testlist.getContent());
//        }
                
        return "shows";
    }
    
    @Transactional
    @RequestMapping (value = "/episodes/{id}", method = RequestMethod.GET)
    public String showEpisodes(Model model, @PathVariable long id){        
        Series series = seriesService.findById(id);        
//        Инициализация коллекций
        series.getForeignDescription().size();
        series.getEpisodes().size();
        
        User user = userService.getPrincipal();
        
        model.addAttribute("series", series);
        model.addAttribute("user", user);
        return "episodes";
    }
    
    public Map<String, Object> checkParameters(Map<String, String[]> params, User user, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        boolean userSettingsChanged = false;

        SortOrder sortOrder = env.getProperty("user.default.sortorder", SortOrder.class);
        if (params.get("sort") != null) {
            try {
                sortOrder = SortOrder.valueOf(params.get("sort")[0].toUpperCase());
            } catch (Exception e) {
//                Не удалось считать параметр. Ну ладно, значение по умолчанию уже назначено
            }
        } else if (user != null) {
            if (user.getSettings().getSortOrder() != null) {
                sortOrder = user.getSettings().getSortOrder();
            }
        }
        result.put("sortOrder", sortOrder);
        if (user != null && user.getSettings().getSortOrder() != sortOrder) {
            user.getSettings().setSortOrder(sortOrder);
            userSettingsChanged = true;
        }

        int page = 0;
        if (params.get("p") != null) {
            try {
                page = Integer.valueOf(params.get("p")[0]);
//                Because PageRequest counts from 0
                page--;
            } catch (Exception e) {
            }
        }
        result.put("page", page);

        int pageSize = env.getProperty("user.default.pagesize", Integer.class);
        if (params.get("s") != null) {
            try {
                pageSize = Integer.valueOf(params.get("s")[0]);
            } catch (Exception e) {
            }
        } else if (user != null) {
            if (user.getSettings().getPageSize() > 0) {
                pageSize = user.getSettings().getPageSize();
            }
        }
        result.put("pageSize", pageSize);
        if (user != null && user.getSettings().getPageSize() != pageSize) {
            user.getSettings().setPageSize(pageSize);
            userSettingsChanged = true;
        }

        String sortProperty = null;
        switch (sortOrder) {
            case GENRE:
                sortProperty = "genres";
                break;
            case RATING:
                sortProperty = "imdbRating.rating";
                break;
            case RELEASED:
                sortProperty = "released";
                break;
            case TITLE:
                sortProperty = "title";
            default:
                sortProperty = "title";
        }
        result.put("sortProperty", sortProperty);

        boolean favoritesFirst = true;
        if (params.get("favorites") != null) {
            if ("yes".equalsIgnoreCase(params.get("favorites")[0])) {
                favoritesFirst = true;
            } else if ("no".equalsIgnoreCase(params.get("favorites")[0])) {
                favoritesFirst = false;
            }
        } else if (user != null) {
            favoritesFirst = user.getSettings().isFavoritesFirst();
        }
        result.put("favoritesFirst", favoritesFirst);
        if (user != null && user.getSettings().isFavoritesFirst() != favoritesFirst) {
            user.getSettings().setFavoritesFirst(favoritesFirst);
            userSettingsChanged = true;
        }

        if (userSettingsChanged) {
            user = userService.saveAndFlush(user);
            request.getSession().setAttribute("user", user);
        }

        return result;
    }
    
}
