package com.edu.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.converter.CartItemConverter;
import com.edu.dto.CartItemDTO;
import com.edu.entity.CartEntity;
import com.edu.entity.CartitemEntity;
import com.edu.entity.ProductEntity;
import com.edu.entity.UserEntity;
import com.edu.service.CartItemService;
import com.edu.service.CartService;

@RestController(value = "cartAPIOfWeb")
public class CartAPI {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemConverter cartItemConverter;
	
	
	@GetMapping("/api/cart/quantity")
	 public ResponseEntity<Integer> getQuantity(@RequestParam("user_id") Long userId) {
        int quantity = cartItemService.quantityCart(userId); 	
        return ResponseEntity.ok(quantity);
    }
	
    @PostMapping("/api/cart/add")
    public ResponseEntity<String> saveCart(@RequestParam("product_id") Long product_id, 
    		@RequestParam("user_id")Long user_id) {
    	
    	if(cartService.cartExists(user_id)){
    		
    		CartEntity c1 = new CartEntity();
    		c1 = cartService.getOneCart(user_id);
    		if(cartItemService.cartitemExists(c1.getId(), product_id)) {
    		cartItemService.updateQuantity(c1.getId(), product_id);
    		return ResponseEntity.ok("quantity +1");

    		}
    		CartitemEntity cItem = new CartitemEntity();
    		ProductEntity p = new ProductEntity();
    		p.setId(product_id);
    		cItem.setCart(c1);
    		cItem.setProduct(p);
    		cItem.setQuantity(1);
    		cartItemService.save(cItem);
    		return ResponseEntity.ok("cartitem mới");
 
    		
    	}else {
    		CartEntity c = new CartEntity();
    		UserEntity u = new UserEntity();
    		u.setId(user_id);
    		c.setUser(u);
    		cartService.save(c);
    		
    		CartEntity c1 = new CartEntity();
    		c1 = cartService.getOneCart(user_id);
    		CartitemEntity cItem = new CartitemEntity();
    		cItem.setCart(c1);
    		ProductEntity p = new ProductEntity();
    		p.setId(product_id);
    		cItem.setProduct(p);
    		cItem.setQuantity(1);
    		cartItemService.save(cItem);
    		return ResponseEntity.ok("them mới");
    	}
        
    }
    
    @GetMapping("/api/cart/get")
    public ResponseEntity<List<CartItemDTO>> getCart(@RequestParam("user_id") Long idUser) {
    	CartEntity cart = cartService.getOneCart(idUser);    	
    	List<CartitemEntity> itemCarts = cart.getCartitems();
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        if (cart == null) {
            return ResponseEntity.notFound().build(); 
        }
        for (CartitemEntity cartitemEntity : itemCarts) {
            CartItemDTO cartItemDTO = cartItemConverter.convertToDTO(cartitemEntity);
            
            cartItemDTOs.add(cartItemDTO);
        }
    	return ResponseEntity.ok(cartItemDTOs);
    }

    @DeleteMapping("api/cart/delete/item")
    public ResponseEntity<String> deleteItemCart (@RequestParam("id")Long id){
    	cartItemService.delete(id);
    	return ResponseEntity.ok("Đã xóa Item ra khỏi giỏ hàng");
    }

}
