package cg.service.cart;

import cg.dto.cart.*;
import cg.dto.report.ProductReportDTO;
import cg.dto.report.DayToDayReportDTO;
import cg.dto.report.ReportDTO;
import cg.dto.report.YearReportDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.enums.ECartStatus;
import cg.service.IGeneralService;
import cg.service.cart.response.CartListResponse;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICartService extends IGeneralService<Cart,Long> {
    Page<CartDTO> findAllByFilters(CartRequest keyword, Pageable pageable);

    BigDecimal getTotalAmountCart(Cart cart);
    Page<CartListResponse> pageableByKeyword(CartRequest request, Pageable pageable);

    Cart create(CartCreReqDTO cartCreReqDTO);

    Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id);

    CartUpResDTO update(CartUpReqDTO cartUpReqDTO);

//    Cart findCartsByCustomerIdAndStatusIsCart(Long customerId, ECartStatus status);

    Cart findCartsByCustomerIdAndStatusIsCart(Long customerId, ECartStatus status);

    public CartDetail createNewCartDetail(CartCreMiniCartReqDTO cartCreMiniCartReqDTO, Cart cart);

    CartUpReqDTO getCartDTOByCartDetail(CartUpReqDTO cartUpReqDTO);

    ReportDTO getReportOfDay(String day);

    List<DayToDayReportDTO> getReportFromDayToDay(String startDay, String endDay);

    List<ProductReportDTO> getBestSeller(Pageable pageable);
    List<YearReportDTO> getReportByYear(@Param("year") int year);
    YearReportDTO getReportByMonth( @Param("year") int year,@Param("month") int month);

    List<ProductReportDTO> getTop5ProductUnMarketTableCurrentMonth(Pageable pageable);
}
