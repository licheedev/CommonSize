package com.licheedev.commonsize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {

    public static void main(String[] args) {

        Config config = new Config(new File("generator_config.properties"));

        Generator generator = new Generator(config);

        File templet = new File(Config.TEMPLET);

        for (OutputConfig outputConfig : config.outputConfigs) {
            File destModule = new File(outputConfig.getModuleName());

            if (config.justDelete) {
                FileUtil.removeFile(destModule);
                continue;
            }

            FileUtil.copyDir(templet, destModule);

            File resDir = new File(destModule, Config.RES_ROOT);
            // 先生成个默认的
            generator.generateFile(outputConfig, resDir, 0);
            // 再生成其他
            for (Integer integer : config.swList) {
                generator.generateFile(outputConfig, resDir, integer);
            }

            System.out.println(outputConfig);
        }
    }

    private final Config mConfig;

    public Generator(Config config) {
        mConfig = config;
    }

    private void generateFile(OutputConfig outputConfig, File resRoot, int smallestWidth) {

        File dir;

        if (smallestWidth > 0) {
            dir = new File(resRoot, "values-sw" + smallestWidth + "dp");
        } else {
            dir = new File(resRoot, "values");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 正数
        File file = new File(dir, mConfig.normalFileName);
        String dimenReg = Config.DIMEN_DP_LINE.replace(Config.REPLACE_REG, mConfig.normalResReg);
        writeContent(outputConfig, file, dimenReg, smallestWidth, false);

        // 负数
        if (mConfig.enableNegative) {
            file = new File(dir, mConfig.negativeFileName);
            dimenReg = Config.DIMEN_DP_LINE.replace(Config.REPLACE_REG, mConfig.negativeResReg);
            writeContent(outputConfig, file, dimenReg, smallestWidth, true);
        }

        // sp
        if (mConfig.enableSp) {
            file = new File(dir, mConfig.spFileName);
            dimenReg = Config.DIMEN_SP_LINE.replace(Config.REPLACE_REG, mConfig.spResReg);
            writeContent(outputConfig, file, dimenReg, smallestWidth, false);
        }
    }

    private void writeContent(OutputConfig outputConfig, File file, String dimenReg, int smallLest,
        boolean negative) {
        StringBuilder sb = new StringBuilder();
        sb.append(Config.XML_HEAD).append("\n").append(Config.RESOURCES_START).append("\n");

        // 0 的时候
        sb.append(mConfig.intent)
            .append(dimenReg.replace("{x}", "" + 0).replace("{value}", "" + 0))
            .append("\n");

        // 最关键的步骤，就是算出 参考屏幕上每单位，对应屏幕上多少dp
        //float per = smallLest / (float) outputConfig.designWidth;
        //if (smallLest <= 0) {
        //    // 如果是负数，则表示为默认的values目录，以360为标准
        //    per = 360 / (float) outputConfig.designWidth;
        //}

        for (int i = 1; i <= outputConfig.itemAmount; i++) {

            float value;
            if (smallLest > 0) {
                value = i / (float) outputConfig.designWidth * smallLest;
            } else {
                value = i / (float) outputConfig.designWidth * 360;
            }

            if (negative) {
                sb.append(mConfig.intent)
                    .append(dimenReg.replace("{x}", "" + i).replace("{value}", "-" + value))
                    .append("\n");
            } else {
                sb.append(mConfig.intent)
                    .append(dimenReg.replace("{x}", "" + i).replace("{value}", "" + value))
                    .append("\n");
            }
        }
        sb.append(Config.RESOURCES_END).append("\n");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
