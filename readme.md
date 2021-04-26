# choosePhoto  图片选择库

# 引入
## Step 1. Add the JitPack repository to your build file
* maven { url 'https://jitpack.io' }

## Step 2. Add the dependency
* api 'com.github.shengwang520:choosePhoto:1.0.05'

# 使用
## 获取图片
### 配置图片获取宽高
* FileOptions.width=500;
* FileOptions.height=500;

### 获取数据
* FileCompat fileCompat = new FileCompat(this, new CallBack() {
            @Override
            public void onSuccess(List<com.media.imagechoose.model.FileFolder> results) {
                popWindow.init(results);
                imageFolder = results.get(0);
                holder.initImageFolder(imageFolder.getName());
                initImgs();
            }

            @Override
            public void onError() {

            }
        });
        fileCompat.loadImages();

## 获取视频
* FileCompat fileCompat = new FileCompat(this, new CallBack() {
            @Override
            public void onSuccess(List<com.media.imagechoose.model.FileFolder> results) {
                popWindow.init(results);
                imageFolder = results.get(0);
                holder.initImageFolder(imageFolder.getName());
                initImgs();
            }

            @Override
            public void onError() {

            }
        });
        fileCompat.loadVideos();

##  Changelog

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
