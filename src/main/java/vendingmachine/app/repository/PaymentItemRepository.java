package vendingmachine.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;

@Repository
public interface PaymentItemRepository extends JpaRepository<PaymentItemEntity, String> { }
