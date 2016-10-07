<%-- 
    Document   : login
    Created on : 09.09.2016, 21:57:29
    Author     : Олег
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
                        <a href="viewprofil" class="navbar-brand">Profil</a>

                    </sec:authorize>


                </div>
                <div class="navbar-collapse collapse" id="navbar-main">


                    <ul class="nav navbar-nav navbar-right">
                        <sec:authorize access="isAnonymous()">
                            <li><a href="home" >Log In</a></li>
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

        <div class="col-md-6 col-md-offset-2">	
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">
                    Invalid password or password
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="alert alert-success">
                    Вы уже вошли!
                </div>
            </c:if>	
        </div>  


        <div class="container" style="background-color:#ADD8E6; border-radius: 15px;"  >
            <br>
            <div class="row" >
                <div  class="col-md-6">
                    <h4 class="text-center">Log In</h4>
                    <form method="post" action="j_spring_security_check" modelAttribute="user" class="form-horizontal" >
                        <label>Username</label>
                        <input class="form-control" type="text" name="j_username" />
                        <label>Password</label>
                        <input class="form-control" type="text" name="j_password" />
                        <input class="btn btn-default" type="submit" value="Log In"  />
                    </form>
                </div>
                <div class="col-md-6">
                    <h4 class="text-center">Registration</h4>
                    <form action="registration" method="post">
                        <label>Username</label>
                        <input class="form-control" type="text" name="username" />
                        <label>Email</label>
                        <input class="form-control" type="email" name="email" />
                        <label>Password</label>
                        <input class="form-control" type="text" name="password" />
                        <input class="btn btn-default" type="submit" value="Registration"  />
                    </form>
                 
                   



                </div>


            </div>
        </div>


    </body>
</html>