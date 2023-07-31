package vendingmachine.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendingmachine.app.model.machine.VendingMachineEntity;

@Repository
public interface VendingMachineRepository extends JpaRepository<VendingMachineEntity, String> { }
