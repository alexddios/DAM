# Profiling Tutorial

This guide explains how to use the profiling framework in MVCGameEngine.

## 1) Overview

The profiling system is built around:
- AbstractProfiler: base class with metric tracking and reporting.
- MetricType: controls how metrics accumulate.
- ProfileSnapshot: immutable view of metrics for HUDs or logs.

You typically:
1. Create a profiler subclass.
2. Define metrics in configureMetrics().
3. Record values using addValue() or interval helpers.
4. Read metrics via getLastSnapshot() or helper getters.

## 2) Metric types

- INSTANT: overwritten each time. Useful for counters like FPS.
- INTERVAL: measure durations between start/stop marks per period.
- TOTAL: accumulates forever.
- TOTAL_PERIOD: accumulates within the current period.
- AVG_PERIOD: accumulates within the current period and exposes avg.

## 3) Create a profiler

Example: render profiler skeleton.

```java
public class RendererProfiler extends AbstractProfiler {

    public static final String METRIC_DRAW = "RENDER_DRAW";
    public static final String METRIC_FRAME = "RENDER_FRAME";
    public static final String METRIC_UPDATE = "RENDER_UPDATE";

    public RendererProfiler(long reportIntervalNanos) {
        super(reportIntervalNanos);
    }

    @Override
    protected void configureMetrics() {
        addMetric(METRIC_DRAW, MetricType.INTERVAL);
        addMetric(METRIC_FRAME, MetricType.INTERVAL);
        addMetric(METRIC_UPDATE, MetricType.INTERVAL);
    }

    @Override
    protected void customReport() {
        // Optional console output
    }
}
```

## 4) Record metrics

### Interval timing (recommended for durations)

Use startInterval/stopInterval around the work you want to measure.

```java
profiler.startInterval(RendererProfiler.METRIC_UPDATE);
// ... update work ...
profiler.stopInterval(RendererProfiler.METRIC_UPDATE);
```

### Direct accumulation

Use addValue when you already have the elapsed time.

```java
long elapsed = System.nanoTime() - start;
profiler.addValue("PHYSICS_DT", elapsed);
```

## 5) Report and access metrics

Snapshots are created automatically at the end of each reporting period.

```java
ProfileSnapshot snapshot = profiler.getLastSnapshot();
if (snapshot != null) {
    ProfileMetricsDTO draw = snapshot.getSectionMetrics(RendererProfiler.METRIC_DRAW);
}
```

The base class includes helpers:
- getAvgMs(key) returns average milliseconds from the last snapshot.

## 6) Example integration in a loop

```java
// Token-based pattern for thread-safe profiling
long frameStart = profiler.startInterval();

long updateStart = profiler.startInterval();
// update logic
profiler.stopInterval(RendererProfiler.METRIC_UPDATE, updateStart);

long drawStart = profiler.startInterval();
// draw logic
profiler.stopInterval(RendererProfiler.METRIC_DRAW, drawStart);

profiler.stopInterval(RendererProfiler.METRIC_FRAME, frameStart);
// stopInterval() automatically triggers period checks and snapshots
```

## 7) Tips

- Keep metric keys centralized as constants.
- Prefer INTERVAL for time sections measured by start/stop.
- Keep reportIntervalNanos consistent (e.g., 1s or 0.5s).
- Avoid heavy work inside customReport(); use snapshots instead.
