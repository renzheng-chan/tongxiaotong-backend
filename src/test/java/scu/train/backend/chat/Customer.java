//package scu.train.backend.chat;
//
//import java.net.InetAddress;
//import java.util.Objects;
//
//public class Customer{
//    private String Name;
//    private String Count;
//    private String password;
//    private String IP;
//    private int port;
//
//    public Customer(String name, String count, String password, String IP, int port) {
//        Name = name;
//        Count = count;
//        this.password = password;
//        this.IP = IP;
//        this.port = port;
//    }
//
//    public String getIP() {
//        return IP;
//    }
//
//    public void setIP(String IP) {
//        this.IP = IP;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getCount() {
//        return Count;
//    }
//
//    public void setCount(String count) {
//        Count = count;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return Objects.equals(this.Count, ((Customer) obj).Count);
//    }
//}
