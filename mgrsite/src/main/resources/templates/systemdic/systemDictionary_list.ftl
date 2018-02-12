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
                <td>编码:</td>
                <td>
                    <input type="text" name="sn"/>
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
    emp_dg.datagrid({
        url: '/systemDictionaryPage', //拉取分页数据
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        toolbar: "#employee_toolbar",
        columns: [[
            {field: 'title', title: '名称', width: 100, align: 'center'},
            {field: 'sn', title: '编码', width: 100, align: 'center'}

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
            var url = $("input[name='id']").val() ? '/systemDictionaryUpdate' : '/systemDictionarySave';
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
<#-- <script type="text/javascript">
        $(function () {
            $("#pagination").twbsPagination({
                    totalPages:${pageResult.pages}||1,
                    visiblePages:5,
                    startPage:${qo.currentPage},
                    first:"首页",
                    prev:"上一页",
                    next:"下一页",
                    last:"尾页",
                    onPageClick:function(event,page){
                $("#currentPage").val(page);
                $("#searchForm").submit();
            }
        });

            $("#query").click(function(){
                $("#currentPage").val(1);
                $("#searchForm").submit();
            });
        })
    </script>

</head>

<body>
        <div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu = "systemDictionary" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>数据字典管理</h3>
				</div>
				<div class="row">
					<!-- 提交分页的表单 &ndash;&gt;
					<form id="searchForm" class="form-inline" method="post" action="/systemDictionary_list.do">
						<input type="hidden" name="currentPage" id="currentPage" value=""/>
						<div class="form-group">
						</div>
						<div class="form-group">
						    <label>关键字</label>
						    <input class="form-control" type="text" name="keyword" value="${(qo.keyword)!''}">
						</div>
						<div class="form-group">
							<button id="query" type="button" class="btn btn-success"><i class="icon-search"></i> 查询</button>
							<a data-cmd="toAdd" href="#" class="easyui-linkbutton" id="addSystemDictionaryBtn"
							   data-options="iconCls:'icon-add',plain:true">添加数据字典</a>
						</div>
					</form>
				</div>
				<div class="row">
					<table class="table">
						<thead>
							<tr>
								<th>名称</th>
								<th>编码</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list pageResult.list as vo>
							<tr>
								<td>${vo.title}</td>
								<td>${vo.sn}</td>
								<td>
									<a href="javascript:void(-1);" class="edit_Btn">修改</a>
								</td>
							</tr>
						</#list>
						</tbody>
					</table>

					<div style="text-align: center;">
						<ul id="pagination" class="pagination"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>


    <div id="systemDictionary-dialog" class="easyui-dialog" title="新增" style="width:400px;height:300px;"
         data-options="modal:true,closed:true,buttons:'#buttons'">
        <form id="systemDictionary_form" method="post">
            <input type="hidden" name="id"/>
            <table>
                <tr>
                    <td>名称:</td>
                    <td>
                        <input type="text" name="title"/>
                    </td>
                </tr>
                <tr>
                    <td>编码:</td>
                    <td>
                        <input type="text" name="sn"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
        <script>
                //对<a>标签做统一的事件处理,抽出cmdObj对象
                $('a').click(function () {
                    var cmd = $(this).data('cmd');
                    if (cmd) {
                        cmdObj[cmd]();
                    }
                });
                var cmdObj = {
                    toAdd: function () {
                        $("#systemDictionary_form").form("clear");
                        $("#systemDictionary-dialog").dialog("setTitle","新增");
                        $("#systemDictionary-dialog").dialog("open");
                    }
                },
						
        </script>
    &lt;#&ndash;
        <div id="systemDictionaryModal" class="modal" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑/增加</h4>
              </div>
              <div class="modal-body">
                      <form id="editForm" class="form-horizontal" method="post" action="/systemDictionary_update.do" style="margin: -3px 118px">
                    <input id="systemDictionaryId" type="hidden" name="id" value="" />
                       <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title" placeholder="字典分类名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">编码</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn" placeholder="字典分类编码">
                        </div>
                    </div>
               </form>
              </div>
              <div class="modal-footer">
                  <a href="javascript:void(0);" class="btn btn-success" id="saveBtn" aria-hidden="true">保存</a>
                <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
              </div>
            </div>
          </div>
        </div>&ndash;&gt;

</body>
</html>-->