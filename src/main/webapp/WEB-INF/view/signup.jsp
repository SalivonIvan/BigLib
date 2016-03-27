<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container-fluid">
            <form:form modelAttribute="customer" class="form-signin" role="form" action="signup" method="post">
                <h2 class="form-signin-heading">Sign in</h2>
                <form:label path="fullName" cssClass="sr-only">Full Name</form:label>
                <form:input path="fullName" cssClass="form-control" cssErrorClass="bg-warning" placeholder="Full Name"  />
                <form:errors path="fullName" />
                <br>
                <form:label path="email" cssClass="sr-only">Full Name</form:label>
                <form:input path="email" cssClass="form-control" cssErrorClass="bg-warning" placeholder="Email"  />
                <form:errors path="email" />
                <br>
                <form:label path="password" cssClass="sr-only">Full Name</form:label>
                <form:password path="password" cssClass="form-control" cssErrorClass="bg-warning" placeholder="Password" />
                <form:errors path="password" />
                <form:label path="password" cssClass="sr-only">Full Name</form:label>
                <form:password path="password" cssClass="form-control" cssErrorClass="bg-warning" placeholder="Re-password" />
                <form:errors path="password" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign me up!</button>
            </form:form>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>