<%-- 
    Document   : addquestion
    Created on : 09.09.2016, 21:58:07
    Author     : Олег
--%>
<%-- 
    Document   : index
    Created on : 09.09.2016, 21:31:07
    Author     : Олег
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Сайт комплекса гостиниц Байконура</title>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="resources/js/jquery-2.2.2.min.js"></script>
        <!-- Bootstrap -->
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <!-- css code for slider  -->
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="resources/js/bootstrap.min.js"></script>


    </head>

    <body>
        <div class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="/vc" class="navbar-brand">Main</a>
                    <sec:authorize access="isAuthenticated()">
                        <a href="profil" class="navbar-brand">Profil</a>
                        <a href="addquestion" class="navbar-brand">Add Question</a>
                    </sec:authorize>

                </div>
                <div class="navbar-collapse collapse" id="navbar-main">


                    <ul class="nav navbar-nav navbar-right">
                        <sec:authorize access="isAnonymous()">
                            <li><a href="login">Log In</a></li>
                            </sec:authorize>  
                        <li> 
                            <sec:authorize access="isAuthenticated()">
                                <a href="logout">Log Out</a>
                            </sec:authorize>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


        <br>
        <br>
        <br>
        <div class="container" style="background-color:#ADD8E6; border-radius: 15px;" >
            <h4>Please add a new question and some discription</h4>
          
            <form  role="form" method="post" action="postquestion">
                <div class="form-group">
            <p>Category</p>
            <select class="form-control" name="category">
            <c:forEach  items="${listCategories}" var="item">
                <option value="${item.id}" >${item.name}</option>
              
            </c:forEach>
            </select>
            <label>Brief description question</label>
            <input class="form-control" type="text" name="name"   />
            <label>Full text question</label>
            <textarea class="form-control" name="fulltext"></textarea>
            <input type="submit" class="btn-success" value="Post Question"/>
            
                </div>
            </form>
            
           


        </div>



    </body>




</html>
