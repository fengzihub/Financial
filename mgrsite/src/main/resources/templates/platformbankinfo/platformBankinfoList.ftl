<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>平台账户管理</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
    <script type="text/javascript" src="/js/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/js/bank.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/base.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>


    <script>

    </script>


</head>
<body>
<div id="employee_toolbar">
    <div>
        <a data-cmd="toSave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>

        <a data-cmd="toUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </div>
</div>
<table id="employee_datagrid"></table>

<div id="employee_dialog" class="easyui-dialog" title="新增" style="width:500px;height:300px;"
     data-options="modal:true,closed:true,buttons:'#employee_diaglog_buttons'">
    <form id="employee_form" method="post">
        <input type="hidden" name="id">
        <table align="center" style="margin-top: 15px;">

            <tr>
                <td>名称</td>
                <td>
                <select id="bankType" class="form-control" autocomplete="off" name="bankName">
                    <option value="工商银行">工商银行</option>
                    <option value="农业银行">农业银行</option>
                </select>
                </td>

            </tr>
            <tr>
                <td>开户人</td>
                <td><input type="text" name="accountName"></td>
            </tr>
            <tr>
                <td>账号</td>
                <td><input type="text" name="accountNumber"></td>
            </tr>
            <tr>
                <td>开户行</td>
                <td><input type="text" name="bankForkName"></td>
            </tr>
        </table>
    </form>
</div>
<div id="employee_diaglog_buttons">
    <a data-cmd="save" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
    <a data-cmd="cancel" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>
<script>

        var employeeDatagrid = $('#employee_datagrid');
        var employeeForm = $('#employee_form');
        var employeeDialog = $('#employee_dialog');

        var cmdObject = {
            toSave:function () {
                employeeForm.form('clear');
                employeeDialog.dialog('setTitle', '新增');
                employeeDialog.dialog('open');
            },
            toUpdate:function () {
                var rowData = employeeDatagrid.datagrid('getSelected');
                if(!rowData){
                    $.messager.alert('温馨提示', '未选中一行！', 'error');
                    return;
                }

                employeeForm.form('clear');
                employeeDialog.dialog('setTitle', '编辑');
                employeeForm.form('load', rowData);


                $("#id").val(rowData.id);
                $("#bankName").val(rowData.bankName);
                $("#accountName").val(rowData.accountName);
                $("#accountNumber").val(rowData.accountNumber);
                $("#bankForkName").val(rowData.bankForkName);

                employeeDialog.dialog('open');
            },
            save:function () {
                var url = $('[name="id"]').val() ? '/platformBankinfoUpdate' : '/platformBankinfoSave';
                employeeForm.form('submit', {
                    url:url,
                    success:function (data) {
                        data = JSON.parse(data);
                        if(!data.success){
                            $.messager.alert('温馨提示', data.msg, 'error');
                            return;
                        }
                        $.messager.alert('温馨提示','保存成功', 'info', function () {
                            employeeDialog.dialog('close');
                            employeeDatagrid.datagrid('reload');
                        });
                    }
                })
            },
            search:function () {
                employeeDatagrid.datagrid('load', {
                    'keyword' : $('input[name="keyword"]').val()
                });
            },
            cancel:function () {
                employeeDialog.dialog('close');
            }
        };

        employeeDatagrid.datagrid({
            url:'/platformBankinfoPage',
            fit:true,
            fitColumns:true,
            singleSelect:true,
            toolbar:'#employee_toolbar',
            pagination:true,
            columns:[[
                {field:'bankName',title:'银行',width:1,align:'center'},
                {field:'accountName',title:'开户人',width:1,align:'center'},
                {field:'accountNumber',title:'账号',width:1,align:'center'},
                {field:'bankForkName',title:'开户行',width:1,align:'center'}

            ]],
            onClickRow: function (rowIndex, rowData) {
                $('#toUpdate').linkbutton(rowData.state == 0 ? 'enable' : 'disable');
            }
        });

        $('a').click(function () {
            var cmd = $(this).data('cmd');
            if(cmd){
                cmdObject[cmd]();
            }
        });


</script>
</body>
</html>