<#assign sf=JspTaglibs["http://www.springframework.org/security/tags"]>
<@sf.authorize access="!isAuthenticated()">
    <a href="/sign_in">sign in</a>
</@sf.authorize>
<@sf.authorize access="isAuthenticated()">
    <a href="/logout">logout</a>
    <br>
    <a href="/profile">profile</a>
</@sf.authorize>

<@sf.authorize access="hasRole('ADMIN')">
    <br>
    <br>
    Manage USERS
</@sf.authorize>