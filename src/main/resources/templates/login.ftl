<#import "common.ftl" as c>

<@c.base>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            Invalid username or password
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${message_type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    <h6 class="mb-4">Please login:</h6>
    <form action="/login" method="post">
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
</@c.base>