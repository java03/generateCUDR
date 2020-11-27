<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	/**
	* 添加列定义
	*/
	function addCol(ele){
		var table = $(ele).parent().parent().parent();
		//获取最后一个序号
		var tds = table.children().eq(table.children().size()-2).children();
		var idName = tds.eq(tds.size()-1).children().eq(0).prop("id");
		var sequenceNum = parseInt(idName.substring(idName.length - 1, idName.length));
		console.log(sequenceNum);
		//获取最后一个button所在的tr
		var lastTr = table.children().eq(table.children().size()-1);
		lastTr.before(
				"<tr><td>列名：</td>"+
			"<td>"+
				"<input type='text' name='colName"+(sequenceNum+1)+"' id='colName"+(sequenceNum+1)+"'>"+
			"</td>"+
			"<td>"+
				"类型：<input type='text' name='colType"+(sequenceNum+1)+"' id='colType"+(sequenceNum+1)+"'>"+
			"</td>"+
			"<td>"+
				"<input type='button' value='+' id='addColBtn"+(sequenceNum+1)+"' onclick='addCol(this)'>"+
				"<input type='button' value='-' id='addColBtn"+(sequenceNum+1)+"' onclick='rmCol(this)'>"+
			"</td>"+
		"</tr>"
				);
	}
	/**
	*移除列定义
	*/
	function rmCol(ele){
		$(ele).parent().parent().remove();
	}

	function generate(){
		var packageName = $("#package").val();
		var tableName = $("#table").val();
		//获取所有列名组件
		var colNameInputs = $("[id^='colName']");
		//获取所有列类型组件
		var colTypeInputs = $("[id^='colType']");
		//存放列名和类型
		var colDefs = "";
		colNameInputs.each(function(i){
			var colName = $(this).val();
			var type = colTypeInputs.eq(i).val();
			if(colName == null || colName == "" || colName == undefined ||
					type == null || type == "" || type == undefined){
				return;
			}
			colDefs += colName+"&"+type+"$";
		});
		var createTablesql = colDefs.substr(0,colDefs.length-1); 
		console.log(createTablesql);
		//封装提交信息
		var submitInfo = {
				packName : packageName,
				tabName : tableName,
				creaSql : createTablesql
		};
		//提交
		$.post("${pageContext.request.contextPath}/generate",submitInfo,
				function(data){
					if("success" == data){
						$("#package").val("");
						$("#table").val("");
						var colNameInputs = $("[id^='colName']");
						colNameInputs.each(function(i){
							if(i == 0){
								return;
							}
							$(this).parent().remove();
							});
					}else{
						
					}
			});

		
	}
</script>
</head>
<body>
<form action="#">
	<table>
		<tr><td>包名：</td><td colspan="3"><input type="text" name="package" id="package"></td></tr>
		<tr><td>表名：</td><td colspan="3"><input type="text" name="table" id="table"></td></tr>
		<tr><td>列名：</td>
			<td>
				<input type="text" name="colName1" id="colName1">
			</td>
			<td>
				类型：<input type="text" name="colType1" id="colType1">
			</td>
			<td>
				<input type="button" value="+" id="addColBtn1" onclick="addCol(this)">
			</td>
		</tr>
		<tr>
			<td colspan="2"><input value="生成" id="generateBtn" type="button" onclick="generate()"></td>
			<td colspan="2"><input value="下载" id="generateBtn" type="button" disabled="disabled"></td>
		</tr>
	</table>
</form>
</body>
</html>