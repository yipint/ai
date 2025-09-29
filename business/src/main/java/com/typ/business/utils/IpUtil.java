package com.typ.business.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public final class IpUtil {

    private IpUtil() {
    }

    public static String getLocalIpv4() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces == null) {
                return getFallbackLocalIp();
            }
            String candidate = null;
            while (interfaces.hasMoreElements()) {
                NetworkInterface network = interfaces.nextElement();
                if (!isUsableNetwork(network)) {
                    continue;
                }
                Enumeration<InetAddress> addresses = network.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        String ip = address.getHostAddress();
                        if (address.isSiteLocalAddress()) {
                            return ip;
                        }
                        if (candidate == null) {
                            candidate = ip;
                        }
                    }
                }
            }
            return candidate != null ? candidate : getFallbackLocalIp();
        } catch (SocketException e) {
            return getFallbackLocalIp();
        }
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }

    private static boolean isUsableNetwork(NetworkInterface network) throws SocketException {
        return network.isUp() && !network.isLoopback() && !network.isVirtual();
    }

    private static String getFallbackLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
}


