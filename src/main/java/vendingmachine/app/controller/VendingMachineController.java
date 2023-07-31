package vendingmachine.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vendingmachine.app.command.vendingMachine.CreateVendingMachineCommand;
import vendingmachine.app.command.vendingMachine.InsertMoneyCommand;
import vendingmachine.app.command.vendingMachine.LoadVendingMachineMoneyCommand;
import vendingmachine.app.dto.vendingMachine.PurchaseResultDto;
import vendingmachine.app.dto.vendingMachine.VendingMachineDto;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;
import vendingmachine.app.services.VendingMachineService;

import java.util.List;

@AllArgsConstructor
@RequestMapping(path = "/api/vending-machine/")
@RestController
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @PostMapping
    VendingMachineDto createVendingMachine(@RequestBody CreateVendingMachineCommand command) {
        return vendingMachineService.createVendingMachine(command);
    }

    @GetMapping
    VendingMachineDto getVendingMachine() {
        return vendingMachineService.getVendingMachine();
    }

    @DeleteMapping
    void deleteVendingMachine() {
        vendingMachineService.deleteVendingMachine();
    }

    @PostMapping("add/{productId}")
    VendingMachineDto addProduct(@PathVariable String productId) {
        return vendingMachineService.addProduct(productId);
    }

    @PostMapping("insert-money")
    VendingMachineDto insertMoney(@RequestBody InsertMoneyCommand command) {
        return vendingMachineService.insertMoney(command);
    }

    @PostMapping("buy/{productId}")
    PurchaseResultDto buyProduct(@PathVariable String productId) {
        return vendingMachineService.buyProduct(productId);
    }

    @GetMapping("return")
    List<PaymentItemEntity> returnInsertedMoney() {
        return vendingMachineService.returnInsertedMoney();
    }

    @PostMapping("load")
    VendingMachineDto loadVendingMachine(@RequestBody LoadVendingMachineMoneyCommand command) {
        return vendingMachineService.loadVendingMachineMoney(command);
    }
}
