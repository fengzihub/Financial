<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台(系统管理平台)</title>
    <#--<#include "../common/header.ftl"/>-->
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
    <script type="text/javascript" src="/js/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/base.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/plugins/flipcountdown/jquery.flipcountdown.css" />
    <script type="text/javascript" src="/js/plugins/flipcountdown/jquery.flipcountdown.js"></script>

    <script type="text/javascript">
        $(function(){
            $("#retroclockbox").flipcountdown({
                size:'xs',
                beforeDateTime:"${(bidRequest.disableDate?string('yyyy-MM-dd HH:mm:ss'))!''}"
            });
        });
    </script>
</head>
<body>

<div id="tt" class="easyui-tabs" data-options="fit:true">
    <div title="借款人信息">
       <#-- <table id="employee_datagrid" data-options="width:500,height:300"></table>-->
           <div class="row">
               <div class="col-sm-3">
                   <div class="panel panel-default">
                       <div class="panel-heading">
                           借款人
                       </div>
                       <div class="panel-body">
                           <img class="el-userhead" src="/images/person_icon.png" />
                           <p class="text-center">
                               <a class="text-info" href="#">${bidRequest.createUser.username}</a>
                           </p><br />
                           <div>
                               籍贯： XX - OO
                           </div>
                           <div>
                               认证信息：
                               <label class="label label-success">
                               <#if userInfo.isRealAuth>
                                   <span class="glyphicon glyphicon-user"></span>
                               </#if>
                               <#if userInfo.isVedioAuth>
                                   <span class="glyphicon glyphicon-eye-open"></span>
                               </#if>
                               </label>
                           </div>
                       </div>
                   </div>
               </div>

               <div class="col-sm-6">
                   <h3 class="text-info" style="margin-top: 0px;">
                   ${bidRequest.title}借款
                       <small>&emsp;<label class="label label-primary">信</label></small>
                   </h3>
                   <div>
                       <table width="100%" height="250px">
                           <tr>
                               <td class="muted" width="80px">借款金额</td>
                               <td class="text-info" width="120px" style="padding-left: 10px;">
                               ${bidRequest.bidRequestAmount}
                               </td>
                               <td class="muted" width="80px">年化利率</td>
                               <td class="text-info" style="padding-left: 10px;">
                               ${bidRequest.currentRate}%
                               </td>
                           </tr>
                           <tr>
                               <td class="muted ">借款期限</td>
                               <td class="text-info" style="padding-left: 10px;">
                               ${bidRequest.monthes2Return}月
                               </td>
                               <td class="muted">总可得利息</td>
                               <td class="text-info" style="padding-left: 10px;">
                               ${bidRequest.totalRewardAmount}
                               </td>
                           </tr>
                           <tr>
                               <td class="muted">还款方式</td>
                               <td class="text-info" style="padding-left: 10px;">
                               ${bidRequest.returnTypeDisplay}
                               </td>
                               <td class="muted">最小投标</td>
                               <td class="text-info" style="padding-left: 10px;">
                               ${bidRequest.minBidAmount}
                               </td>
                           </tr>
                           <tr>
                               <td class="muted">风控意见</td>
                               <td class="text-info" style="padding-left: 10px;" colspan="3">
                               ${(bidRequest.note)!""}
                               </td>
                           </tr>
                           <tr>
                               <td class="muted">剩余时间</td>
                               <td class="text-info" style="padding-left: 10px;" colspan="3">
                                   <div id="retroclockbox"></div>
                               </td>
                           </tr>
                       </table>
                   </div>
               </div>
               <div class="col-sm-3">
                   <table style="height:110px;width:230px;">
                       <tr>
                           <td  class="muted">投标总数</td><td class="text-info" style="padding-left: 10px;">
                       ${bidRequest.bidCount}
                       </td>
                       </tr>
                       <tr>
                           <td  class="muted">还需金额</td><td class="text-info" style="padding-left: 10px;">
                       ${bidRequest.remainAmount} 元
                       </td>
                       </tr>
                       <tr>
                           <td  class="muted" colspan="2">投标进度</td>
                       </tr>
                       <tr>
                           <td colspan="2">
                               <div style="margin-bottom: 10px;" class="progress">
                                   <div style="width: ${bidRequest.persent}%" class="progress-bar progress-bar-info progress-bar-striped"></div>
                               </div>
                           </td>
                       </tr>
                   </table>

               <#if bidRequest.bidRequestState==1>
                   <br />
                   <a class="btn btn-danger btn-block" style="font-size: 18px;" href="#">
                       投标中
                   </a>
               <#elseif bidRequest.bidRequestState==4 || bidRequest.bidRequestState==5>
                   <h4 class="text-primary">满标审核中</h4>
               <#elseif bidRequest.bidRequestState==7>
                   <h4 class="text-primary">还款中</h4>
               <#elseif bidRequest.bidRequestState==8>
                   <h4 class="text-primary">已还清</h4>
               </#if>
               </div>
           </div>
    </div>
    <div title="标的审核信息">
        <div  class="easyui-panel" title="标的审核信息">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>审核类型</th>
                    <th>审核时间</th>
                    <th>审核人</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <#list audits as audit>
                <tr style="cursor: pointer;" lid="2101" st="1" class="more">
                    <td>${audit.auditTypeDisplay}</td>
                    <td>${audit.auditTime?string("yyyy-MM-dd HH:mm:SS")}</td>
                    <td>${audit.auditor.username}</td>
                    <td>${audit.remark}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div title="借款人信息">
        <div class="easyui-panel" title="借款人信息">
            <table>
                <tbody>
                <tr>
                    <td class="muted text-right" width="140px;">真实姓名</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${realAuth.anonymousRealName}
                    </td>
                    <td class="muted text-right" width="140px;">生日</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${realAuth.bornDate}
                    </td>
                    <td class="muted text-right" width="140px;">身份证号码</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${realAuth.anonymousIdNumber}
                    </td>
                    <td class="muted text-right" width="140px;">身份证地址</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${realAuth.anonymousAddress}
                    </td>
                </tr>
                <tr>
                    <td class="muted text-right" width="140px;">注册时间</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">

                    </td>
                    <td class="muted text-right" width="140px;">借款额度</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${bidRequest.bidRequestAmount}
                    </td>
                    <td class="muted text-right" width="140px;">性别</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${realAuth.sexDisplay}
                    </td>
                    <td class="muted text-right" width="140px;">住房条件</td>
                    <td width="150px;" style="padding-left: 10px;" class="text-info">
                    ${userInfo.houseCondition.title}
                    </td>
                </tr>
                <tr>
                    <td class="muted text-right">文化程度</td>
                    <td style="padding-left: 10px;" class="text-info">
                    ${userInfo.educationBackground.title}
                    </td>
                    <td class="muted text-right">每月收入</td>
                    <td style="padding-left: 10px;" class="text-info">
                    ${userInfo.incomeGrade.title}
                    </td>
                    <td class="muted text-right">婚姻情况</td>
                    <td style="padding-left: 10px;" class="text-info">
                    ${userInfo.marriage.title}
                    </td>
                    <td class="muted text-right">子女情况</td>
                    <td style="padding-left: 10px;" class="text-info">
                    ${userInfo.kidCount.title}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div title="认证材料">
        <div class="easyui-panel" title="认证材料">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>材料类型</th>
                    <th>材料数量</th>
                </tr>
                </thead>
                <tbody>
                <#list userFiles as file>
                <tr style="cursor: pointer;" lid="2101" st="1" class="more">
                    <th>${file.fileType.title}</th>
                    <td>1</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div title="还款情况">
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>还款状态</th>
                    <th>最近一周</th>
                    <th>最近1月</th>
                    <th>最近6月</th>
                    <th>6个月前</th>
                    <th>总计[?]</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>提前还款</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                </tr>
                <tr>
                    <td>准时还款</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                </tr>
                <tr>
                    <td>逾期已还</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                </tr>
                <tr>
                    <td>逾期未还</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                    <td>0</td>
                </tr>
                </tbody>
            </table>
        </div>
    <div>
    <div title="xxx">
    <div>
        <div class="easyui-panel" title="投标记录">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>投标人</th>
                    <th>年利率 </th>
                    <th>有效金额(¥)</th>
                    <th>投标时间</th>
                    <th>类型</th>
                </tr>
                </thead>
                <tbody>
                <#if bidRequest.bids?size &gt; 0>
                    <#list bidRequest.bids as bid>
                    <tr>
                        <td>${bid.bidUser.username}</td>
                        <td>
                        ${bid.actualRate}%
                        </td>
                        <td style="padding-right:60px;" class="text-info">
                        ${bid.availableAmount}
                        </td>
                        <td>
                        ${bid.bidTime?string("yyyy-MM-dd HH:mm:ss")}
                        </td>
                        <td>手动投标</td>
                    </tr>
                    </#list>
                <#else>
                <tr>
                    <td colspan="6">
                        <p class="text-primary text-center">暂时没有投标数据</p>
                    </td>
                </tr>
                </#if>
                </tbody>
            </table>
        </div>
