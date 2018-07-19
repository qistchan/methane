package com.bitmoe.osp.qistchan.methane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

import com.bitmoe.osp.qistchan.tools.TeamcenterCMDRunner;

public class Controller {

    @FXML   public TableView table;             //表格视图注册
    @FXML   public TableColumn colId;           //表格列注册
    @FXML   public TableColumn colKeyValue;     //表格列注册
    @FXML   public TableColumn colEng;          //表格列注册
    @FXML   public TableColumn colChs;          //表格列注册
    @FXML   public Button buttonExport;         //导出按钮注册
    @FXML   public Button buttonImport;         //导入按钮注册
    @FXML   public Button buttonClose;          //关闭按钮注册

    @FXML	public TextField textUSER;

    //关闭按钮事件
    @FXML
    public void closeBtn (ActionEvent event) {
        System.exit(0);
    }

    //导出操作事件
    @FXML
    @SuppressWarnings("unchecked")
    public void exportBtn (ActionEvent event){
        try {
            TeamcenterCMDRunner tcCMD = new TeamcenterCMDRunner();
            tcCMD.SetENV(
                    "C:\\Teamcenter\\TCROOT",
                    "Z:\\tcdata",
                    "infodba",
                    "infodba",
                    "dba",
                    "GB2312");
            tcCMD.RunTarget("ExportGroupName");
            ObservableList<RecordClass> list = FXCollections.observableArrayList();
            XMLReader xmlReader = new XMLReader();
            xmlReader.read("GB2312", System.getProperty("user.dir") + "/groups_en_US.xml");
            colId.setCellValueFactory(new PropertyValueFactory("recordID"));
            colKeyValue.setCellValueFactory(new PropertyValueFactory("recordKeyValue"));
            colEng.setCellValueFactory(new PropertyValueFactory("recordEng"));
            colChs.setCellValueFactory(new PropertyValueFactory("recordChs"));

            for (int i = 0; i < xmlReader.list.size(); i++) {
                RecordClass recordClass = new RecordClass();
                recordClass.setRecordID(String.valueOf(i + 1));
                recordClass.setRecordKeyValue(xmlReader.list.get(i)[0]);
                recordClass.setRecordEng(xmlReader.list.get(i)[1]);
                recordClass.setRecordChs(xmlReader.list.get(i)[2]);
                list.add(recordClass);
            }

            table.setItems(list);
        } catch (Exception e){
            System.err.println(e);
        }

    }
}
