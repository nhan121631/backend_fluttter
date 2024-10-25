<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="newURL" value="/quan-tri/new/list"/>
<c:url var="editNewURL" value="/quan-tri/new/edit"/>
<c:url var="newAPI" value="/api/new"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thêm bài viết</title>
</head>
<body>
<div class="main-content">
	<div class="main-content-inner">
		<div class="breadcrumbs" id="breadcrumbs">
			<script type="text/javascript">
				try {
					ace.settings.check('breadcrumbs', 'fixed')
				} catch (e) {
				}
			</script>

			<ul class="breadcrumb">
				<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Home</a>
				</li>

				<li><a href="#">Forms</a></li>
				<li class="active">Form Elements</li>
			</ul>
			<!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<c:if test="${not empty message}">
						<div class="alert alert-${alert}">
  							${message}
						</div>
					</c:if>
					<form:form class="form-horizontal" acceptCharset="UTF-8" action="add" method="POST" modelAttribute="model" enctype="multipart/form-data">
						 <%-- role="form" id="formSubmit" --%>
						<div class="form-group">
							  <label for="categoryCode" class="col-sm-3 control-label no-padding-right">Category:</label>
							  <div class="col-sm-9">
							  	 <form:select path="categoryId" id="categoryId" name="categoryId">
							  	 	<form:option value="" label="-- Chọn thể loại --"/>
							  	 	<form:options items="${categories}"/>
							  	 </form:select>
							  </div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">Product name</label>
								<div class="col-sm-9">
									<form:input path="name" cssClass="col-xs-10 col-sm-5" id="name" name="name"/>
								</div>
						</div>
						
						<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">Cost Price</label>
								<div class="col-sm-9">
									<form:input path="costPrice" cssClass="col-xs-10 col-sm-5" id="costPrice" name="cotsPrice"/>
								</div>
						</div>
						
						<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">Sell Price</label>
								<div class="col-sm-9">
									<form:input path="sellPrice" cssClass="col-xs-10 col-sm-5" id="sellPrice" name="sellPrice"/>
								</div>
						</div>
						
						<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Quantity</label>
								<div class="col-sm-9">
									<form:input path="quantity" cssClass="col-xs-10 col-sm-5" id="quantity" name="quantity" type="number" min="0" step="1"/>
								</div>
						</div>
						
						<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">Image</label>
								<div class="col-sm-9">
									<input type="file" class="col-xs-10 col-sm-5" id="thumbnail" name="thumbnail"/>
								</div>
						</div>
						<div class="form-group">
						  	<label for="shortDescription" class="col-sm-3 control-label no-padding-right">Short Description:</label>
						  	<div class="col-sm-9">
		                   		<form:textarea path="description" rows="5" cols="10" cssClass="form-control summernote" id="description"/>
						  	</div>
						</div>
						<form:hidden path="id" id="newId" name="newId"/>
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
											<c:if test="${not empty model.id}">
												<button class="btn btn-info" type="submit" id="btnAddOrUpdateNew">
													<i class="ace-icon fa fa-check bigger-110"></i>
													Update product
												</button>
											</c:if>
											<c:if test="${empty model.id}">
												<form:button class="btn btn-info" id="btnAddOrUpdateNew">
													<i class="ace-icon fa fa-check bigger-110"></i>
													Add product
												</form:button>
											</c:if>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												Cancel
											</button>
							</div>		
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>	

<script>
$(document).ready(function() {
    //Thiết lập Editor Summernote
    $('.summernote').summernote({
        height: 240,                 
        minHeight: null,             
        maxHeight: null,             
        focus: false                 
    }); 
});
	/* $('#btnAddOrUpdateNew').click(function (e) {
	    e.preventDefault();
	    var data = {};
	    var formData = $('#formSubmit').serializeArray();
	    $.each(formData, function (i, v) {
            data[""+v.name+""] = v.value;
        });
	    var id = $('#newId').val();
	    if (id == "") {
	    	addNew(data);
	    } else {
	    	updateNew(data);
	    }
	});
	
	function addNew(data) {
		$.ajax({
            url: '${newAPI}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
            	window.location.href = "${editNewURL}?id"+result.id+"&message=insert_success";
            },
            error: function (error) {
            	window.location.href = "${newURL}?page=1&limit=2&message=error_system";
            }
        });
	}
	
	function updateNew(data) {
		$.ajax({
            url: '${newAPI}',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
            	window.location.href =  "${editNewURL}?id"+result.id+"&message=update_success";
            },
            error: function (error) {
            	window.location.href =  "${editNewURL}?id"+result.id+"&message=error_system";
            }
        });
	} */
</script>
</body>
</html>