</div>

<#--<table id="employee_datagrid"></table>-->



<script>

    var emp_dg = $('#employee_datagrid');
    /*var emp_from = $("#employee_form");
    var emp_dia = $("#employee-dialog");*/
   /* var toolsa = $("#employee_toolbar a").data("cmd");*/


    //审核提交按钮
   /* $(".sh").click(function () {
        var ret = $(this).val();
        console.log(ret);
        $("#state").val(ret);

        emp_from.form("submit", {
            url: '/bidRequest_audit',
            success: function (data) {
                data = JSON.parse(data);
                if (!data.success) {
                    $.messager.alert("温馨提示", data.msg, "error");
                    return;
                }
                $.messager.alert("温馨提示","保存成功", "info", function () {
                    emp_dia.dialog("close");
                    emp_dg.datagrid("reload");
                });
            }
        })
    }),*/

    /*emp_dg.datagrid({
        url: '/bidRequestPage', //拉取分页数据
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            {field: 'title', title: '标题', width: 100, align: 'center',formatter: function (value, row, index) {
                console.log(row);
                return '<a target="_blank" href="/borrow_info.do?id='+row.id+'">'+row.title+'</a>';
            }
            },
            {
                field: 'createUser', title: '借款人', width: 100, align: 'center', formatter: function (value, row, index) {
                // console.log(row);
                if (row.createUser == null) {
                    return "";
                } else {
                    return row.createUser.username;
                }
            }
            },
            {field: 'applyTime', title: '申请时间', width: 100, align: 'center'},
            {field: 'bidRequestAmount', title: '借款金额(元)', width: 100, align: 'center'},
            {field: 'monthes2Return', title: '期限', width: 100, align: 'center'},
            {field: 'currentRate', title: '利率', width: 100, align: 'center'},
            {field: 'totalRewardAmount', title: '总利息', width: 100, align: 'center'},
            {field: 'bidRequestStateDisplay', title: '状态', width: 100, align: 'center'}

        ]]

    });
    //对<a>标签做统一的事件处理,抽出cmdObj对象
    $('a').click(function () {
        var cmd = $(this).data('cmd');
        if (cmd) {
            cmdObj[cmd]();
        }
    });*/

   /* /!*var cmdObj = {
        toAdd: function () {
            emp_from.form("clear");
            emp_dia.dialog("setTitle", '新增');
            emp_dia.dialog("open");
        },

        toEdit: function () {
            var rowData = emp_dg.datagrid("getSelected");
            //console.log(rowData);
            if (!rowData) {
                $.messager.alert("温馨提示", "未选中一行数据", "error");
                return;
            }
            emp_from.form("clear");

            //审核回显
            $("#id").val(rowData.id);
            $("#username").html(rowData.createUser.username);
            $("#title").html(rowData.title);
            $("#bidRequestAmount").html(rowData.bidRequestAmount);
            $("#currentRate").html(rowData.currentRate);
            $("#monthes2Return").html(rowData.monthes2Return);
            $("#totalRewardAmount").html(rowData.totalRewardAmount);
            $("#returnType").html(rowData.returnTypeDisplay);

            //emp_from.form("load", rowData);
            emp_dia.dialog("setTitle", '满标一审');
            emp_dia.dialog("open");



        },
        //审核提交按钮
        c1: function () {
            emp_dia.dialog("close");
        },
        search: function () {
            emp_dg.datagrid('load', {
                keyword: $('input[name="keyword"]').val()
            });
        }

    };*/

</script>

</body>
</html>