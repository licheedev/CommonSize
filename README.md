# CommonSize
Android通用屏幕适配方案

## 原理 smallestWidth
https://developer.android.com/guide/practices/screens_support.html#NewQualifiers

关键的地方就是sw后面数值的单位是`dp`

* 跟hongyang的方案不同，不会用绝对分辨率适配，而使用sw（最小宽度）限定符方案
* 只关注最小宽度，无视高度，不会受有/没有虚拟导航键影响，也可以适配18:9的全面屏
* 设备的sw不会有太多，没有合适的配置的话，会使用最接近设备最小宽度的那个较小的配置（如没有小米mix2的392的话，会用384这个配置，误差不会很大）
* 预览应该无压力，毕竟sw列表就是根据官方模拟器配置列表得来的

```java
// 获取最小宽度的代码
Configuration config = getResources().getConfiguration();
int smallestScreenWidthDp = config.smallestScreenWidthDp;
```

## 使用

1. 在根build.gradle中添加

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. 子module添加依赖

```
dependencies {
        // 随便选一个，没有就按照下面配置重新生成自己要的
        // 公司美工喜欢做iphone(750*1334)的图，就任意选一个w750的就行了
        // 本人所在公司的广告机项目做的图是1080P的，所以有个w1080的配置
        compile 'com.github.licheedev.CommonSize:common_size_w1080_n1920:1.2'
        compile 'com.github.licheedev.CommonSize:common_size_w750_n1334:1.2'
        compile 'com.github.licheedev.CommonSize:common_size_w750_n1000:1.2'
}
```

没有合适的话，就按照下面配置重新生成一个module，复制到自己项目里面，然后 `compile project(':生成的模块名')`导入即可

## 生成新的尺寸配置

### 运行生成器
![generator](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/generator.png)

### 生成器配置 generator_config.properties

```properties
# 输出配置，格式为w*n，其中w为参考宽度，美工的图给多少就填多少，n为dimen资源文件中，dimen条目的个数
# 输出配置可以填多个，分别以英文逗号,隔开
output_config=750*1000,750*1334,1080*1920
# 模块名字规则，{w}为上面的参考宽度，{n}为上面的条目数目
module_name_reg=common_size_w{w}_n{n}
# 缩进空格长度
intent_length=4
# 正数尺寸文件名
normal_file_name=normal_dp.xml
# 正数资源命名规则,{x}为要替换的数值
normal_res_reg=normal_{x}dp
# 是否启用生成负数尺寸
enable_negative=true
# 负数尺寸文件名
negative_file_name=negative_dp.xml
# 负数资源命名规则,{x}为要替换的数值
negative_res_reg=negative_{x}dp
# 是否启用生成sp尺寸
enable_sp=true
# 其他配置文件名
sp_file_name=normal_sp.xml
# sp资源命名规则,{x}为要替换的数值
sp_res_reg=normal_{x}sp
# 最小宽度列表路径
smallest_width_list=smallest_width_list.txt
# 只删除目标module
just_delete=false
```

### 最小宽度列表 smallest_width_list.txt
```
// 计算公式一般为 宽度像素*160/dpi
320 // 320*480(mdpi)、480*800(hdpi)
360 // 720*1280(xhdpi)、1080*1920(xxhdpi)、Galaxy Nexus、Nexus5、荣耀8
384 // 768*1280(xhdpi)、Nexus4
392 // Xiaomi Mix2
411 // 1080*1920(420dpi)、1440*2560(560dpi)、Nexus 5x、Nexus 6、Nexus 6p、Pixel xl
480 // 480*800(mdpi)
540 // 540*960(mdpi)
600 // 600*1024(mdpi)、1200*1920(xhdpi)、Nexus 7
768 // 768*1280(mdpi)、2048*1536(xhdpi)、Nexus 9
800 // 800*1280(mdpi)、2560*1600(xhdpi)、Nexus 10
900 // 900*1600(mdpi)、2560*1800(xhdpi)、Pixel C
1080 // 1080*1920(mpdi)
```

## 运行情况
小米mix2运行情况

![mix2](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/mix2.jpg)

其他机型

![荣耀8](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/rongyao8.jpg)
![as_preview](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/as_preview.png)
![nexus_4](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/nexus_4.png)
![nexus7](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/nexus7.png)
![nexus_9](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/nexus_9.png)
![pixel_c](https://raw.githubusercontent.com/licheedev/CommonSize/master/pics/pixel_c.png)

