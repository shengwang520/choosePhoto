# choosePhoto  图片选择库 适配安卓10+

# 引入
## Step 1. Add the JitPack repository to your build file
* maven { url '<https://jitpack.io>' }

## Step 2. Add the dependency
* api 'com.github.shengwang520:choosePhoto:1.0.13'

# 使用
* 需要权限：android.permission.READ_EXTERNAL_STORAGE，android.permission.WRITE_EXTERNAL_STORAGE

## 1.获取图片
### 配置图片获取最小宽高，满足1条即为有效数据
* FileOptions.width=500;
* FileOptions.height=500;

### 获取数据
* private fun loadImage() {

        val fileCompat = FileCompat(this)
        fileCompat.loadImages(object : CallBack.OnLoadFileFolderListener {
            override fun onSuccess(results: List<FileFolder>) {
                Logger.d("获取到的图片数量：" + results.size)
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }

## 2.获取视频
* private fun loadVideo() {

        val fileCompat = FileCompat(this)
        fileCompat.loadVideos(object : CallBack.OnLoadFileFolderListener {
            override fun onSuccess(results: List<FileFolder>) {
                Logger.d("获取到的视频数量：" + results.size)
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }
## 3.获取图片和视频
* private fun loadImageAndVideo() {

        val fileCompat = FileCompat(this)
        fileCompat.loadImageAndVideos(object : CallBack.OnLoadFileListener {
            override fun onSuccess(results: List<FileBean>) {
                Logger.d("获取到的图片视频数量：" + results.size)
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }

## 4.获取上传文件路径
* String path=FileBean.getUploadFilePath(context);

## 5.获取显示文件的地址（安卓10以上路径是Uri）
* String path=FileBean.getFilePathQ(context);

## Changelog

### Version:1.0.13
* 重新设计回调，调整使用方式，与之前的版本不兼容，请参考最新文档使用
* 新增获取所有图片和视频数据，按照时间降序

### Version:1.0.12
* 修复图片查询失败

### Version:1.0.11
* 新增图片压缩方法

### Version:1.0.10
* 调整代码语言为kotlin
* 新增设置视频选择范围配置
* 修复部分手机获取不到文件后缀出现的崩溃问题

### Version:1.0.09
* 升级编译环境为7.0.1
* 适配安卓12

### Version:1.0.07
* 移除权限判断，添加demo示例

### Version:1.0.06
* 删除无用资源库，减轻sdk大小

### Version:1.0.05
* 添加安卓10+适配
* 移除无用代码

### Version:1.0.04
* 初始版本

## License

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
