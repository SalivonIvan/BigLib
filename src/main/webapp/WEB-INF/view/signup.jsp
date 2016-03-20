<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container-fluid">
            <form:form modelAttribute="customer" class="form-signin" role="form" action="signup" method="post">
                <h2 class="form-signin-heading">Sign in</h2>
                <label for="inputName" class="sr-only">Full Name</label>
                <input type="text" name="inputName" id="inputName" class="form-control" placeholder="Full Name" required autofocus>
                <br>
                <label for="inputEmail" class="sr-only">Email</label>
                <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email" required autofocus>
                <br>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required>

                <label for="inputPasswordRe" class="sr-only">Password</label>
                <input type="password" name="inputPasswordRe" id="inputPasswordRe" class="form-control" placeholder="Re-enter password" required autofocus>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign me up!</button>
            </form:form>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>