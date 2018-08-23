package com.rc.gate.server;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 作者：LizG on 2018/8/16 23:16
 * 描述：gate服务端启动器
 */

public class ServerApp {

    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);
    private static File cfg = null;
    private static File log = null;
    private static int gateId;

    public static void main(String[] args) {

    }

    /** 启动配置 服务的ip端口等等的 */
    public void configuration(String[] args) throws ParseException {
        parseArgs(args);

        //TODO 自动配置
        /** 设置网关id*/
        gateId = 1;

        /** <gateserver ip = "0.0.0.0" port = "9090" /> */
        int gateListenPort = 9090;

        /** <auth   ip = "127.0.0.1" port = "8080" /> */
        String authIP = "127.0.0.1";
        int authPort = 8080;

        /** <logic   ip = "127.0.0.1" port = "7070" /> */
        String logicIP = "127.0.0.1";
        int logicPort = 7070;

        /** 启动服务器 */
        new Thread(()->GateServer.start(gateListenPort)).start();

    }

    /** 解析参数 */
    /** 来源：https://github.com/a2888409/face2face */
    static void parseArgs(String[] args) throws ParseException {
        // Create a Parser
        CommandLineParser parser = new BasicParser( );
        Options options = new Options( );
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("c", "cfg", true, "config Absolute Path");
        options.addOption("l", "log", true, "log configuration");

        // Parse the program arguments
        CommandLine commandLine = parser.parse( options, args );
        // Set the appropriate variables based on supplied options

        if( commandLine.hasOption('h') ) {
            printHelpMessage();
            System.exit(0);
        }
        if( commandLine.hasOption('c') ) {
            cfg = new File(commandLine.getOptionValue('c'));
        } else {
            printHelpMessage();
            System.exit(0);
        }
        if( commandLine.hasOption('l') ) {
            log = new File(commandLine.getOptionValue('l'));
        } else {
            printHelpMessage();
            System.exit(0);
        }
    }

    static void printHelpMessage() {
        System.out.println( "Change the xml File and Log.XML Path to right Absolute Path base on your project Location in your computor");
        System.out.println("Usage example: ");
        System.out.println( "java -cfg D:\\MyProject\\face2face\\gate\\src\\main\\resources\\auth.xml  -log D:\\MyProject\\face2face\\gate\\src\\main\\resources\\log.xml");
        System.exit(0);
    }

}
