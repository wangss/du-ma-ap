<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>



</head>
<body>

<h1>springMVC字节流输入上传文件</h1>
<form name="userForm1" action="../file/upload" enctype="multipart/form-data" method="post">

    <input type="text" name="name"/>

    <div id="newUpload1">
        <input type="file" name="files">
    </div>

    <input type="button" id="btn_add1" value="增加一行" >
    <input type="submit" value="上传" >
</form>
<br>
<br>
<hr align="left" width="60%" color="#FF0000" size="3">
<br>
<br>
<h1>springMVC包装类上传文件</h1>
<form name="userForm2" action="../file/upload2" enctype="multipart/form-data" method="post">

    name:<input type="text" name="name">

    <div id="newUpload2">
    <input type="file" name="file">
</div>
<input type="button" id="btn_add2" value="增加一行" >
<input type="submit" value="上传" >


</form>
</body>
</html>