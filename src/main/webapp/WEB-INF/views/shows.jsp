<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="f" uri="functions.tld"  %>
<fmt:setBundle basename="webtext"/>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title><fmt:message key="shows.pagetitle"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value='static/img/favicon.png'/>">
        <link rel="stylesheet" href="<c:url value='static/css/bootstrap.min.css'/>">        
        <link rel="stylesheet" href="<c:url value='static/css/local.css'/>">   
    </head>
    <body>
        <!-- NAVIGATION BAR -->
        <%--<jsp:include page="/WEB-INF/jspf/navbar.jspf"/>--%>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>

        <!-- PAGE CONTENT -->
        <div class="container-fluid">
            <c:forEach var="movie" items="${MovieFullList}">
                <div class="movie">                                        
                    <div class="rating"><span class="label label-warning "><span class="glyphicon glyphicon-stats"></span>&nbsp;${movie.imdbRating.rating}</span></div>
                    <c:if test="${user != null}">
                        <c:choose>
                            <c:when test="${user != null and f:colcontains(user.favorites, movie)}">
                                <div class="favorite" id="fav${movie.id}" onclick="toggleFav(${movie.id})"><span class="label label-danger"><span class="glyphicon glyphicon-heart icon-favorite"></span></span></a></div>
                            </c:when>
                            <c:otherwise>
                                <div class="favorite" id="fav${movie.id}" onclick="toggleFav(${movie.id})"><span class="label label-white"><span class="glyphicon glyphicon-heart-empty icon-love"></span></span></a></div>
                            </c:otherwise>                                        
                        </c:choose>
                    </c:if>
                    <div class="poster" ><a href="<c:url value="/episodes/${movie.id}"/>"><img src="<c:url value="http://image.tmdb.org/t/p/w300${movie.poster}"/>" alt="<c:out value="${movie.title}" />"></a></div>
                    <div class="btn-group">
                        <button class="btn btn-default btn-poster" data-toggle="modal" data-target="#descModal<c:out value="${movie.id}"/>"><c:out value="${movie.title}"/></button>

                    </div> 
                    <!--<div class="movtitle"><--c:out value="${movie.title}" /></div>-->
                    <c:set var="id" value="${movie.id}"/>
                    <div class="modal fade" id="descModal<c:out value="${movie.id}"/>" tabindex="-1" role="dialog" aria-labelledby="descModal" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="descModal"><c:out value="${movie.title}" /></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="descposter">
                                        <img class="descposter" src="http://image.tmdb.org/t/p/w154${movie.poster}" >                                        
                                    </div>
                                    <div class="descposter descdesc">                                        
                                        <p><fmt:message key="episodes.genre"/>&nbsp;:&nbsp;<c:forEach var="genre" items="${movie.genres}"><c:out value="${genre.name}"/>&nbsp;</c:forEach></p>
                                        <p><fmt:message key="episodes.released"/>&nbsp;:&nbsp;<c:out value="${movie.startDate}"/></p>
                                    </div>
                                    <div class="desctext">
                                        <p><c:out value="${movie.description}"/></p>
                                    </div>
                                </div>
                                <div class="modal-footer" style="clear:both;">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>  
        </div>

        <!-- Pages bar -->

        <nav>
            <div class="container-fluid" style="text-align: center;">                
                <ul class="pagination" style="display: inline-block; ">
                    <c:if test="${pagesFirst}">
                        <li class="disabled">
                        </c:if>
                        <c:if test="${!pagesFirst}">
                        <li>
                        </c:if>
                        <a aria-label="Previous" href="<c:url value="" >
                               <c:param name="sort" value="${sortOrder}"/>    
                               <c:param name="p" value="1"/>
                               <c:param name="s" value="${pageSize}"/>
                           </c:url>"><c:out value="${currentPage}"/><span aria-hidden="true">&laquo;</span></a>                        
                    </li>

                    <c:forEach var="currentPage" begin="1" end="${pagesTotal}" step="1">
                        <c:if test="${currentPage == pagesCurrent+1}">
                            <li class="active">
                            </c:if>
                            <c:if test="${currentPage != pagesCurrent+1}">
                            <li>
                            </c:if>
                            <a href="<c:url value="" >
                                   <c:param name="sort" value="${sortOrder}"/>    
                                   <c:param name="p" value="${currentPage}"/>
                                   <c:param name="s" value="${pageSize}"/>
                               </c:url>"><c:out value="${currentPage}"/></a>
                        </li>

                    </c:forEach>
                    <c:if test="${pagesLast}">
                        <li class="disabled">
                        </c:if>
                        <c:if test="${!pagesLast}">
                        <li>
                        </c:if>
                        <a aria-label="Next" href="<c:url value="" >
                               <c:param name="sort" value="${sortOrder}"/>    
                               <c:param name="p" value="${pagesTotal}"/>
                               <c:param name="s" value="${pageSize}"/>
                           </c:url>"><c:out value="${currentPage}"/><span aria-hidden="true">&raquo;</span></a>   

                    </li>
                </ul>
            </div>
        </nav>
       <script>
            var contextPath = '<%= request.getContextPath()%>';
            var csrf = '<c:out value="${_csrf.token}"/>';       
        </script> 
        <script src="<c:url value="/static/js/jquery-1.12.0.min.js"></c:url>"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js"></c:url>"></script>
        <script src="<c:url value="/static/js/local.js"></c:url>"></script>
    </body>
</html>