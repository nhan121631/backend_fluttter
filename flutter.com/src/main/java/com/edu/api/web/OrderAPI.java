package com.edu.api.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.converter.OrderConverter;
import com.edu.dto.CartItemDTO;
import com.edu.dto.OrderDTO;
import com.edu.dto.OrderItemDTO;
import com.edu.dto.ProductDTO;
import com.edu.entity.CartEntity;
import com.edu.entity.CartitemEntity;
import com.edu.entity.CategoryEntity;
import com.edu.entity.OrderEntity;
import com.edu.entity.OrderitemEntity;
import com.edu.entity.ProductEntity;
import com.edu.entity.UserEntity;
import com.edu.service.CartItemService;
import com.edu.service.CartService;
import com.edu.service.OrderItemService;
import com.edu.service.OrderService;
import com.edu.service.ProductService;

@RestController(value = "orderAPIOfWeb")
public class OrderAPI {
	@Autowired OrderService orderService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired CartService cartService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderConverter orderConverter;

    @GetMapping("/api/order/get")
    public ResponseEntity<List<OrderDTO>> getOrder(@RequestParam("user_id") Long id) {
        List<OrderEntity> orders = orderService.findByUserIdAndStatusIsNot(id, 3);    	
        List<OrderDTO> listorders = new ArrayList<>();
        for (OrderEntity item : orders) {
			listorders.add(orderConverter.toDto(item));
			System.out.println("product: "+item.getOrderitems());
			}
        return ResponseEntity.ok(listorders);
    }
    
    
    @PostMapping("/api/order/check")
    public ResponseEntity<String> checkOutOrder(
            @RequestParam("user_id") Long id,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("payment") int payment,
            @RequestParam("note") String note,
            @RequestParam("total") double total) {

        // Tạo đối tượng OrderEntity
    	UserEntity user = new UserEntity();
    	user.setId(id);
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setFullname(name);
        order.setAddress(address);
        order.setPhone(phone);
        order.setPayment(payment);
        order.setNote(note);
        order.setTotalPrice(total);
        order.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())); // Thiết lập ngày giờ hiện tại
        
        // Lưu đơn hàng
        OrderEntity savedOrder = orderService.save(order);
        Long orderId = savedOrder.getId();
        order.setId(orderId);
        
        // Lấy giỏ hàng của người dùng
        CartEntity cart = cartService.getOneCart(id);  // Sử dụng user_id để lấy giỏ hàng
        List<CartitemEntity> cartItems = cartItemService.findByCartId(cart.getId());
        
        // Tạo danh sách Orderitem
        List<OrderitemEntity> orderItems = new ArrayList<>();
        
        // Chuyển đổi Cartitem thành Orderitem
        for (CartitemEntity item : cartItems) {
            OrderitemEntity orderItem = new OrderitemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getSellPrice());
            orderItem.setTotalPrice(item.getProduct().getSellPrice() * item.getQuantity());
            orderItems.add(orderItem);
            
         // Cập nhật số lượng sản phẩm trong kho
            ProductEntity product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity()); // Giảm số lượng sản phẩm
            productService.saveQ(product); // Lưu lại thay đổi số lượng
        }
        
      
        
        
        orderItemService.saveAll(orderItems);
        
        //Xóa Cartitem
       cartItemService.deleteByCartId(cart.getId());
        
        // Xóa Cart
		     cartService.delete(cart.getId());
        
        return ResponseEntity.ok("Successfully checked out and cart cleared");
    }

    
}
