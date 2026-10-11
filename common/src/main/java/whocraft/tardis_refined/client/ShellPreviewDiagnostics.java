package whocraft.tardis_refined.client;

import whocraft.tardis_refined.TardisRefined;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** Change-only logging for tracing shell appearance through preview render paths. */
public final class ShellPreviewDiagnostics {
    private static final ConcurrentMap<String, String> LAST_REPORTED = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Long> LAST_TIMED_REPORT = new ConcurrentHashMap<>();

    private ShellPreviewDiagnostics() {}

    public static void report(String path, String details) {
        String previous = LAST_REPORTED.put(path, details);
        if (!details.equals(previous)) {
            TardisRefined.LOGGER.info("Shell preview [{}]: {}", path, details);
        }
    }

    /** Logs a changing animation snapshot at most once per interval per path. */
    public static void reportEvery(String path, String details, long intervalMillis) {
        long now = System.currentTimeMillis();
        Long previous = LAST_TIMED_REPORT.get(path);
        if (previous == null || now - previous >= intervalMillis) {
            LAST_TIMED_REPORT.put(path, now);
            TardisRefined.LOGGER.info("Shell preview [{}]: {}", path, details);
        }
    }
}
