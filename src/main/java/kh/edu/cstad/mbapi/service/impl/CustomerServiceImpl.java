package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        //check condition existsByEmail
        if(customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        //check condition existsByPhoneNumber
        if(customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number already exists"
            );
        }


        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setIsDeleted(false);

        log.info("Customer Before Save : {}", customer.getId());

        customerRepository.save(customer);

        log.info("Customer After Save : {}", customer.getId());

        return customerMapper.toCustomerResponse(customer);

    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone Number Not Found")
                );
    }


    //Update using patch update what user want
    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone Number Not Found")
                );

        customerMapper.toCustomerPartially
                (updateCustomerRequest,
                        customer);

        customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);

    }

}
