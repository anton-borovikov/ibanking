<#import "common.ftl" as c>

<@c.base>
    <#if message??>
        <div class="alert alert-${message_type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    <h6 class="mb-4">New user:</h6>
    <form action="/registration" method="post">

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="username" value="<#if user??>${user.username}</#if>"/>
            </div>
            <#if usernameError??>
                <div class="alert alert-danger" role="alert">${usernameError}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="password""/>
            </div>
            <#if passwordError??>
                <div class="alert alert-danger" role="alert">${passwordError}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="password" name="password2" class="form-control" placeholder="retype password""/>
            </div>
            <#if password2Error??>
                <div class="alert alert-danger" role="alert">${password2Error}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="email" name="userEmail" class="form-control" placeholder="yourname@domain.com" value="<#if user??>${user.userEmail}</#if>"/>
            </div>
            <#if userEmailError??>
                <div class="alert alert-danger" role="alert">${userEmailError}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="text" name="firstName" class="form-control" placeholder="first name" value="<#if user??>${user.firstName}</#if>"/>
            </div>
            <#if firstNameError??>
                <div class="alert alert-danger" role="alert">${firstNameError}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="text" name="lastName" class="form-control" placeholder="last name" value="<#if user??>${user.lastName}</#if>"/>
            </div>
            <#if lastNameError??>
                <div class="alert alert-danger" role="alert">${lastNameError}</div>
            </#if>
        </div>

        <div class="form-group row">
            <div class="input-group-lg col-sm-3">
                <input type="text" name="age" class="form-control" placeholder="age" value="<#if user??>${user.age!""}</#if>"/>
            </div>
            <#if ageError??>
                <div class="alert alert-danger" role="alert">${ageError}</div>
            </#if>
        </div>

        <div class="col-sm-6">
            <div class="g-recaptcha" data-sitekey="6LcsYHQUAAAAAO9_Zb_HKSws10dtczroSbRoQZwE"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">${captchaError}</div>
            </#if>
        </div>
        <div class="mt-3">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Create</button>
        </div>
    </form>
</@c.base>