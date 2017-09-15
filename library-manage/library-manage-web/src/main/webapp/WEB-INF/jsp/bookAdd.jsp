<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="bookAddForm" class="bookForm" method="post">
		<table cellpadding="10">
			<tr>
				<td>ISBN码:</td>
				<td>
					<input class="easyui-numberbox" type="text" name="id" data-options="validType:'length[13,13]',required:true" />
				</td>
			</tr>
			<tr>
				<td>图书标题:</td>
				<td>
					<input class="easyui-textbox" type="text" name="title" data-options="required:true" style="width: 280px;"></input>
				</td>
			</tr>
			<tr>
				<td>图书类目:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton selectBookCat" >选择类目</a> 
					<input id="inputCid" type="hidden" name="cid" style="width: 280px;"></input>
				</td>
			</tr>
			<tr>
				<td>图书封面:</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton onePicUpload">上传图片</a>
					<input type="hidden" name="image" />
				</td>
			</tr>
			<tr>
				<td>馆藏数量:</td>
				<td>
					<input class="easyui-numberbox" type="text" name="totalNum" data-options="min:1,max:10,precision:0,required:true" />
				</td>
			</tr>
			<tr>
				<td>图书描述:</td>
				<td>
					<textarea style="width: 800px; height: 300px; visibility: hidden;" name="intro"></textarea>
				</td>
			</tr>
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	var bookAddEditor;
	//页面初始化完毕后执行此方法
	$(function() {
		//创建富文本编辑器
		bookAddEditor = KindEditor.create("#bookAddForm [name=intro]",
				L.kingEditorParams);
		//初始化类目选择和图片上传器
		LIBRARY.init();
	});
	//提交表单
	function submitForm() {
		//有效性验证
		if (!$('#bookAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		//同步文本框中的商品描述
		bookAddEditor.sync();

		//ajax的post方式提交表单
		$.post("/book/bookAddSubmit.do", $("#bookAddForm").serialize(), function(data) {
			if (data.code == 200) {
				$.messager.alert('提示', '添加图书成功!');
				clearForm();
			}else{
				$.messager.alert('提示', data.message);
			}
		});
	}

	function clearForm() {
		$('#bookAddForm').form('reset');
		bookAddEditor.html('');
		$('#tempImage').remove();
		$('#spanCname').text('');
		$('#inputCid').val('');
	}
</script>
