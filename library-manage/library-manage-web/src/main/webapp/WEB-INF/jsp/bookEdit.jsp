<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="bookEditForm" class="bookForm" method="post">
		<input type="hidden" name="id"/>
	    <table cellpadding="5">
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
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	</div>
</div>
<script type="text/javascript">
	var bookEditEditor;
	$(function(){
		bookEditEditor = KindEditor.create("#bookEditForm [name=intro]",
				L.kingEditorParams);
		bookEditEditor.html($("#bookList").datagrid("getSelections")[0].intro);
		LIBRARY.init();
	});
	
	function submitForm(){
		if(!$('#bookEditForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		bookEditEditor.sync();
		$.post("book/bookUpdate.do",$("#bookEditForm").serialize(), function(data){
			if(data.code == 200){
				$.messager.alert('提示','修改图书成功!','info',function(){
					$("#bookEditWindow").window('close');
					$("#bookList").datagrid("reload");
				});
			}else{
				$.messager.alert('提示', data.message);
			}
		});
	}
</script>