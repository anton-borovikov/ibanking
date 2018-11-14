<#assign
    isSecContext = Session.SPRING_SECURITY_CONTEXT??
>

<#if isSecContext>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    >
</#if>

<#if user??>
<#if !user.isActivated()>
    <div class="alert alert-warning alert-dismissible fade show mb-0 text-center" role="alert">
        <a class="nav-link pb-0 pt-0" href="/user/activation">Please confirm your account by activation code!</a>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</#if>
</#if>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">iBanking</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/services">SERVICES</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/branches">BRANCHES</a>
            </li>
                <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/accounts">YOUR ACCOUNTS</a>
                </li>
                </#if>
        </ul>
        <#if user??>
            <div>
                <a class="nav-link" href="/user/profile">${user.username}</a>
            </div>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Logout</button>
            </form>
        <#else>
            <form action="/login" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary mr-1">Login</button>
            </form>
            <div class="navbar-text">/</div>
            <form action="/registration" method="get">
                <button type="submit" class="btn btn-primary ml-1">New</button>
            </form>
        </#if>
    </div>
</nav>