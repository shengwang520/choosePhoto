# choosePhoto  图片选择库 适配安卓10+

# 引入
## Step 1. Add the JitPack repository to your build file
* maven { url '<https://jitpack.io>' }

## Step 2. Add the dependency
* api 'com.github.shengwang520:choosePhoto:1.0.07'

# 使用
* 需要权限：android.permission.READ_EXTERNAL_STORAGE，android.permission.WRITE_EXTERNAL_STORAGE

## 1.获取图片
### 配置图片获取最小宽高，满足1条即为有效数据
* FileOptions.width=500;
* FileOptions.height=500;

### 获取数据
* FileCompat fileCompat = new FileCompat(this, new CallBack() {

            @Override
            public void onSuccess(List<FileFolder> results) {
               //数据获取成功
            }
            
            @Override
            public void onError() {
                //数据获取失败
            }
            
        });
        
        fileCompat.loadImages();

## 2.获取视频
* FileCompat fileCompat = new FileCompat(this, new CallBack() {

            @Override
            public void onSuccess(List<FileFolder> results) {
               //数据获取成功
            }
            
            @Override
            public void onError() {
                //数据获取失败
            }
            
        });
        
        fileCompat.loadVideos();

## 3.获取上传文件路径
* String path=FileBean.getUploadFilePath(context);

## 4.获取显示文件的地址（安卓10以上路径是Uri）
* String path=FileBean.getFilePathQ(context);

##  Changelog

### Version:1.0.07
* 移除权限判断，添加demo示例

### Version:1.0.06
* 删除无用资源库，减轻sdk大小

### Version:1.0.05
* 添加安卓10+适配
* 移除无用代码

### Version:1.0.04
* 初始版本

##  License

    Copyright 2021 shengwang520

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
