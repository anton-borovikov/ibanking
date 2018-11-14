<#import "common.ftl" as c>

<@c.base>
After registration we sent you email message with activation link.
Please check your email and use the link to activate your account.<br><br>
If you didn't receive the our message we can send it again.
    <#if !user.isActivated()>
        <div class="mt-3">
            <form action="/user/resendCode" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Send activation code</button>
            </form>
        </div>
    </#if>
</@c.base>