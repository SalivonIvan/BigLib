<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container">
            <div class="jumbotron text-center">
                <h1>Welcome to the Big Library</h1>
                <p class="lead"><img height="200" src="resources/image/Books.png"></p>
                <p><a class="btn btn-lg btn-success" href="signup" role="button">Sign in</a></p>
            </div>
            <div class="row">
                <div class="list-group col-md-8 col-md-offset-2">
                    <a href="#" class="list-group-item list-book"><img height="25" src="resources/image/logo.svg">
                        Cras justo odio
                    </a>
                    <a href="#" class="list-group-item list-book"><img height="25" src="resources/image/logo.svg">Dapibus ac facilisis in</a>
                    <a href="#" class="list-group-item list-book"><img height="25" src="resources/image/logo.svg">Morbi leo risus</a>
                    <a href="#" class="list-group-item list-book"><img height="25" src="resources/image/logo.svg">Porta ac consectetur ac</a>
                    <a href="#" class="list-group-item list-book"><img height="25" src="resources/image/logo.svg">Vestibulum at eros</a>
                </div>
            </div>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>