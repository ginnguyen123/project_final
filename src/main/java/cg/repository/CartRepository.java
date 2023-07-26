package cg.repository;

import cg.dto.cart.CartDTO;
import cg.dto.cart.CartUpReqDTO;
import cg.dto.report.ProductReportDTO;
import cg.dto.report.DayToDayReportDTO;
import cg.dto.report.ReportDTO;
import cg.dto.report.YearReportDTO;
import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT cr FROM Cart AS cr " +
            " join CartDetail cd on cr.id = cd.cart.id " +
            " join Customer  cus on cus.id = cr.customer.id" +
            " join LocationRegion lr on cr.locationRegion.id = lr.id " +

            "WHERE (cr.name_receiver LIKE :#{#request.keyword} or " +
            "cd.product.title LIKE :#{#request.keyword} or " +
            "lr.districtName LIKE :#{#request.keyword} or " +
            "lr.wardName LIKE :#{#request.keyword} or " +
            "lr.provinceName LIKE :#{#request.keyword} or " +
            "lr.address LIKE :#{#request.keyword})" +
            "AND cr.deleted = false " +
            "GROUP BY cr.id"
    )
    Page<Cart> pageableByKeyword(CartRequest request, Pageable pageable);



    @Query("SELECT NEW cg.dto.cart.CartDTO (pi.id,pi.customer.id,pi.totalAmount,pi.locationRegion.id,pi.status) " +
            "FROM Cart AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id);

    @Query("SELECT NEW cg.dto.cart.CartUpReqDTO (pi.id,pi.name_receiver,pi.phone_receiver,pi.totalAmount,pi.locationRegion.id,pi.status) " +
            "FROM Cart AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartUpReqDTO> getCartDTOByCartDetail(Long id);

    @Query("SELECT NEW cg.dto.report.ReportDTO (cart.totalAmount,COUNT (cart.id)) " +
            "FROM Cart as cart WHERE DATE_FORMAT(cart.createdAt,'%Y-%m-%d') = :day " +
            "AND cart.status='PAID' GROUP BY cart.id")
    ReportDTO getReportOfDay(@Param("day") String day);

    @Query("SELECT NEW cg.dto.report.DayToDayReportDTO(SUM(cart.totalAmount),DATE_FORMAT(cart.createdAt,'%Y-%m-%d')) " +
            "FROM Cart AS cart " +
            "WHERE DATE_FORMAT(cart.createdAt,'%Y-%m-%d')>:startDay " +
            "AND DATE_FORMAT(cart.createdAt,'%Y-%m-%d')<:endDay " +
            "AND cart.status = 'PAID' " +
            "GROUP BY DATE_FORMAT(cart.createdAt,'%Y-%m-%d') ORDER BY DATE_FORMAT(cart.createdAt,'%Y-%m-%d')")
    List<DayToDayReportDTO> getReportFromDayToDay(@Param("startDay") String startDay,@Param("endDay") String endDay);

    @Query("SELECT NEW cg.dto.report.ProductReportDTO(p.id,p.productAvatar.fileUrl, p.title, SUM(cdt.quantity) ,SUM(cdt.totalAmount)) " +
            "FROM Product AS p " +
            "INNER JOIN CartDetail AS cdt ON p.id = cdt.product.id " +
            "INNER JOIN Cart AS c ON c.id=cdt.cart.id WHERE c.status='UNPAID' " +
            "GROUP BY p.id ORDER BY SUM(cdt.quantity) DESC ")
    Page<ProductReportDTO> getBestSeller(Pageable pageable);

    @Query("SELECT c FROM Cart AS c WHERE c.user.id = :userId AND c.status = :status")
    Cart findCartsByCustomerIdAndStatusIsCart(@Param("userId")Long userId , @Param("status") ECartStatus status);

    @Query("SELECT NEW cg.dto.report.YearReportDTO (" +
            "YEAR(c.createdAt), " +
            "SUM(c.totalAmount), " +
            "COUNT(c.id) " +
            ") " +
            "FROM Cart AS c " +
            "WHERE YEAR(c.createdAt) = :year " +
            "AND c.status = 'PAID' " +
            "GROUP BY YEAR (c.createdAt) " +
            "ORDER BY YEAR (c.createdAt) ASC"
    )
    List<YearReportDTO> getReportByYear(@Param("year") int year);

    @Query("SELECT NEW cg.dto.report.YearReportDTO (" +
            "MONTH(c.createdAt), " +
            "SUM(c.totalAmount), " +
            "COUNT(c.id) " +
            ") " +
            "FROM Cart AS c " +
            "WHERE MONTH(c.createdAt) = :month " +
            "AND YEAR(c.createdAt) = :year " +
            "AND c.status = 'PAID' " +
            "GROUP BY MONTH(c.createdAt)"
    )
    YearReportDTO getReportByMonth( @Param("year") int year,@Param("month") int month);

    @Query("SELECT NEW cg.dto.report.ProductReportDTO(p.id,p.productAvatar.fileUrl, p.title, SUM(cdt.quantity) ,SUM(cdt.totalAmount)) " +
            "FROM Product AS p " +
            "INNER JOIN CartDetail AS cdt ON p.id = cdt.product.id " +
            "INNER JOIN Cart AS c ON c.id=cdt.cart.id WHERE c.status='UNPAID' " +
            "GROUP BY p.id ORDER BY SUM(cdt.quantity) ASC ")
    Page<ProductReportDTO> getTop5ProductUnMarketTableCurrentMonth(Pageable pageable);
}

