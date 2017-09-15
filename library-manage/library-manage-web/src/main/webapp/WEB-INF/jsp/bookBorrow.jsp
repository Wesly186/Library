<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="padding: 10px 10px 10px 10px">
	<form id="bookBorrowForm" class="borrowForm" method="post">
		<table cellpadding="10">
			<tr>
				<td>用户名</td>
				<td>
					<input class="easyui-textbox" type="text" name="username" data-options="required:true" validType="remote['user/checkUsername.do','username']"   
                            missingMessage="用户名不能为空" invalidMessage="用户名不存在" />
				</td>
			</tr>
			<tr>
				<td>书名</td>
				<td>
					<input class="easyui-textbox" type="text" name="title" data-options="required:true"  validType="remote['book/checkBookName.do','title']"   
                            missingMessage="书名不能为空" invalidMessage="书名不存在" style="width: 280px;"/>
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
	//提交表单
	function submitForm() {
		//有效性验证
		if (!$('#bookBorrowForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		//ajax的post方式提交表单
		$.post("/book/bookBorrow.do", $("#bookBorrowForm").serialize(), function(data) {
			if (data.code == 200) {
				$.messager.alert('提示', '借阅图书成功!');
				clearForm();
			}else{
				$.messager.alert('提示', data.message);
			}
		});
	}

	function clearForm() {
		$('#bookBorrowForm').form('reset');
	}
</script>