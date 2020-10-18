module Csv {
    requires commons.csv;
    requires java.sql;

    opens com.xingray.csv.annotations;
    exports com.xingray.csv.annotations;

    opens com.xingray.csv;
    exports com.xingray.csv;
}