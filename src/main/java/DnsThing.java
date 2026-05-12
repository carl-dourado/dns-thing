import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DnsThing {
    public static void main(String[] args) {
        if (args.length == 0 || hasArg(args, "--help")) {
            printHelp();
            return;
        }

        String domain = args[0];
        boolean jsonOutput = hasArg(args, "--json");
        LookupResult result = resolve(domain);

        if (jsonOutput) {
            System.out.println(result.toJson());
            return;
        }

        printText(result);

        if (!result.ok) {
            System.exit(1);
        }
    }

    private static LookupResult resolve(String domain) {
        long startedAt = System.nanoTime();

        try {
            InetAddress[] addresses = InetAddress.getAllByName(domain);
            List<String> ips = new ArrayList<>();

            for (InetAddress address : addresses) {
                ips.add(address.getHostAddress());
            }

            return new LookupResult(domain, true, ips, elapsedMs(startedAt), null);
        } catch (UnknownHostException error) {
            return new LookupResult(domain, false, List.of(), elapsedMs(startedAt), error.getMessage());
        }
    }

    private static double elapsedMs(long startedAt) {
        long elapsed = System.nanoTime() - startedAt;
        return Math.round((elapsed / 1_000_000.0) * 100.0) / 100.0;
    }

    private static boolean hasArg(String[] args, String expected) {
        return Arrays.asList(args).contains(expected);
    }

    private static void printText(LookupResult result) {
        System.out.printf("dominio: %s%n", result.domain);
        System.out.printf("status: %s%n", result.ok ? "resolveu" : "falhou");
        System.out.printf("tempo: %.2fms%n", result.ms);

        if (!result.ok) {
            System.out.printf("erro: %s%n", result.error);
            return;
        }

        System.out.println("enderecos:");
        for (String ip : result.ips) {
            System.out.printf("  - %s (%s)%n", ip, ip.contains(":") ? "IPv6" : "IPv4");
        }
    }

    private static void printHelp() {
        System.out.println("uso:");
        System.out.println("  java -cp src/main/java DnsThing github.com");
        System.out.println("  java -cp src/main/java DnsThing github.com --json");
    }

    private static class LookupResult {
        private final String domain;
        private final boolean ok;
        private final List<String> ips;
        private final double ms;
        private final String error;

        private LookupResult(String domain, boolean ok, List<String> ips, double ms, String error) {
            this.domain = domain;
            this.ok = ok;
            this.ips = ips;
            this.ms = ms;
            this.error = error;
        }

        private String toJson() {
            StringBuilder builder = new StringBuilder();
            builder.append("{\n");
            builder.append("  \"domain\": \"").append(escape(domain)).append("\",\n");
            builder.append("  \"ok\": ").append(ok).append(",\n");
            builder.append("  \"ms\": ").append(ms).append(",\n");
            builder.append("  \"ips\": [");

            for (int index = 0; index < ips.size(); index++) {
                if (index > 0) {
                    builder.append(", ");
                }
                builder.append("\"").append(escape(ips.get(index))).append("\"");
            }

            builder.append("],\n");
            builder.append("  \"error\": ");
            builder.append(error == null ? "null" : "\"" + escape(error) + "\"");
            builder.append("\n}");
            return builder.toString();
        }

        private String escape(String value) {
            return value.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }
}
