package com.xingray.csv;

import com.xingray.csv.annotations.DataType;

public class ExportConfigItem {

    public static final int DATA_TYPE_TIME_MILLS = DataType.TIME_MILLS;
    public static final int DATA_TYPE_TIME_SECONDS = DataType.TIME_SECONDS;

    private int index;
    private boolean enable;
    private String fieldName;
    private String exportName;
    private int dateType;
    private String format;
    private String zoneId;
    private String defaultValue;

    public ExportConfigItem() {
    }

    public ExportConfigItem(int index, boolean enable, String fieldName, String exportName, String format, String defaultValue) {
        this.index = index;
        this.enable = enable;
        this.fieldName = fieldName;
        this.exportName = exportName;
        this.format = format;
        this.defaultValue = defaultValue;
    }

    public ExportConfigItem(int index, boolean enable, String fieldName, String exportName, int dateType, String format, String defaultValue) {
        this.index = index;
        this.enable = enable;
        this.fieldName = fieldName;
        this.exportName = exportName;
        this.dateType = dateType;
        this.format = format;
        this.defaultValue = defaultValue;
    }

    public ExportConfigItem(int index, boolean enable, String fieldName, String exportName, int dateType, String format, String zoneId, String defaultValue) {
        this.index = index;
        this.enable = enable;
        this.fieldName = fieldName;
        this.exportName = exportName;
        this.dateType = dateType;
        this.format = format;
        this.zoneId = zoneId;
        this.defaultValue = defaultValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "ExportConfigItem{" +
                "index=" + index +
                ", enable=" + enable +
                ", fieldName='" + fieldName + '\'' +
                ", exportName='" + exportName + '\'' +
                ", dateType=" + dateType +
                ", format='" + format + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
