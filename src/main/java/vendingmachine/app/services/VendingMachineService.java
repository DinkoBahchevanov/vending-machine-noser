package vendingmachine.app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vendingmachine.app.command.vendingMachine.CreateVendingMachineCommand;
import vendingmachine.app.command.vendingMachine.InsertMoneyCommand;
import vendingmachine.app.command.vendingMachine.LoadVendingMachineMoneyCommand;
import vendingmachine.app.dto.vendingMachine.PurchaseResultDto;
import vendingmachine.app.dto.vendingMachine.VendingMachineDto;
import vendingmachine.app.mapper.ProductMapper;
import vendingmachine.app.mapper.VendingMachineMapper;
import vendingmachine.app.model.machine.VendingMachineEntity;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;
import vendingmachine.app.model.paymentItem.PaymentItemValue;
import vendingmachine.app.model.product.ProductEntity;
import vendingmachine.app.repository.PaymentItemRepository;
import vendingmachine.app.repository.ProductRepository;
import vendingmachine.app.repository.VendingMachineRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VendingMachineService {

    private final VendingMachineRepository vendingMachineRepository;

    private final ProductRepository productRepository;

    private final PaymentItemRepository paymentItemRepository;

    private final VendingMachineMapper vendingMachineMapper;

    private final ProductMapper productMapper;

    public VendingMachineDto createVendingMachine(CreateVendingMachineCommand command) {
        if (vendingMachineRepository.findAll().isEmpty()) {
            List<Double> paymentItems = command.getPaymentItems();
            List<PaymentItemEntity> paymentItemEntities = new ArrayList<>();
            for ( Double paymentItem : paymentItems ) {
                paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.fromBigDecimal(BigDecimal.valueOf(paymentItem))));
            }

            VendingMachineEntity vendingMachine = vendingMachineRepository.save(new VendingMachineEntity(
                    productRepository.findAllById(command.getProductIds()),
                    paymentItemRepository.saveAll(paymentItemEntities),
                    new ArrayList<>()));

            return vendingMachineMapper.entityToDto(vendingMachine);
        }

        return null;
    }

    public VendingMachineDto loadVendingMachineMoney(LoadVendingMachineMoneyCommand command) {
        VendingMachineEntity vendingMachine = getVendingMachineEntity();
        List<PaymentItemEntity> paymentItems = createPaymentItemEntities(command);
        vendingMachine.getPaymentItemEntities().addAll(paymentItems);

        return vendingMachineMapper.entityToDto(vendingMachineRepository.save(vendingMachine));
    }

    private List<PaymentItemEntity> createPaymentItemEntities(LoadVendingMachineMoneyCommand command) {
        List<PaymentItemEntity> paymentItemEntities = new ArrayList<>();

        for ( int i = 0; i < command.getTenStQuantity(); i++ ) {
            paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.TEN_ST));
        }
        for ( int i = 0; i < command.getTwentyStQuantity(); i++ ) {
            paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.TWENTY_ST));
        }
        for ( int i = 0; i < command.getFiftyStQuantity(); i++ ) {
            paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.FIFTY_ST));
        }
        for ( int i = 0; i < command.getLevQuantity(); i++ ) {
            paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.LEV));
        }
        for ( int i = 0; i < command.getTwoLevQuantity(); i++ ) {
            paymentItemEntities.add(new PaymentItemEntity(PaymentItemValue.TWO_LEV));
        }

        return paymentItemEntities;
    }

    public PurchaseResultDto buyProduct(String productId) {
        VendingMachineEntity vendingMachine = getVendingMachineEntity();

        Optional<ProductEntity> optionalProduct = vendingMachine.getProductEntities().stream().filter(productEntity -> productEntity.getId().equals(productId)).findFirst();
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException(String.format("No product with id %s in the vending machine!", productId));
        }

        ProductEntity productEntity = optionalProduct.get();

        BigDecimal insertedMoney = vendingMachine.getTotalInsertedMoney();
        if (insertedMoney.compareTo(productEntity.getPrice()) < 0) {
            throw new RuntimeException("Not enough money in the vending machine!");
        }

        if (insertedMoney.equals(productEntity.getPrice())) {
            vendingMachine.getProductEntities().remove(productEntity);
            vendingMachine.setInsertedMoney(new ArrayList<>());
            vendingMachineRepository.save(vendingMachine);
            return new PurchaseResultDto(productMapper.entityToDto(productEntity), new ArrayList<>());
        }

        List<PaymentItemEntity> allVendingMachineStoredMoney = vendingMachine.getPaymentItemEntities();
        List<PaymentItemEntity> allVendingMachineInsertedMoney = vendingMachine.getInsertedMoney();
        List <PaymentItemEntity> changeItems = calculateChange(vendingMachine, productEntity,
                allVendingMachineInsertedMoney, allVendingMachineStoredMoney, insertedMoney);

        vendingMachine.setInsertedMoney(new ArrayList<>());
        vendingMachine.setPaymentItemEntities(allVendingMachineStoredMoney);
        vendingMachine.getPaymentItemEntities().addAll(allVendingMachineInsertedMoney);
        vendingMachine.getProductEntities().remove(productEntity);

        productEntity.getCategory().getProductEntities().remove(productEntity);
        productRepository.delete(productEntity);
        vendingMachineRepository.save(vendingMachine);

        return new PurchaseResultDto(productMapper.entityToDto(productEntity), changeItems);
    }

    public List<PaymentItemEntity> returnInsertedMoney() {
        VendingMachineEntity vendingMachine = getVendingMachineEntity();

        List<PaymentItemEntity> insertedMoney = vendingMachine.getInsertedMoney();

        vendingMachine.setInsertedMoney(new ArrayList<>());
        vendingMachineRepository.save(vendingMachine);

        return insertedMoney;
    }

    public VendingMachineDto addProduct(String productId) {
        VendingMachineEntity vendingMachine = getVendingMachineEntity();

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No such product!"));

        List<ProductEntity> vendingMachineProductsByCategory = vendingMachine.getProductEntities().stream()
                .filter(product -> product.getCategory().getId().equals(productEntity.getCategory().getId()))
                .collect(Collectors.toList());

        if (vendingMachineProductsByCategory.size() < 10) {
            vendingMachine.getProductEntities().add(productEntity);
        }

        return vendingMachineMapper.entityToDto(vendingMachineRepository.save(vendingMachine));
    }

    private VendingMachineEntity getVendingMachineEntity() {
        List<VendingMachineEntity> vendingMachineEntities = vendingMachineRepository.findAll();
        if (vendingMachineEntities.isEmpty()) {
            throw new RuntimeException("No Vending Machine existing!");
        }
        return vendingMachineEntities.get(0);
    }

    private List<PaymentItemEntity> calculateChange(VendingMachineEntity vendingMachine, ProductEntity productEntity,
         List<PaymentItemEntity> allVendingMachineInsertedMoney, List<PaymentItemEntity> allVendingMachineStoredMoney,
         BigDecimal insertedMoney) {

        Map<BigDecimal, List<PaymentItemEntity>> coinMap = new TreeMap<>(Comparator.reverseOrder());

        processAllPaymentItemsForChangeCalculation(coinMap, vendingMachine.getPaymentItemEntities());
        processAllPaymentItemsForChangeCalculation(coinMap, vendingMachine.getInsertedMoney());

        BigDecimal changeAmount = insertedMoney.subtract(productEntity.getPrice());
        List<PaymentItemEntity> changeItems = new ArrayList<>();

        for (Map.Entry<BigDecimal, List<PaymentItemEntity>> entry : coinMap.entrySet()) {
            BigDecimal coinValue = entry.getKey();
            List<PaymentItemEntity> coinEntityList = entry.getValue();
            int numberOfCoins = ( changeAmount.divide(coinValue, 0, RoundingMode.DOWN) ).intValue();
            if (!coinEntityList.isEmpty() && numberOfCoins > 0) {
                for (int i = 0; i < numberOfCoins; i++) {
                    if (coinMap.get(coinValue).isEmpty()) {
                        break;
                    }

                    PaymentItemEntity paymentItemEntity = coinMap.get(coinValue).get(coinMap.get(coinValue).size() - 1);
                    if (allVendingMachineStoredMoney.contains(paymentItemEntity)) {
                        allVendingMachineStoredMoney.remove(paymentItemEntity);
                    } else if (allVendingMachineInsertedMoney.contains(paymentItemEntity)){
                        allVendingMachineInsertedMoney.remove(paymentItemEntity);
                    }
                    coinMap.get(coinValue).remove(paymentItemEntity);
                    changeItems.add(paymentItemEntity);
                }
                changeAmount = changeAmount.remainder(coinValue);
            }
        }

        if (changeAmount.compareTo(BigDecimal.valueOf(0.1)) > 0) {
            throw new RuntimeException("Can't return change");
        }
        return changeItems;
    }

    private void processAllPaymentItemsForChangeCalculation(Map<BigDecimal, List<PaymentItemEntity>> coinMap, List<PaymentItemEntity> paymentItemEntities) {
        for (PaymentItemEntity paymentItem : paymentItemEntities) {
            if (coinMap.containsKey(paymentItem.getValue().getValue())) {
                coinMap.get(paymentItem.getValue().getValue()).add(paymentItem);
            } else {
                ArrayList<PaymentItemEntity> coins = new ArrayList<>();
                coins.add(paymentItem);
                coinMap.put(paymentItem.getValue().getValue(), coins);
            }
        }
    }

    public VendingMachineDto getVendingMachine() {
        VendingMachineEntity vendingMachineEntity = getVendingMachineEntity();
        return vendingMachineMapper.entityToDto(vendingMachineEntity);
    }

    public void deleteVendingMachine() {
        VendingMachineEntity vendingMachineEntity = getVendingMachineEntity();
        vendingMachineEntity.getProductEntities().clear();
        vendingMachineRepository.delete(vendingMachineEntity);
    }

    public VendingMachineDto insertMoney(InsertMoneyCommand command) {
        VendingMachineEntity vendingMachineEntity = getVendingMachineEntity();
        PaymentItemEntity paymentItemEntity = new PaymentItemEntity(PaymentItemValue.fromBigDecimal(BigDecimal.valueOf(command.getValue())));
        vendingMachineEntity.getInsertedMoney().add(paymentItemEntity);

        return vendingMachineMapper.entityToDto(vendingMachineRepository.save(vendingMachineEntity));
    }
}
