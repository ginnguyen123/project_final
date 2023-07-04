package cg.controller;

import cg.dto.cart.CartDTO;
import cg.dto.cart.CartUpReqDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.dto.product.ProductDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.repository.CartDetailRepository;
import cg.service.cart.ICartService;
import cg.service.products.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/carts")
public class OrderController {

    @Autowired
    private IProductService productService;

    @Autowired
    CartDetailRepository cartDetailRepository;
    @Autowired
    ICartService iCartService;

    @GetMapping({"", "/"})
    public String showList(){
        return "product/order/table-data-oder";
    }

    @GetMapping("/update/{id}")
    public String showUpdateCart(@PathVariable Long id, Model model){
        Optional<Cart> cartOptional = iCartService.findById(id);
        Map obj = new HashMap<>();

        if (!cartOptional.isPresent()){
            return "errors/pages-404";
        }
        Cart cart = cartOptional.get();
        System.out.print(cart);
        CartUpReqDTO cartDTO = cart.toCartUpReqDTO();

        obj.put("cart", cartDTO);
        model.addAttribute("data", obj);
        return "product/order/table-update-order";
    }



}
