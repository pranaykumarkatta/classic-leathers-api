package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.production.model.PurchaseOrder;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
public class PurchaseOrderService {
    public List<ArticleDto> getArticles() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\Articles\\ARTICLES.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<ArticleDto> articleDtoList = new ArrayList<>();
            for (String row : rowData) {
                ArticleDto articleDto = new ArticleDto();
                String[] cellData = row.split(",");
                articleDto.setClassicSku(cellData[0]);
                articleDto.setTeakWoodSku(cellData[1]);
                articleDto.setLeather(cellData[2]);
                articleDto.setHandStitchingPattern(cellData[3]);
                articleDto.setStyle(cellData[4]);
                articleDto.setLining(cellData[5]);
                articleDto.setSole(cellData[6]);
                articleDtoList.add(articleDto);
            }
            return articleDtoList;
        }
        return Collections.emptyList();
    }

    public void savePurchaseOrder(List<PurchaseOrder> purchaseOrderList, String fileName) {

        for (PurchaseOrder purchaseOrder:purchaseOrderList
             ) {
            Object[] data = new Object[]{purchaseOrder.getTeakWoodSku(),
                    purchaseOrder.getClassicSku(),
                    purchaseOrder.getLeather(),
                    purchaseOrder.getSize_40_quantity(),
                    purchaseOrder.getSize_41_quantity(),
                    purchaseOrder.getSize_42_quantity(),
                    purchaseOrder.getSize_43_quantity(),
                    purchaseOrder.getSize_44_quantity(),
                    purchaseOrder.getSize_45_quantity(),
                    purchaseOrder.getSize_46_quantity(),
                    purchaseOrder.getTotalQuantity(),
                    purchaseOrder.getHandStitchingPattern(),
                    purchaseOrder.getStyle(),
                    purchaseOrder.getLining(),
                    purchaseOrder.getSole()

            };
            try {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\"+fileName+".xlsx",0,data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
