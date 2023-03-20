//package scu.train.backend.chat;
//
//import java.io.*;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class CenterServer {
//
//    private Map<Customer,UUID> customerList;
//    private ServerSocket serverSocket;
//    private int serverPort;
//
//    public CenterServer() {
//        try {
//            serverPort =80;
//            serverSocket = new ServerSocket(serverPort);
//            customerList = new HashMap<>();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void start(){
//        System.out.println("服务器启动");
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        while(true){
//            try {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("IP= "+clientSocket.getInetAddress().toString().substring(1)+"已上线");
//                executorService.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        process(clientSocket);
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//
//    private Map<String,String> getPara(BufferedReader reader) throws IOException {
//        String line = "";
//        StringBuilder stringBuilder = new StringBuilder();
//        while((line  = reader.readLine()) != null){
//            if(line.charAt(line.length()-1) == '\3'){
//                line = line.substring(0,line.length()-1);
//                stringBuilder.append(line);
//                break;
//            }
//            stringBuilder.append(line);
//        }
//        return parseKV(stringBuilder.toString());
//
//    }
//    private void process(Socket clientSocket) {
//        while (true) {
//            try {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
//                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
//                Map<String,String> paraMap = getPara(bufferedReader);
//
//                if(paraMap.get("status").equals("1")){
//                    //注册
//                    if(Register(paraMap,clientSocket)){
//                        printWriter.println("RegisterSuccess\3");
//                    }else{
//                        printWriter.println("RegisterFalse\3");
//                    }
//
//                }else if(paraMap.get("status").equals("2")){
//                    //登录
//                    if(Login(paraMap,clientSocket)){
//                        printWriter.println("LoginSuccess\3");
//                    }else{
//                        printWriter.println("LoginFault\3");
//                    }
//                }else if(paraMap.get("status").equals("3")){
//                    //查询
//                    String queryResult = query();
//                    printWriter.println(queryResult);
//                }else if(paraMap.get("status").equals("4")){
//                    //连接用户
//                    String DestInfo = connect(paraMap.get("count"));
//                    if(DestInfo!=null){
//                        printWriter.println(DestInfo+"\3");
//                        printWriter.flush();
//                    }else{
//                        printWriter.println("False\3");
//                        printWriter.flush();
//                    }
//                }else if(paraMap.get("status").equals("0")) {
//                    System.exit(0);
//                    System.out.println("IP= "+clientSocket.getInetAddress()+" Port= "+clientSocket.getPort()+"已下线");
//                    return;
//                }
//            } catch (IOException e) {
//                System.out.println("IP= "+clientSocket.getInetAddress()+" Port= "+clientSocket.getPort()+"已下线");
//                break;
//            }
//        }
//
//    }
//
//    private String connect(String count) {
//        for (Map.Entry<Customer,UUID> tem:customerList.entrySet()) {
//            if(tem.getKey().getCount().equals(count)){
//                return "DestIP="+String.valueOf(tem.getKey().getIP())+"&DestPort="+tem.getKey().getPort();
//            }
//        }
//        return null;
//    }
//
//    private String query() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Map.Entry<Customer,UUID> tem:customerList.entrySet()) {
//            stringBuilder.append("count:"+tem.getKey().getCount()+" Name: "+tem.getKey().getName()+"\n");
//        }
//        String result = stringBuilder.toString();
//        result = result.substring(0,result.length()-1);
//        result += "\3";
//        return result;
//    }
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
//    private Boolean Login(Map<String,String> paraMap,Socket socket){
//        String count = paraMap.get("count");
//        String name = paraMap.get("name");
//        String password = paraMap.get("password");
//        int port = Integer.parseInt(paraMap.get("port"));
//        String ip = socket.getInetAddress().toString().substring(1);
//        Customer customer = new Customer(name,count,password,ip,port);
//        for (Map.Entry<Customer,UUID> tem:customerList.entrySet()) {
//            if(tem.getKey().getCount().equals(customer.getCount()) && tem.getKey().getPassword().equals(customer.getPassword())){
//                tem.getKey().setIP(customer.getIP());
//                tem.getKey().setPort(customer.getPort());
//                tem.setValue(UUID.randomUUID());
//                return true;
//            }
//        }
//        return false;
//    }
//    private Boolean Register(Map<String,String> paraMap,Socket socket) {
//        String count = paraMap.get("count");
//        String name = paraMap.get("name");
//        String password = paraMap.get("password");
//        int port = Integer.parseInt(paraMap.get("port"));
//        String ip = socket.getInetAddress().toString().substring(1);
//        System.out.println(ip);
//        Customer customer = new Customer(name,count,password,ip,port);
//        for (Map.Entry<Customer,UUID> tem:customerList.entrySet()) {
//            if(tem.getKey().getCount().equals(customer.getCount()) ){
//                return false;
//            }
//        }
//        customerList.put(customer,UUID.randomUUID());
//        return true;
//    }
//
//    public static void main(String[] args) {
//        CenterServer  centerServer = new CenterServer();
//        centerServer.start();
//    }
//
//}
