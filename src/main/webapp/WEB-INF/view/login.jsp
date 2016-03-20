<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container-fluid">
            <form class="form-signin" role="form" action="login" method="post">
                <h2 class="form-signin-heading">Log in</h2>
                <label for="inputEmail" class="sr-only">Email</label>
                <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Log me in!</button>
            </form>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>