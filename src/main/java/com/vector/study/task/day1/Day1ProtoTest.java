package com.vector.study.task.day1;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vector.study.entity.HelloProtoBuf;

/**
 * author: vector.huang
 * date: 2016/12/17 15:03
 */
public class Day1ProtoTest {

    public static byte[] decoder(HelloProtoBuf.Info info){
        //编写好ProtocolBuffer 的Message之后,编码解码就是简单
        return info.toByteArray();
    }

    public static HelloProtoBuf.Info encoder(byte[] data) throws InvalidProtocolBufferException {
        return HelloProtoBuf.Info.parseFrom(data);
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {

        //创建也是那么的简单, 直接用Builder 生成
        HelloProtoBuf.Info.Builder builder = HelloProtoBuf.Info.newBuilder();
        builder.setId(1)
                .setName("Hello Protocol Buffer!!");

        HelloProtoBuf.Info info = builder.build();
        byte[] data = decoder(info);
        System.out.println(data.length);

        HelloProtoBuf.Info info1 = encoder(data);

        System.out.println(info == info1);
        System.out.println(info.equals(info1));

        System.out.println(info1);
    }



}
