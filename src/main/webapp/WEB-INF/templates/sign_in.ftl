<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
</head>
<body>
<form action="/login/processing" method="post">
    Login:
    <input type="text" name="username" id="username"/>
    <br>
    Password:
    <input type="password" name="password" id="password"/>
    <br>
    <input type="submit" value="press me please">
</form>
<#if error??>
    <div style="color: coral">${error}</div>
</#if>
</body>
</html>