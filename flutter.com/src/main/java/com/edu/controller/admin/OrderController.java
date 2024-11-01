package com.edu.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.converter.OrderConverter;
import com.edu.dto.OrderDTO;
import com.edu.entity.OrderEntity;
import com.edu.service.EmailService;
import com.edu.service.OrderService;

@Controller(value = "orderOfController")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/quan-tri/order/list", method = RequestMethod.GET)
    public String getOrder(Model m) {
        List<OrderEntity> list = orderService.findAll();
        List<OrderDTO> dto = new ArrayList<>();
        for (OrderEntity orderEntity : list) {
            dto.add(orderConverter.toDto(orderEntity));
        }
        m.addAttribute("model", dto);
        return "admin/order/list";
    }

    @PutMapping("quan-tri/order/updateStatus")
    public ResponseEntity<String> updateOrderStatus(@RequestParam("id") Long id) {
       orderService.updateStatus(id);
       
       OrderEntity  order = orderService.findOneById(id);
       if(order.getStatus()==2) {
    	   emailService.sendSuccessOrder(order.getUser().getEmail());
       }
       
       //emailService.sendPasswordResetCode(email, resetCode);

       return ResponseEntity.ok("Order status updated successfully");
     
    }
}
