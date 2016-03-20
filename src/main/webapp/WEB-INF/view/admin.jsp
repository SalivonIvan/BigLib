<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="jspf/header.jspf" %> 
<main>
    <section class="main">
        <div class="container">
            <div class="jumbotron text-center">
                <h3>Admin Page</h3>
                <div class="row">
                    <div class="col-xs-8 col-sm-8 col-md-8"><a href="#" class="list-book">
                            <img height="25" src="resources/image/logo.svg">
                            Dapibus ac facilisis inСписок груп є гнучким та потужним компонентом для відображення не лише простих пунктів списку, але і для чогось складнішого, що стосується налаштування вмісту.
                        </a></div>
                    <div class="col-xs-2 col-sm-2 col-md-2">
                        <button type="submit" class="btn btn-default btn-sm">
                            Delete Book
                        </button>
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-2">
                        <button type="submit" class="btn btn-default btn-sm">
                            Edit Book
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-8 col-sm-8 col-md-8"><a href="#" class="list-book">
                            <img height="25" src="resources/image/logo.svg">
                            Dapibus ac facilisis inСписок груп є гнучким та потужним компонентом для відображення не лише простих пунктів списку, але і для чогось складнішого, що стосується налаштування вмісту.
                        </a></div>
                    <div class="col-xs-2 col-sm-2 col-md-2">
                        <button type="submit" class="btn btn-default btn-sm">
                            Delete Book
                        </button>
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-2">
                        <button type="submit" class="btn btn-default btn-sm">
                            Edit Book
                        </button>
                    </div>
                </div>
                <form role="form">
                    <div class="form-group">
                        <label for="exampleInputFile">Add Book</label>
                        <input type="file" id="exampleInputFile" class="center-block">
                    </div>
                    <button type="submit" class="btn btn-default">Add Book</button>

                </form>

            </div>
        </div>
    </section>
</main>
<%@include file="jspf/footer.jspf" %>