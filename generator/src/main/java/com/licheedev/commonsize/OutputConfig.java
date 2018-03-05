package com.licheedev.commonsize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by John on 2018/3/5.
 */

public class OutputConfig {

    public int designWidth;
    public int itemAmount;
    private String moduleNameReg;

    public OutputConfig(int designWidth, int itemAmount, String moduleNameReg) {
        this.designWidth = designWidth;
        this.itemAmount = itemAmount;
        this.moduleNameReg = moduleNameReg;
    }

    public String getModuleName() {

        String moduleName;
        if (moduleNameReg != null && moduleNameReg.contains("{w}") && moduleNameReg.contains(
            "{n}")) {
            moduleName = moduleNameReg.replace("{w}", "" + designWidth);
            moduleName = moduleName.replace("{n}", "" + itemAmount);
            // 再检查一遍
            if (!moduleName.contains("{w}") && !moduleName.contains("{n}")) {
                return moduleName;
            }
        }

        moduleName = "common_size_w" + designWidth + "_n" + itemAmount;

        return moduleName;
    }

    public static List<OutputConfig> parseOutputConfigs(String outputConfig, String moduleNameReg) {
        ArrayList<OutputConfig> outputConfigs = new ArrayList<>();

        try {
            Pattern pattern = Pattern.compile("\\d+\\*\\d+");
            Matcher matcher = pattern.matcher(outputConfig);
            while (matcher.find()) {
                String sizeStr = matcher.group();
                String[] values = sizeStr.split("\\*");
                int designWidth = Integer.parseInt(values[0]);
                int itemAmount = Integer.parseInt(values[1]);
                outputConfigs.add(new OutputConfig(designWidth, itemAmount, moduleNameReg));
            }
        } catch (NumberFormatException e) {
            //            e.printStackTrace();
        }

        return outputConfigs;
    }

    @Override
    public String toString() {
        return "OutputConfig{"
            + "designWidth="
            + designWidth
            + ", itemAmount="
            + itemAmount
            + ", moduleNameReg='"
            + getModuleName()
            + '\''
            + '}';
    }
}
