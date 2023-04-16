package com.classicLeathers.classicLeathersTool.production.model;

public class ArticleDto {

    private String sku;
    private String leather;
    private String handStitchingPattern;
    private String style;
    private String lining;
    private String sole;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getLeather() {
        return leather;
    }

    public void setLeather(String leather) {
        this.leather = leather;
    }

    public String getHandStitchingPattern() {
        return handStitchingPattern;
    }

    public void setHandStitchingPattern(String handStitchingPattern) {
        this.handStitchingPattern = handStitchingPattern;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLining() {
        return lining;
    }

    public void setLining(String lining) {
        this.lining = lining;
    }

    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
    }
}
