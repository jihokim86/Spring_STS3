<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header">Board Read</h1>
   </div>
   <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">Board Read Page</div>
         <!-- /.panel-heading -->
       
            <div class="form-group">
               <label >Bno</label> <input class="form-control" name="bno" value='<c:out value="${board.bno }"/>' readonly="readonly">
            </div>
            
            <div class="form-group">
               <label >Title</label> <input class="form-control" name="title" value='<c:out value="${board.title }"/>' readonly="readonly">
            </div>
            
            <div class="form-group">
               <label >Content</label> 
            <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.bno }"/></textarea>
            </div>
            
            <div class="form-group">
               <label>Writer</label> 
               <input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly"></input>
            </div>
            	
            	 <form id='actionForm' action="/board/list" method="get">
		               <input type="hidden" name="pageNum" value='${cri.pageNum}'> 
		               <input type="hidden" name="amount" value='${cri.amount}'>
		               <input type="hidden" id="bno" name="bno" value='${board.bno}'>
		            </form>
		                           
                    <button type="button" class="btn btn-default listBtn"><a href='/board/list'>List</a></button>
				    <button type="button" class="btn btn-default modBtn"><a href='/board/modify?bno=<c:out value="${board.bno}"/>'>Modify</a></button>
				    
					<script>
						var actionForm = $("#actionForm");
						
						$(".listBtn").click(function(e){
							e.preventDefault();
							actionForm.submit();
						})
						
						$(".modBtn").click(function(e){
							e.preventDefault();
							actionForm.attr("action", "/board/modify");
							actionForm.submit();
						})
					
					</script>	
            	
                    </div>
                </div>
            </div>    
            <!-- /.row -->
            </form>
      </div>
      <!-- /.panel-body -->
      </div>
      <!-- /.panel-body -->
	</div>
	<!-- /.col-lg-6 -->
</div>
<!-- /.row -->



<%@include file="../includes/footer.jsp"%>