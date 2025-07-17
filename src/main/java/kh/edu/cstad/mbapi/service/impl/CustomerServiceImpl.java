package kh.edu.cstad.mbapi.service.impl;

import jakarta.transaction.Transactional;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.domain.CustomerSegment;
import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.repository.CustomerSegmentRepository;
import kh.edu.cstad.mbapi.repository.KYCRepository;
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
    private final CustomerSegmentRepository customerSegmentRepository;
    private final KYCRepository kycRepository;

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {

        if(!customerRepository.existsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone number not found");
        }

        customerRepository.disableByPhoneNumber(phoneNumber);

    }

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {
        log.info(createCustomerRequest.toString());

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

        //validation national ID Card for Creating KYC
        if(kycRepository.existsByNationalIdCard(createCustomerRequest.nationalIdCard())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National ID Card already exists"
            );
        }

        //validation Customer Segment
        CustomerSegment customerSegment = customerSegmentRepository
                .findBySegment(createCustomerRequest.customerSegment())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer segment not found"));

        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setCustomerSegment(customerSegment);
        customer.setIsDeleted(false);

       KYC kyc = new KYC();
       kyc.setCustomer(customer);
       kyc.setNationalIdCard(createCustomerRequest.nationalIdCard());
       kyc.setIsDeleted(false);
       kyc.setIsVerified(false);
       customer.setKyc(kyc);

        log.info("Customer");

       customer = customerRepository.save(customer);

       return customerMapper.toCustomerResponse(customer);

    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository. findAllByIsDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
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
