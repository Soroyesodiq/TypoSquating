import java.util.*;

public class Question1 {
    public static void main(String[] args) {
        System.out.println(typoSquats(new ArrayList<>(Arrays.asList("dipo.com", "dare.com.uk")),
                new ArrayList<>(Arrays.asList("dipo.cm", "dipo.t", "dare.com.us", "dare.com.uk", "dare.com"))));
    }

    public static List<String> typoSquats(List<String> realDomains, List<String> newDomains) {
        List<String> ans = new ArrayList<>();
        if(realDomains.size() == 0 || newDomains.size() == 0) {
            return ans;
        }

        Map<String, List<String>> domains = new HashMap<>();
        for(String domain: realDomains) {
            String name = domain.substring(0, domain.indexOf('.'));
            String host = domain.substring(domain.indexOf('.')+1);
            List<String> hosts = new ArrayList<>();
            while (host.indexOf('.') != -1) {
                hosts.add(host.substring(0, host.indexOf('.')));
                host = host.substring(host.indexOf('.')+1);
            }
            hosts.add(host);
            domains.put(name, hosts);
        }
        for(String domain: newDomains) {
            String name = domain.substring(0, domain.indexOf('.'));
            if(!domains.containsKey(name)) {
                continue;
            }
            List<String> realHosts = domains.get(name);
            String host = domain.substring(domain.indexOf('.') + 1);
            List<String> hosts = new ArrayList<>();
            while (host.indexOf('.') != -1) {
                hosts.add(host.substring(0, host.indexOf('.')));
                host = host.substring(host.indexOf('.')+1);
            }
            hosts.add(host);
            if(realHosts.size() != hosts.size()) {
                ans.add(domain);
                //newDomains.remove(domain);
                continue;
            }
            for(int i = 0; i < hosts.size(); i++) {
                if(!realHosts.get(i).equals(hosts.get(i))) {
                    ans.add(domain);
                    //newDomains.remove(domain);
                    break;
                }
            }

        }

        return ans;
    }
}
