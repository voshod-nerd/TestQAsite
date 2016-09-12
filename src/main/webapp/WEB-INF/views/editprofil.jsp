<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <a href="/vc" class="navbar-brand">Main</a>
                    <sec:authorize access="isAuthenticated()">
                        <a href="viewprofil" class="navbar-brand">Profil</a>
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
            <h4>Profil User</h4>
            <form role="form" method="post" enctype="multipart/form-data"  action="saveprofil" >
            <p>
                <label>Add photo</label>
                <input class="form-control"  type="file"  name="file" />
            </p>
            
            <label>Username</label>
            <input type="hidden" value="${user.id}" name="id" />
            <input class="form-control" value="${user.username}" name="username"  />
            <input class="form-control" value="${user.email}" name="email" />
            <input type="submit" value="Save" />
            </form>

        </div>



    </body>




</html>