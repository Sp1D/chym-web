<%@page import="net.sp1d.chym.loader.type.LangType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="webtext"/>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<% String cpath = getServletContext().getContextPath(); 
   LangType RU = LangType.RU;
%>
<!DOCTYPE html>
<html>
    <head>
        <title>
            <fmt:message key="episodes.pagetitle"/>
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="${cpath}/static/img/favicon.png">
        <link rel="stylesheet" href="${cpath}/static/css/bootstrap.min.css">        
        <link rel="stylesheet" href="${cpath}/static/css/local.css">                

<!--            <style type="text/css">
                body {
                    padding-top: 5%;
                    padding-bottom: 5%;
                    padding-left: 10%;
                    padding-right: 5%;
                }
            </style>-->
        </head>
        <body>
            <!-- NAVIGATION BAR -->
            <%@include file="/WEB-INF/jspf/navbar.jspf" %>
                        
            <div class="container target">
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="page-header text-center">
                            <h2>${series.foreignTitle[LangType.RU]} / ${series.title}<small> (${series.startYear})</small></h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <p class="text-right">
                            <img src="<c:url value="http://image.tmdb.org/t/p/w500${series.poster}"/>" alt="${movie.title}" class="img-responsive" align="right">                            
                        </p>
                    </div>
                    <div class="col-md-8">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <td class="">${series.foreignDescription[LangType.RU]}</td>
                                </tr>
                                <tr>                                    
                                    <td><b class=""><fmt:message key="episodes.genre"/>&nbsp;:&nbsp;</b><c:forEach var="genre" items="${series.genres}"><c:out value="${genre.foreignName[LangType.RU]}"/>&nbsp;</c:forEach></td>
                                </tr>
                                <tr>
                                    <td><b class=""><fmt:message key="episodes.imdb"/>&nbsp;:&nbsp;</b>${series.imdbRating.rating}</td>
                                </tr>
                                <tr>
                                    <td><b class=""><fmt:message key="episodes.released"/>&nbsp;:&nbsp;</b>${series.endDate}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-8">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                            <c:forEach var="season" items = "${series.seasons.descendingSet()}">
                                <div class="panel panel-default">
                                    <div class="panel-heading" role="tab" id="heading${season}">
                                        <h4 class="panel-title">
                                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${season}" aria-expanded="false" aria-controls="collapse${season}" class="collapsed">
                                                Season ${season}
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse${season}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${season}">
                                        <div class="panel-body">
                                            <table class="table">
                                                <tbody>
                                                    <%--
                                                    <c:forEach var="episode" items="${series.episodes}">
                                                        <c:if test="${episode.seasonN eq season}">
                                                            <tr>
                                                                <td>${episode.episodeN}</td>
                                                                <td>${episode.foreignTitle[LangType.RU]}
                                                                    <!--<span class="label label-info">new</span></td>-->
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    --%>
                                                    <c:forEach var="episode" items="${episodes[season]}">
                                                        <tr>
                                                            <td>${episode.episodeN}</td>
                                                            <td>${episode.foreignTitle[LangType.RU]}
                                                                <!--<span class="label label-info">new</span></td>-->
                                                           </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>                            
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-4">
                        
                    </div>
                </div>
                <!-- /container -->
            </div>
            <script src="<c:url value="/static/js/jquery-1.12.0.min.js"></c:url>"></script>
            <script src="<c:url value="/static/js/bootstrap.min.js"></c:url>"></script>
    </body>
</html>
