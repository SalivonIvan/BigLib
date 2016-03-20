<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container">
            <div class="error-page-alert">
                <div class="alert alert-danger text-center" role="alert">
                    <c:if test="${not empty param.error}">
                        <!--${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}-->
                        Не правильно введений логін, або пароль!</c:if>
                    </div>
                    <a href="index" class="btn btn-lg btn-primary btn-block" role="button">
                        Повернутись на сторінку авторизації
                    </a>
                </div>
            </div>
        </section>
    </main>
<%@include file="jspf/footer.jspf" %>