<#import "common.ftl" as c>
<@c.base>
    <h5 class="mb-4">Banks branches</h5>

    <script>
        function citySelect() {
            var sel = document.getElementById("cityFilter");
            var city = sel.options[sel.selectedIndex].text;
            document.location = 'http://localhost:8080/branches?cityFilter=' + city;
        }
    </script>

    <div class="form-group col-sm-3">
        <select class="form-control" name="cityFilter" id="cityFilter" onchange="citySelect()">
            <option selected="selected" disabled="disabled">Select your city:</option>
            <option>All</option>
                    <#list cities as city>
                        <option>${city.cityName}</option>
                    </#list>
        </select>
    </div>

    <div id="branchList">
    <#list branches as branch>
        <div  id="${branch.id}">
            <div class="form-group row mt-4">
                <div class="col-sm-12">
                    <div class="p-3 bg-light text-dark"><h6>${branch.branchName}</h6></div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-6 mb-3">
                    ${branch.branchAddress}
                </div>
            </div>
        </div>
    </#list>
    </div>
</@c.base>