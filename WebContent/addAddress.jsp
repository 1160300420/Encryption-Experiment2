<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>
<form id="from" action="addAddress" method="post">
<table>
<tr><td>addressId</td><td><input type="text" name="addressId"></td></tr>
<tr><td>userId</td><td><input type="text" name="userId"></td></tr>
<tr><td>name</td><td><input type="text" name="name"></td></tr>
<tr><td>phone</td><td><input type="text" name="phone"></td></tr>
<tr><td>bigAddress</td><td><input type="text" name="bigAddress"></td></tr>
<tr><td>smallAddress</td><td><input type="text" name="smallAddress"></td></tr>
<tr><td>address</td><td><input type="text" name="address"></td></tr>
<tr><td colspan="2" align="center"><input type="submit"  value="添加地址"></td></tr>
</table>
</form>
</body>
</html>