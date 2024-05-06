package com.myapp.examples.loginregister;

public class report {


    private String reportdesc;
    private String reportdate;
    private String reporttype;


    public report(String reportdesc, String reportdate, String reporttype) {

        this.reportdesc = reportdesc;
        this.reportdate = reportdate;
        this.reporttype = reporttype;

    }


    public String getReportDesc() {
        return reportdesc;
    }

    public String getReportDate() {
        return reportdate;
    }
    public String getReportType() {
        return reporttype;
    }

}
