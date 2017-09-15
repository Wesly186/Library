<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="bookList" title="图书列表" 
       data-options="singleSelect:false,collapsible:false,pagination:true,url:'/book/bookList.do',method:'get',pageSize:20,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:100">IBSN</th>
            <th data-options="field:'title',width:150">书名</th>
            <th data-options="field:'image',width:500">封面</th>
            <th data-options="field:'totalNum',width:50">馆藏量</th>
            <th data-options="field:'borrowedNum',width:50">借出数</th>
            <th data-options="field:'cid',width:70">图书分类</th>
            <th data-options="field:'intro',width:200">介绍</th>
        </tr>
    </thead>
</table>
<div id="bookEditWindow" class="easyui-window" title="编辑图书" data-options="modal:true,closed:true,iconCls:'icon-save',href:'bookEdit.do'" style="width:70%;height:80%;padding:10px;">
</div>
<script>

    function getSelectionsIds(){
    	var bookList = $("#bookList");
    	var sels = bookList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	$(".tree-title:contains('图书录入')").parent().click();
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一本图书才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一本图书!');
        		return ;
        	}
        	
        	$("#bookEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#bookList").datagrid("getSelections")[0];
        			$("#bookEditForm").form("load",data);
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中图书!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的图书吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("book/bookDelete.do",params, function(data){
            			if(data.code == 200){
            				$("#bookList").datagrid("reload");            					
            			}else{
            				$.messager.alert('提示',data.message);
            			}
            		});
        	    }
        	});
        }
    }];
</script>