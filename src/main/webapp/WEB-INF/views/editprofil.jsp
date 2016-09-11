<%-- 
    Document   : editprofil
    Created on : 09.09.2016, 21:59:36
    Author     : Олег
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simple site QA</title>

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
                    <a href="/vc" class="navbar-brand">Главная</a>
                    <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="navbar-collapse collapse" id="navbar-main">
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <ul class="nav navbar-nav">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">Главная<span class="caret"></span></a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">Профиль<span class="caret"></span></a>
                            </li>
                        </ul>
                    </sec:authorize>  

                    <ul class="nav navbar-nav navbar-right">

                        <li><a href="contact" >Контакты</a></li>
                        <sec:authorize access="ROLE_ANONYMOUS">
                            <td><a href="login">Login</a></td>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <a href="logout">Выйти</a>
                        </sec:authorize>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <br>


        <div class="container" style="background-color:#ADD8E6;" >
            <br>
            <h3 class="text-center">Simple QA site</h3>
            

           


           


        </div>

        

        <!-- Низ страницы -->
        <div class="navbar navbar-default navbar-bottom"></div>
    </body>
</html>
