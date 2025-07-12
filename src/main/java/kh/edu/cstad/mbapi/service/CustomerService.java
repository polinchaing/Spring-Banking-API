package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);

    List<CustomerResponse> findAll();

    CustomerResponse findByPhoneNumber(String phoneNumber);

    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);

    void deleteByPhoneNumber(String phoneNumber);
}
