<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Danh sách Order</title>
</head>

<body>
    <div class="main-content">            
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Trang chủ</a>
                    </li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <c:if test="${not empty message}">
                            <div class="alert alert-${alert}">
                                ${message}
                            </div>
                        </c:if>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Order id</th>
                                                <th>Full Name</th>
                                                <th>Phone</th>
                                                <th>Address</th>
                                                <th>Note</th>
                                                <th>Status</th>
                                                <th>Payment</th>
                                                <th>Total</th>
                                                <th>Created Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${model}">
                                                <tr>
                                                    <td>${item.id}</td>
                                                    <td>${item.fullname}</td>
                                                    <td>${item.phone}</td>                                                
                                                    <td>${item.address}</td>
                                                    <td>${item.note}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${item.status == 0}">
                                                                <button class="btn btn-warning" onclick="updateStatus(${item.id})">Giao hàng</button>
                                                            </c:when>
                                                            <c:when test="${item.status == 1}">
                                                                <button class="btn btn-success" onclick="updateStatus(${item.id})">Hoàn thành</button>
                                                            </c:when>
                                                            <c:when test="${item.status == 2}">
                                                                <span style="color: green">Đã giao xong</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>${item.payment}</td>
                                                    <td>${item.totalPrice}</td>
                                                    <td>${item.createdDate}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <ul class="pagination" id="pagination"></ul>    
                                    <input type="hidden" value="" id="page" name="page"/>
                                    <input type="hidden" value="" id="limit" name="limit"/>                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

   <script>
function updateStatus(orderId) {
    console.log("Updating status for order ID:", orderId);
    $.ajax({
        url: 'updateStatus?id=' + orderId, // Pass orderId as a request parameter
        type: 'PUT',
        contentType: 'application/json',
        success: function (result) {
            console.log("Status updated successfully");
            // Reload or refresh the order list after successful update
            window.location.href = "list";
        },
        error: function (error) {
            console.log("Error updating status");
            window.location.href = "list?,message=update_fail";
        }
    });
}
</script>

</body>
</html>
