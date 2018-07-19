package com.bitmoe.osp.qistchan.methane;

public class RecordClass {

    private String recordID;
    private String recordKeyValue;
    private String recordEng;
    private String recordChs;


    public String getRecordID(){
        return recordID;
    }

    public String getRecordKeyValue(){
        return recordKeyValue;
    }

    public String getRecordEng(){
        return recordEng;
    }

    public String getRecordChs(){
        return recordChs;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public void setRecordKeyValue(String recordKeyValue) {
        this.recordKeyValue = recordKeyValue;

    }

    public void setRecordEng(String recordEng) {
        this.recordEng = recordEng;
    }

    public void setRecordChs(String recordChs) {
        this.recordChs = recordChs;
    }
}
