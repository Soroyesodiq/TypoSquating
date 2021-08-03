import java.util.*;

public class Question2 {

    public static void main(String[] args) {
        System.out.println(typoSquats(new ArrayList<>(Arrays.asList("dipo.com", "dare.com.uk")),
                new ArrayList<>(Arrays.asList("d!pp.com", "dipo.cm", "dipo.t", "dare.com.us", "dar3.com.uk", "dare.com"))));
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

        //Checking for stage 2 of the typosquat

        Set<Character> firstSet = new HashSet<>(Arrays.asList('l', 'i', '1', '!', '|'));
        Set<Character> secondSet = new HashSet<>(Arrays.asList('s', '5', '$'));
        Set<Character> thirdSet = new HashSet<>(Arrays.asList('o', '0'));
        Set<Character> fourthSet = new HashSet<>(Arrays.asList('a', '@'));
        Set<Character> fifthSet = new HashSet<>(Arrays.asList('e', '3'));

        Map<Integer, List<String>> lengthDomains = new HashMap<>();
        for(String domain: realDomains) {
            if(lengthDomains.containsKey(domain.length())) {
                lengthDomains.get(domain.length()).add(domain);
            } else {
                lengthDomains.put(domain.length(), new ArrayList<>(Arrays.asList(domain)));
            }
        }
        boolean isFake = false;
        for(String domain: newDomains) {
            if(!lengthDomains.containsKey(domain.length())) continue;
            for(String realDomain: lengthDomains.get(domain.length())) {
                if(domain.equals(realDomain)) continue;
                isFake = false;
                for (int i = 0; i<realDomain.length(); i++) {
                    if(realDomain.charAt(i) != domain.charAt(i)) {
                        System.out.println(domain.charAt(i));
                        if(firstSet.contains(realDomain.charAt(i)) && firstSet.contains(domain.charAt(i))) {
                            isFake = true;
                        } else if (secondSet.contains(realDomain.charAt(i)) && secondSet.contains(domain.charAt(i))) {
                            isFake = true;
                        } else if (thirdSet.contains(realDomain.charAt(i)) && thirdSet.contains(domain.charAt(i))) {
                            isFake = true;
                        } else if (fourthSet.contains(realDomain.charAt(i)) && fourthSet.contains(domain.charAt(i))) {
                            isFake = true;
                        } else if (fifthSet.contains(realDomain.charAt(i)) && fifthSet.contains(domain.charAt(i))) {
                            isFake = true;
                        } else {
                            isFake = false;
                            break;
                        }

                    }
                }
                if(isFake && !ans.contains(domain)) ans.add(domain);
            }
        }
        return ans;
    }
}
