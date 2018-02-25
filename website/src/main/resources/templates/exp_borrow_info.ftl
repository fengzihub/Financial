<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
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
					beforeDateTime:"${bidRequest.disableDate?string('MM/dd/yyyy HH:mm:ss')}"
				});
				</#if>
				
				$("#bidBtn").on("click",function(){
					var amount = parseFloat($("#amount").val());
					if(!amount){
						$.messager.popup("请输入投资金额");
						return;
					}
					if(parseFloat($("#usableAmount").val())<amount){
						$.messager.popup("投资金额已超过账户余额");
						return;
					}
					if(parseFloat($("#minBidAmount").val())>amount){
						$.messager.popup("投资金额低于最小投标金额");
						return;
					}
					if(parseFloat($("#maxBidAmount").val())<amount){
						$.messager.popup("投资金额已超过借款标金额");
						return;
					}
					$.post("/borrow_bid.do",{
						bidRequestId : ${bidRequest.id},
						amount:amount
					},function(data){
						if(data.success){
							$.messager.popup("恭喜你,投标成功");
							location.reload();
						}else{
							$.messager.popup(data.message);
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav = "invest" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container" style="margin-top: 10px;">
			<div class="row">
				<div class="col-sm-3">
					<div class="panel panel-default">
						<div class="panel-heading">
							新手标
						</div>
						<div class="panel-body">
							<img class="el-userhead" src="/images/person_icon.png" />
							<p class="text-center">
								<a class="text-info" href="#">系统发布</a>
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

						<#if expAccount??>
						<input id="usableAmount" autocomplete="off" value="${expAccount.usableAmount?string('0.##')}" type="hidden"/>
						<input id="minBidAmount" autocomplete="off" value="${bidRequest.minBidAmount?string('0.##')}" type="hidden"/>
						<input id="maxBidAmount" autocomplete="off" value="${bidRequest.remainAmount?string('0.##')}" type="hidden"/>
						
						<table style="height:100px;width:180px;">
							<tr>
								<td class="muted">可用体验金</td>
								<td>
									<span class="text-info">
										${expAccount.usableAmount}
									</span>
								</td>
							</tr>
							<tr>
								<td class="muted">已投</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td class="muted">还需要</td>
								<td>
									<span class="text-info">
										${bidRequest.remainAmount}
									</span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="form-group">
										<input class="form-control input-sm" id="amount" autocomplete="off" placeholder="投资金额" />
									</div>
								</td>
							</tr>
						</table>
						<button id="bidBtn" class="btn btn-danger  btn-block">
							马上投标
						</button>
						<#else>
							<br />
							<a class="btn btn-danger btn-block" style="font-size: 18px;" href="/login.html">
								登录投标
							</a>
						</#if>
					</#if>
					
					<#if bidRequest.bidRequestState==4 || bidRequest.bidRequestState==5>
						<h4 class="text-primary">满标审核中</h4>
					</#if>
					<#if bidRequest.bidRequestState==7>
						<h4 class="text-primary">还款中</h4>
					</#if>
					<#if bidRequest.bidRequestState==8>
						<h4 class="text-primary">已还清</h4>
					</#if>
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
		
	</body>
</html>