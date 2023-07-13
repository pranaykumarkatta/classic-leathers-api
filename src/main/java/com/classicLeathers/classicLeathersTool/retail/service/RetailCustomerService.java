package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.Customer;
import com.classicLeathers.classicLeathersTool.retail.model.Vendor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class RetailCustomerService {
    private final String CUSTOMER_DETAILS_FILE_PATH = "D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\CustomerDetails.xlsx";

    public List<Customer> getCustomers() {
        List<String> fileData = new ArrayList<>();
        fileData.addAll(new FileUtils().getData(CUSTOMER_DETAILS_FILE_PATH, 0));

        if (fileData.size() != 0) {
            fileData.remove(0);//Remove header data
            List<Customer> customers = new ArrayList<>();
            for (String row : fileData) {
                String[] columns = row.split(",");
                customers.add(new Customer(columns[0], columns[1], Long.parseLong(columns[2])));
            }
            Map customerMap = new HashMap();
            for (Customer customer : customers) {
                customerMap.put(customer.getName().toUpperCase(Locale.ROOT) + "_" + customer.getMobileNumber(), customer);
            }

            return new ArrayList<>(customerMap.values());
        }
        return Collections.EMPTY_LIST;
    }

    public void addCustomer(Customer customer) {
        Object[] data = new Object[]{customer.getName(), customer.getGender(), customer.getMobileNumber()};
        try {
            new FileUtils().WriteData(CUSTOMER_DETAILS_FILE_PATH, 0, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}