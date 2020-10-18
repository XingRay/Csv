package com.xingray.csv;

import com.xingray.csv.annotations.*;
import com.xingray.csv.util.Util;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CsvUtil {

    public static <T> void write(List<T> list, Class<T> cls, String filePath, List<ExportConfigItem> configItems) {
        write(list, cls, filePath, configItems, null);
    }

    public static <T> void write(List<T> list, Class<T> cls, String filePath, List<ExportConfigItem> configItems, ProgressListener listener) {
        write(list, cls, filePath, "utf-8", configItems, listener);
    }

    public static <T> void write(List<T> list, Class<T> cls, String filePath, String charset, List<ExportConfigItem> exportConfigItems, ProgressListener listener) {
        if (list == null || list.isEmpty()) {
            return;
        }

        if (exportConfigItems == null || exportConfigItems.isEmpty()) {
            return;
        }

        List<ExportConfigItem> enabledExportConfigItems = new ArrayList<>();
        for (ExportConfigItem item : exportConfigItems) {
            if (item.isEnable()) {
                enabledExportConfigItems.add(item);
            }
        }
        exportConfigItems = enabledExportConfigItems;

        if (exportConfigItems.isEmpty()) {
            return;
        }

        exportConfigItems.sort(Comparator.comparingInt(ExportConfigItem::getIndex));

        int size = exportConfigItems.size();
        String[] exportNames = new String[size];
        for (int i = 0; i < size; i++) {
            ExportConfigItem item = exportConfigItems.get(i);
            exportNames[i] = item.getExportName();
        }

        Map<String, Method> getterCache = new HashMap<>();
        size = list.size();
        int exportItemSize = exportConfigItems.size();
        String[] recordItems = new String[exportItemSize];

        Field[] fields = cls.getDeclaredFields();
        ArrayList<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(exportNames);

        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
             CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat)) {

            for (int i = 0; i < size; i++) {
                T t = list.get(i);
                if (t == null) {
                    continue;
                }

                for (int j = 0; j < exportItemSize; j++) {
                    ExportConfigItem exportConfigItem = exportConfigItems.get(j);
                    String fieldName = exportConfigItem.getFieldName();

                    Object fieldValue = null;
                    if (fieldNames.contains(fieldName)) {
                        fieldValue = Util.get(t, fieldName, getterCache);
                    }
                    if (fieldValue == null) {
                        recordItems[j] = exportConfigItem.getDefaultValue();
                        continue;
                    }

                    String format = exportConfigItem.getFormat();
                    if (format == null || format.isBlank()) {
                        recordItems[j] = fieldValue.toString();
                        continue;
                    }

                    if (exportConfigItem.getDateType() == ExportConfigItem.DATA_TYPE_TIME_SECONDS) {
                        recordItems[j] = Util.secondsToFormattedString((long) fieldValue, format);
                    } else if (exportConfigItem.getDateType() == ExportConfigItem.DATA_TYPE_TIME_MILLS) {
                        recordItems[j] = Util.millsToFormattedString((long) fieldValue, format);
                    } else if (fieldValue instanceof Date) {
                        recordItems[j] = Util.toFormattedString((Date) fieldValue, format);
                    } else {
                        recordItems[j] = String.format(format, fieldValue);
                    }
                }

                //noinspection ConfusingArgumentToVarargsMethod
                csvPrinter.printRecord(recordItems);
                if (listener != null) {
                    listener.onProgress(i + 1, size);
                }
            }

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <T> void write(List<T> list, Class<T> cls, String filePath) {
        write(list, cls, filePath, (ProgressListener) null);
    }

    public static <T> void write(List<T> list, Class<T> cls, String filePath, ProgressListener listener) {
        write(list, cls, filePath, "utf-8", listener);
    }

    public static <T> void write(List<T> list, Class<T> cls, String filePath, String charset, ProgressListener listener) {
        Field[] fields = cls.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }

        List<ExportConfigItem> items = new ArrayList<>(fields.length);
        int defaultIndex = Integer.MAX_VALUE / 2;

        for (Field field : fields) {
            Enable enableAnnotation = field.getAnnotation(Enable.class);
            if (enableAnnotation != null) {
                boolean enable = enableAnnotation.value();
                if (!enable) {
                    continue;
                }
            }

            ExportConfigItem item = new ExportConfigItem();
            item.setEnable(true);

            Index indexAnnotation = field.getAnnotation(Index.class);
            if (indexAnnotation != null) {
                item.setIndex(indexAnnotation.value());
            } else {
                item.setIndex(defaultIndex);
                defaultIndex++;
            }

            String fieldName = field.getName();
            item.setFieldName(fieldName);

            Name nameAnnotation = field.getAnnotation(Name.class);
            if (nameAnnotation != null) {
                item.setExportName(nameAnnotation.value());
            } else {
                item.setExportName(fieldName);
            }

            Format formatAnnotation = field.getAnnotation(Format.class);
            if (formatAnnotation != null) {
                item.setFormat(formatAnnotation.value());
            }

            DataType dataTypeAnnotation = field.getAnnotation(DataType.class);
            if (dataTypeAnnotation != null) {
                item.setDateType(dataTypeAnnotation.value());
            }

            Default defaultAnnotation = field.getAnnotation(Default.class);
            if (defaultAnnotation != null) {
                item.setDefaultValue(defaultAnnotation.value());
            }

            items.add(item);
        }

        write(list, cls, filePath, items, listener);
    }
}
