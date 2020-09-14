package program;

import command.Command;
import command.ErrorCommand;
import commons.Writer;
import swing_package.InfoMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    public static ConcurrentLinkedQueue<ByteBuffer> messages = new ConcurrentLinkedQueue<>();

    public static void run(String ip, int port) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(InetAddress.getByName(ip), port);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(addr);
        System.out.println("Соединение с " + sc);
        while (sc.isConnectionPending()) {
            sc.finishConnect();
        }
        pool.submit(() -> read(sc, messages));
        pool.submit(() -> answer(sc, messages));
    }

    private static void answer(SocketChannel sc, ConcurrentLinkedQueue<ByteBuffer> messages) {
        try {
            while (!MainClient.globalKillFlag.get()) {
                ByteBuffer buf;
                if (!messages.isEmpty()) {
                    buf = messages.poll();
                    sc.write(buf);
                }
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            Command com = new ErrorCommand(MainClient.user, "client.error.answer");
            CommanderClient.switcher(com, MainClient.collection);
        }
        System.out.println("Закрылся answer");
        MainClient.globalKillFlag.set(true);
    }

    private static void read(SocketChannel sc, ConcurrentLinkedQueue<ByteBuffer> messages) {
        try {
            while (true) {
                ByteBuffer buf = ByteBuffer.allocateDirect(8192);
                int read = sc.read(buf);
                if (MainClient.globalKillFlag.get()) {
                    sc.close();
                    break;
                }
                if (read == -1) {
                    sc.close();
                    throw new IOException();
                } else if (read != 0) {
                    //поток обработки
                    pool.submit(() -> {
                        try {
                            buf.flip();
                            process(buf, messages);
                        } catch (IOException | ClassNotFoundException e) {
                            MainClient.globalKillFlag.set(true);
                            Command com = new ErrorCommand(MainClient.user, "client.error.process");
                            CommanderClient.switcher(com, MainClient.collection);
                        }
                    });
                }
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            Command com = new ErrorCommand(MainClient.user, "client.error.read");
            CommanderClient.switcher(com, MainClient.collection);
        }
        Writer.writeln("reader умер");
        MainClient.globalKillFlag.set(true);
    }

    private static void process(ByteBuffer buf, ConcurrentLinkedQueue<ByteBuffer> messages) throws IOException, ClassNotFoundException {
        byte[] arr = new byte[buf.remaining()];
        buf.get(arr);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(arr));
        Command com = (Command) objectInputStream.readObject();
        CommanderClient.switcher(com, MainClient.collection);
        objectInputStream.close();
        buf.clear();
    }

    public static void SendCommand(Command command) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);
        objectOutputStream.flush();
        ByteBuffer bb = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        Client.messages.add(bb);
    }
}