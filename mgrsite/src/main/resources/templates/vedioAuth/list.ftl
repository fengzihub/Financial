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

    <a data-cmd="toAdd" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true">添加审核</a>

    <a data-cmd="toEdit" id="edit" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-edit',plain:true"></a>
    <a data-cmd="toDelete" id="delete" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-remote',plain:true">删除</a>

    <div>
        关键字:<input name="keyword">
        <a data-cmd="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
    </div>
</div>
<div id="buttons">
<#--<a data-cmd="s1" href="#" class="easyui-linkbutton" value="1">审核通过</a>
<a data-cmd="s2" href="#" class="easyui-linkbutton" value="2">审核拒绝</a>
<a data-cmd="c1" href="#" class="easyui-linkbutton">关闭</a>-->

    <button type="button" class="easyui-linkbutton sh" value="1">通过</button>
    <button type="button" class="easyui-linkbutton sh" value="2">拒绝</button>

</div>

<table id="employee_datagrid"></table>

<div id="employee-dialog" class="easyui-dialog" title="新增" style="width:400px;height:300px;"
     data-options="modal:true,closed:true,buttons:'#buttons'">
    <form id="employee_form" method="post" action="/realAuth_audit">
    <#--<input type="hidden" name="id" id="id" value=""/>-->
        <input type="hidden" name="state" id="state" value=""/>
        <table>
            <tr>
                <td>用户名:</td>
                <td>
                    <input type="text" name="username" id="username" class="easyui-combobox"/>
                    <input type="hidden" name="id" id="id"/>
                </td>
            </tr>
            <tr>
                <td>审核备注:</td>
                <td>
                    <input type="text" name="remark" id="remark"/>
                </td>
            </tr>
        </table>
    </form>
</div>


<script>
    var emp_dg = $('#employee_datagrid');
    var emp_from = $("#employee_form");
    var emp_dia = $("#employee-dialog");
    var toolsa = $("#employee_toolbar a").data("cmd");

    //添加审核 easyui combobox下拉框自动补全
    $("#username").combobox({
        method: 'get',
        valueField: 'username',
        textField: 'username',
        mode: "remote",
        onChange: function (values, o) {
            if (values != "") {
                var url = '/vedioAuth_autocomplate?keyword='+values;
                $("#username").combobox("reload", url);
            }
        },
        onSelect:function(record){
            if(record != ""){
                $("#id").val(record.id);
            }
        }
    });

    //审核提交按钮
    $(".sh").click(function () {
        var ret = $(this).val();
        console.log(ret);
        $("#state").val(ret);

        emp_from.form("submit", {
            url: '/vedioAuth_audit',
            success: function (data) {
                data = JSON.parse(data);
                    if (!data.success) {
                        $.messager.alert("温馨提示", data.msg, "error");
                        return;
                    }
                    $.messager.alert("温馨提示", "保存成功", "info", function () {
                        emp_dia.dialog("close");
                        emp_dg.datagrid("reload");
                    });
                }
            })
        }),

        emp_dg.datagrid({
            url: '/vedioAuthPage', //拉取分页数据
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            toolbar: "#employee_toolbar",
            columns: [[
                {
                    field: 'applier', title: '用户名', width: 100, align: 'center', formatter: function (value, row, index) {
                    // console.log(row);
                    if (row.applier == null) {
                        return "";
                    } else {
                        return row.applier.username;
                    }
                }
                },
                {field: 'stateDisplay', title: '状态', width: 100, align: 'center'},
                {field: 'remark', title: '审核说明', width: 100, align: 'center'},

                {
                    field: 'auditor', title: '审核人', width: 100, align: 'center', formatter: function (value, row, index) {
                    // console.log(row);
                    if (row.auditor == null) {
                        return "";
                    } else {
                        return row.auditor.username;
                    }
                }
                },
                {field: 'auditTime', title: '审核时间', width: 100, align: 'center'}
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
        toAdd: function () {
            emp_from.form("clear");
            emp_dia.dialog("setTitle", '新增');
            emp_dia.dialog("open");
        },
        toDelete: function () {
            var rowData = emp_dg.datagrid("getSelected");
            if (!rowData) {
                $.messager.alert("温馨提示", "未选中一行数据", "error");
                return;
            } else {
                $.messager.confirm("温馨提示", "你确定删除", function (yes) {
                    if (yes) {
                        $.post("/systemDictionaryDelete?id=" + rowData.id, function (data) {
                            if (!data.success) {
                                $.messager.alert("温馨提示", data.msg, "error");
                                return;
                            } else {
                                $.messager.alert("温馨提示", data.msg, "info", function () {
                                    emp_dia.dialog("close");
                                    emp_dg.datagrid("reload");
                                });
                            }
                        })
                    }
                })
            }
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
            $("#username").val(rowData.applier.username);
            $("#realName").val(rowData.realName);
            $("#idNumber").val(rowData.idNumber);

            $("#remark").val(rowData.remark);

            //emp_from.form("load", rowData);
            emp_dia.dialog("setTitle", '审核');
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

    };

</script>

</body>
</html>