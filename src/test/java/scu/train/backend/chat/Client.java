//package scu.train.backend.chat;
//
//import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
//
//import java.io.*;
//import java.net.*;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class Client {
//    private Customer customer;
//    private Socket socket;
//    private ServerSocket serverSocket;
//    private final Scanner scanner;
//    private static  final String serverHost = "127.0.0.1";
//    private static final int serverPort = 80;
//    BufferedReader bufferedReader;
//    PrintWriter  printWriter;
//    final Object obj  = new Object();
//
//    public Client() {
//        scanner = new Scanner(System.in);
//        try {
//            serverSocket = new ServerSocket(0);
//            socket = new Socket(serverHost,serverPort);
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            printWriter = new PrintWriter(socket.getOutputStream(),true);
//        } catch (IOException e) {
//            System.out.println("服务器未启动");
//            System.exit(1);
//        }
//    }
////    private void processListen(Socket socket){
////        try {
////            BufferedReader chatReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
////            PrintWriter chatPrinter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
////            String preInfo = chatReader.readLine();
////            Map<String, String> preMap = parseKV(preInfo);
////            System.out.println("有人发起了聊天，他/她的名字是: " + preMap.get("name") + " 账号为: " + preMap.get("count") + ",请输入 1.接收聊天 2.拒绝聊天");
////            int choice = Integer.parseInt(new Scanner(System.in).nextLine());
////            chatPrinter.println(choice);
////            chatPrinter.flush();
////            if (choice == 1) {
////                System.out.println("开始聊天");
////                Thread thread = new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        while(true){
////                            try {
////                                String response = chatReader.readLine();
////                                System.out.println("对方说: "+response);
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }
////                });
////                thread.start();
////                while(true){
////                    String words = new Scanner(System.in).nextLine();
////                    printWriter.println(words);
////                    printWriter.flush();
////                }
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//
//    //该方法用来监听有人发起聊天
//
//
//    private void  listen() {
//        while (true) {
//            try {
//                Socket chatSocket = serverSocket.accept();
//                //被接收后，进行加锁
//                BufferedReader chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream(),StandardCharsets.UTF_8));
//                PrintWriter chatPrinter = new PrintWriter(new OutputStreamWriter(chatSocket.getOutputStream()),true);
//                String preInfo = chatReader.readLine();
//                Map<String, String> preMap = parseKV(preInfo);
//                System.out.println("有人发起了聊天，他/她的名字是: " + preMap.get("name") + " 账号为: " + preMap.get("count") + ",请输入 1.接收聊天 2.拒绝聊天");
//                int choice = Integer.parseInt(new Scanner(System.in).nextLine());
//                chatPrinter.println(choice);
//                chatPrinter.flush();
//                if(choice == 1){
//                    System.out.println("------开始聊天,输入exit以退出-----");
//                    Thread thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while(true){
//                                try {
//                                    String response = chatReader.readLine();
//                                    if(response == null){
//                                        System.out.println("连接已断开");
//                                        break;
//                                    }
//                                    System.out.println("对方说: "+response);
//                                } catch (IOException e) {
//                                    System.out.println("连接已断开");
//                                    break;
//                                }
//                            }
//                        }
//                    });
//                    thread.start();
//                    while(true){
//                        String words = scanner.nextLine();
//                        if(words.equals("exit")){
//                            chatSocket.close();
//                            break;
//                        }
//                        chatPrinter.println(words);
//                        chatPrinter.flush();
//                    }
//                }
//                System.out.println("退出");
//                return;
//            }catch(IOException e){
//                System.out.println("连接断开");
//                break;
//            }
//        }
//    }
//    public void start(){
//        while (true) {
//            System.out.println("*********************");
//            System.out.println("请选择 1.注册 2.登录 3.退出");
//            System.out.println("*********************");
//            int choice = Integer.parseInt(scanner.nextLine());
//            switch(choice){
//                case 1:
//                    if(register(socket)){
//                        in();
//                    }else{
//                        System.out.println("注册失败");
//                    }
//                    break;
//                case 2:
//                    if(login()){
//                        in();
//                    }else{
//                        System.out.println("登陆失败");
//                    }
//                    break;
//                case 3:
//                    return;
//                default:
//                    break;
//            }
//        }
//    }
//    private void in(){
//        while (true) {
//            System.out.println("****登录成功****");
//            System.out.println("选择 1.查询 2.连接 3.接受连接 4.返回");
//            int choice = Integer.parseInt(new Scanner(System.in).nextLine());
//            switch (choice) {
//                case 1:
//                    query();
//                    break;
//                case 2:
//                    connect();
//                    break;
//                case 3:
//                    System.out.println("开始等待连接");
//                    listen();
//                    break;
//                case 4:
//                    return;
//                default:
//                    break;
//            }
//        }
//    }
//
//
//
//    private void connect() {
//        try{
//            System.out.println("请输入你要连接的好友的count");
//            String count = scanner.nextLine();
//            printWriter.println("status=4&count="+count+"\3");
//            printWriter.flush();
//            //接收响应
//            //接收响应
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            String line;
//            StringBuilder stringBuilder = new StringBuilder();
//            while((line = bufferedReader.readLine())!=null && !line.equals("")){
//                if(line.charAt(line.length()-1) == '\3'){
//                    line = line.substring(0,line.length()-1);
//                    stringBuilder.append(line);
//                    break;
//                }
//                stringBuilder.append(line);
//            }
//            String response = stringBuilder.toString();
//            System.out.println("Response:"+response);
//            if(response.equals("") || response.equals("False")){
//                System.out.println("该用户已下线");
//            }else{
//                Map<String,String> map = parseKV(response);
//                System.out.println("目标IP："+map.get("DestIP")+"目标端口:"+map.get("DestPort"));
//                System.out.println("开始连接目标....");
//                try{
//                    Socket chatSocket = new Socket(map.get("DestIP"), Integer.parseInt(map.get("DestPort")));
//                    System.out.println("连接成功,准备开始聊天");
//                    chat(chatSocket);
//                    System.out.println("连接已断开");
//                }catch(IOException e){
//                    System.out.println("连接失败");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void chat(Socket chatSocket) {
//        try {
//            BufferedReader chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream(), StandardCharsets.UTF_8));
//            PrintWriter chatPrinter = new PrintWriter(chatSocket.getOutputStream(), true);
//            chatPrinter.println("count=" + customer.getCount() + "&name=" + customer.getName());
//            Scanner sc = new Scanner(System.in);
//            int choice = Integer.parseInt(chatReader.readLine());
//            if (choice == 1) {
//                System.out.println("-----对方已同意,开始聊天,输入exit退出----");
//                boolean flag =false;
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while(true){
//                            try {
//                                String response = chatReader.readLine();
//                                if(response == null){
//                                    System.out.println("连接已断开");
//                                    break;
//                                }
//                                System.out.println("对方说:"+response);
//                            } catch (IOException e) {
//                                System.out.println("连接已断开2");
//                                return;
//                            }
//                        }
//                    }
//                });
//                thread.start();
//                while (true) {
//                    if(!thread.isAlive()){
//                        return;
//                    }
//                    String words = sc.nextLine();
//                    if(words == null || words.equals("exit")){
//                        chatSocket.close();
//                        System.out.println("连接已断开3");
//                        return;
//                    }
//                    chatPrinter.println(words);
//                    chatPrinter.flush();
//                }
//            }
//        }catch (IOException e ) {
//            System.out.println("连接已断开4");
//        }
//
//    }
//
//
//    private static Map<String,String> parseKV(String string) {
//        Map<String, String> parameters = new HashMap<>();
//        // 1. 按照 & 拆分成多个键值对
//        String[] kvTokens = string.split("&");
//        // 2. 按照 = 拆分每个键和值
//        for (String kv : kvTokens) {
//            String[] result = kv.split("=");
//            parameters.put(result[0], result[1]);
//        }
//        return parameters;
//    }
//
//    private void query() {
//        try{
//            printWriter.println("status=3\3");
//            printWriter.flush();
//            //接收响应
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            String line = "";
//            StringBuilder stringBuilder = new StringBuilder();
//            while((line = bufferedReader.readLine())!=null){
//                if(line.charAt(line.length()-1) == '\3'){
//                    line = line.substring(0,line.length()-1);
//                    stringBuilder.append(line);
//                    break;
//                }
//                stringBuilder.append(line+'\n');
//            }
//            String response = stringBuilder.toString();
//            System.out.println(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private Boolean register(Socket socket) {
//        try{
//            System.out.println("请输入你要注册的账号");
//            String count = scanner.nextLine();
//            System.out.println("请输入你要注册的姓名");
//            String name = scanner.nextLine();
//            System.out.println("请输入你要注册的密码");
//            String password = scanner.nextLine();
//            String body = "status=1&count="+count+"&name="+name+"&password="+password+"&port="+serverSocket.getLocalPort()+"\3";
//            printWriter.println(body);
//            printWriter.flush();
//
//            //接收响应
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            String line = "";
//            StringBuilder stringBuilder = new StringBuilder();
//            while((line = bufferedReader.readLine())!=null){
//                if(line.charAt(line.length()-1) == '\3'){
//                    line = line.substring(0,line.length()-1);
//                    stringBuilder.append(line);
//                    break;
//                }
//                stringBuilder.append(line);
//            }
//            String response = stringBuilder.toString();
//            //
//            if(response.equals("RegisterSuccess")){
//                customer = new Customer(name,count,password,socket.getInetAddress().toString().substring(1), socket.getLocalPort());
//                return true;
//
//            }else if(response.equals("RegisterFalse")){
//                return false;
//            }else{
//                System.out.println("其他错误");
//                return false;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//
//    }
//    private Boolean login(){
//        System.out.println("请输入你的账号");
//        String count = scanner.nextLine();
//        System.out.println("请输入你的密码");
//        String password = scanner.nextLine();
//        printWriter.println("status=2&count="+count+"&password="+password+"&port="+serverSocket.getLocalPort()+'\3');
//
//        try {
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            String line = "";
//            StringBuilder stringBuilder = new StringBuilder();
//            while((line = bufferedReader.readLine())!=null){
//                if(line.charAt(line.length()-1) == '\3'){
//                    line = line.substring(0,line.length()-1);
//                    stringBuilder.append(line);
//                    break;
//                }
//                stringBuilder.append(line);
//            }
//            String response = stringBuilder.toString();
//            if(response.equals("LoginSuccess")){
//               return true;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public static void main(String[] args) {
//        Client client = new Client();
//        client.start();
//    }
//
//
//}
