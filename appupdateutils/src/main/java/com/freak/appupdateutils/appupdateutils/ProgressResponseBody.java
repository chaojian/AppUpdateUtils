package com.freak.appupdateutils.appupdateutils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 *
 * OkHttp把请求和响应分别封装成了RequestBody和ResponseBody，举例子来说，ResponseBody内部封装了响应的Head、Body等内容，
 * 如果我们要获取当然的下载进度，即传输了多少字节，那么我们就要对ResponseBody做出某些修改，以便能让我们知道传输的进度以及设置相应的回调函数供我们使用
 *
 * Source相当于一个输入流InputStream，即响应的数据流。Source可以很轻易获得，
 * 通过调用responseBody.source()方法就能获得一个Source对象。
 * 那么，到现在为止，source()方法看起来应该是这样的： bufferedSource = Okio.buffer(responseBody.source());
 * 显然，这样直接返回了一个BufferedSource对象，那么我们的ProgressListener并没有在任何地方得到设置，因此上面的方法是不妥的，
 * 解决方法是利用Okio提供的ForwardingSource来包装我们真正的Source，并在ForwardingSource的read()方法内实现我们的接口回调
 *
 * @author freak
 * @date 2019/1/2
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;

    private final ProgressListener progressListener;

    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        //传入回调
        this.progressListener = progressListener;
    }

    /**
     *
     * @return 返回响应内容的类型，比如image/jpeg
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     *
     * @return 返回响应内容的长度
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * BufferedSource可以理解为一个带有缓冲区的响应体
     * @return
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }


    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink,byteCount);
                //不断统计当前下载好的数据
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //接口回调
                progressListener.onProgress(totalBytesRead,responseBody.contentLength(),bytesRead == -1);
                return bytesRead;
            }
        };
    }

}
