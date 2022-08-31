<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header">Board Modify/Delete</h1>
   </div>
   <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">Board Modify/Delete Page</div>
         <!-- /.panel-heading -->
       		<form name="frm" id="frm" method="post">
            <div class="form-group">
               <label >Bno</label> <input class="form-control" name="bno" value='<c:out value="${board.bno }"/>' readonly="readonly">
            </div>
            
            <div class="form-group">
               <label >Title</label> <input class="form-control" name="title" value='<c:out value="${board.title }"/>' >
            </div>
            
            <div class="form-group">
               <label >Content</label> 
            <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content }"/></textarea>
            </div>
            
            <div class="form-group">
               <label>Writer</label> 
               <input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
            </div>
            
            <div class="form-group">
               <label>RegDate</label> 
               <input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate }"/>' readonly="readonly" >
            </div>
            
            <div class="form-group">
               <label>Update Date</label> 
               <input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate }"/>' readonly="readonly" >
            </div>
           
            <button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
            <button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
            <button type="submit" data-oper='list' class="btn btn-info">List</button>
            </form>
            
      </div>
      <!-- /.panel-body -->
      </div>
      <!-- /.panel-body -->
	</div>
	<!-- /.col-lg-6 -->
</div>
<!-- /.row -->

<script type="text/javascript">
	$(document).ready(function(){
		
		var formObj = $("#frm");
		
		$('button').on("click",function(e){
			e.preventDefault();
			
			var operation = $(this).data("oper");
			
			console.log(operation);
			
			if(operation === 'remove'){
				$("#frm").attr("action","/board/remove").submit();
			}else if(operation === 'modify'){
				$("#frm").attr("action","/board/modify").submit();
			}else if(operation === 'list'){
				$("#frm").attr("action","/board/list").attr("method","get").submit();
				return;
			}
		/* formObj.submit(); */
		});
	});
	
	
</script>

<%@include file="../includes/footer.jsp"%>