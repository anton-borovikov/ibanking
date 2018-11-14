<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div>
    You have logged as: ${user.getUsername()}
</div>
<div>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Logout"/>
    </form>
</div>
<div>
    <a href="/user/profile">User profile</a>
</div>
<div>
    <#list services as service>
        <div>
            ${service.serviceName}
        </div>
    </#list>
</div>
<div>
    Select your city:
    <form method="get" action="/main">
        <select name="cityFilter">
            <#list cities as city>
                <option>${city.cityName}</option>
            </#list>
        </select>
    </form>
</div>
<div>
    <#list branches as branch>
        <div>
            ${branch.city.cityName}
            ${branch.branchName}
            ${branch.branchAddress}
        </div>
    </#list>
</div>
</body>
</html>