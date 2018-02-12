<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台(系统管理平台)</title>
	<#include "../common/header.ftl"/>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
</head>
<body>
<#--<div class="row"  style="margin-top:20px;">
    <div class="col-sm-3">
        <ul id="menu" class="list-group">
            <li class="list-group-item">
                <a href="#" data-toggle="collapse" data-target="#systemDictionary_group_detail"><span>数据字典分组</span></a>
                <ul class="in" id="systemDictionary_group_detail">
					<#list systemDictionaryGroups as vo>
                    <li><a class="group_item" data-dataid="${vo.id}" href="#"><span>${vo.title}</span></a></li>
				</#list>
                </ul>
            </li>
        </ul>
    </div>-->
<div id="employee_toolbar">
    <a data-cmd="toAdd" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true">新增</a>
    <a data-cmd="toEdit" id="edit" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-edit',plain:true">编辑</a>
    <a data-cmd="toDelete" id="delete" href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-remote',plain:true">删除</a>
    <div>
        关键字:<input name="keyword">
        <a data-cmd="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
    </div>
</div>
<div id="buttons">
    <a data-cmd="save" href="#" class="easyui-linkbutton">保存</a>
    <a data-cmd="close" href="#" class="easyui-linkbutton">关闭</a>
</div>

<table id="employee_datagrid"></table>

<div id="employee-dialog" class="easyui-dialog" title="新增" style="width:400px;height:300px;"
     data-options="modal:true,closed:true,buttons:'#buttons'">
    <form id="employee_form" method="post">
        <input type="hidden" name="id"/>
        <table>
            <tr>
                <td>名称:</td>
                <td>
                    <input type="text" name="title"/>
                </td>
            </tr>
            <tr>
                <td>序列:</td>
                <td>
                    <input type="text" name="sequence"/>
                </td>
            </tr>
        </table>
    </form>
</div>


<script>
    //点击数据分类 显示当前明细
    var emp_dg = $('#employee_datagrid');
    var emp_from = $("#employee_form");
    var emp_dia = $("#employee-dialog");
    var toolsa = $("#employee_toolbar a").data("cmd");
    emp_dg.datagrid({
        url: '/systemDictionaryItemPage', //拉取分页数据
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        toolbar: "#employee_toolbar",
        columns: [[
            {field: 'title', title: '名称', width: 100, align: 'center'},
            {field: 'sequence', title: '序列', width: 100, align: 'center'}
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
            }else {
                $.messager.confirm("温馨提示","你确定删除", function (yes) {
                    if (yes) {
                        $.post("/systemDictionaryDelete?id="+rowData.id, function (data) {
                            if (!data.success) {
                                $.messager.alert("温馨提示", data.msg, "error");
                                return;
                            }else {
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
            if (!rowData) {
                $.messager.alert("温馨提示", "未选中一行数据", "error");
                return;
            }
            emp_from.form("clear");
            //回显
            emp_from.form("load", rowData);
            emp_dia.dialog("setTitle", '编辑');
            emp_dia.dialog("open");
        },
        save: function () {
            var url = $("input[name='id']").val() ? '/systemDictionaryItemUpdate' : '/systemDictionaryItemSave';
            emp_from.form("submit", {
                url: url,
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
        },
        close: function () {
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
