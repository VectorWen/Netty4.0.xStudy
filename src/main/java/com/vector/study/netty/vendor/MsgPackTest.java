package com.vector.study.netty.vendor;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: vector.huang
 * date: 2016/12/15 09:19
 */
public class MsgPackTest implements Runnable{


    @Override
    public void run() {
        try {
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packInt(2);
            packer.packString("1234567890");
            packer.close();

            byte[] b = packer.toByteArray();
            System.out.println(b.length);
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(b);
            int id = unpacker.unpackInt();
            System.out.println(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MsgPackTest().run();
    }
}
