/*
 * Copyright (c) 2018 Bitmoe Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.bitmoe.osp.qistchan.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {
    private String command;
    private String charSet;

    public void setCommand(String cmd){
        this.command = cmd;
    }
    public void setCharSet(String charSets) { this.charSet = charSets; }

    public List<String> run() {
        String screenLine;
        List<String> screenLines = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(this.command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName(this.charSet)));
            while ((screenLine = bufferedReader.readLine()) != null)
                screenLines.add(screenLine);
            process.waitFor();
            return screenLines;
        } catch (Exception e){
            System.err.println(e);
            return null;
        }
    }
}
