<#import "common.ftl" as c>

<@c.base>
    <h6 class="mb-4">Your profile:</h6>
    <form action="/user/profile" method="post">
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="email" name="userEmail" class="form-control" placeholder="yourname@domain.com"  value="<#if user??>${user.userEmail}</#if>"/><#if userEmailError??>${userEmailError}</#if>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" name="firstName" class="form-control" placeholder="first name" value="<#if user??>${user.firstName}</#if>" /><#if firstNameError??>${firstNameError}</#if>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" name="lastName" class="form-control" placeholder="last name" value="<#if user??>${user.lastName}</#if>" /><#if lastNameError??>${lastNameError}</#if>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" name="age" class="form-control" placeholder="age"  value="<#if user??>${user.age!""}</#if>" /><#if ageError??>${ageError}</#if>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>

    <#if !user.isActivated()>
        <div class="mt-3">
            <form action="/user/resendCode" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Send activation code</button>
            </form>
        </div>
    </#if>
</@c.base>