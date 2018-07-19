package com.bitmoe.osp.qistchan.methane;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import org.apache.commons.logging.*;

public class XMLReader {

    private static Log log = LogFactory.getLog(XMLReader.class);

    public List<String[]> list = new ArrayList<>();

    public void read (String charSet, String pathName) {

        File xmlfile = new File(pathName);
        SAXReader read = new SAXReader();
        Document document = null;

        try {
            document = read.read(xmlfile);
            Element root = document.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext();){
                Element elementTemp = it.next();
                Element elementMasterValue = elementTemp.element("Property").element("Value").element("MasterValue");
                Element elementLocalizedValue = elementTemp.element("Property").element("Value").element("LocalizedValue");
                String stringLocalizeValue = elementLocalizedValue.getText();
                String uidValue = elementTemp.attribute("uidValue").getValue();
                if (elementLocalizedValue.getText().replace(" ","").length()<=2){
                    stringLocalizeValue = "[DEFAULT]";
                }
                String[] listContent = {uidValue, delSpace(elementMasterValue.getText()), delSpace(stringLocalizeValue)};
                this.list.add(listContent);
            }

        } catch (Exception e){
            log.error(e);    //抛出异常
        }

    }

    private String delSpace (String string){
        return string.replace(" ","").replace("\n","");
    }


}