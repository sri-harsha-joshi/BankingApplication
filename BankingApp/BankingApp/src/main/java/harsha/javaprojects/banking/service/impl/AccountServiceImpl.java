package harsha.javaprojects.banking.service.impl;

import harsha.javaprojects.banking.dto.AccountDto;
import harsha.javaprojects.banking.entity.Account;
import harsha.javaprojects.banking.mapper.AccountMapper;
import harsha.javaprojects.banking.repository.AccountRepository;
import harsha.javaprojects.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account with given Id doesn't exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account =accountRepository.findById(id).
                orElseThrow(()->new RuntimeException("Account with given id does not exist"));
        Double total =account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount =accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account=accountRepository.findById(id).
                orElseThrow(()->new RuntimeException("Account with given Id does not exist"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient amount");
        }
        Double total =account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount =accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList =accountRepository.findAll();
        return accountList.stream().map((account)
                        -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
