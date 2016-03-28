<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container-fluid">
            <form:form modelAttribute="customer" cssClass="form-signin" action="signup" method="post">
                <h2 class="form-signin-heading">Sign in</h2>
                <form:label path="fullName" cssClass="sr-only">Full Name</form:label>
                <form:input path="fullName" cssClass="form-control form-control-signin" placeholder="Full Name"  />
                <form:errors path="fullName" cssClass="text-danger" />
                <br>
                <form:label path="email" cssClass="sr-only">Email</form:label>
                <form:input path="email" cssClass="form-control form-control-signin" placeholder="Email"  />
                <form:errors path="email" cssClass="text-danger" />
                <br>
                <form:label path="password" cssClass="sr-only">Password</form:label>
                <form:password path="password" cssClass="form-control form-control-signin"  placeholder="Password" />
                <form:errors path="password" cssClass="text-danger" />
                <form:label path="password" cssClass="sr-only">Re-password</form:label>
                <form:password path="password" cssClass="form-control form-control-signin" placeholder="Re-password" />
                <form:errors path="password" cssClass="text-danger" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign me up!</button>
            </form:form>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>