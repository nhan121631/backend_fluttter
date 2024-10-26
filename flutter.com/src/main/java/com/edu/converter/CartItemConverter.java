package com.edu.converter;

import org.springframework.stereotype.Component;

import com.edu.dto.CartDTO;
import com.edu.dto.CartItemDTO;
import com.edu.dto.ProductDTO;
import com.edu.entity.CartitemEntity;

@Component
public class CartItemConverter {
	   public static CartItemDTO convertToDTO(CartitemEntity cartItemEntity) {
	        if (cartItemEntity == null) {
	            return null; // Trả về null nếu đối tượng không hợp lệ
	        }

	        CartDTO cartDTO = new CartDTO();
	        cartDTO.setId(cartItemEntity.getCart().getId());

	     // Tạo ProductDTO từ ProductEntity
	        ProductDTO productDTO = new ProductDTO();
	        productDTO.setId(cartItemEntity.getProduct().getId());
	        productDTO.setName(cartItemEntity.getProduct().getName());
	        productDTO.setCostPrice(cartItemEntity.getProduct().getCostPrice());
	        productDTO.setSellPrice(cartItemEntity.getProduct().getSellPrice());
	        productDTO.setThumbnail(cartItemEntity.getProduct().getThumbnail());
	        productDTO.setQuantity(cartItemEntity.getProduct().getQuantity());
	        productDTO.setDescription(cartItemEntity.getProduct().getDescription());
	        productDTO.setCategoryId(cartItemEntity.getProduct().getCategory().getId());

	        CartItemDTO cartItemDTO = new CartItemDTO();
	        cartItemDTO.setId(cartItemEntity.getId());
	        cartItemDTO.setQuantity(cartItemEntity.getQuantity());
	        cartItemDTO.setCart(cartDTO); // Thiết lập cart
	        cartItemDTO.setProduct(productDTO); // Thiết lập sản phẩm
	        cartItemDTO.setCreatedDate(cartItemEntity.getCreatedDate());
	        cartItemDTO.setModifiedDate(cartItemEntity.getModifiedDate());
	        cartItemDTO.setCreateBy(cartItemEntity.getCreateBy());
	        cartItemDTO.setModifiedBy(cartItemEntity.getModifiedBy());

	        return cartItemDTO;
	    }
	 
}
