package com.example.fmdm;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionServ";

    private static final String appName = "MYAPP";

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

//    private static final UUID MY_UUID_INSECURE =
//            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    String temp = "";

    float pinky,ring,middle,index,thumb,contact1,contact2,contact3,contact4, contact5, contact6;

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }


    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {

        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);

                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            }catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }

            mmServerSocket = tmp;
        }

        public void run(){
            Log.d(TAG, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try{
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.d(TAG, "run: RFCOM server socket start.....");

                socket = mmServerSocket.accept();

                Log.d(TAG, "run: RFCOM server socket accepted connection.");

            }catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }

            //talk about this is in the 3rd
            if(socket != null){
                connected(socket,mmDevice);
            }

            Log.i(TAG, "END mAcceptThread ");
        }

        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
            }
        }

    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = MY_UUID_INSECURE;
        }

        public void run(){
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                        +MY_UUID_INSECURE );
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;

            // Always cancel discovery because it will slow down a connection
            mBluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket



            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                    mmSocket.connect();

                Log.d(TAG, "run: ConnectThread connected.");
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE );
            }

            //will talk about this in the 3rd video
            connected(mmSocket,mmDevice);
        }
        public void cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }
    }



    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    /**
     AcceptThread starts and sits waiting for a connection.
     Then ConnectThread starts and attempts to make a connection with the other devices AcceptThread.
     **/

    public void startClient(BluetoothDevice device,UUID uuid){
//        uuid = MY_UUID_INSECURE;
        Log.d(TAG, "startClient: Started.");

        //initprogress dialog
        mProgressDialog = ProgressDialog.show(mContext,"Connecting Bluetooth"
                ,"Please Wait...",true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    /**
     Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     receiving incoming data through input/output streams respectively.
     **/
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            //dismiss the progressdialog when connection is established
            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];  // buffer store for the stream
            StringBuilder finalMessage = new StringBuilder();
            StringBuilder stringBuilder = new StringBuilder();
            String appendedString,finalString;

            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                // Read from the InputStream

//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(mmInStream))){
//                    String incomingMessage = null;
//                    while((incomingMessage = reader.readLine()) != null) {
//                        System.out.println(incomingMessage);
//                        Log.d("INCOMING" , incomingMessage);
//
//                        Intent incomingMessageIntent = new Intent("incomingMessage");
//                        incomingMessageIntent.putExtra("theMessage", incomingMessage);
//                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(incomingMessageIntent);
//                    }
//                } catch(IOException e) {
//                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
//                    break;
//                }

                try {

//                    bytes = mmInStream.read(buffer);


//                    char firstChar = incomingMessage.charAt(0);
//                    char lastChar = incomingMessage.charAt(incomingMessage.length()-1);
                    char sb_First = 0, sb_Last = 0;
//                    char sbFirst = stringBuilder.charAt(0);
//                    char sbLast = stringBuilder.charAt(stringBuilder.length()-1);

                    do {
                        bytes = mmInStream.read(buffer,0,4);
                        String incomingMessage = new String(buffer, 0, bytes);
//                        Log.d("INCOMING" , incomingMessage);
                        System.out.println("The OUTPUT: " + incomingMessage);
                        stringBuilder.append(incomingMessage);
                        if(stringBuilder.charAt(0) != 'a'){
                            stringBuilder.setLength(0);
                        }

                        System.out.println(String.valueOf(stringBuilder));
                        if(stringBuilder.length() > 0){
                        char sbFirst = stringBuilder.charAt(0);
                        char sbLast = stringBuilder.charAt(stringBuilder.length()-1);
                        sb_First = sbFirst;
                        sb_Last = sbLast;
                        }
                        if(stringBuilder.length() >= 46 && !String.valueOf(sb_Last).equals("b")){
                            stringBuilder.setLength(0);
                        }
                    }while (String.valueOf(sb_First).equals("a") && !String.valueOf(sb_Last).equals("b"));

                    if(stringBuilder.length() == 46) {
                        appendedString = String.valueOf(stringBuilder);
                        stringBuilder.setLength(0);
                        finalString = removeFirstandLast(appendedString);
                        System.out.println(finalString);

                        List<String> strings = new ArrayList<String>();
                        int i = 0;
                        while (i < finalString.length()) {
                            strings.add(finalString.substring(i, Math.min(i + 4,finalString.length())));
                            i += 4;
                        }




                        thumb = Float.parseFloat(strings.get(0));
                        index = Float.parseFloat(strings.get(1));
                        middle = Float.parseFloat(strings.get(2));
                        ring = Float.parseFloat(strings.get(3));
                        pinky = Float.parseFloat(strings.get(4));
                        contact1 = Float.parseFloat(strings.get(5));
                        contact2 = Float.parseFloat(strings.get(6));
                        contact3 = Float.parseFloat(strings.get(7));
                        contact4 = Float.parseFloat(strings.get(8));
                        contact5 = Float.parseFloat(strings.get(9));
                        contact6 = Float.parseFloat(strings.get(10));

                        System.out.println("thumb: " + thumb);
                        System.out.println("i: " + index);
                        System.out.println("middle: " + middle);
                        System.out.println("ring: " + ring);
                        System.out.println("pinky: " + pinky);
                        System.out.println("contact1: " + contact1);
                        System.out.println("contact2: " + contact2);
                        System.out.println("contact3: " + contact3);
                        System.out.println("contact4: " + contact4);
                        System.out.println("contact5: " + contact5);
                        System.out.println("contact6: " + contact6);

                        //a
                        if((thumb>=1.00 && thumb<=1.00) && (index>=1.00 && index<=1.00) && (middle>=1.00 && middle<=1.00) && (ring>=1.00 && ring<=1.00) && (pinky>=1.00  && pinky<=1.00)){
                            Message message = Message.obtain();

                            Bundle bundle = new Bundle();
                            bundle.putString("key", "a");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }


                    }



//                    Log.d(TAG, "InputStream: " + incomingMessage);
//                    System.out.println("INPUTSTREAM : " + incomingMessage);

                    //another 1

//                    while(true){
//                        char firstFinal = finalMessage.charAt(0);
//                        char lastFinal = finalMessage.charAt(finalMessage.length()-1);
//                        if(String.valueOf(firstChar).equals("&") && !String.valueOf(lastChar).equals("*")){
//                            finalMessage.append(incomingMessage);
//
//
//                        }else if(String.valueOf(firstFinal).equals("&") && String.valueOf(lastFinal).equals("*")){
//                            finalString = String.valueOf(finalMessage);
//                            finalMessage.setLength(0);
//
//                            String[] array = finalString.split("(?<=\\G...)");
//
//                            thumb = Float.parseFloat(array[0]);
//                            index = Float.parseFloat(array[1]);
//                            middle = Float.parseFloat(array[2]);
//                            ring = Float.parseFloat(array[3]);
//                            pinky = Float.parseFloat(array[4]);
//                            contact1 = Float.parseFloat(array[5]);
//                            contact2 = Float.parseFloat(array[6]);
//                            contact3 = Float.parseFloat(array[7]);
//                            contact4 = Float.parseFloat(array[8]);
//                            contact5 = Float.parseFloat(array[9]);
//                            contact6 = Float.parseFloat(array[10]);
//
//
//                        }else{
//                            //need to try
//                            //finalMessage.setLength(0);
//                        }
//                        break;
//                    }




//                    Intent incomingMessageIntent = new Intent("incomingMessage");
//                    incomingMessageIntent.putExtra("theMessage", incomingMessage);
//                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(incomingMessageIntent);

                } catch (IOException e) {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
//                    break;
                }
            }
        }

        //Call this from the main activity to send data to the remote device
        public void write(byte bytes) {
//            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + bytes);
            try {
                mmOutStream.write(24);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
//     * @param out The bytes to write
//     * @see ConnectedThread#write(byte[])
     */
    public void write(byte out) {
        // Create temporary object
        ConnectedThread r;

        // Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write Called.");
        //perform the write
        mConnectedThread.write(out);
    }

    public static String removeFirstandLast(String str)
    {

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(str);

        // Removing the last character
        // of a string
        sb.deleteCharAt(str.length() - 1);

        // Removing the first character
        // of a string
        sb.deleteCharAt(0);

        // Converting StringBuilder into a string
        // and return the modified string
        return sb.toString();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String text = bundle.getString("key");
            if(text!= temp){
                System.out.println(text);
                Log.d(TAG, "LETTER: " + text);
                Intent incomingMessageIntent = new Intent("incomingMessage");
                incomingMessageIntent.putExtra("theMessage", text);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(incomingMessageIntent);

                temp = text;
            }


            return false;
        }
    });
}
