package com.laba3.bigchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message)
    {
        System.out.println(message);

    }

    public static String readString()
    {
        String message = "";
        while (true)
        {
            try
            {
                message = reader.readLine();
                break;
            }
            catch (IOException ignored)
            {
                System.out.println("Произошла ошибка. Попробуйте еще раз.");
            }
        }
        return message;
    }
}
