package vendingmachine.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import vendingmachine.app.command.vendingMachine.CreateVendingMachineCommand;
import vendingmachine.app.command.vendingMachine.LoadVendingMachineMoneyCommand;
import vendingmachine.app.dto.vendingMachine.PurchaseResultDto;
import vendingmachine.app.dto.vendingMachine.VendingMachineDto;
import vendingmachine.app.model.machine.VendingMachineEntity;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;
import vendingmachine.app.model.paymentItem.PaymentItemValue;
import vendingmachine.app.model.product.ProductCategoryEntity;
import vendingmachine.app.model.product.ProductEntity;
import vendingmachine.app.repository.ProductRepository;
import vendingmachine.app.repository.VendingMachineRepository;
import vendingmachine.app.services.VendingMachineService;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendingMachineServiceTest {

    @Mock
    private VendingMachineRepository vendingMachineRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private VendingMachineService vendingMachineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVendingMachine_AlreadyExists() {
        CreateVendingMachineCommand command = new CreateVendingMachineCommand(Collections.emptyList(), Collections.emptyList());

        when(vendingMachineRepository.findAll()).thenReturn(Collections.singletonList(new VendingMachineEntity()));

        VendingMachineDto vendingMachineDto = vendingMachineService.createVendingMachine(command);

        assertNull(vendingMachineDto);
    }

    @Test
    void testLoadVendingMachineMoney_Success() {
        LoadVendingMachineMoneyCommand command = new LoadVendingMachineMoneyCommand(1, 2, 3, 4, 5);

        // Create VendingMachineEntity with payment items
        VendingMachineEntity vendingMachineEntity = new VendingMachineEntity();
        List<PaymentItemEntity> paymentItems = new ArrayList<>(Arrays.asList(
                new PaymentItemEntity(PaymentItemValue.TEN_ST),
                new PaymentItemEntity(PaymentItemValue.TWENTY_ST),
                new PaymentItemEntity(PaymentItemValue.FIFTY_ST),
                new PaymentItemEntity(PaymentItemValue.LEV),
                new PaymentItemEntity(PaymentItemValue.TWO_LEV)
        ));
        vendingMachineEntity.setPaymentItemEntities(paymentItems);
        vendingMachineEntity.setInsertedMoney(new ArrayList<>());
        vendingMachineEntity.setProductEntities(new ArrayList<>());

        // Mock
        when(vendingMachineRepository.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(vendingMachineEntity)));
        when(vendingMachineRepository.save(Mockito.any(VendingMachineEntity.class))).thenReturn(vendingMachineEntity);

        VendingMachineDto vendingMachineDto = vendingMachineService.loadVendingMachineMoney(command);

        // Assertions
        assertNotNull(vendingMachineDto);
        assertEquals(vendingMachineEntity, vendingMachineRepository.findAll().get(0));
        assertEquals(paymentItems.size(), vendingMachineEntity.getPaymentItemEntities().size());
    }

    @Test
    void testBuyProduct_Success() {
        String productId = "product-1";
        BigDecimal productPrice = BigDecimal.valueOf(1.5);

        VendingMachineEntity vendingMachineEntity = new VendingMachineEntity();
        vendingMachineEntity.setInsertedMoney(new ArrayList<>());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setPrice(productPrice);
        productEntity.setCategory(new ProductCategoryEntity());

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(productEntity);
        vendingMachineEntity.setProductEntities(productEntities);

        vendingMachineEntity.getInsertedMoney().add(new PaymentItemEntity(PaymentItemValue.TWO_LEV));
        vendingMachineEntity.getInsertedMoney().add(new PaymentItemEntity(PaymentItemValue.TWO_LEV));
        vendingMachineEntity.getPaymentItemEntities().add(new PaymentItemEntity(PaymentItemValue.FIFTY_ST));

        when(vendingMachineRepository.findAll()).thenReturn(Collections.singletonList(vendingMachineEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(vendingMachineRepository.save(any())).thenReturn(vendingMachineEntity);

        PurchaseResultDto purchaseResult = vendingMachineService.buyProduct(productId);

        // Assertions
        assertNotNull(purchaseResult);
        assertTrue(vendingMachineEntity.getProductEntities().isEmpty());
        assertTrue(vendingMachineEntity.getInsertedMoney().isEmpty());
        assertFalse(vendingMachineEntity.getPaymentItemEntities().isEmpty());
        assertEquals(2.0, purchaseResult.getChange().size());
        assertEquals(2, purchaseResult.getChange().size());
    }
}
