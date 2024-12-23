package org._iir.backend.modules.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Get infos
    @GetMapping
    public ResponseEntity<AccountDTO> getInfos() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getInfos());
    }

    // Update infos
    @PutMapping
    public ResponseEntity<AccountDTO> updateInfos(@RequestBody InfoREQ req) {
        AccountDTO result = accountService.updateInfos(req);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Update password
    @PutMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordREQ req) {
        accountService.updatePassword(req);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
