package com.example.navee.smartroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by navee on 6/6/2017.
 */

public class AsyncTask extends android.os.AsyncTask<String,String,String> {
    Socket socket;
    InputStream input;
    @Override
    protected String doInBackground(String... params) {
        String modifiedSentence = "hello";
        Socket clientSocket;
        try {
            clientSocket = new Socket("192.168.1.100", 8081);


            DataOutputStream outToServer = new DataOutputStream(
                    clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes("hello world");

            modifiedSentence =
                    (inFromServer.readLine());

            clientSocket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return modifiedSentence;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

