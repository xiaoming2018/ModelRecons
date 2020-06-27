<%--
  Created by IntelliJ IDEA.
  User: sun xiaoming
  Date: 2019/11/3
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>重建</title>
    <script src="resource/js/jquery3.3.1.js"></script>
    <script type="application/javascript">
      function test() {
        alert("1111");
        $.ajax({
          url: "shell",
          async: false,
          success: function (result) {
            debugger;
            alert(result.msg);
          }
        });
      }
    </script>
  </head>
  <body>

    <h2>Mitsuba Rendering System</h2>
    <button id="mitsuba" onclick="test()">计算</button>
    <a href="http://182.61.3.41:8080/ModelRecons/resource/cow.xml">测试</a>
  </body>
  <form action="fileload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="提交">
  </form>

</html>
