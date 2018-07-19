package com.bitmoe.osp.qistchan.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TeamcenterCMDRunner {
    private String tcRootPath;
    private String tcDataPath;
    private String tcUSER;
    private String tcPASS;
    private String tcGROUP;
    private String tcCharSet;

    public void SetENV(
            String rootPath,
            String dataPath,
            String tcUSER,
            String tcPASS,
            String tcGROUP,
            String tcCharSet
    ){
        this.tcRootPath = rootPath;
        this.tcDataPath = dataPath;
        this.tcUSER = tcUSER;
        this.tcPASS = tcPASS;
        this.tcGROUP = tcGROUP;
        this.tcCharSet = tcCharSet;
    }

    private String GenerateCMD(List<String> targetCommand){
        try {
            FloderCheck("TCCMD");
            String tcCMDPath = System.getProperty("user.dir") + "\\TCCMD\\" + System.currentTimeMillis() + ".bat";
            String tcProfilevarsPath = this.tcDataPath + "\\tc_profilevars.bat";
            System.out.println(tcProfilevarsPath);
            FileWriter fileWriter = new FileWriter(tcCMDPath);
            FileReader fileReader = new FileReader(tcProfilevarsPath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedWriter.write("@set TC_DATA=" + this.tcDataPath + "\r\n");
            bufferedWriter.write("@set TC_ROOT=" + this.tcRootPath + "\r\n");
            String tempString = null;
            while ((tempString = bufferedReader.readLine())!=null){
                if(tempString.contains(":EOF")){
                    for (int i=0;i<targetCommand.size();i++){
                        bufferedWriter.write(targetCommand.get(i) + "\r\n");
                    }
                }
                bufferedWriter.write(tempString + "\r\n");
            }
            bufferedWriter.flush();
            fileWriter.close();
            bufferedWriter.close();
            fileReader.close();
            bufferedReader.close();
            return tcCMDPath;


        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private boolean FloderCheck (String fileName){
        File file = new File(System.getProperty("user.dir") + "\\" + fileName);
        if (!file.exists() && !file.isDirectory()){
            file.mkdir();
            return false;
        } else {
            return true;
        }
    }

    private List<String> GetTargetCMD (
            String scriptName,
            String tcUSER,
            String tcPASS,
            String tcGROUP
    ){
        List<String> targetCMD = new ArrayList<String>();
        if (FloderCheck("Script")){
            try {
                FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\Script\\" + scriptName + ".bat");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String tempString = null;

                targetCMD.add("@set TCCMD_USER=" + tcUSER);
                targetCMD.add("@set TCCMD_PASS=" + tcPASS);
                targetCMD.add("@set TCCMD_GROUP=" + tcGROUP);

                while ((tempString = bufferedReader.readLine())!=null) {
                    targetCMD.add(tempString);
                }

                fileReader.close();
                bufferedReader.close();
                return targetCMD;
            } catch (Exception e){
                return null;
            }
        } else {
            System.out.println("Scrpt folder is not exist!");
            return null;
        }
    }

    public List<String> RunTarget (String targetCMD){
        CommandRunner cmdRunner = new CommandRunner();
        cmdRunner.setCommand(
                this.GenerateCMD(
                        this.GetTargetCMD(
                                targetCMD, this.tcUSER, this.tcPASS, this.tcGROUP
                        )
                )
        );
        cmdRunner.setCharSet(this.tcCharSet);
        return cmdRunner.run();
    }

    public void disPlayList (List<String> list){
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
