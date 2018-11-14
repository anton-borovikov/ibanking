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
    <h5 class="mb-4">Your accounts</h5>
    <div id="accountList">
        <#list accountsUser as account>
          <div class="form-group row">
              <label class="col-sm-2 col-form-label">${account.accountNumber}</label>
              <label class="col-sm-2 col-form-label">${account.accountBalance}</label>
          </div>
        </#list>
    </div>
    <h5 class="mb-4">Payments</h5>
    <form action="/accounts" method="post" class="form-inline">
        <div class="form-group col-sm-12">
            <select class="form-control col-sm-3" name="accountFrom">
                <option selected="selected" disabled="disabled">From account</option>
                <#list accountsUser as account>
                      <div class="form-group row">
                          <option>
                              <label class="col-sm-3 col-form-label">${account.accountNumber} - ${account.accountBalance} RUB</label>
                          </option>
                      </div>
                </#list>
            </select>
            <select class="form-control col-sm-4 ml-2" name="accountTo">
                <option selected="selected" disabled="disabled">To account</option>
                <#list accountsAll as account>
                      <div class="form-group row">
                          <option>
                              <label class="col-sm-4 col-form-label">${account.accountNumber} - ${account.accountBalance} RUB (${account.user.getFirstName()})</label>
                          </option>
                      </div>
                </#list>
            </select>
            <input type="text" name="amount" class="form-control col-sm-2 ml-2" placeholder="Amount"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary ml-2">Transfer</button>
        </div>
    </form>
</@c.base>