package com.common.app.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class FileResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private OnProgressListener mOnProgressListener;
    private BufferedSource mBufferedSource;

    public FileResponseBody(ResponseBody responseBody, OnProgressListener onProgressListener) {
        this.mResponseBody = responseBody;
        this.mOnProgressListener = onProgressListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    /**
     * 回调进度接口
     *
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                mOnProgressListener.onLoading(mResponseBody.contentLength(), totalBytesRead);
                return bytesRead;
            }
        };
    }
}
