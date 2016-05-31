/*
 * The MIT License
 *
 * Copyright 2015 Shekhar Gulati <shekhargulati84@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shekhargulati.reactivex.rxokhttp.functions;

import com.shekhargulati.reactivex.rxokhttp.HttpStatus;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public interface ResponseTransformer<R> extends IoFunction<Response, R> {

    static ResponseTransformer<Response> identity() {
        return t -> t;
    }

    static ResponseTransformer<HttpStatus> httpStatus() {
        return response -> HttpStatus.of(response.code(), response.message());
    }

    static <T> ResponseTransformer<T> fromBody(final ResponseBodyTransformer<T> bodyTransformer) {
        return response -> {
            try (ResponseBody body = response.body()) {
                return bodyTransformer.apply(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
