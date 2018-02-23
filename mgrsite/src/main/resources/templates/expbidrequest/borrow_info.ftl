<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "../common/header.ftl"/>
<link rel="stylesheet" type="text/css" href="/js/plugins/flipcountdown/jquery.flipcountdown.css" />
<script type="text/javascript" src="/js/plugins/flipcountdown/jquery.flipcountdown.js"></script>
		
		<style type="text/css">
			.el-userhead{
				width: 100px;
				height: 100px;
				display: block;
				margin: 0px auto;
				
			}
			.muted{
				color: #999;
			}
			.text-info{
				color: #09d;
			}
		</style>
		
		<script type="text/javascript">
			$(function(){
				<#if bidRequest.disableDate??>
				$("#retroclockbox").flipcountdown({
					size:'xs',
					beforeDateTime:"${bidRequest.disableDate?string('yyyy-MM-dd HH:mm:ss')}"
				});
				</#if>
			});
		</script>
	</head>
	<body>
		<div class="container">
			<#include "../common/top.ftl"/>
			<div class="row">
				<div class="col-sm-3">
					<#include "../common/menu.ftl" />
				</div>
				<div class="col-sm-9">
					<div class="row">
						<div class="col-sm-3">
							<div class="panel panel-default">
								<div class="panel-heading">
									新手标
								</div>
								<div class="panel-body">
									<img class="el-userhead" src="/images/person_icon.png" />
									<p class="text-center">
										<a class="text-info" href="#">${bidRequest.createUser.username}</a>
									</p><br />
								</div>
							</div>	
						</div>
						
						<div class="col-sm-6">
							<h3 class="text-info" style="margin-top: 0px;">
								${bidRequest.title}
								<small>&emsp;<label class="label label-primary">${bidRequest.bidRequestTypeDisplay}</label></small>
							</h3>
							<div>
								<table width="100%" height="250px">
									<tr>
										<td class="muted" width="80px">标的金额</td>
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
										<td class="muted">起投金额</td>
										<td class="text-info" style="padding-left: 10px;">
											${bidRequest.minBidAmount}
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
										<div style="width: ${bidRequest.persent}%;" class="progress-bar progress-bar-info progress-bar-striped"></div>
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
					
					<div class="panel panel-default">
						<div class="panel-heading">
							<div style="font-size: 16px;">标的审核信息</div>
						</div>
						<div class="panel-body">
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
					
					<div class="panel panel-default">
						<div class="panel-heading">
							<div style="font-size: 16px;">投标记录</div>
						</div>
						<div class="panel-body">
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
					
				</div>
			</div>
		</div>
	</body>
</html>