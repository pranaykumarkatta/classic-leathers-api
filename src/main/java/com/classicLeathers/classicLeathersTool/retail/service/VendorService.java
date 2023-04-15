package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.Vendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class VendorService {

    public List<Vendor> getVendors() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("C:\\Users\\Dell\\OneDrive\\CLASSIC_DOCS\\RETAIL_DOCS\\VendorDetails.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<Vendor> vendorList= new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                Vendor v = new Vendor();
                v.setName(cellData[0]);
                v.setPlace(cellData[1]);
                vendorList.add(v);
            }
            return vendorList;
        }
        return Collections.emptyList();
    }

    public void addVendor(Vendor vendor) {
        Object[] data = new Object[]{vendor.getName(),vendor.getPlace()};
        try {
            new FileUtils().WriteData("C:\\Users\\Dell\\OneDrive\\CLASSIC_DOCS\\RETAIL_DOCS\\VendorDetails.xlsx",0,data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
