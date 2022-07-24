package com.company.controller;

import com.company.dto.TransactionDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.service.TransactionService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/bankAndPayment/pagination/{cardId}")
    public ResponseEntity<?> pagination(@PathVariable("cardId") String cardId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.pagination(page, size, cardId);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/bankAndPayment/byProfileId/{profileId}")
    public ResponseEntity<?> byProfileId(@PathVariable("profileId") String profileId,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.byProfileId(page, size, profileId);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/bankAndPayment/byPhoneNumber/{byPhoneNumber}")
    public ResponseEntity<?> byPhoneNumber(@PathVariable("byPhoneNumber") String byPhoneNumber,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.byPhoneNumber(page, size, byPhoneNumber);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/bankAndPayment/debit/{cardId}")
    public ResponseEntity<?> debit(@PathVariable("cardId") String cardId,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.debit(page, size, cardId);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/bankAndPayment/credit/{cardId}")
    public ResponseEntity<?> credit(@PathVariable("cardId") String cardId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.credit(page, size, cardId);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/bankAndPayment/month/{cardId}")
    public ResponseEntity<?> month(@PathVariable("cardId") String cardId,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransactionDTO> list = transactionService.month(page, size, cardId);
        return ResponseEntity.ok().body(list);
    }
}
