<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<%--<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform">--%>
<head>
<!--    <meta http-equiv="Refresh" content="30"/>-->
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Hello, world!</title>

</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="/job4j_todo/js/modifyTable.js"></script>
<script type="text/javascript">

    var name = '<%= session.getAttribute("user") %>';
    //sessionStora
    console.log(name);
<%--//     <%String session_val = (String)session.getAttribute("sessionval");--%>
// System.out.println("session_val"+session_val);
// %>
</script>
<%--<%--%>
<%--    String id = request.getParameter("id");--%>
<%--    Post post = new Post(0, "", "new vacancy", Calendar.getInstance());--%>
<%--    if (id != null) {--%>
<%--        post = DbStore.instOf().findById(Integer.valueOf(id));--%>
<%--    }--%>
<%--%>--%>
<div class="container">
    <div class="row">
        <ul class="nav">
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out
                            value="${user.name}"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">| Выйти</a>
                </li>
            </c:if>
        </ul>
    </div>
<!--    <form action= "http://localhost:8080/job4j_todo/items" method= "POST" id="form_item">-->
    <form id="form_item">
        <div class="form-group">
            <h5>New Item:</h5>
            <input type="text" class="form-control" id="formNameItem" name="nameItem" placeholder="Enter the name of item">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" id="formDescItem" name="descItem" placeholder="Write details...">
        </div>
<!--        <button type="submit" class="btn btn-success" onclick="return addItem();">Add</button>-->
        <button type="button" class="btn btn-success" onclick="return addItem();">Add</button>
        <br/>
        <br/>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="showAll" onchange="showAllIt()">
            <label class="form-check-label" for="showAll" > Show All Items</label>
        </div>
        <c:if test="${user != null}">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="showUsersItems" onchange="showAllIt()">
                <label class="form-check-label" for="showUsersItems" > Show User's Items</label>
            </div>
        </c:if>
        <div class="row pt-3">
            <h5>Item's list:</h5>
            <table width="80%" class="table table-dark" id="items_tab" name="items">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Item</th>
                    <th scope="col">Done</th>
                </tr>
                </thead>
                <tbody id="table_body">
                </tbody>
            </table>
        </div>
    </form>
</div>
</body>
</html>