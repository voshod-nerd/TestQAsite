<%-- 
    Document   : addanswer
    Created on : 09.09.2016, 21:57:55
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
            <h4>${question.dateadd} ${question.name}</h4>
            <label>Full text question</label>
            <p>${question.fulltext}</p>
            <p>Category: ${question.idcategory.name}
            <p>Autor: ${question.iduser.username}</p>

            <h4>All answers</h4>
            <c:forEach items="${listAnswers}" var="item">
                <p>${item.dateadd} ${item.text}</p>
                <p>Autor: ${item.iduser.username}</p>
            </c:forEach>

            <sec:authorize access="isAuthenticated()">
                <form role="form" action="postanswer" method="post" >
                  <div class="form-group">
                      <p>Add your answer</p>
                   <label for="email">Answer text </label>
                   
                   <input  class="form-control"  type="text" name="textanswer"  />   
                   <input type="hidden" name="question"  value="${question.id}"/>   
                   <input class="btn-success" type="submit" value="Post answer" />   
                  </div> 
                </form>
            </sec:authorize>


        </div>



    </body>




</html>
