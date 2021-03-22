package com.musinsa.urlshortening.config;

import com.musinsa.urlshortening.domain.exception.OutOfLengthException;
import org.springframework.stereotype.Component;

@Component
public class Base62 {

    final String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    final int RADIX = 62;

    final int codecLength = CODEC.length();

    final int minLength = codecLength * codecLength * codecLength;

    final int MAX_LENGTH = 8;

    public String encoding(Long param) {
        final StringBuffer sb = new StringBuffer();
        param = param + minLength;
        while(param > 0) {
            sb.append(CODEC.charAt((int) (param % RADIX)));
            param /= RADIX;
        }

        if (sb.length() > MAX_LENGTH) {
            throw new OutOfLengthException();
        }

        return sb.toString();
    }

    public Long decoding(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += CODEC.indexOf(param.charAt(i)) * power;
            power *= RADIX;
        }
        return sum - minLength;
    }
}
