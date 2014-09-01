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

import android.util.Log;

import com.poker.common.wifi.listener.CommunicationListener;
import com.poker.common.wifi.listener.WifiClientListener;

public class SocketServer {

	private ServerSocket server;

	private static SocketServer serverSocket;

	public static List<Socket> socketQueue = new ArrayList<Socket>();

	private static final String TAG = "SocketServer";

	private int mPort;

	private CommunicationListener listener;
	
	private WifiClientListener clientListener;
	
	private WifiCreateListener createListener;
	
	public interface WifiCreateListener{
		void onCreateSuccess();
		void OnCreateFailure(String strError);
	}
	
	
	private boolean onGoinglistner = true;

	public static synchronized SocketServer newInstance(int port) {
		if (serverSocket == null) {
			serverSocket = new SocketServer(port);
		}
		return serverSocket;
	}
	
	public static synchronized SocketServer newInstance() {
		return serverSocket;
	}
	
	//设置热点接入人数变化监听器
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

	private SocketServer(final int port) {
		this.mPort = port;
	}

	public interface SocketCreateListener{
		void onSuccess();
		void onFailure();
	}
	
	
	public void createServerSocket(SocketCreateListener cListener){
		try {
			server = new ServerSocket();
			server.setReuseAddress(true);
			InetSocketAddress address = new InetSocketAddress(mPort);
			server.bind(address);
			cListener.onSuccess();
			Log.i(TAG, "server  =" + server);
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d(TAG, "server int fail ");
			cListener.onFailure();
		}
	}
	
	public void beginListen(final WifiClientListener clientListener) {
		setClientListener(clientListener);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (server != null) {
					while (onGoinglistner) {
						try {
							final Socket socket = server.accept();
							if (socket != null) {
								//监听到客户端接入
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

	//对单个客户端发出消息,必须在开始监听行为后才可执行
	public void sendMessage(final Socket client, final String msg) {
		Log.i(TAG, "into sendMsg(final Socket client,final ChatMessage msg) msg = " + msg);
		PrintWriter out = null;
		if (client.isConnected()) {
			if (!client.isOutputShutdown()) {
				try {
					out = new PrintWriter(client.getOutputStream());
					out.println(msg);
					out.flush();
					if(listener!=null){
						listener.onSendSuccess();
					}
					Log.i(TAG, "into sendMsg(final Socket client,final ChatMessage msg) msg = " + msg + " success!");
				} catch (IOException e) {
					e.printStackTrace();
					if(listener!=null){
						listener.onSendFailure(e.getMessage());
					}
					Log.d(TAG, "into sendMsg(final Socket client,final ChatMessage msg) fail!");
				}
			}
		}
		Log.i(TAG, "out sendMsg(final Socket client,final ChatMessage msg) msg = " + msg);
	}

	public CommunicationListener getListener() {
		return listener;
	}

	public void setListener(CommunicationListener listener) {
		this.listener = listener;
	}

	public void sendMessageToAllClients(final String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < socketQueue.size(); i++) {
					sendMessage(socketQueue.get(i), msg);
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
						if(listener!=null){
							listener.onStringReceive(str);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public int getConnectNumber() {
		return socketQueue.size();
	}

	public void stopListner() {
		onGoinglistner = false;
	}

	public WifiCreateListener getCreateListener() {
		return createListener;
	}

	public void setCreateListener(WifiCreateListener createListener) {
		this.createListener = createListener;
	}
}