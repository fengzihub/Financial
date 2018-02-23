<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "../common/header.ftl"/>
<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" ></script>

<script type="text/javascript">
	$(function() {

		$("#editForm").validate({
			rules : {
				bidRequestAmount:{
					required:true,
					number:true,
					min:${minBidRequestAmount}
				},
				currentRate:{
					required:true,
					number:true,
					min:5,
					max:20
				},
				minBidAmount:{
					required:true,
					number:true,
					min:${minBidAmount}
				},
				title:"required"
			},
			messages: {
				bidRequestAmount:{
					required:"请填写借款金额",
					number:"借款金额为数字",
					min:"借款金额最小为{0}"
				},
				currentRate:{
					required:"请填写借款利息",
					number:"借款利息为数字",
					min:"最低借款利息为{0}%",
					max:"最大借款利息不能超过{0}%"
				},
				minBidAmount:{
					required:"请填写最小投标金额",
					number:"最小投标金额为数字",
					min:"最小投标金额必须大于{0}"
				},
				title:"必须填写借款原因"
			},
            submitHandler:function(form){
                $(form).ajaxSubmit(function(data){
                    console.log(data);
                    window.location.href="/expBidRequest_list.do";
                });
            }
			/*,
			submitHandler:function(form){
				$(form).ajaxSubmit(function(data){
					window.location.href="/expBidRequest_list.do";
				});
			},
			//自定义错误样式
			errorClass:"text-danger col-sm-6",
			//未通过验证,进行高亮处理或其他处理；
			highlight:function(input){
				$(input).closest(".form-group").addClass("has-error");
			},
			//通过验证,清除高亮效果或其他处理；
			unhighlight:function(input){
				$(input).closest(".form-group").removeClass("has-error");
			},
			//错误提示信息加载的位置
			errorPlacement:function(label, element){
				label.appendTo(element.closest(".form-group"));
			}*/
		});

	});
</script>
</head>
<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu="expBidRequestPublish" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>体验金发布</h3>
				</div>
				<form class="form-horizontal el-borrow-form" id="editForm" novalidate="novalidate" method="post" action="/expBidRequestPublish.do">
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							借款金额
							<input class="form-control" name="bidRequestAmount" id="bidRequestAmount"/>
							<span class="input-group-addon">元</span>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							借款利息<input class="form-control" name="currentRate" /><span class="input-group-addon">%</span>

					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							借款期限
							<select class="form-control" name="monthes2Return">
								<option value="1">1</option>
							</select>
							<span class="input-group-addon">月</span>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							还款方式
						<label class="radio-inline">
							<input type="radio" value="0" checked="checked" name="returnType" />
							按月分期
						</label>
						<label class="radio-inline">
							<input type="radio" value="1" name="returnType" />
							按月到期
						</label>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							最小投标
							<input class="form-control" name="minBidAmount" />
							<span class="input-group-addon">元</span>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							招标天数
							<select class="form-control" name="disableDays">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
							<span class="input-group-addon">天</span>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"/>
							借款标题
							<input name="title" class="form-control" />
					</div>
					<div class="form-group">
						<button class="btn btn-primary col-lg-offset-3" type="submit">
							发布体验标
						</button>
					</div>
					
				</form>				
			</div>
		</div>
	</div>
</body>
</html>