package com.poker.common.wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.poker.common.wifi.listener.WifiClientListener;
import com.poker.common.wifi.listener.WifiMessageListener;

import android.util.Log;

public class SocketServer {

	private ServerSocket server;

	private static SocketServer serverSocket;

	public static List<Socket> socketQueue = new ArrayList<Socket>();

	private static final String TAG = "SocketServer";

	private int mPort;

	private ServerMsgListener serverListener;
	
	private WifiMessageListener sendListener;
	
	private WifiClientListener clientListener;

	private boolean onGoinglistner = true;

	public static interface ServerMsgListener {

		public void handlerErorMsg(String errorMsg);

		public void handlerHotMsg(String hotMsg);

	}

	public static synchronized SocketServer newInstance(int port, ServerMsgListener serverListener) {
		Log.i(TAG, "into newInstance(int port, ServerMsgListener serverListener)...................................");
		if (serverSocket == null) {
			serverSocket = new SocketServer(port, serverListener);
		}
		Log.i(TAG, "out newInstance(int port, ServerMsgListener serverListener)...................................");
		return serverSocket;
	}

	// �л���Ϣ������
	public void setMsgListener(ServerMsgListener listener) {
		this.serverListener = listener;
	}
	
	//������Ϣ���ͼ�����
	public void setMessageSendListener(WifiMessageListener sendListener){
		this.sendListener = sendListener;
	}
	
	//���ÿͻ��������仯������
	public void setClientListener(WifiClientListener clientListener){
		this.clientListener = clientListener;
		
	}
	
	private void closeConnection() {
		Log.i(TAG, "into closeConnection()...................................");
		if (server != null) {
			try {
				server.close();
				server = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.i(TAG, "out closeConnection()...................................");
	}

	public void clearServer() {
		closeConnection();
	}

	private SocketServer(final int port, ServerMsgListener serverListener) {
		Log.i(TAG, "into SocketServer(final int port, ServerMsgListener serverListener) ...................................");
		this.mPort = port;
		this.serverListener = serverListener;
		Log.i(TAG, "out SocketServer(final int port, ServerMsgListener serverListener) ...................................");
	}

	public void beginListen() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server = new ServerSocket();
					server.setReuseAddress(true);
					InetSocketAddress address = new InetSocketAddress(mPort);
					server.bind(address);
					serverListener.handlerHotMsg(Global.INT_SERVER_SUCCESS);
					Log.i(TAG, "server  =" + server);
				} catch (IOException e1) {
					e1.printStackTrace();
					serverListener.handlerErorMsg(Global.INT_SERVER_FAIL);
					Log.d(TAG, "server int fail ");
				}
				if (server != null) {
					while (onGoinglistner) {
						try {
							final Socket socket = server.accept();
							if (socket != null) {
								//�������ͻ��˽���
								if (!socketQueue.contains(socket)) {
									socketQueue.add(socket);
									if(null!=clientListener){
										clientListener.clientIncrease(socket.getInetAddress().getHostName());
									}
								}
								serverAcceptMsg(socket);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	//�Ե����ͻ��˷�����Ϣ
	public void sendMsg(final Socket client, final String msg) {
		Log.i(TAG, "into sendMsg(final Socket client,final ChatMessage msg) msg = " + msg);
		PrintWriter out = null;
		if (client.isConnected()) {
			if (!client.isOutputShutdown()) {
				try {
					out = new PrintWriter(client.getOutputStream());
					out.println(msg);
					out.flush();
					if(sendListener!=null){
						sendListener.sendSuccess(msg);
					}
					Log.i(TAG, "into sendMsg(final Socket client,final ChatMessage msg) msg = " + msg + " success!");
				} catch (IOException e) {
					e.printStackTrace();
					if(sendListener!=null){
						sendListener.sendFailure(msg);
					}
					Log.d(TAG, "into sendMsg(final Socket client,final ChatMessage msg) fail!");
				}
			}
		}
		Log.i(TAG, "out sendMsg(final Socket client,final ChatMessage msg) msg = " + msg);
	}

	public void sendMsgToAllCLients(final String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < socketQueue.size(); i++) {
					sendMsg(socketQueue.get(i), msg);
				}
			}
		}).start();
	}

	private void serverAcceptMsg(final Socket socket) {
		new Thread(new Runnable() {
			public void run() {
				BufferedReader in;
				try {
					while (!socket.isClosed()) {
						in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
						String str = in.readLine();
						if (str == null || str.equals("")) {
							break;
						}
						Log.i(TAG, "client" + socket + "str =" + str);
						serverListener.handlerHotMsg(str);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public int connectNumber() {
		return socketQueue.size();
	}

	public void stopListner() {
		onGoinglistner = false;
	}
}