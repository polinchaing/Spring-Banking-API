package kh.edu.cstad.mbapi;

import kh.edu.cstad.mbapi.repository.CustomerSegmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MobileBankingAppApplicationTests {

    @Autowired
    private CustomerSegmentRepository  customerSegmentRepository;

    @Test
    void testFetchType(){
        customerSegmentRepository.
                findAll().
                forEach(customer-> System.out.println(customer.getCustomer()));
    }

}
