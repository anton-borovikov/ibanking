<#import "common.ftl" as c>

<@c.base>
    <h5 class="mb-4">Banks services</h5>
    <#list services as service>
        <div class="form-group row">
            <div class="col-sm-12">
                <div class="p-3 bg-light text-dark"><h6>${service.serviceName}</h6></div>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-6 mb-3">
                ${service.serviceDiscription}
            </div>
        </div>
    </#list>
</@c.base>