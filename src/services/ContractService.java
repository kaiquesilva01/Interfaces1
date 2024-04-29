package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService {
    private PaymentService paymentService;

    public ContractService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processContract(Contract contract,int months) {
        double basicQuota = contract.getTotalValue() / months;

        for (int i=1; i <=months;i++) {
            LocalDate dueDate = contract.getDate().plusMonths(i);
            double interest = paymentService.interest(basicQuota, i);
            double fee = paymentService.paymentFee(basicQuota + interest);
            double quota = basicQuota + interest + fee;
            contract.getInstallments().add(new Installment(dueDate,quota));
        }
    }
}
