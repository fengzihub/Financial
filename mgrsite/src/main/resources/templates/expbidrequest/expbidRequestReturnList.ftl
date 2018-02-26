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
</head>
<body>
<div id="employee_toolbar">

<#-- <a data-cmd="toAdd" href="#" class="easyui-linkbutton"
    data-options="iconCls:'icon-add',plain:true">新增</a>
-->
  <a data-cmd="returnMoney" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-edit',plain:true">还款</a>

</div>


<table id="employee_datagrid"></table>



<script>

    var emp_dg = $('#employee_datagrid');
    var emp_from = $("#employee_form");
    var emp_dia = $("#employee-dialog");
    var toolsa = $("#employee_toolbar a").data("cmd");


    emp_dg.datagrid({
        url: '/expBidRequestReturnListPage', //拉取分页数据
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        toolbar: "#employee_toolbar",
        columns: [[

            {field: 'bidRequestTitle', title: '标题', width: 100, align: 'center',formatter: function (value, row, index) {
                console.log(row);
                return '<a target="_blank" href="/borrow_info.do?id='+row.bidRequestId+'">'+row.bidRequestTitle+'</a>';
            }
            },
            {field: 'totalAmount', title: '结算体验标金额', width: 100, align: 'center'},
            {field: 'principal', title: '结算体验金', width: 100, align: 'center'},
            {field: 'interest', title: '结算利息', width: 100, align: 'center'},
            {field: 'deadLine', title: '结算期限', width: 100, align: 'center'},
            {field: 'stateDisplay', title: '状态', width: 100, align: 'center'}


        ]]

    });
    //对<a>标签做统一的事件处理,抽出cmdObj对象
    $('a').click(function () {
        var cmd = $(this).data('cmd');
        if (cmd) {
            cmdObj[cmd]();
        }
    });

    var cmdObj = {

        returnMoney: function () {
            var rowData = emp_dg.datagrid("getSelected");
            if (!rowData) {
                $.messager.alert("温馨提示", "未选中一行数据", "error");
                return;
            } else {
                $.messager.confirm("温馨提示", "你确定还款", function (yes) {
                    if (yes) {
                        $.post("/returnMoney?id=" + rowData.id, function (data) {
                            if (!data.success) {
                                $.messager.alert("温馨提示", data.msg, "error");
                                return;
                            } else {
                                $.messager.alert("温馨提示", "还款成功", "info", function () {
                                    emp_dia.dialog("close");
                                    emp_dg.datagrid("reload");
                                });
                            }
                        })
                    }
                })
            }
        },



    };

</script>

</body>
</html>